/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.midideconstructor;

import apollo.chordbase.ChordDatabase;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.MidiCommand;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.NoteOff;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.NoteOn;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.RawMidiMessageParser;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.SetTempo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Track;

/**
 *
 * @author Talonos
 */
public class Deconstructor 
{
    static ChordDatabase d = new ChordDatabase();
    
    static int samplesPerBeat = CONSTS.SAMPLES_PER_BEAT;
    static int beatsPerSegment = CONSTS.BEATS_PER_SEGMENT;
    
    public DeconstructedPossibility deconstruct(Track[] tracks, double ppq)
    {
        //Have a bunch of weights for different points in the song.
        //Array, because there is also a track (First) and a tailsize (second)
        Map<Integer, double[]>[][] songWeights = new TreeMap[tracks.length][4];
        
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < tracks.length; x++)
            {
                songWeights[x][y] = new TreeMap<>();
            }
        }
        
        //This hold what notes are currently being held down, which helps us calculate
        //the song weights.
        Map<Integer, Integer> currentlyHeldNotes = new TreeMap<>();
    
        //Prepare the buffers.
        int endOfSong = CONSTS.END_OF_SONG;
        for (int z = 0; z < endOfSong+ppq; z+=((int)ppq/samplesPerBeat))
        {
            for (int y = 0; y < 4; y++)
            {
                for (int x = 0; x < tracks.length; x++)
                {
                    //Every 16th of a quarter note, we take a sample.
                    songWeights[x][y].put(z, new double[12]);
                }
            }
        }
        
        //Use the library I found to make something that changes midi bytecode into
        //java classes.
        RawMidiMessageParser midiParser = new RawMidiMessageParser();
        for (int i = 0; i < tracks.length; i++)
        {
            System.out.println("Track "+i+": "+tracks[i].size()+" events.");
            for (int j = 0; j < tracks[i].size(); j++)
            {
                MidiEvent e = tracks[i].get(j);
                MidiMessage m = e.getMessage();
                MidiCommand c = midiParser.parse(m);
                if (c instanceof NoteOn)
                {
                    NoteOn noteOn = (NoteOn)c;
                    if (noteOn.getVelocity() == 0)
                    {
                        handleNoteRelease(currentlyHeldNotes, songWeights[i], noteOn.getNoteNumber(), (int)e.getTick(), (int)ppq/samplesPerBeat, (int)ppq, endOfSong);
                    }
                    currentlyHeldNotes.put(noteOn.getNoteNumber(), (int)e.getTick());
                }
                if (c instanceof NoteOff)
                {
                    NoteOff noteOff = (NoteOff)c;
                    handleNoteRelease(currentlyHeldNotes, songWeights[i], noteOff.getNoteNumber(), (int)e.getTick(), (int)ppq/samplesPerBeat, (int)ppq, endOfSong);
                }
            }
        }
        
        //Once we get to here, songWeights is full. Every ppq/16 step has a map entry with
        //a set of song weights.
        DeconstructedPossibility toReturn = new DeconstructedPossibility();
        int pulsesThisSegmentSoFar = 0;
        List<ChordConfidences> segment = new ArrayList<>();
        int modesPicked[] = new int[4];
        for (int pulses = 0; pulses <= endOfSong; pulses+= ppq/samplesPerBeat)
        {
            //Get four different confidences.
            double[][] weights = assembleSongWeightsArray(songWeights, pulses);
            
            //Pick the best.
            double bestSoFar = -1;
            ChordConfidences cc = null;
            int modePicked = -1;
            for (int mode = 0; mode < 4; mode++)
            {
                ChordConfidences proposedCC = d.getChordWithConfidence(weights[mode]);
                if (proposedCC.confidence > bestSoFar)
                {
                    cc = proposedCC;
                    bestSoFar = cc.confidence;
                    modePicked = mode;
                }
            }
            modesPicked[modePicked]++;
            segment.add(cc);
            //If we're at the end of a segment, save it.
            pulsesThisSegmentSoFar++;
            if (pulsesThisSegmentSoFar%(samplesPerBeat*beatsPerSegment)==0)
            {
                toReturn.halfMeasures.add(segment);
                segment = new ArrayList<>();
                pulsesThisSegmentSoFar = 0;
            }
        }
        
        System.out.println("Most successful modes:");
        System.out.println("  "+modesPicked[0]+", "+modesPicked[1]+", "+modesPicked[2]+", "+modesPicked[3]);
        return toReturn;
    }

    private static void handleNoteRelease(Map<Integer, Integer> currentlyHeldNotes, Map<Integer, double[]>[] songWeights, int noteNumber, int endTime, int timeStep, int ppq, int endOfSong)
    {
        for (int x = 0; x < 4; x++)
        {
            handleNoteRelease(currentlyHeldNotes, songWeights[x], noteNumber, endTime, timeStep, ppq, endOfSong, x);
        }
        currentlyHeldNotes.remove(noteNumber);
    }
    
    
    
    private static void handleNoteRelease(Map<Integer, Integer> currentlyHeldNotes, Map<Integer, double[]> songWeights, int noteNumber, int endTime, int timeStep, int ppq, int endOfSong, int mode) 
    {
        double extendRatio = .82;
        double previousRatio = .5;
        switch (mode)
        {
            case 0:
                extendRatio = .6;
                previousRatio = .3;
                break;
            case 1:
                extendRatio = .8;
                previousRatio = .4;
                break;
            case 2:
                extendRatio = .87;
                previousRatio = .5;
                break;
            case 3:
                extendRatio = .94;
                previousRatio = .6;
                break;
        }
        
        //Make sure we're actually holding the key down.
        if (!currentlyHeldNotes.keySet().contains(noteNumber))
        {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe("A note at "+endTime+" was released before ever being held down!");
            return;
        }
        
        //Get the sstart time, and remove the held note. (It's been released now.)
        int startTime = currentlyHeldNotes.get(noteNumber);
        
        //Figure out the pitch
        int pitch = noteNumber%12;
        
        //Truncate both to the nearest time step.
        startTime = (startTime/timeStep)*timeStep;
        endTime = (endTime/timeStep)*timeStep;
        
        //Figure out how much this note should "weigh." number of pulses / pulsesPerQuarter = number of quarter notes.
        double numberOfQuarterNotes = (double)(endTime-startTime)/(double)ppq;
        
        double geosums = ((extendRatio/(1-extendRatio))+(previousRatio/(1-previousRatio)))/samplesPerBeat;
        
        //System.out.println("Geosums: "+geosums);
        
        double noteWeightMultiplier = numberOfQuarterNotes/(geosums+numberOfQuarterNotes);
        noteWeightMultiplier *= 100.0;
        
        //As long as the note is being held down, contribute the full note weight.
        for (int songPosition = (int)startTime; songPosition <= endTime; songPosition+=timeStep)
        {
            try
            {
                songWeights.get(songPosition)[pitch] += noteWeightMultiplier;
            }
            catch (Exception e)
            {
                System.out.println(songPosition+", "+pitch);
                System.exit(-1);
            }
        }
        
        //Go back in time, contributing a little of your chord to the past.
        double adjustedWeight = 1;
        for (int songPosition = ((int)startTime-timeStep); songPosition >=0; songPosition-=timeStep)
        {
            adjustedWeight *= previousRatio;
            songWeights.get(songPosition)[pitch] += (noteWeightMultiplier*adjustedWeight);
        }
        
        //Go forward in time, contributing a little more of your chord to the future.
        adjustedWeight = 1;
        for (int songPosition = ((int)endTime+timeStep); songPosition <=endOfSong+timeStep; songPosition+=timeStep)
        {
            adjustedWeight *= extendRatio;
            songWeights.get(songPosition)[pitch] += (noteWeightMultiplier*adjustedWeight);
        }
        
    }

    private double[][] assembleSongWeightsArray(Map<Integer, double[]>[][] songWeights, int pulses) 
    {
        double[][] toReturn = new double[4][12];
        for (int x = 0; x < songWeights.length; x++)
        {
            for (int pitch = 0; pitch < 12; pitch ++)
            {
                for (int mode = 0; mode < 4; mode++)
                {
                    //This is the ugliest line of code I have written in forever. :(
                    toReturn[mode][pitch] += songWeights[x][mode].get(pulses)[pitch];
                }
            }
        }
        return toReturn;
    }
}
