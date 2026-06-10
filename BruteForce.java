import java.io.IOException;

public class BruteForce {

    private static final char[] VOWELS = {
            'а', 'е', 'ё', 'и', 'й', 'о', 'у', 'ы', 'э', 'ю', 'я'
    };
    private static final char[] PUNCTUATION = {
            '.', ',', '!', '?', ':', '"', '«', '»', '\''
    };

    private final CaesarCipher cipher = new CaesarCipher();
    private final FileManager fileManager = new FileManager();

    public String decryptByBruteForce(String inputFile, String sampleFile) throws IOException {
        String encryptedText = fileManager.readFile(inputFile);

        String bestDecryption = "";
        int bestScore = -1;
        int bestKey = 0;

        for (int key = 0; key < cipher.getAlphabetSize(); key++) {
            String decrypted = cipher.decrypt(encryptedText, key);
            int score = evaluateText(decrypted);

            if (score > bestScore) {
                bestScore = score;
                bestDecryption = decrypted;
                bestKey = key;
            }
        }

        System.out.println("Найден ключ: " + bestKey + " (оценка: " + bestScore + ")");
        return bestDecryption;
    }

    private int evaluateText(String text) {
        int score = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                score++;
            }
        }

        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));
            for (char vowel : VOWELS) {
                if (c == vowel) {
                    score++;
                    break;
                }
            }
        }

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            for (char punct : PUNCTUATION) {
                if (c == punct) {
                    score += 2;
                    break;
                }
            }
        }

        return score;
    }
}
