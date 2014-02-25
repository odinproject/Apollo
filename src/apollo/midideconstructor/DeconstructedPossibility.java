/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.midideconstructor;

import apollo.chordbase.Chord;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Talonos
 */
public class DeconstructedPossibility 
{
    public List<List<ChordConfidences>> halfMeasures = new ArrayList<>();
    public List<double[]> sampleWeights = new ArrayList<>();
    
    public Map<Chord, Double> getVoteOnMeasure(int segmentNum)
    {
        Map<Chord, Double> toReturn = new TreeMap<>();
        List<ChordConfidences> segment = halfMeasures.get(segmentNum);
        for (ChordConfidences cc : segment)
        {
            if (cc.chord == null)
            {
                continue;
            }
            if (!toReturn.containsKey(cc.chord))
            {
                toReturn.put(cc.chord, 0.0);
            }
            toReturn.put(cc.chord, toReturn.get(cc.chord)+cc.confidence/32);
        }
        return toReturn;
    }
    
    public String getVoteStringOnMeasure(int segmentNum)
    {
        Map<Chord, Double> votes = getVoteOnMeasure(segmentNum);
        String toReturn = new String();
        for (Chord c : votes.keySet())
        {
            toReturn+="  "+c.getName()+": "+((double)Math.round(votes.get(c)*10000)/100.0)+".\n";
        }
        return toReturn;
    }

    List<double[]> getSampleWeights() 
    {
        return sampleWeights;
    }
}
