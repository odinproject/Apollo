/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import apollo.chordbase.ChordDatabase;

/**
 *
 * @author Talonos
 */
public class chordTests 
{
    public static void main(String[] args)
    {
        ChordDatabase d = new ChordDatabase();
        double[] testChord = {100,0,0,100,0,0,0,100,0,0,0,0};
        System.out.println(d.identifyChord(testChord).getName());
    }
}
