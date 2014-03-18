/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.composer;

import apollo.chordbase.BrennanChordDatabase;
import apollo.chordbase.CheatChordDatabase;
import apollo.chordbase.Chord;
import apollo.chordbase.ChordDatabase;
import apollo.learning.ChordWeights;
import apollo.learning.Scribe;
import apollo.orchestra.Bar;
import apollo.orchestra.Note;
import apollo.orchestra.Orchestra;
import apollo.orchestra.Tick;
import game.GameProperties;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Martin
 */
public class Composer {
    
    private Orchestra orchestra;
    private GameProperties properties;
    
    // sometimes we need to place stops in the 'yet-to-be-generated' next bar
    // this meta-bar carries information about stops that need to be accounted
    // for when composing the next bar
    private Bar nextBarStops;
    
    // the pitch of the current chord (A, B, C#...), expressed as an integer
    // with the same format as the ChordDatabase (C = 0, C# = 1, D = 2...)
    // Ranges from 0 to 11;
    private int currentChordPitch;
    // the type of the current chord (Major, Minor...), expressed as an integer
    // with the same format as the ChordDatabase (Major = 0, Minor = 1...)
    // ranges from 0 to 9
    private int currentChordType;
    
    // the current energy level of the game
    private double currentEnergy;
    
    // the current tension level of the game
    private double currentTension;
    
    // toggles whether the rhythm should be played
    private boolean rhythmOn;
    // togggles whether the chords should be randomly or predictively selected
    private boolean randomOn;
    
    // File I/O
    private Scribe scribe;
    
    // The actual weights for all possible chord transitions
    // this contains (among other things) a four-dimensional array called weights
    // [A][B][C][D]
    // "A" represents which predictor we are looking at: 0 = Energy, 1 = Tension
    // "B" represents the start chord type (0 = Major, 1 = Minor, etc...)
    // "C" represents the pitch shift. This ranges from 0 to 22 (which represents
    //   the shift ranges -11 through 11. 0 = -11, 1 = -10, w = -9...
    // "D" represents the end chord type (0 = Major, 2 = Minor, etc...)
    //   The value in the "D" entry is the average energy (or intensity) for that
    //   chord transition
    private ChordWeights weights;
    
    // This is just a copy of the old cheatChordList
    private CheatChordDatabase cheatSheet;
    // A reference to the chordDatabase to lookup chords for a given pitch/type 
    // on demand
    private BrennanChordDatabase database;
    
    private RhythmDatabase rhythmDatabase;
   
    public final static Integer MIDDLE_C_OFFSET = 60;
    
    /**
     * Constructor
     * @param o the orchestra to which the music will be sent
     */
    public Composer (Orchestra o)
    {
        rhythmOn = true;
        randomOn = false;
        
        orchestra = o;
        cheatSheet = new CheatChordDatabase();
        database = new BrennanChordDatabase();
        rhythmDatabase = new RhythmDatabase();
        cheatSheet.setSongNumber(2);
        
        // this is currently UNUSED. I will be expanding this soon.
        nextBarStops = new Bar();
        for (int i=0; i<16; i++)
        {
            Tick emptyTick = new Tick();
            nextBarStops.addTick(i, emptyTick);
        }
        // end of UNUSED code.
        
        // initialize our File I/O helper
        scribe = new Scribe();
        
        // right now we start with whatever chord is first in the cheat sheet
        // although this could be any chord you want.
        Chord c = nextChordFromCheatData();
        orchestra.addBarToTrack(1, barForChord(c));
        
        currentEnergy = 0.5;
        currentTension = -1.0;
        
        currentChordPitch = 2;
        currentChordType = 1;
        
        // upload the serialized weights that we learned earlier
        weights = scribe.loadWeightsFromFile("chordweights.txt");
        
        
    }
    
    /**
     * Get a link to the game's properties, which we may query at any time
     * @param prop the link to the game's properties
     */
    public void setGameProperties(GameProperties prop)
    {
        properties = prop;
    }
    
    public void update()
    {
        if (orchestra.unplayedBarsForTrack(1)< 1)
        {
            // get cheat chords
            Chord c = getRandomChord();
            if (!randomOn)
            {
                //c = nextChordForCurrentFactors();
                c = nextChordFromBrennanDatabase();
            }
            orchestra.addBarToTrack(1, barForChord(c));
        }
        
        // switches tension depending on whether the player is in the cave or forest
        if (properties.getPlayerXPosition() > -1333.0)
        {
            currentTension = -1.0;
        }
        else
        {
            currentTension = 1.0;
        }
        
        // depending on speed, change rhythm between the 3 pre-generated rhythms
        if (orchestra.unplayedBarsForTrack(2)< 1 && properties != null && rhythmOn)
        {
            // get rhythms
            double speed = Math.abs(properties.getPlayerSpeed());
            Bar nextRhythm = new Bar();
            if (speed > 2)
            {
                nextRhythm = intenseRhythm();
            }
            else if (speed < 2 && speed > 0)
            {
                nextRhythm = mediumRhythm();
            }
            else if (speed == 0)
            {
                nextRhythm = simpleRhythm();
            }
            orchestra.addBarToTrack(2, nextRhythm);
        }
    }
    
    /**
     * Returns a random chord from the chord database
     * @return Chord a random chord
     */
    public Chord getRandomChord()
    {
        Random rand = new Random();
        int randomPitchIndex = rand.nextInt(12);
        int randomTypeIndex = rand.nextInt(9);
        return database.getChordByPitchAndType(randomPitchIndex, randomTypeIndex);
    }
    
    /**
     * Our Dynamic Chord Selector
     * @return Chord the best possible chord for the current game state
     */
    public Chord nextChordForCurrentFactors()
    {   
        Double[][][][] chordWeights = weights.getWeights();
        
        // get the type and relative pitch transition that maximizes tension
        int closestToTypeIndex = 0;
        int closestRelativePitchIndex = 0;
        Double closestScore = 10000.0;
        
        int secondClosestToTypeIndex = 0;
        int secondClosestRelativePitchIndex = 0;
        Double secondClosestScore = 20000.0;
            
        for (int relativePitchIndex=0; relativePitchIndex<22; relativePitchIndex++)
        {
            for (int toTypeIndex=0; toTypeIndex<8; toTypeIndex++)
            {
                double score = chordWeights[1][currentChordType][relativePitchIndex][toTypeIndex];
                if (score == 0.0)
                {
                    score = 1000.0;
                }
                // only tension for now
                if (Math.abs(score - currentTension) < closestScore)
                {
                    secondClosestScore = closestScore;
                    secondClosestToTypeIndex = closestToTypeIndex;
                    secondClosestRelativePitchIndex = closestRelativePitchIndex;
                    
                    closestScore = Math.abs(score - currentTension);
                    closestToTypeIndex = toTypeIndex;
                    closestRelativePitchIndex = relativePitchIndex;
                }
            }
        }
        // look in ChordLibrary
        
        int relativePitch = transposePitch(currentChordPitch, closestRelativePitchIndex - 11);
        int secondRelativePitch = transposePitch(currentChordPitch, secondClosestRelativePitchIndex - 11);
        
        Random rand = new Random();
        int closest = rand.nextInt(3);

        Chord nextChord = database.getChordByPitchAndType(relativePitch, closestToTypeIndex);
        currentChordPitch = relativePitch;
        currentChordType = closestToTypeIndex;
        if (closest == 1)
        {
            nextChord = database.getChordByPitchAndType(secondRelativePitch, secondClosestToTypeIndex);
            currentChordPitch = secondRelativePitch;
            currentChordType = secondClosestToTypeIndex;
        }
        
        System.out.println(nextChord);
        
        return nextChord;
    }
    
    /**
     * Given a pitch, and a relative shift ammount, find the new pitch
     * @param pitch int representation of pitch (C = 0, C# = 1, D = 2...)
     * @param shift int representation of pitch shift, positive or negative
     * @return 
     */
    public int transposePitch(int pitch, int shift)
    {
        int transpose = pitch + shift;
        if (transpose < 0)
        {
            transpose += 12;
        }
        else if (transpose > 11)
        {
            transpose -= 12;
        }
        
        return transpose;
    }
    
    /**
     * Create a new bar based on a chord
     * @param c a chord
     * @return Bar a bar representation of the input chord
     */
    public Bar barForChord(Chord c)
    {
        Bar newBar = new Bar();
        Set<Integer> notes = c.getNotes();
        
        // fill newBar with empty Ticks
        for (int i=0; i<16; i++)
        {
            Tick emptyTick = new Tick();
            newBar.addTick(i, emptyTick);
        }
        
        // we limit the chord to a melodic range of MIDDLE_C +- 12
        // it ends up sounding identical to the gaussian anyways
        int melodicRange = 12;
        
        for (int pitch = 0; pitch < 128; pitch++)
        {
            if (notes.contains(pitch%12) && pitch > MIDDLE_C_OFFSET - melodicRange 
                                         && pitch < MIDDLE_C_OFFSET + melodicRange)
            {
                Note n = new Note((short)pitch, (short)75);
                newBar.getTick(0).addNote(n);
            }
            
            Note stopNote = new Note((short)pitch, (short)100);
            newBar.getTick(0).addStop(stopNote);
        }
        
        return newBar;
    }
    
    /**
     * @return Chord: the next Chord in the cheat database if it exists,
     * otherwise an empty Chord with no notes
     */
    public Chord nextChordFromCheatData()
    {
        if (cheatSheet != null)
        {
            return cheatSheet.getNextChord(0);
        }
        else
        {
            return new Chord();
        }
    }
    
    /**
     * Create a random rhythm bar
     * @return Bar a bar containing a random rhythm
     * WARNING - this bar contains no stops (no NoteOff events) because
     * many rhythm instruments do not require NoteOff events.
     */
    public Bar randomRhythm()
    {
        Random random = new Random();
        Bar randomRhythm = new Bar();

        for (int i=0; i<16; i++)
        {
            int beat = random.nextInt(2);
            Tick t = new Tick();
            if (beat == 1)
            {
                Note n = new Note((short)60, (short)300);
                t.addNote(n);
            }
            randomRhythm.addTick(i, t);
        }
        return randomRhythm;
    }
    
    public Bar simpleRhythm()
    {
        Bar simpleRhythm = new Bar();

        for (int i=0; i<16; i++)
        {
            Tick t = new Tick();
            if (i%8 == 0)
            {
                Note n = new Note((short)60, (short)300);
                t.addNote(n);
            }
            simpleRhythm.addTick(i, t);
        }
        return simpleRhythm;
    }
    
    public Bar mediumRhythm()
    {
        Bar simpleRhythm = new Bar();

        for (int i=0; i<16; i++)
        {
            Tick t = new Tick();
            if (i == 0 || i == 6 || i == 8 || i == 10)
            {
                Note n = new Note((short)60, (short)300);
                t.addNote(n);
            }
            simpleRhythm.addTick(i, t);
        }
        return simpleRhythm;
    }
    
    public Bar intenseRhythm()
    {
        Bar intenseRhythm = new Bar();
        
        for (int i=0; i<16; i++)
        {
            Tick t = new Tick();
            if (i == 0 || i == 2 || i == 6 | i == 8 | i == 12 | i == 13 | i == 14)
            {
                Note n = new Note((short)60, (short)300);
                t.addNote(n);
            }
            intenseRhythm.addTick(i, t);
        }
        
        return intenseRhythm;
    }

    private Chord nextChordFromBrennanDatabase() 
    {
        return database.getNextChord(0);
    }
        
}
