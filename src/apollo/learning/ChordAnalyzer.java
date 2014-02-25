package apollo.learning;

/**
 *
 * @author Martin
 */
public class ChordAnalyzer
{
    private Scribe scribe;
    
    public ChordAnalyzer()
    {
        scribe = new Scribe();
    }
    
    public void analyzeChords()
    {
        // Extract all variables
        scribe.extractDataFromChordProgressionFile("ChordProgs/Aeris.txt");
    }
}
