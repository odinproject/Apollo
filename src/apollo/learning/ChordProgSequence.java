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
    
    private ArrayList<Integer> pitchArray;
    private ArrayList<Integer> typeArray;
    private ArrayList<Integer> fromTypeArray;
    private ArrayList<Integer> toTypeArray;
    private ArrayList<Integer> relativePitchTransitionArray;
    private ArrayList<String> tags;
    
    private double energy;
    private double tension;
    
    public ChordProgSequence()
    {
        pitchArray = new ArrayList();
        typeArray = new ArrayList();
        fromTypeArray = new ArrayList();
        toTypeArray = new ArrayList();
        relativePitchTransitionArray = new ArrayList();
        tags = new ArrayList();
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
    
    public void addFromType(int type)
    {
        fromTypeArray.add(type);
    }
    
    public void addToType(int type)
    {
        toTypeArray.add(type);
    }
    
    public void addRelativePitchTransition(int transition)
    {
        relativePitchTransitionArray.add(transition);
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
    
    public ArrayList<Integer> getFromTypes()
    {
        return fromTypeArray;
    }
    
    public ArrayList<Integer> getToTypes()
    {
        return toTypeArray;
    }
    
    public ArrayList<Integer> getRelativePitchTransitions()
    {
        return relativePitchTransitionArray;
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
