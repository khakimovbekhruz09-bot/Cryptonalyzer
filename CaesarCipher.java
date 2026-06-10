import java.util.Arrays;
import java.util.List;

public class CaesarCipher {

    private static final List<Character> ALPHABET = Arrays.asList(
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м',
            'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ',
            'ы', 'ь', 'э', 'я',
            '.', ',', '«', '»', ':', '!', '?', ' '
    );

    private int findCharIndex(char c) {
        for (int i = 0; i < ALPHABET.size(); i++) {
            if (ALPHABET.get(i) == c) {
                return i;
            }
        }
        return -1;
    }

    public String encrypt(String text, int key) {
        key = normalizeKey(key);
        StringBuilder result = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            int index = findCharIndex(currentChar);

            if (index != -1) {
                int newIndex = (index + key) % ALPHABET.size();
                result.append(ALPHABET.get(newIndex));
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }

    public String decrypt(String text, int key) {
        return encrypt(text, ALPHABET.size() - (key % ALPHABET.size()));
    }

    public int getAlphabetSize() {
        return ALPHABET.size();
    }

    public char[] getAlphabet() {
        char[] alphabet = new char[ALPHABET.size()];
        for (int i = 0; i < ALPHABET.size(); i++) {
            alphabet[i] = ALPHABET.get(i);
        }
        return alphabet;
    }

    private int normalizeKey(int key) {
        key = key % ALPHABET.size();
        if (key < 0) {
            key = key + ALPHABET.size();
        }
        return key;
    }
}
