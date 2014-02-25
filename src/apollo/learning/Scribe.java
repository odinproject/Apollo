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
public class Scribe {
    
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
            System.out.println("REACTOR FAILURE IN THE PRIMARY ENGINE ROOM!");
        }
        
        return chordTypeIndex;
    }
}
