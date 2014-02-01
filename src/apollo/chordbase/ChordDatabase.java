/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.chordbase;

/**
 *
 * @author Talonos
 */
public class ChordDatabase 
{
    
    private Chord[][]_chordLibrary;
    
    /**
     * Creates a new chord database, populating it with chords, adding 
     * transitions between those chords, and then pruning those transitions.
     */
    public ChordDatabase()
    {
        //Creata a library of chords.
        _chordLibrary = new Chord[12][9];
        
        //Populate the C chords:
        // - C major
        Chord cMajor = new Chord();
        cMajor.addNote(0);
        cMajor.addNote(4);
        cMajor.addNote(7);
        _chordLibrary[0][0] = cMajor;
        // - C minor
        Chord cMinor = new Chord();
        cMinor.addNote(0);
        cMinor.addNote(3);
        cMinor.addNote(7);
        _chordLibrary[0][1] = cMinor;
        // - C 7
        Chord c7 = new Chord();
        c7.addNote(0);
        c7.addNote(4);
        c7.addNote(7);
        c7.addNote(10);
        _chordLibrary[0][2] = c7;
        // - C 7 Major
        Chord c7Major = new Chord();
        c7Major.addNote(0);
        c7Major.addNote(4);
        c7Major.addNote(7);
        c7Major.addNote(11);
        _chordLibrary[0][3] = c7Major;
        // - C 7 Minor
        Chord c7Minor = new Chord();
        c7Minor.addNote(0);
        c7Minor.addNote(3);
        c7Minor.addNote(7);
        c7Minor.addNote(10);
        _chordLibrary[0][4] = c7Minor;
        // - C diminished;
        Chord cDiminished = new Chord();
        cDiminished.addNote(0);
        cDiminished.addNote(3);
        cDiminished.addNote(6);
        cDiminished.addNote(9);
        _chordLibrary[0][5] = cDiminished;
        // - C augmented
        Chord cAugmented = new Chord();
        cAugmented.addNote(0);
        cAugmented.addNote(4);
        cAugmented.addNote(8);
        _chordLibrary[0][6] = cAugmented;
        // - C half diminished
        Chord cHalfDiminished = new Chord();
        cHalfDiminished.addNote(0);
        cHalfDiminished.addNote(3);
        cHalfDiminished.addNote(6);
        cHalfDiminished.addNote(10);
        _chordLibrary[0][7] = cHalfDiminished;
        // - C minor-major
        Chord cMinorMajor = new Chord();
        cMinorMajor.addNote(0);
        cMinorMajor.addNote(3);
        cMinorMajor.addNote(7);
        cMinorMajor.addNote(11);
        _chordLibrary[0][8] = cMinorMajor;
        
        //Transpose chords to fill out the rest of the library.
        for (int pitch = 1; pitch < 12; pitch++)
        {
            for (int chordNum = 0; chordNum < 9; chordNum++)
            {
                _chordLibrary[pitch][chordNum] = new Chord(_chordLibrary[0][chordNum], pitch);
            }
        }
        
        //With the library full, we now make transitions between chords.
    }
    
// C       C#      D       D#      E       F       F#      G       G#      A       A#      B
// 0	   1       2       3       4       5       6       7       8       9       10      11

    /**
     * Gets a chord by absolute number. All major chords are 0-11, then all minor 
     * chords are 12+(0 to 11), etc.
     * @param i the chord to get.
     * @return the chord requested.
     */
    public Chord getChordByNumber(int i) 
    {
        System.out.println("Returning: "+(i%12)+", "+(i/12));
        return _chordLibrary[i%12][i/12];
    }
}
