/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.chordbase;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A class to represent a chord in a single octave.
 * @author Talonos
 */
public class Chord implements Comparable
{
    /**
     * The notes in this chord.
     */
    private Set<Integer> _notes;
    
    /**
     * The name of this chord.
     */
    private String name = "Unnamed Chord";
    
    /**
     * Creates an empty chord.
     */
    public Chord()
    {
        _notes = new HashSet();
    }

    /**
     * Creates a new chord by duplicating an old chord and then transposing it 
     * by an amount.
     * @param oldChord the chord to duplicate.
     * @param transposeAmount the number of half-steps upward to transpose
     */
    public Chord(Chord oldChord, int transposeAmount)
    {
        if (transposeAmount < 0)
        {
            throw new IllegalArgumentException("Transposing by negative numbers "
                    + "is not allowed. Please trenspose upwards by an equivelent"
                    + " amount. (For example, transpose up by 11 instead of down"
                    + " by one.)");
        }
        _notes = new HashSet();
        for (Integer note : oldChord.getNotes())
        {
            addNote((note+transposeAmount)%12);
        }
    }
    
    /**
     * Returns a set of integers representing the notes in a single octave of
     * this chord. The notes are represented using the same notation as listed
     * in ApolloPlayer.java (as of 2/1/14) in "octave 0", which is to say, 0 is 
     * C. 1 is C#, 2 is D, etc.
     * @return 
     */
    public Set<Integer> getNotes()
    {
        Set<Integer> toReturn = new HashSet<>();
        toReturn.addAll(_notes);
        return toReturn;
    }
    
    /**
     * Adds a note to this chord.
     * @param note a positive number from 0 to 11 representing a note on the 
     * scale. The notes are represented using the same notation as listed
     * in ApolloPlayer.java (as of 2/1/14) in "octave 0", which is to say, 0 is 
     * C. 1 is C#, 2 is D, etc.
     */
    public void addNote(Integer note)
    {
       if (note < 0 )
       {
           throw new IllegalArgumentException("A negative note was added to a chord.");
       }
       if (note > 11)
       {
           System.out.println("[WARNING] A note higher than 11 was added to a chord. "
                   + "This probably indicates a problem of some kind with the coding "
                   + "logic. I will transpose it down octaves until it is within the "
                   + "valid range. ");
           note = note%12;
       }
       this._notes.add(note);
    }
    
    /**
     * Gets the name of the chord, as defined by its name.
     * @return the name of the chord.
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Renames a chord to the given name.
     * @param name the new name.
     */
    public void rename(String name) 
    {
        this.name = name;
    }
    
    /**
     * Returns the string representation of this object, which is simply its name.
     * @return the name of the chord.
     */
    public String toString()
    {
        return name;
    }

    @Override
    public int compareTo(Object o) 
    {
        if (o instanceof Chord)
        {
            return name.compareTo(((Chord)o).name);
        }
        return -1;
    }

    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this._notes);
        return hash;
    }

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
        final Chord other = (Chord) obj;
        if (!Objects.equals(this._notes, other._notes)) 
        {
            return false;
        }
        return true;
    }
    
    /**
     * Represents the tone of the chord. 0 is c, 1 is c#, and so forth.
     */
    private int tone = -1;
    
    private int chordType = -1;
    
    /**
     * Returns the tone of the chord. 0 is c, 1 is c#, and so forth.
     * @return the tone, or -1 if this is a non-standard chord.
     */
    public int getTone()
    {
        return tone;
    }
    
    /**
     * Sets the tone for this chord.
     * @param tone the new Tone.
     */
    public void setTone(int tone) 
    {
        this.tone = tone;
    }

    /**
     * Gets the chord type, as defined in the ChordDatabase.
     * @return the chordType, or -1 if this is a non-standard chord.
     */
    public int getChordType() 
    {
        return chordType;
    }

    /**
     * Sets the chordType of this chord.
     * @param chordType the new type of chord.
     */
    public void setChordType(int chordType) 
    {
        this.chordType = chordType;
    }
    
    
}
