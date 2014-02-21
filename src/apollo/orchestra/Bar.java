package apollo.orchestra;

import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class Bar {
    
    // 16 ticks in a bar
    // 4 beats <- 4 ticks in a beat
    Tick[] ticks;
    
    public Bar()
    {
        ticks = new Tick[16];
    }
    
    public Tick getTick(int tickNumber)
    {
        return ticks[tickNumber];
    }
    
    public void addTick(int index, Tick t)
    {
        ticks[index] = t;
    }
    
}
