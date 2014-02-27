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
    // type transition
    private Double[][][] chordTypeTransitions;
    // relative pitch transition
    private Double[][] relativePitchTransitions;
    
    // initialType + relativePitchChange + finalType
    private Double[][][][] superTransitions;
    
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
        
        chordTypeTransitions = new Double[2][9][9];
        
        for (int i=0; i<2; i++)
        {
            for (int j=0; j<9; j++)
            {
                for (int k=0; k<9; k++)
                {
                    chordTypeTransitions[i][j][k] = 0.0;
                }
            }
        }
        
        // -11 to 11 (22 in total)
        // Just +11 to the relative shift stored in the file (1,X,2) 
        // and you get the relativePitchTransitionIndex
        relativePitchTransitions = new Double[2][22];
        
        for (int i=0; i<2; i++)
        {
            for (int j=0; j<22; j++)
            {
                relativePitchTransitions[i][j] = 0.0;
            }
        }
        
        superTransitions = new Double[2][9][22][9];
        
        for (int i=0; i<2; i++)
        {
            for (int j=0; j<9; j++)
            {
                for (int k=0; k<22; k++)
                {
                    for (int l=0; l<9; l++)
                    {
                        superTransitions[i][j][k][l] = 0.0;
                    }
                }
            }
        }
    }
    
    public void printFindings()
    {
//        System.out.println("Raw Pitch Associations:");
//        for (int i=0; i<chordPitches[0].length; i++)
//        {
//             
//            System.out.println(i + "," + chordPitches[0][i] + "," + chordPitches[1][i]);
//        }
//        
//        System.out.println("Raw Type Associations:");
//        for (int i=0; i<chordTypes[0].length; i++)
//        {
//            System.out.println(i + "," + chordTypes[0][i] + "," + chordTypes[1][i]);
//        }
//        
//        System.out.println("Type Transition Associations:");
//        for (int i=0; i<9; i++)
//        {
//            for (int j=0; j<9; j++)
//            {
//                String comma = "";
//                if (j < 8) comma = ",";
//                System.out.print(chordTypeTransitions[1][i][8]  + comma);
//            }
//            System.out.print("\n");
//        }
//        
//        System.out.println("Relative Pitch Transition Associations:");
//        for (int i=0; i<22; i++)
//        {
//            System.out.println(relativePitchTransitions[1][i]);
////            System.out.println((i-11) + "," + relativePitchTransitions[0][i] + "," + relativePitchTransitions[1][i]);
//        }
//        
        System.out.println("Super Transition Associations:");
        for (int k=0; k<22; k++)
        {
            for (int l=0; l<9; l++)
            {
                String comma = "";
                if (l < 8) comma = ",";
                System.out.print(superTransitions[1][0][k][l] + comma);
            }
            System.out.print("\n");
        }
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

        ////////////////////////////////////////////////////////////////
        // RAW CHORD PITCHES
        ////////////////////////////////////////////////////////////////
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
        
        ////////////////////////////////////////////////////////////////
        // RAW CHORD TYPES
        ////////////////////////////////////////////////////////////////
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
        
        ////////////////////////////////////////////////////////////////
        // CHORD TYPE TRANSITIONS
        ////////////////////////////////////////////////////////////////
        
        // extract fromtype and totype from our index
//            int fromType = (int)Math.floor(j/9);
//            int toType = j % 9;
        
        ArrayList<Integer> fromTypes = sequence.getFromTypes();
        ArrayList<Integer> toTypes = sequence.getToTypes();
        
        // represents the fromType
        for (int i=0; i<9; i++)
        {
            // represents the toType
            for (int j=0; j<9; j++)
            {
                for (int k=0; k<fromTypes.size(); k++)
                {
                    if (fromTypes.get(k) == i && toTypes.get(k) == j)
                    {
                        chordTypeTransitions[0][i][j] = roundDouble((chordTypeTransitions[0][i][j] + energy) / 2, 2);
                        chordTypeTransitions[1][i][j] = roundDouble((chordTypeTransitions[1][i][j] + tension) / 2, 2);
                        break;
                    }
                }
            }
        }
        
        ////////////////////////////////////////////////////////////////
        // RELATIVE PITCH TRANSITIONS
        ////////////////////////////////////////////////////////////////
        
        ArrayList<Integer> relativeChordPitchTransitions = sequence.getRelativePitchTransitions();
        
        for (int i=0; i<22; i++)
        {
            for (int j=0; j<relativeChordPitchTransitions.size(); j++)
            {
                if (relativeChordPitchTransitions.get(j)+10 == i)
                {
                    relativePitchTransitions[0][i] = roundDouble((relativePitchTransitions[0][i] + energy) / 2, 2);
                    relativePitchTransitions[1][i] = roundDouble((relativePitchTransitions[1][i] + tension) / 2, 2);
                }
            }
        }
        
        ////////////////////////////////////////////////////////////////
        // SUPER TRANSITIONS
        ////////////////////////////////////////////////////////////////

        
        for (int j=0; j<9; j++)
        {
            for (int k=0; k<22; k++)
            {
                for (int l=0; l<9; l++)
                {
                    for (int m=0; m<fromTypes.size(); m++)
                    {
                        if (fromTypes.get(m) == j && relativeChordPitchTransitions.get(m)+10 == k && toTypes.get(m) == l)
                        {
                            superTransitions[0][j][k][l] = roundDouble((superTransitions[0][j][k][l] + energy) / 2, 2);
                            superTransitions[1][j][k][l] = roundDouble((superTransitions[1][j][k][l] + tension) / 2, 2);
                        }
                    }
                }
            }
        }
        
    }
    
    public ChordWeights getWeights()
    {
        ChordWeights weights = new ChordWeights();
        for (int i=0; i<2; i++)
        {
            for (int j=0; j<9; j++)
            {
                for (int k=0; k<22; k++)
                {
                    for (int l=0; l<9; l++)
                    {
                        weights.addComprehensiveScore(i, j, k, l, superTransitions[i][j][k][l]);
                    }
                }
            }
        }
        return weights;
    }
    
    public void serializeWeights()
    {
        ChordWeights weights = getWeights();
        String weightString = weights.toString();
        scribe.writeTextToFile("chordweights.txt", weightString);
    }
    
    private double roundDouble(double d, int numberOfDecimalPlaces)
    {
        double roundFactor = 1.0;
        
        for (int i=0; i<numberOfDecimalPlaces; i++)
        {
            roundFactor *= 10;
        }
        
        return Math.round(d * roundFactor) / roundFactor;
    }
}