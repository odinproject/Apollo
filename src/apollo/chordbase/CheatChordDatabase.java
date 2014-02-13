/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.chordbase;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Talonos
 */
public class CheatChordDatabase extends ChordDatabase
{
    private final int C_NOTE = 0;
    private final int CS_NOTE = 1;
    private final int D_NOTE = 2;
    private final int DS_NOTE = 3;
    private final int E_NOTE = 4;
    private final int F_NOTE = 5;
    private final int FS_NOTE = 6;
    private final int G_NOTE = 7;
    private final int GS_NOTE = 8;
    private final int A_NOTE = 9;
    private final int AS_NOTE = 10;
    private final int B_NOTE = 11;
    
    private final int MAJOR = 0;
    private final int MINOR = 1;
    private final int SEVEN = 2;
    private final int SEVEN_MAJOR = 3;
    private final int SEVEN_MINOR = 4;
    private final int DIMINISHED = 5;
    private final int AUGMENTED = 6;
    private final int HALF_DIMINISHED = 7;
    private final int MINOR_MAJOR = 8;
    
    List<Chord>[] chordList;
    
    public CheatChordDatabase()
    {
        super();
        chordList = new ArrayList[4];
        chordList[0] = new ArrayList<>();
        chordList[1] = new ArrayList<>();
        chordList[2] = new ArrayList<>();
        chordList[3] = new ArrayList<>();
        
        
        chordList[0].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[G_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[A_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[G_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[A_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[G_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[A_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][SEVEN_MAJOR]);
        chordList[0].add(this._chordLibrary[C_NOTE][SEVEN_MAJOR]);
        chordList[0].add(this._chordLibrary[E_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[E_NOTE][MINOR]);
        chordList[0].add(this._chordLibrary[E_NOTE][MAJOR]);
        chordList[0].add(this._chordLibrary[E_NOTE][MAJOR]);
        //---------------------------------------------------
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[A_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[A_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[F_NOTE][SEVEN_MAJOR]);
        chordList[1].add(this._chordLibrary[F_NOTE][SEVEN_MAJOR]);
        chordList[1].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[1].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[1].add(this._chordLibrary[AS_NOTE][SEVEN_MAJOR]);
        chordList[1].add(this._chordLibrary[AS_NOTE][SEVEN_MAJOR]);
        chordList[1].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[A_NOTE][SEVEN]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[A_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[A_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[F_NOTE][SEVEN_MAJOR]);
        chordList[1].add(this._chordLibrary[F_NOTE][SEVEN_MAJOR]);
        chordList[1].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[1].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[1].add(this._chordLibrary[AS_NOTE][SEVEN_MAJOR]);
        chordList[1].add(this._chordLibrary[AS_NOTE][SEVEN_MAJOR]);
        chordList[1].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[A_NOTE][SEVEN]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[DS_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[DS_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[CS_NOTE][DIMINISHED]);
        chordList[1].add(this._chordLibrary[CS_NOTE][DIMINISHED]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[DS_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[DS_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[CS_NOTE][DIMINISHED]);
        chordList[1].add(this._chordLibrary[CS_NOTE][DIMINISHED]);
        chordList[1].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[CS_NOTE][DIMINISHED]);
        chordList[1].add(this._chordLibrary[CS_NOTE][DIMINISHED]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[DS_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[DS_NOTE][MAJOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[1].add(this._chordLibrary[D_NOTE][MINOR]);
        //---------------------------------------------------

        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[G_NOTE][MINOR]);
        chordList[2].add(this._chordLibrary[G_NOTE][MINOR]);
        chordList[2].add(this._chordLibrary[C_NOTE][SEVEN_MINOR]);
        chordList[2].add(this._chordLibrary[C_NOTE][SEVEN_MINOR]);
        chordList[2].add(this._chordLibrary[F_NOTE][SEVEN]);
        chordList[2].add(this._chordLibrary[F_NOTE][SEVEN]);
        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[G_NOTE][MINOR]);
        chordList[2].add(this._chordLibrary[G_NOTE][MINOR]);
        chordList[2].add(this._chordLibrary[C_NOTE][SEVEN_MINOR]);
        chordList[2].add(this._chordLibrary[C_NOTE][SEVEN_MINOR]);
        chordList[2].add(this._chordLibrary[F_NOTE][SEVEN]);
        chordList[2].add(this._chordLibrary[F_NOTE][SEVEN]);
        chordList[2].add(this._chordLibrary[DS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[DS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[F_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[G_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[G_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[C_NOTE][SEVEN_MINOR]);
        chordList[2].add(this._chordLibrary[C_NOTE][SEVEN_MINOR]);
        chordList[2].add(this._chordLibrary[F_NOTE][SEVEN]);
        chordList[2].add(this._chordLibrary[F_NOTE][SEVEN]);
        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        chordList[2].add(this._chordLibrary[AS_NOTE][MAJOR]);
        //-------------------------------------------------------
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][HALF_DIMINISHED]);
        chordList[3].add(this._chordLibrary[D_NOTE][HALF_DIMINISHED]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[CS_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[CS_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][HALF_DIMINISHED]);
        chordList[3].add(this._chordLibrary[D_NOTE][HALF_DIMINISHED]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][HALF_DIMINISHED]);
        chordList[3].add(this._chordLibrary[D_NOTE][HALF_DIMINISHED]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[CS_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[CS_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][HALF_DIMINISHED]);
        chordList[3].add(this._chordLibrary[D_NOTE][HALF_DIMINISHED]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MAJOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[G_NOTE][SEVEN]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[C_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[C_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[D_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[D_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[E_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[E_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[C_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[C_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[FS_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[FS_NOTE][MINOR]);
        chordList[3].add(this._chordLibrary[D_NOTE][AUGMENTED]);
        chordList[3].add(this._chordLibrary[D_NOTE][AUGMENTED]);        
    }
    
    int thingsDoneSoFar = 0;
    
    public Chord getNextChord(int emotiveState) 
    {
        Chord toReturn = chordList[3].get(thingsDoneSoFar++);
        if (thingsDoneSoFar == chordList[3].size())
        {
            thingsDoneSoFar = 0;
        }
        return toReturn;
    }
}
