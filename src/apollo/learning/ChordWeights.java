package apollo.learning;

import apollo.chordbase.Chord;

/**
 *
 * @author Martin
 */
public class ChordWeights {
    
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
    private Double[][][][] transitionScores;
    
    // how much to weight the effect of transitionScores
    private double comprehensiveWeight;
    // how much to weight the effect of individual transitions
    private double typeTransitionWeight;
    // how much to weight the effect of raw chord types
    private double rawTypeWeight;
    
    public ChordWeights()
    {
        // right now we only care about the transitionScores
        comprehensiveWeight = 1.0;
        typeTransitionWeight = 0.0;
        rawTypeWeight = 0.0;
        transitionScores = new Double[2][9][22][9];
        
        for (int i=0; i<2; i++)
        {
            for (int j=0; j<9; j++)
            {
                for (int k=0; k<22; k++)
                {
                    for (int l=0; l<9; l++)
                    {
                        transitionScores[i][j][k][l] = 0.0;
                    }
                }
            }
        }
    }
    
    public void addComprehensiveScore(int i, int j, int k, int l, double score)
    {
        transitionScores[i][j][k][l] = transitionScores[i][j][k][l] + score*comprehensiveWeight;
    }
    
    public void addRawTypeScore(int i, int l, double score)
    {
        for (int j=0; j<9; j++)
        {
            for (int k=0; k<22; k++)
            {
                transitionScores[i][j][k][l] = transitionScores[i][j][k][l] + score*rawTypeWeight;
            }
        }
    }
    
    public void addTypeTransitionScore(int i, int j, int l, double score)
    {
        for (int k=0; k<22; k++)
        {
            transitionScores[i][j][k][l] = transitionScores[i][j][k][l] + score*typeTransitionWeight;
        }
    }
    
    /**
     * Imports a final weight directly into the transitionScores
     * @param i the classifier index (0=energy, 1=tension)
     * @param j the starting chord type (0=Major, 1=minor...)
     * @param k the relative pitch shift index (0=-11, 1=-10... 22=11)
     * @param l the ending chord type (0=Major, 1=minor...)
     * @param score the intended intensity or energy score for this transition
     */
    public void importWeight(int i, int j, int k, int l, double score)
    {
        transitionScores[i][j][k][l] = score;
    }
    
    public String toString()
    {
        String output = "";
        for (int i=0; i<2; i++)
        {
            if (i == 0)
            {
                output += "Energy\n";
            }
            else
            {
                output += "Tension\n";
            }
            
            for (int j=0; j<9; j++)
            {
                output += "FromChordType:"+j+"\n";
                for (int k=0; k<22; k++)
                {
                    for (int l=0; l<9; l++)
                    {
                        String comma = "";
                        if (l < 8) comma = ",";
                        output += transitionScores[i][j][k][l] + comma;
                    }
                    output += "\n";
                }
            }
        }
        return output;
    }
    
    public Double[][][][] getWeights()
    {
        return transitionScores;
    }
    
}
