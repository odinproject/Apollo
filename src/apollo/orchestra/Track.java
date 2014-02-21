package apollo.orchestra;

import java.util.LinkedList;

/**
 *
 * @author Martin
 */
public class Track {
    
    // -> FIRST |1' ' ' '|2' ' ' '|3' ' ' '|4' ' ' '|... LAST
    //    Adding new bars will be pushed on the LAST end
    //    To play the score, we draw from the FIRST end
    LinkedList<Bar> bars;
    
    // between 0 and 16
    int channel;
    
    public Track()
    {
        bars = new LinkedList<Bar>();
        channel = 0;
    }
    
    public Bar getNextBar()
    {
        // retrieves the bar on the FIRST end of the track
        return bars.getFirst();
    }
    
    public Tick getTick(int barIndex, int tickIndex)
    {
        return bars.get(barIndex).getTick(tickIndex);
    }
    
    public void addBar(Bar b)
    {
        // adds the bar to the LAST end of the track
        bars.add(b);
    }
    
    public int getChannel()
    {
        return channel;
    }
    
}
