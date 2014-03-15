/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.chordbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

/**
 *
 * @author Talonos
 */
public class ChordProgLearner 
{
    
    protected static ChordNode[][]_intenseChordNodeLibrary;
    
    protected static ChordNode[][]_relaxedChordNodeLibrary;
    
    private static int intenseTransitions = 0;
    private static int relaxedTransitions = 0;
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        _intenseChordNodeLibrary = new ChordDatabase()._chordNodeLibrary;
        _relaxedChordNodeLibrary = new ChordDatabase()._chordNodeLibrary;
        
        clearTransitions(_intenseChordNodeLibrary);
        clearTransitions(_relaxedChordNodeLibrary);
        
        File rootDir = new File("./ChordProgs");
        for (File f : rootDir.listFiles())
        {
            processFile(f);
        }
        System.out.println("Total "+intenseTransitions+" intense transitions");
        System.out.println("Total "+relaxedTransitions+" intense transitions");
        ObjectOutputStream o;
        o = new ObjectOutputStream(new FileOutputStream("./intenseDatabase.cpd"));
        o.writeObject(_intenseChordNodeLibrary);
        o = new ObjectOutputStream(new FileOutputStream("./relaxedDatabase.cpd"));
        o.writeObject(_relaxedChordNodeLibrary);
    }

    private static void processFile(File f) throws FileNotFoundException, IOException 
    {
        System.out.println("Processing "+f.getName() +":");
        BufferedReader r = new BufferedReader(new FileReader(f));
        String headerInfo = r.readLine();
        StringTokenizer tokens = new StringTokenizer(headerInfo, " ,:");
        System.out.print(" - Tokens: ");
        while (tokens.hasMoreTokens())
        {
            System.out.print(tokens.nextToken()+" | ");
        }
        tokens = new StringTokenizer(headerInfo, ": ,");
        if (!tokens.nextToken().equals("E"))
        {
            System.err.println("Corrupt file: "+f.getName());
        }
        double energy = Double.parseDouble(tokens.nextToken());
        if (!tokens.nextToken().equals("I"))
        {
            System.err.println("Corrupt file: "+f.getName());
        }
        double intensity = Double.parseDouble(tokens.nextToken());
        
        if (intensity == 0)
        {
            System.out.println(" - We don't care about it: 0 intensity:");
            return;
        }
        
        //Skip the next line. It contains tags, which I don't care about.
        r.readLine();
        
        //Start processing chordTransitions.
        ChordNode[][] library = (intensity > 0? _intenseChordNodeLibrary:_relaxedChordNodeLibrary);
        
        String stringToProcess = r.readLine(); 
        int usefulTransitions = 0;
        while(stringToProcess != null)
        {
            tokens = new StringTokenizer(stringToProcess, ", ");
            int oldType = Integer.parseInt(tokens.nextToken());
            int step = Integer.parseInt(tokens.nextToken());
            int newType = Integer.parseInt(tokens.nextToken());
            
            if ((oldType==newType && step == 0)||(oldType==-1)||oldType == 8 || newType == 8)
            {
                //Useless transition.
                stringToProcess = r.readLine(); 
                continue;
            }
            
            for (int tone = 0; tone < 12; tone++)
            {
                int newTone = (tone+step+24)%12;
                library[tone][oldType].addTransition(library[newTone][newType]);
            }
            usefulTransitions++;
            stringToProcess = r.readLine(); 
        }
        System.out.println(" - Added "+usefulTransitions+" useful transitions to the "+
                (intensity > 0?"intense":"relaxed")+" database.");
        if (intensity > 0)
        {
            intenseTransitions += usefulTransitions;
        }
        else
        {
            relaxedTransitions += usefulTransitions;
        }
        
    }

    private static void clearTransitions(ChordNode[][] library) 
    {
        for (int x = 0; x < library.length; x++)
        {
            for (int y = 0; y < library[0].length; y++)
            {
                library[x][y].clearTransitions();
            }
        }
    }
}
