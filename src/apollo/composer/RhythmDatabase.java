/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.composer;

import apollo.orchestra.Bar;
import apollo.orchestra.Tick;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class RhythmDatabase {
    
    // represents a collection of rhythms. [question=1, answer=0][rhythm number]
    private ArrayList<ArrayList<Bar>> rhythms;
    
    public RhythmDatabase()
    {
        rhythms = new ArrayList();
        ArrayList<Bar> questions = new ArrayList();
        ArrayList<Bar> answers = new ArrayList();
        
        answers.add(createRhythmWithBeats(new int[]{1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}));
        answers.add(createRhythmWithBeats(new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}));
        answers.add(createRhythmWithBeats(new int[]{1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1}));
        answers.add(createRhythmWithBeats(new int[]{1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0}));
        answers.add(createRhythmWithBeats(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));
        answers.add(createRhythmWithBeats(new int[]{1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}));
        answers.add(createRhythmWithBeats(new int[]{1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 1, 0, 0, 0}));
        answers.add(createRhythmWithBeats(new int[]{1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        
        rhythms.add(questions);
        rhythms.add(answers);
    }
    
    public void addRhythm (int type, int index, Bar b)
    {
        rhythms.get(type).add(index, b);
    }
    
    public Bar getRhythm (int type, int index)
    {
        return rhythms.get(type).get(index);
    }
    
    private Bar createRhythmWithBeats(int[] beats)
    {
        Bar r = new Bar();
        for (int i=0; i<15; i++)
        {
            if (beats[i] == 1)
            {
                Tick t = new Tick();
                t.addStop((short)60);
                t.addNote((short)60, (short)200);
                r.addTick(i, t);
            }
            else if (beats[i] == 2)
            {
                Tick t = new Tick();
                t.addStop((short)60);
                r.addTick(i,t);
            }
        }
        return r;
    }
    
}
