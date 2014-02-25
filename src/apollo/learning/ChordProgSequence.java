/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.learning;

import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class ChordProgSequence {
    
    private ArrayList<Integer> pitchArray  = new ArrayList();
    private ArrayList<Integer> typeArray = new ArrayList();
    private ArrayList<String> tags  = new ArrayList();
    
    private double energy;
    private double tension;
    
    public void ChordProgSequence()
    {
        pitchArray = new ArrayList<Integer>();
        typeArray = new ArrayList<Integer>();
        tags = new ArrayList<String>();
    }
    
    public void setEnergy(double energy)
    {
        this.energy = energy;
    }
    
    public void setTension(double tension)
    {
        this.tension = tension;
    }
    
    public void addPitch(int pitch)
    {
        pitchArray.add(pitch);
    }
    
    public void addType(int type)
    {
        typeArray.add(type);
    }
    
    public void addTag(String tag)
    {
        tags.add(tag);
    }
    
    public ArrayList<Integer> getPitches()
    {
        return pitchArray;
    }
    
    public ArrayList<Integer> getTypes()
    {
        return typeArray;
    }
    
    public ArrayList<String> getTags()
    {
        return tags;
    }
    
    public double getEnergy()
    {
        return energy;
    }
    
    public double getTension()
    {
        return tension;
    }
}
