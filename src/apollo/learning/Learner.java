package apollo.learning;

/**
 * Main Learning Application. Defers to ChordAnalyzer.
 * @author Martin
 */
public class Learner
{
    public static void main(String[] args)
    {
        ChordAnalyzer analyzer = new ChordAnalyzer();
        analyzer.analyzeFile("ChordProgs/Aeris.txt");
        analyzer.analyzeFile("ChordProgs/DeathWaltz.txt");
        analyzer.analyzeFile("ChordProgs/FF7Win.txt");
        analyzer.analyzeFile("ChordProgs/FF8102.txt");
        analyzer.analyzeFile("ChordProgs/FF8104.txt");
        analyzer.analyzeFile("ChordProgs/FF8105.txt");
        analyzer.analyzeFile("ChordProgs/FF8106.txt");
        analyzer.analyzeFile("ChordProgs/FF8114.txt");
        analyzer.analyzeFile("ChordProgs/FF8116.txt");
        analyzer.analyzeFile("ChordProgs/FF8118.txt");
        analyzer.analyzeFile("ChordProgs/FF8120.txt");
        analyzer.analyzeFile("ChordProgs/FF8121.txt");
        analyzer.analyzeFile("ChordProgs/FF8122.txt");
        analyzer.analyzeFile("ChordProgs/FF8202.txt");
        analyzer.analyzeFile("ChordProgs/FF8203.txt");
        analyzer.analyzeFile("ChordProgs/FF8204.txt");
        analyzer.analyzeFile("ChordProgs/FF8206.txt");
        analyzer.analyzeFile("ChordProgs/FF8207.txt");
        analyzer.analyzeFile("ChordProgs/FF8307.txt");
        analyzer.analyzeFile("ChordProgs/GreenHill.txt");
        analyzer.analyzeFile("ChordProgs/Kakariko.txt");
        analyzer.analyzeFile("ChordProgs/LostWoods.txt");
        analyzer.analyzeFile("ChordProgs/Pirate.txt");
        analyzer.analyzeFile("ChordProgs/pokemon.txt");
        analyzer.analyzeFile("ChordProgs/SF2_01.txt");
        analyzer.analyzeFile("ChordProgs/SF2_02.txt");
        analyzer.analyzeFile("ChordProgs/SF2_03.txt");
        analyzer.analyzeFile("ChordProgs/SF2_04.txt");
        analyzer.analyzeFile("ChordProgs/SSBB.txt");
        analyzer.analyzeFile("ChordProgs/TakeBackNight.txt");
        // NOT FINAL FANTASY
        analyzer.analyzeFile("ChordProgs/KH1_01.txt");
        analyzer.analyzeFile("ChordProgs/KH1_02.txt");
        analyzer.analyzeFile("ChordProgs/KH1_03.txt");
        analyzer.analyzeFile("ChordProgs/KH1_04.txt");
        analyzer.analyzeFile("ChordProgs/KH1_05.txt");
        analyzer.analyzeFile("ChordProgs/KH1_06.txt");
        analyzer.analyzeFile("ChordProgs/KH1_07.txt");
        analyzer.analyzeFile("ChordProgs/KH1_08.txt");
        analyzer.analyzeFile("ChordProgs/KH1_10.txt");
        analyzer.analyzeFile("ChordProgs/KH1_11.txt");
        analyzer.analyzeFile("ChordProgs/KH1_12.txt");
        analyzer.analyzeFile("ChordProgs/KH1_13.txt");
        analyzer.analyzeFile("ChordProgs/KH1_14.txt");
        analyzer.analyzeFile("ChordProgs/KH1_15.txt");
        analyzer.analyzeFile("ChordProgs/KH1_16.txt");
        
//        analyzer.printFindings();
        analyzer.serializeWeights();
        
    }
}
