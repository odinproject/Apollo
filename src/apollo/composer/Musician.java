/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.composer;

/**
 *
 * @author Martin
 */
public class Musician {
    
    // the name of the musician (purely decorative to increase perceived creativity)
    private String name;
    // the musician that this musician 'follows'. If leader is nil, then this 
    // musician is the lead musician, and sets the tone for everyone else
    private Musician leader;
    
}
