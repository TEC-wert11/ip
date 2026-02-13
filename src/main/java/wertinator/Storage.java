package wertinator;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//class wertinator.Storage to handle the input/output of the progress
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

    public List<Task> loadTasks() throws IOException{
        List<String> lines = loadLines();
        List<Task> tasks = new ArrayList<Task>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Task task = parseTaskFromLine(line);

            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void saveTasks(TaskList taskList) throws IOException {
        List<String> lines = new ArrayList<String>();

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String line = toLine(task);
            lines.add(line);
        }

        saveLines(lines);
    }

    public List<String> loadLines() throws IOException {
        ensureExists();
        return Files.readAllLines(filePath);
    }

    public void saveLines(List<String> lines) throws  IOException{
        ensureExists();
        Files.write(filePath,lines);
    }

    private String toLine(Task task) {

        String doneFlag;
        if (task.isDone()) {
            doneFlag = "1";
        }
        else {
            doneFlag = "0";
        }

        String dateField = "";

        if (task.getDate() != null) {
            dateField = task.getDate().toString(); // yyyy-mm-dd
        }

        return task.getTaskType() + " | " + doneFlag + " | " + task.getName() + " | " + dateField;
    }

    private Task parseTaskFromLine(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }

        String[] parts = line.split(" \\| ", -1);
        //if somehow theres less than 3 things (tasktype, doneness, name), the line is invalid
        if (parts.length < 3) {
            return null;
        }

        Task.TaskTypes type = Task.TaskTypes.valueOf(parts[0]);
        boolean isDone = parts[1].equals("1");
        String name = parts[2];

        Task task = new Task(name, type);
        task.setDone(isDone);

        if (parts.length >= 4) {
            String dateString = parts[3].trim();
            if (!dateString.isEmpty()) {
                if (type == Task.TaskTypes.DEADLINE || type == Task.TaskTypes.EVENT) {
                    try {
                        task.setDateFromString(dateString);
                    }
                    catch (IllegalArgumentException e) {
                        //ignore
                    }
                }
            }
        }
        return task;
    }

    public List<String> loadCheerQuotes() {

        ArrayList<String> quotes = new ArrayList<>();
        Path path = Path.of("data", "cheer.txt");

        // If file does not exist, just return empty list
        if (!Files.exists(path)) {
            return quotes;
        }

        try {
            List<String> lines = Files.readAllLines(path,StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                if (!line.isEmpty()) {
                    quotes.add(line);
                }
            }
        } catch (IOException e) {
            // If something goes wrong reading file, return empty list
            return quotes;
        }

        return quotes;
    }


}
