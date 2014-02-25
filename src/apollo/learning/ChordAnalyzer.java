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
    
    private Double[][] chordPitches;
    private Double[][] chordTypes;
    
//    private ArrayList<ArrayList<Double>> chordPitches;
//    private ArrayList<ArrayList<Double>> chordTypes;
//    private ArrayList<ArrayList<ArrayList<Double>>> transitionPitches;
//    private ArrayList<ArrayList<ArrayList<Double>>> transitionTypes;
    
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
        
        
//        chordPitches = new ArrayList<ArrayList<Double>>();
//        chordTypes = new ArrayList<ArrayList<Double>>();
//        transitionPitches = new ArrayList<ArrayList<ArrayList<Double>>>();
//        transitionTypes = new ArrayList<ArrayList<ArrayList<Double>>>();
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
        
        System.out.println("Energy: " + tension);
        
        for (Integer i : sequence.getPitches())
        {
            chordPitches[0][i] = (chordPitches[0][i] + energy) / 2;
            chordPitches[1][i] = (chordPitches[1][i] + tension) / 2;
        }
        
        for (Integer i : sequence.getTypes())
        {
            chordTypes[0][i] = (chordTypes[0][i] + energy) / 2;
            chordTypes[1][i] = (chordPitches[1][i] + tension) / 2;
        }
    }
}
