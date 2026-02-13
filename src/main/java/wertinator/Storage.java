package wertinator;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Storage class that help with input/output handling for saving of data to the external txt file.
 */
public class Storage{
    private final Path filePath;

    public Storage(String relativePath){
        this.filePath = Paths.get(relativePath);
    }

    /**
     * makes sure the .txt and its parent folder exists by creating one if its not there
     * @throws IOException
     */
    private void ensureExists() throws IOException {
        Path parent = filePath.getParent();
        if (parent != null){
            Files.createDirectories(parent);
        }
        if (!Files.exists(filePath)){
            Files.createFile(filePath);
        }
    }

    /**
     * read the tasks that was left from last time and return the list of tasks
     * @return
     * @throws IOException
     */
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

    /**
     * Iterate through the Tasks in taskList, and saves each of the task into txt by lines
     * @param taskList
     * @throws IOException
     */
    public void saveTasks(TaskList taskList) throws IOException {
        List<String> lines = new ArrayList<String>();

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String line = toLine(task);
            lines.add(line);
        }

        saveLines(lines);
    }

    /**
     * read the txt file
     * @return
     * @throws IOException
     */
    public List<String> loadLines() throws IOException {
        ensureExists();
        return Files.readAllLines(filePath);
    }

    /**
     * save one line to txt file
     * @param lines
     * @throws IOException
     */
    public void saveLines(List<String> lines) throws  IOException{
        ensureExists();
        Files.write(filePath,lines);
    }

    /**
     * converts task object to line in save format for txt
     * @param task
     * @return
     */
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

    /**
     * process one line of the save data into task object
     * @param line
     * @return
     */
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
}
