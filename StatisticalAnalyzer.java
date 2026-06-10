import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatisticalAnalyzer {

    private final CaesarCipher cipher = new CaesarCipher();
    private final FileManager fileManager = new FileManager();

    public String decryptByStatisticalAnalysis(String inputFile, String sampleFile) throws IOException {
        String encryptedText = fileManager.readFile(inputFile);

        if (sampleFile != null && !sampleFile.isEmpty() && fileManager.fileExists(sampleFile)) {
            String sampleText = fileManager.readFile(sampleFile);
            return decryptWithSample(encryptedText, sampleText);
        }
        return decryptWithoutSample(encryptedText);
    }

    private String decryptWithSample(String encrypted, String sample) {
        Map<Character, Double> sampleFreq = calculateFrequency(sample);
        Map<Character, Double> encryptedFreq = calculateFrequency(encrypted);

        int bestKey = 0;
        double minDifference = Double.MAX_VALUE;

        for (int key = 0; key < cipher.getAlphabetSize(); key++) {
            double difference = calculateDifference(encryptedFreq, sampleFreq, key);
            if (difference < minDifference) {
                minDifference = difference;
                bestKey = key;
            }
        }

        System.out.println("Найден ключ: " + bestKey + " (отличие: " + minDifference + ")");
        return cipher.decrypt(encrypted, bestKey);
    }

    private String decryptWithoutSample(String encrypted) {
        Map<Character, Integer> charCount = new HashMap<>();

        for (char ch : encrypted.toCharArray()) {
            if (isInAlphabet(ch)) {
                charCount.merge(ch, 1, Integer::sum);
            }
        }

        char mostFrequent = ' ';
        int maxCount = 0;
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }

        int spaceIndex = findIndexInAlphabet(' ');
        int mostFrequentIndex = findIndexInAlphabet(mostFrequent);
        int key = mostFrequentIndex - spaceIndex;
        if (key < 0) {
            key = key + cipher.getAlphabetSize();
        }

        System.out.println("Самый частый символ: '" + mostFrequent + "', предполагаемый ключ: " + key);
        return cipher.decrypt(encrypted, key);
    }

    private Map<Character, Double> calculateFrequency(String text) {
        Map<Character, Integer> count = new HashMap<>();
        for (char ch : text.toCharArray()) {
            count.merge(ch, 1, Integer::sum);
        }
        Map<Character, Double> frequency = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : count.entrySet()) {
            frequency.put(entry.getKey(), (double) entry.getValue() / text.length());
        }
        return frequency;
    }

    private double calculateDifference(Map<Character, Double> encryptedFreq,
                                       Map<Character, Double> sampleFreq,
                                       int key) {
        double difference = 0;
        char[] alphabet = cipher.getAlphabet();

        for (int i = 0; i < alphabet.length; i++) {
            int newIndex = (i + key) % alphabet.length;
            char decryptedChar = alphabet[newIndex];

            double encFreq = encryptedFreq.getOrDefault(alphabet[i], 0.0);
            double sampFreq = sampleFreq.getOrDefault(decryptedChar, 0.0);
            double diff = encFreq - sampFreq;
            difference += diff * diff;
        }
        return difference;
    }

    private boolean isInAlphabet(char c) {
        for (char ch : cipher.getAlphabet()) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }

    private int findIndexInAlphabet(char c) {
        char[] alphabet = cipher.getAlphabet();
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }
}
