import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public String readFile(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
    }

    public void writeFile(String content, String filePath) throws IOException {
        Files.writeString(Path.of(filePath), content == null ? "" : content, StandardCharsets.UTF_8);
    }

    public boolean fileExists(String filePath) {
        return filePath != null && !filePath.isBlank() && Files.isRegularFile(Paths.get(filePath));
    }
}
