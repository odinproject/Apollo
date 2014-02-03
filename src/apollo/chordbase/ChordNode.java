/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.chordbase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a node in the connected graph representing the possible chord transitions.
 * This node contains a chord, but is distinct from a chord so as to separate the notions
 * of "playing" from the notions of "transitioning."
 * @author Talonos
 */
public class ChordNode 
{
    /**
     * The chord this node contains
     */
    private Chord _chord;
    
    /**
     * The list of transitions this ChordNode can lead to. TODO: Make a set, perhaps?
     */
    private List<ChordNodeTransition> _transitions = new ArrayList<>();
    
    public ChordNode(Chord c)
    {
        this._chord = c;
    }
    
    /**
     * Adds a new arc leading from this ChordNode to one listed.
     * @param destination the destination chord node of the arc.
     */
    public void addTransition(ChordNode destination)
    {
        _transitions.add(new ChordNodeTransition(this, destination));
    }

    /**
     * Returns the hashcode of this ChordNode.
     * @return the hashcode.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this._chord);
        return hash;
    }

    /**
     * Compares two ChordNodes to see if they are equal, as defined by their stored chords.
     * @param obj the ChordNode to compare to.
     * @return whether they are equal.
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final ChordNode other = (ChordNode) obj;
        if (!Objects.equals(this._chord, other._chord)) 
        {
            return false;
        }
        return true;
    }
    
}