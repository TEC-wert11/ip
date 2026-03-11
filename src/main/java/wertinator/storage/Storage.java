package wertinator.storage;

import wertinator.task.Task;
import wertinator.task.TaskList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Storage class that help with input/output handling for saving of data to the external txt file.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a storage object using the given relative file path.
     *
     * @param relativePath relative path to save file
     */
    public Storage(String relativePath) {
        assert relativePath != null : "Storage file path should not be null";
        this.filePath = Paths.get(relativePath);
    }

    /**
     * Makes sure the text file and its parent folder exist.
     *
     * @throws IOException if file creation fails
     */
    private void ensureExists() throws IOException {
        Path parent = filePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Reads saved tasks and returns them as a list.
     *
     * @return list of loaded tasks
     * @throws IOException if reading fails
     */
    public List<Task> loadTasks() throws IOException {
        List<String> lines = loadLines();
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Task task = parseTaskFromLine(line);

            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Saves all tasks from a task list into the text file.
     *
     * @param taskList task list to save
     * @throws IOException if saving fails
     */
    public void saveTasks(TaskList taskList) throws IOException {
        assert taskList != null : "Task list to save should not be null";
        List<String> lines = new ArrayList<>();

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String line = toLine(task);
            lines.add(line);
        }

        saveLines(lines);
    }

    /**
     * Reads all lines from the save file.
     *
     * @return list of lines
     * @throws IOException if reading fails
     */
    public List<String> loadLines() throws IOException {
        ensureExists();
        return Files.readAllLines(filePath);
    }

    /**
     * Writes lines into the save file.
     *
     * @param lines lines to write
     * @throws IOException if writing fails
     */
    public void saveLines(List<String> lines) throws IOException {
        assert lines != null : "Lines to save should not be null";
        ensureExists();
        Files.write(filePath, lines);
    }

    /**
     * Converts a task into one save-file line.
     *
     * @param task task to convert
     * @return formatted save line
     */
    private String toLine(Task task) {
        assert task != null : "Task should not be null when converting to save line";

        String doneFlag;
        if (task.isDone()) {
            doneFlag = "1";
        } else {
            doneFlag = "0";
        }

        if (task.getTaskType() == Task.TaskTypes.TODO) {
            return task.getTaskType() + " | " + doneFlag + " | " + task.getName() + " | ";
        }

        if (task.getTaskType() == Task.TaskTypes.DEADLINE) {
            String byField = "";
            if (task.getByDate() != null) {
                byField = task.getByDate().toString();
            }
            return task.getTaskType() + " | " + doneFlag + " | " + task.getName() + " | " + byField;
        }

        if (task.getTaskType() == Task.TaskTypes.EVENT) {
            String fromField = "";
            String toField = "";

            if (task.getFromDate() != null) {
                fromField = task.getFromDate().toString();
            }
            if (task.getToDate() != null) {
                toField = task.getToDate().toString();
            }

            return task.getTaskType() + " | " + doneFlag + " | " + task.getName()
                    + " | " + fromField + " | " + toField;
        }

        return task.getTaskType() + " | " + doneFlag + " | " + task.getName() + " | ";
    }

    /**
     * Processes one line of save data into a task object.
     *
     * @param line one save-file line
     * @return parsed task, or null if invalid
     */
    private Task parseTaskFromLine(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }

        String[] parts = line.split(" \\| ", -1);
        if (parts.length < 3) {
            return null;
        }

        Task.TaskTypes type = Task.TaskTypes.valueOf(parts[0]);
        boolean isDone = parts[1].equals("1");
        String name = parts[2];

        Task task = new Task(name, type);
        task.setDone(isDone);

        try {
            if (type == Task.TaskTypes.DEADLINE && parts.length >= 4) {
                String byString = parts[3].trim();
                if (!byString.isEmpty()) {
                    task.setByDateFromString(byString);
                }
            } else if (type == Task.TaskTypes.EVENT && parts.length >= 5) {
                String fromString = parts[3].trim();
                String toString = parts[4].trim();

                if (!fromString.isEmpty()) {
                    task.setFromDateFromString(fromString);
                }
                if (!toString.isEmpty()) {
                    task.setToDateFromString(toString);
                }
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        return task;
    }

    /**
     * Loads cheer quotes from cheer.txt.
     *
     * @return list of cheer quotes
     */
    public List<String> loadCheerQuotes() {
        ArrayList<String> quotes = new ArrayList<>();
        Path path = Path.of("data", "cheer.txt");

        if (!Files.exists(path)) {
            return quotes;
        }

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                if (!line.isEmpty()) {
                    quotes.add(line);
                }
            }
        } catch (IOException e) {
            return quotes;
        }

        return quotes;
    }
}