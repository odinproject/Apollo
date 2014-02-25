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
    
    public void analyzeFile(String fileName)
    {
        ChordProgSequence sequence = scribe.extractDataFromChordProgressionFile(fileName);
        analyzeSequence(sequence);
    }
    
    private void analyzeSequence(ChordProgSequence sequence)
    {
        
    }
}
