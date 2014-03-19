/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.orchestra;

import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class Tick {
    
    private ArrayList<Note> notes;
    private ArrayList<Note> stops;
    
    public Tick()
    {
        notes = new ArrayList<Note>();
        stops = new ArrayList<Note>();
    }
    
    /**
     * Copy constructor
     * @param other 
     */
    public Tick(Tick other)
    {
        notes = new ArrayList<Note>();
        stops = new ArrayList<Note>();
        for (int i=0; i<other.getNotes().size(); i++)
        {
            Note n = new Note(other.getNotes().get(i));
            notes.add(n);
        }
        for (int i=0; i<other.getStops().size(); i++)
        {
            Note n = new Note(other.getStops().get(i));
            stops.add(n);
        }
    }
    
    public ArrayList<Note> getNotes()
    {
        return notes;
    }
    
    public ArrayList<Note> getStops()
    {
        return stops;
    }
    
    public void addNote(Note n)
    {
        notes.add(n);
    }
    
    public void addNote(short pitch, short velocity)
    {
        notes.add(new Note(pitch, velocity));
    }
    
    public void addStop(Note n)
    {
        stops.add(n);
    }
    
    public void addStop(short pitch)
    {
        stops.add(new Note(pitch, (short)0));
    }
    
}
