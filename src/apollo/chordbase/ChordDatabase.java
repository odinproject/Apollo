/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.chordbase;

import apollo.midideconstructor.ChordConfidences;
import java.util.Set;

/**
 *
 * @author Talonos
 */
public class ChordDatabase 
{
    /**
     * A lookup table, so you can pull any chord from the database specifically.
     */
    protected Chord[][]_chordLibrary;
    
    /**
     * A lookup table, so you can pull any ChordNode from the database specifically.
     */
    protected ChordNode[][]_chordNodeLibrary;
    
    /**
     * Creates a new chord database, populating it with chords, adding 
     * transitions between those chords, and then pruning those transitions.
     */
    
    protected ChordNode _currentChordNode;
    private double confidence;
    
    public ChordDatabase()
    {
        //Creata a library of chords.
        _chordLibrary = new Chord[12][9];
        _chordNodeLibrary = new ChordNode[12][9];
        
        //Populate the C chords:
        // - C major
        Chord cMajor = new Chord();
        cMajor.addNote(0);
        cMajor.addNote(4);
        cMajor.addNote(7);
        cMajor.rename("C major");
        _chordLibrary[0][0] = cMajor;
        // - C minor
        Chord cMinor = new Chord();
        cMinor.addNote(0);
        cMinor.addNote(3);
        cMinor.addNote(7);
        cMinor.rename("C minor");
        _chordLibrary[0][1] = cMinor;
        // - C 7
        Chord c7 = new Chord();
        c7.addNote(0);
        c7.addNote(4);
        c7.addNote(7);
        c7.addNote(10);
        c7.rename("C seven");
        _chordLibrary[0][2] = c7;
        // - C 7 Major
        Chord c7Major = new Chord();
        c7Major.addNote(0);
        c7Major.addNote(4);
        c7Major.addNote(7);
        c7Major.addNote(11);
        c7Major.rename("C 7 major");
        _chordLibrary[0][3] = c7Major;
        // - C 7 Minor
        Chord c7Minor = new Chord();
        c7Minor.addNote(0);
        c7Minor.addNote(3);
        c7Minor.addNote(7);
        c7Minor.addNote(10);
        c7Minor.rename("C 7 minor");
        _chordLibrary[0][4] = c7Minor;
        // - C diminished;
        Chord cDiminished = new Chord();
        cDiminished.addNote(0);
        cDiminished.addNote(3);
        cDiminished.addNote(6);
        cDiminished.addNote(9);
        cDiminished.rename("C diminished");
        _chordLibrary[0][5] = cDiminished;
        // - C augmented
        Chord cAugmented = new Chord();
        cAugmented.addNote(0);
        cAugmented.addNote(4);
        cAugmented.addNote(8);
        cAugmented.rename("C augmented");
        _chordLibrary[0][6] = cAugmented;
        // - C half diminished
        Chord cHalfDiminished = new Chord();
        cHalfDiminished.addNote(0);
        cHalfDiminished.addNote(3);
        cHalfDiminished.addNote(6);
        cHalfDiminished.addNote(10);
        cHalfDiminished.rename("C half-diminished");
        _chordLibrary[0][7] = cHalfDiminished;
        // - C minor-major
        Chord cMinorMajor = new Chord();
        cMinorMajor.addNote(0);
        cMinorMajor.addNote(3);
        cMinorMajor.addNote(7);
        cMinorMajor.addNote(11);
        cMinorMajor.rename("C minor-major");
        _chordLibrary[0][8] = cMinorMajor;
        
        //Transpose chords to fill out the rest of the library.
        for (int pitch = 0; pitch < 12; pitch++)
        {
            for (int chordNum = 0; chordNum < 9; chordNum++)
            {
                _chordLibrary[pitch][chordNum] = new Chord(_chordLibrary[0][chordNum], pitch);
                _chordLibrary[pitch][chordNum].rename(getPitchName(pitch)+" "+getChordName(chordNum));
                _chordLibrary[pitch][chordNum].setTone(pitch);
                _chordLibrary[pitch][chordNum].setChordType(chordNum);
            }
        }
        //With the library full, we now wrap the chords in their respective nodes.
        
        for (int pitch = 0; pitch < 12; pitch ++)
        {
            for (int chordNum = 0; chordNum < 9; chordNum++)
            {
                _chordNodeLibrary[pitch][chordNum] = new ChordNode(_chordLibrary[pitch][chordNum]);
            }
        }
        
        //With the library full, we now make transitions between chords.
        //This adds every possible chord as a transition to every possible chordnode.
        //TODO: Wither prune afterwards or check sanity of a chord before adding it.
        for (int spitch = 0; spitch < 12; spitch ++)
        {
            for (int schordNum = 0; schordNum < 9; schordNum++)
            {
                for (int dpitch = 0; dpitch < 12; dpitch ++)
                {
                    for (int dchordNum = 0; dchordNum < 9;dchordNum++)
                    {
                        _chordNodeLibrary[spitch][schordNum].addTransition(_chordNodeLibrary[dpitch][dchordNum]);
                    }
                }
            }
        }
        
        //Initialize to C major.
        _currentChordNode = _chordNodeLibrary[0][0];
    }
    
// C       C#      D       D#      E       F       F#      G       G#      A       A#      B
// 0	   1       2       3       4       5       6       7       8       9       10      11

    /**
     * Gets a chord by absolute number. All major chords are 0-11, then all minor 
     * chords are 12+(0 to 11), etc.
     * @param i the chord to get.
     * @return the chord requested.
     */
    public Chord getChordByNumber(int i) 
    {
        System.out.println("Returning: "+(i%12)+", "+(i/12));
        return _chordLibrary[i%12][i/12];
    }
    
    /**
     * Gets a chord by the ChordDatabase index for pitch and type
     * @param pitch C, D, E, F#...
     * @param type Major, Minor, ...
     * @return 
     */
    public Chord getChordByPitchAndType(int pitch, int type)
    {
        return _chordLibrary[pitch][type];
    }

    /**
     * Gets another chord from the database.
     * @param emotiveState a number to determine chord transitions.
     * @return 
     */
    public Chord getNextChord(int emotiveState) 
    {
        _currentChordNode = _currentChordNode.getTransition(emotiveState);
        return _currentChordNode.getChord();
    }
    
    /**
     * Given a set of pitch weights, tries to identify what chord the weights represent 
     * and return the most accurate chord..
     * @param weights an array of 12 weights, starting at the weight for "c" and 
     * going up in half-steps from there..
     * @return the chord it fits most. Null if given the zero vector as weights. Undefined
     * if the weights can represent multiple chords. TODO: Fix that.
     */
    public Chord identifyChord(double[] weights)
    {
        //Sanity check.
        if (weights.length != 12)
        {
            throw new IllegalArgumentException("You must pass 12 weights to the "
                    + "identifyChord function. No more, no less.");
        }
        //Normalize the weights. (Turn it into a unit vector)
        double runningTally = 0;
        for (int i = 0; i < 12; i++)
        {
            runningTally+=weights[i]*weights[i];
        }
        if (runningTally == 0)
        {
            //No chord here, so don't bother returning one.
            return null;
        }
        for (int i = 0; i < 12; i++)
        {
            weights[i]/= Math.sqrt(runningTally);
        }
        
        //Check each chord against the weights we have.
        Chord bestChordSoFar = null;
        double bestSimilaritySoFar = -1;
        
        for (int chordPitch = 0; chordPitch < 12; chordPitch++)
        {
            for (int chordType = 0; chordType < 9; chordType++)
            {
                Chord chordImLookingAt = _chordLibrary[chordPitch][chordType];
                Set<Integer> pitchesImLookingAt = chordImLookingAt.getNotes();
                double[] chordWeights = new double[12];
                //Hardcoded normalization:
                double weightAmount = (pitchesImLookingAt.size()==3?0.577350269:.5);
                for (Integer i : pitchesImLookingAt)
                {
                    chordWeights[i] = weightAmount;
                }
                //run a cosine similarity between the two vectors:
                double similarity = 0;
                for (int i = 0; i < 12; i++)
                {
                    similarity+= chordWeights[i]*weights[i];
                }
                //Check to see if this is better than what we have:
                if (similarity > bestSimilaritySoFar)
                {
                    bestChordSoFar = chordImLookingAt;
                    bestSimilaritySoFar = similarity;
                }
            }
        }
        confidence = bestSimilaritySoFar;
        return bestChordSoFar;
    }

    /**
     * Returns a string associated with a given pitch
     * @param pitch the pitch to get the string for.
     * @return the Note-letter associated with the given pitch.
     */
    private String getPitchName(int pitch) 
    {
        switch(pitch)
        {
            case 0:
                return "C";
            case 1:
                return "C#";
            case 2:
                return "D";
            case 3:
                return "D#";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "F#";
            case 7:
                return "G";
            case 8:
                return "G#";
            case 9:
                return "A";
            case 10:
                return "A#";
            case 11:
                return "B";
            default:
                return "Broken";
        }
    }

    /**
     * Returns a string describing a given chord type.
     * @param chordNum the type of chord, as defined by the chord library.
     * @return a string containing the name of the chord type.
     */
    private String getChordName(int chordNum) 
    {
        switch(chordNum)
        {
            case 0:
                return "major";
            case 1:
                return "minor";
            case 2:
                return "seven";
            case 3:
                return "7 major";
            case 4:
                return "7 minor";
            case 5:
                return "diminished";
            case 6:
                return "augmented";
            case 7:
                return "half-diminished";
            case 8:
                return "minor-major";
            default:
                return "broken";
        }
    }

    public double getConfidence() 
    {
        return confidence;
    }

    public ChordConfidences getChordWithConfidence(double[] weights) 
    {
        ChordConfidences toReturn = new ChordConfidences();
        toReturn.chord = this.identifyChord(weights);
        toReturn.confidence = confidence;
        return toReturn;
    }
}
