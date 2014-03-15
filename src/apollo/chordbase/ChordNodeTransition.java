/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.chordbase;

import java.io.Serializable;
import java.util.Objects;

/**
 * A class to represent a transition from one chord node to another.
 * This class stores both the source and destination node, as well as possible 
 * information about the emotional content of the transition. I dunno, though.
 * We'll have to see how it works out.
 * @author Talonos
 */
class ChordNodeTransition implements Serializable
{
    /**
     * The source of the transition. We might not need this, actually, but I'm putting
     * it in just in case.
     */
    private ChordNode _source;
    
    /**
     * The destination chord.
     */
    private ChordNode _destination;

    /**
     * Creates a new ChordNodeTransition between two ChordNodes
     * @param source
     * @param destination 
     */
    ChordNodeTransition(ChordNode source, ChordNode destination) 
    {
        this._source = source;
        this._destination = destination;
    }

    /**
     * Gets the current source ChordNode of this transition.
     * @return the current source ChordNode
     */
    public ChordNode getSource() 
    {
        return _source;
    }

    /**
     * Sets the source ChordNode.
     * @param source the new source.
     */
    public void setSource(ChordNode source) 
    {
        this._source = source;
    }

    /**
     * Gets the current destination ChordNode of this transition.
     * @return the current destination ChordNode
     */
    public ChordNode getDestination() 
    {
        return _destination;
    }

    /**
     * Sets the destination ChordNode.
     * @param destination the new destination.
     */
    public void setDestination(ChordNode destination) 
    {
        this._destination = destination;
    }

    /**
     * Returns whether the two ChordNodes are equal, as defined by the source
     * and destination chords being equal.
     * @param obj the object to compare to.
     * @return 
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
        final ChordNodeTransition other = (ChordNodeTransition) obj;
        if (!Objects.equals(this._source, other._source)) 
        {
            return false;
        }
        if (!Objects.equals(this._destination, other._destination)) 
        {
            return false;
        }
        return true;
    }
    
    /**
     * The hashcode of this object.
     * @return the hashcode
     */
    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this._source);
        hash = 79 * hash + Objects.hashCode(this._destination);
        return hash;
    }

    

}
