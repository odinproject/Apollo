/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.chordbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Talonos
 */
public class BrennanChordDatabase extends ChordDatabase 
{
    private ChordNode[][] _calmData;
    private ChordNode[][] _intenseData;
    public BrennanChordDatabase()
    {
        super();
        ObjectInputStream i = null;
        try 
        {
            i = new ObjectInputStream(new FileInputStream("./relaxedDatabase.cpd"));
            this._calmData = (ChordNode[][])(i.readObject());
            
            i = new ObjectInputStream(new FileInputStream("./intenseDatabase.cpd"));
            this._intenseData = (ChordNode[][])(i.readObject());
            _currentChordNode = _chordNodeLibrary[0][0];
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(BrennanChordDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(BrennanChordDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(BrennanChordDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            try 
            {
                i.close();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(BrennanChordDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private List<Chord> macro = new LinkedList<>();
    
    boolean isRelaxed;
    
    @Override
    public Chord getNextChord(double emotiveState) 
    {
        swapIsRelaxed(emotiveState);
        
        //System.out.println("Getting: ");
        //new Exception().printStackTrace(System.out);
        if (macro.isEmpty())
        {
            generateNewMacro();
        }
        
        return macro.remove(0);
    }

    Random dice = new Random();
    
    private void generateNewMacro() 
    {   
        switch(dice.nextInt(2))
        {
            case 0:
                basicStructure();
                return;
            case 1:
                alternateStructure();
                return;
            case 2:
                otherStructure();
                return;
        }
        return;
    }

    private void basicStructure() 
    {
        ChordNode a = _currentChordNode;
        ChordNode b = _currentChordNode.getTransition(0);
        ChordNode c = _currentChordNode.getTransition(0);
        ChordNode d = c.getTransition(0);
        ChordNode e = c.getTransition(0);
        _currentChordNode = c.getTransition(0);
        macro.add(a.getChord());
        macro.add(b.getChord());
        macro.add(a.getChord());
        macro.add(c.getChord());
        macro.add(d.getChord());
        macro.add(c.getChord());
        macro.add(e.getChord());
        macro.add(c.getChord());
    }

    private void alternateStructure() 
    {
        ChordNode a = _currentChordNode;
        ChordNode b;
        ChordNode c;
        while (true)
        {
            b = a.getTransition(0);
            c = b.getTransition(0);
            if (c.containsTransitionTo(a))
            {
                break;
            }
        }
        macro.add(a.getChord());
        macro.add(b.getChord());
        macro.add(c.getChord());
        macro.add(a.getChord());
        macro.add(b.getChord());
        macro.add(a.getChord());
        macro.add(c.getChord());
        macro.add(a.getChord());
        _currentChordNode = a.getTransition(0);
        
    }

    private void otherStructure() 
    {
        ChordNode a = _currentChordNode;
        ChordNode b;
        ChordNode c;
        while (true)
        {
            b = a.getTransition(0);
            c = b.getTransition(0);
            if (c.containsTransitionTo(a))
            {
                break;
            }
        }
        ChordNode d;
        ChordNode e;
        while (true)
        {
            d = c.getTransition(0);
            e = d.getTransition(0);
            if (e.containsTransitionTo(c))
            {
                break;
            }
        }
        macro.add(a.getChord());
        macro.add(b.getChord());
        macro.add(c.getChord());
        macro.add(a.getChord());
        macro.add(c.getChord());
        macro.add(d.getChord());
        macro.add(e.getChord());
        macro.add(c.getChord());
    }

    
    private void swapIsRelaxed(double emotiveState) 
    {
        if (isRelaxed&&emotiveState>0)
        {
            Random dice = new Random();
            isRelaxed = false;
            this._currentChordNode = this._intenseData[dice.nextInt(8)][dice.nextInt(12)];
        }
        else if (!isRelaxed&&emotiveState<0)
        {
            Random dice = new Random();
            isRelaxed = true;
            this._currentChordNode = this._calmData[dice.nextInt(8)][dice.nextInt(12)];
        }
        System.out.println("Swapping to relaxed = "+isRelaxed);
    }
}
