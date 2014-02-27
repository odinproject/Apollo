package apollo.learning;

import apollo.chordbase.Chord;

/**
 *
 * @author Martin
 */
public class ChordWeights {
    
    private Double[][][][] transitionScores;
    
    private double comprehensiveWeight;
    private double typeTransitionWeight;
    private double rawTypeWeight;
    
    public ChordWeights()
    {
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
