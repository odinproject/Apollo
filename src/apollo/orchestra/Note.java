/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.orchestra;

/**
 *
 * @author Martin
 */
public class Note {
    
    short pitch;
    short velocity;
    
    public Note()
    {
        this.pitch = 60;
        this.velocity = 100;
    }
    
    public Note(short pitch, short velocity)
    {
        this.pitch = pitch;
        this.velocity = velocity;
    }
    
    public short getPitch()
    {
        return pitch;
    }
    
    public short getVelocity()
    {
        return velocity;
    }
}
