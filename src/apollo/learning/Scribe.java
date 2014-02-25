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
     * @param chordName
     * @return 
     */
    public int chordPitchIndexForString(String chordName)
    {
        int chordPitchType = 0;
        if (chordName.equals("C"))
        {
            chordPitchType = 0;
        }
        else if (chordName.equals("C#"))
        {
            chordPitchType = 1;
        }
        else if (chordName.equals("D"))
        {
            chordPitchType = 2;
        }
        else if (chordName.equals("D#"))
        {
            chordPitchType = 3;
        }
        else if (chordName.equals("E"))
        {
            chordPitchType = 4;
        }
        else if (chordName.equals("F"))
        {
            chordPitchType = 5;
        }
        else if (chordName.equals("F#"))
        {
            chordPitchType = 6;
        }
        else if (chordName.equals("G"))
        {
            chordPitchType = 7;
        }
        else if (chordName.equals("G#"))
        {
            chordPitchType = 8;
        }
        else if (chordName.equals("A"))
        {
            chordPitchType = 9;
        }
        else if (chordName.equals("A#"))
        {
            chordPitchType = 10;
        }
        else if (chordName.equals("B"))
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
     * @param chordType
     * @return 
     */
    public int chordTypeIndexForString(String chordType)
    {
        int chordTypeIndex = 0;
        
        if (chordType.equals("major"))
        {
            chordTypeIndex = 0;
        }
        else if (chordType.equals("minor"))
        {
            chordTypeIndex = 1;
        }
        else if (chordType.equals("seven"))
        {
            chordTypeIndex = 2;
        }
        else if (chordType.equals("7 major"))
        {
            chordTypeIndex = 3;
        }
        else if (chordType.equals("7 minor"))
        {
            chordTypeIndex = 4;
        }
        else if (chordType.equals("diminished"))
        {
            chordTypeIndex = 5;
        }
        else if (chordType.equals("augmented"))
        {
            chordTypeIndex = 6;
        }
        else if (chordType.equals("half-diminished"))
        {
            chordTypeIndex = 7;
        }
        else if (chordType.equals("minor-major"))
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
