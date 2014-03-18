package apollo.orchestra;

import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class Bar {
    
    // 16 ticks in a bar
    // 4 beats in a bar <- 4 ticks in a beat
    Tick[] ticks;
    
    public Bar()
    {
        ticks = new Tick[16];
        for (int i=0; i<15; i++)
        {
            Tick t = new Tick();
            ticks[i] = t;
        }
    }
    
    public Tick getTick(int tickNumber)
    {
        return ticks[tickNumber];
    }
    
    public Tick[] getTicks()
    {
        return ticks;
    }
    
    public void addTick(int index, Tick t)
    {
        ticks[index] = t;
    }
    
}
