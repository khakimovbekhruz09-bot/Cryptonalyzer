import java.nio.file.Files;
import java.nio.file.Paths;

public class Validator {

    public boolean isFileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    public boolean isValidKey(int key, int alphabetSize) {
        return key >= 0 && key < alphabetSize;
    }

    public int normalizeKey(int key, int alphabetSize) {
        key = key % alphabetSize;
        if (key < 0) {
            key = key + alphabetSize;
        }
        return key;
    }

    public int parseShift(String raw, int alphabetSize) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Сдвиг не может быть пустым.");
        }
        try {
            return normalizeKey(Integer.parseInt(raw.trim()), alphabetSize);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Сдвиг должен быть целым числом.");
        }
    }
}
