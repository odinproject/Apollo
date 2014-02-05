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
public class Funcs {

    static void PrintArray(double[] weights) 
    {
        System.out.print("[");
        for (int i = 0; i < weights.length; i++)
        {
            System.out.print(weights[i]+", ");
        }
        System.out.println("]");
    }
    
}
