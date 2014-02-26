package apollo.learning;

import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class ChordAnalyzer
{
    private Scribe scribe;
    
    // [12]     PITCH of each chord (A, A#, B, etc...)
    // [9]      TYPE of each chord (Major, Minor...)
    // [108]    PITCH AND TYPE of each chord (A# diminished, C minor...)
    // [144]    PITCH of each transition (C -> D, A -> A...)
    // [81]     TYPE of each transition (Minor -> Major, Major -> Diminished...)
    
    // raw PITCH of each chord (A, A#, B, etc...)
    private Double[][] chordPitches;
    // raw TYPE of each chord (Major, Minor...)
    private Double[][] chordTypes;
    // raw PITCH AND TYPE of each chord (A# diminished, C minor...)
//    private Double[][] chordPitch_Types;
    
    // at the end of analysis, we would like a list of raw variables mapped
    // statistically to their influence on the two identifiers (tension, energy)
    
    public ChordAnalyzer()
    {
        scribe = new Scribe();
        
        chordPitches = new Double[2][12];
        
        for (int i=0; i<2; i++)
        {
            for (int j=0; j<12; j++)
            {
                chordPitches[i][j] = 0.0;
            }
        }
        
        chordTypes = new Double[2][9];
        
        for (int i=0; i<2; i++)
        {
            for (int j=0; j<9; j++)
            {
                chordTypes[i][j] = 0.0;
            }
        }
        
//        chordPitch_Types = new Double[2][108];
//        
//        for (int i=0; i<2; i++)
//        {
//            for (int j=0; j<9; j++)
//            {
//                chordTypes[i][j] = 0.0;
//            }
//        }
    }
    
    public void analyzeFile(String fileName)
    {
        ChordProgSequence sequence = scribe.extractDataFromChordProgressionFile(fileName);
        analyzeSequence(sequence);
    }
    
    private void analyzeSequence(ChordProgSequence sequence)
    {
        double energy = sequence.getEnergy();
        double tension = sequence.getTension();

        // RAW CHORD PITCHES
        for (int j=0; j<12; j++)
        {
            for (Integer pitchIndex : sequence.getPitches())
            {
                if (pitchIndex == j)
                {
                    Double chordPitchForEnergy = (chordPitches[0][j] + energy) / 2;
                    chordPitches[0][j] = chordPitchForEnergy;
                    Double chordPitchForTension = (chordPitches[1][j] + tension) / 2;
                    chordPitches[1][j] = chordPitchForTension;
                    break;
                }
            }
            
            // for very tiny numbers, round down to zero
            if (Math.abs(chordPitches[0][j]) < 0.001)
            {
                chordPitches[0][j] = 0.0;
            }
            if (Math.abs(chordPitches[1][j]) < 0.001)
            {
                chordPitches[1][j] = 0.0;
            }
        }
        
        // RAW CHORD TYPES
        for (int j=0; j<9; j++)
        {
            for (Integer typeIndex : sequence.getTypes())
            {
                if (typeIndex == j)
                {
                    Double chordPitchForEnergy = (chordTypes[0][j] + energy) / 2;
                    chordTypes[0][j] = chordPitchForEnergy;
                    Double chordPitchForTension = (chordTypes[1][j] + tension) / 2;
                    chordTypes[1][j] = chordPitchForTension;
                    break;
                }
            }
            
            // for very tiny numbers, round down to zero
            if (Math.abs(chordTypes[0][j]) < 0.001)
            {
                chordPitches[0][j] = 0.0;
            }
            if (Math.abs(chordTypes[1][j]) < 0.001)
            {
                chordPitches[1][j] = 0.0;
            }
        }
    }
}