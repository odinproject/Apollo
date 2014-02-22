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
    
    public Track(int channel)
    {
        bars = new LinkedList<Bar>();
        this.channel = channel;
    }
    
    public Bar getNextBar()
    {
        // retrieves the bar on the FIRST end of the track
        return bars.getFirst();
    }
    
    public Tick getTick(int barIndex, int tickIndex)
    {
        if (barIndex < bars.size())
        {
            return bars.get(barIndex).getTick(tickIndex);
        }
        else
        {
            return new Tick();
        }   
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
    
    public void setChannel(int channel)
    {
        this.channel = channel;
    }
    
}
