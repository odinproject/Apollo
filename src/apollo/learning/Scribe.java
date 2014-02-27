package apollo.learning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class Scribe
{
    
    // The scribe can read and write files, and translate text into data 
    // structures for the learning algorithms
    public Scribe()
    {
        
    }
    
    public ArrayList<String> readTextFile(String fileName)
    {
        ArrayList<String> returnValue = new ArrayList<String>();
        FileReader file = null;

        try {
          file = new FileReader(fileName);
          BufferedReader reader = new BufferedReader(file);
          String line = "";
          while ((line = reader.readLine()) != null) {
              returnValue.add(line);
          }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    // Ignore issues during closing 
                }
            }
        }
        return returnValue;
      } 
    
    public void writeTextToFile(String fileName, String s)
    {
        FileWriter output = null;
        try {
            output = new FileWriter(fileName, true);
            output.write(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    // Ignore issues during closing
                }
            }
        }
    }
    
    public ChordWeights loadWeightsFromFile(String fileName)
    {
        ChordWeights weights = new ChordWeights();
        ArrayList<String> lines = readTextFile(fileName);
        
        // we start out in the energy section
        int section = 0;
        int fromType = 0;
        int relativePitchIndex = 0;
        
        for (int i=0; i<lines.size(); i++)
        {
            String line = lines.get(i);
            if (line.equalsIgnoreCase("Energy"))
            {
                section = 0;
            }
            else if (line.equalsIgnoreCase("Tension"))
            {
                section = 1;
            }
            else if (line.contains("From"))
            {
                String[] components = line.split(":");
                fromType = Integer.parseInt(components[1]);
                relativePitchIndex = 0;
            }
            else
            {
                String[] scores = line.split(",");
                for (int toType=0; toType<scores.length; toType++)
                {
                    double score = Double.parseDouble(scores[toType]);
                    weights.importWeight(section, fromType, relativePitchIndex, toType, score);
                }
                relativePitchIndex++;
            }
        }
        return weights;
    }
    
    public ChordProgSequence extractDataFromChordProgressionFile(String fileName)
    {
        ChordProgSequence sequence = new ChordProgSequence();
        
        ArrayList<String> lines = readTextFile(fileName);
        int lineCount = 0;
        for (String line : lines)
        {
            // first line, set energy and tension
            if (lineCount == 0)
            {
                String[] components = line.split(",");
                sequence.setEnergy(Double.parseDouble(components[0].substring(2)));
                sequence.setTension(Double.parseDouble(components[1].substring(2))); 
            }
            else if (lineCount == 1)
            {
                String[] components = line.split(",");
                for (int i=0; i<components.length; i++)
                {
                    sequence.addTag(components[i]);
                }
            }
            else
            {
                String[] block = line.split("#");
                
                // if the line contained a 'sharp'
                if (block.length > 2)
                {
                    // merge the second and third blocks
                    block[1] = block[1]+block[2];
                }
                
                String[] components = block[0].split("\\s+");
                // parse the pitch transition and type transition
                String[] data = components[0].split(",");
                int initialType = Integer.parseInt(data[0]);
                int pitchTransition = Integer.parseInt(data[1]);
                int finalType = Integer.parseInt(data[2]);
                
                sequence.addFromType(initialType);
                sequence.addToType(finalType);
                
                sequence.addRelativePitchTransition(pitchTransition);
                
                
                // extract the single chord pitch and type
                String[] chordData = block[1].split("\\s+");
                // if it's one of those chords with mutli-word names (7 minor...)
                if (chordData.length > 3)
                {
                    // merge the third and fourth blocks
                    chordData[2] = chordData[2] + " " + chordData[3];
                }
                
                int pitch = chordPitchIndexForString(chordData[1]);
                int type = chordTypeIndexForString(chordData[2]);
               
                sequence.addPitch(pitch);
                sequence.addType(type);
            }
            
            lineCount++;
        }
        
        return sequence;
    }
    
    /**
     * get the pitch index in the ChordDatabase for the given chord name
     * @param chordName the string value representing a chord's pitch (A, B, C...)
     * @return the ChordDatabase index for the given pitch
     */
    public int chordPitchIndexForString(String chordName)
    {
        int chordPitchType = 0;
        if (chordName.equalsIgnoreCase("C"))
        {
            chordPitchType = 0;
        }
        else if (chordName.equalsIgnoreCase("C#"))
        {
            chordPitchType = 1;
        }
        else if (chordName.equalsIgnoreCase("D"))
        {
            chordPitchType = 2;
        }
        else if (chordName.equalsIgnoreCase("D#"))
        {
            chordPitchType = 3;
        }
        else if (chordName.equalsIgnoreCase("E"))
        {
            chordPitchType = 4;
        }
        else if (chordName.equalsIgnoreCase("F"))
        {
            chordPitchType = 5;
        }
        else if (chordName.equalsIgnoreCase("F#"))
        {
            chordPitchType = 6;
        }
        else if (chordName.equalsIgnoreCase("G"))
        {
            chordPitchType = 7;
        }
        else if (chordName.equalsIgnoreCase("G#"))
        {
            chordPitchType = 8;
        }
        else if (chordName.equalsIgnoreCase("A"))
        {
            chordPitchType = 9;
        }
        else if (chordName.equalsIgnoreCase("A#"))
        {
            chordPitchType = 10;
        }
        else if (chordName.equalsIgnoreCase("B"))
        {
            chordPitchType = 11;
        }
        else
        {
            System.out.println("WE'VE BEEN HIT CAPTAIN! ERROR ON THE PORT BOW!");
        }
        
        return chordPitchType;
    }
    
    /**
     * get the type index in the ChordDatabase for the given chord type
     * @param chordType the string value representing a chord type (minor, major...)
     * @return the ChordDatabase index for the given type
     */
    public int chordTypeIndexForString(String chordType)
    {
        int chordTypeIndex = 0;
        
        if (chordType.equalsIgnoreCase("major"))
        {
            chordTypeIndex = 0;
        }
        else if (chordType.equalsIgnoreCase("minor"))
        {
            chordTypeIndex = 1;
        }
        else if (chordType.equalsIgnoreCase("seven"))
        {
            chordTypeIndex = 2;
        }
        else if (chordType.equalsIgnoreCase("7 major"))
        {
            chordTypeIndex = 3;
        }
        else if (chordType.equalsIgnoreCase("7 minor"))
        {
            chordTypeIndex = 4;
        }
        else if (chordType.equalsIgnoreCase("diminished"))
        {
            chordTypeIndex = 5;
        }
        else if (chordType.equalsIgnoreCase("augmented"))
        {
            chordTypeIndex = 6;
        }
        else if (chordType.equalsIgnoreCase("half-diminished"))
        {
            chordTypeIndex = 7;
        }
        else if (chordType.equalsIgnoreCase("minor-major"))
        {
            chordTypeIndex = 8;
        }
        else
        {
            System.out.println("REACTOR FAILURE IN THE PRIMARY ENGINE ROOM! : " + chordType);
        }
        
        return chordTypeIndex;
    }
}
