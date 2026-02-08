import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//class Storage to handle the input/output of the progress
public class Storage{
    private final Path filePath;

    public Storage(String relativePath){
        this.filePath = Paths.get(relativePath);
    }

    private void ensureExists() throws IOException {
        Path parent = filePath.getParent();
        if (parent != null){
            Files.createDirectories(parent);
        }
        if (!Files.exists(filePath)){
            Files.createFile(filePath);
        }
    }

    public List<String> loadLines() throws IOException {
        ensureExists();
        return Files.readAllLines(filePath);
    }

    public void saveLines(List<String> lines) throws  IOException{
        ensureExists();
        Files.write(filePath,lines);
    }
}
