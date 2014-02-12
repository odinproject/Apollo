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

    public static void printArray(double[] weights) 
    {
        System.out.print("[");
        for (int i = 0; i < weights.length; i++)
        {
            System.out.print(Math.round(weights[i]*100000)/100000+", ");
        }
        System.out.println("]");
    }
    
}
