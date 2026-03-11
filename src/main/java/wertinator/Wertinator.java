package wertinator;

import wertinator.parser.Parser;
import wertinator.storage.Storage;
import wertinator.task.Task;
import wertinator.task.TaskList;
import wertinator.ui.Ui;

import java.io.IOException;
import java.util.List;
import java.util.Random;


/**
 * Main class.
 */
public class Wertinator {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Class constructor.
     *
     * @param filePath
     */
    public Wertinator(String filePath) {
        assert filePath != null : "File path should not be null";

        ui = new Ui();
        storage = new Storage(filePath);
        // load tasks
        try {
            tasks = new TaskList(storage.loadTasks());
        }
        catch (Exception e) {
            ui.showError("Theres some problem with the saved data mate, I can't load it.");
            tasks = new TaskList();
        }
    }

    /**
     * main() method, doesnt really need any input. just constructs a Wertinator instance based on saved data and run it.
     *
     * @param args
     */
    public static void main(String[] args) {
        new Wertinator("data/wertinator.Wertinator.txt").run();
    }

    /**
     *
     */
    public void run() {
        System.out.println(ui.showWelcome());

        boolean sayGoodBye = false;

        while (!sayGoodBye) {
            String fullCommand = ui.readCommand();
            System.out.println(ui.showLine());

            String response = getResponse(fullCommand);
            System.out.println(response);

            if (Parser.getCommandWord(fullCommand).equals("bye")) {
                sayGoodBye = true;
            }
        }
    }

    public String getResponse(String input) {
        assert input != null : "User input should not be null";

        String commandWord = Parser.getCommandWord(input);
        String arguments = Parser.getArguments(input);

        if (commandWord.equals("bye")) {
            return ui.showGoodbye();
        }
        else if (commandWord.equals("list")) {
            return handleList();
        }
        else if (commandWord.equals("todo")) {
            return handleTodo(arguments);
        }
        else if (commandWord.equals("deadline")) {
            return handleDeadline(arguments);
        }
        else if (commandWord.equals("event")) {
            return handleEvent(arguments);
        }
        else if (commandWord.equals("done")) {
            return handleDone(arguments);
        }
        else if (commandWord.equals("undo")) {
            return handleUndo(arguments);
        }
        else if (commandWord.equals("delete")) {
            return handleDelete(arguments);
        }
        else if (commandWord.equals("find")) {
            return handleFind(arguments);
        }
        else if (commandWord.equals("cheer")) {
            List<String> quotes = storage.loadCheerQuotes();

            if (quotes.isEmpty()) {
                return ui.showCheer("No cheer quotes found. Go and add some to data\\cheer.txt.");
            } else {
                Random random = new Random();
                int index = random.nextInt(quotes.size());
                return ui.showCheer(quotes.get(index));
            }
        }
        else {
            return ui.showError("Unknown command.");
        }
    }

    /**
     * handles "list"
     */
    private String handleList() {
        if (tasks.size() == 0) {
            return "Nothing to do yet. How nice!";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * handles "todo ..."
     *
     * @param arguments
     */
    private String handleTodo(String arguments) {
        if (arguments.isBlank()) {
            return ui.showError("To do what? Broman.");
        }

        Task task = new Task(arguments.trim(), Task.TaskTypes.TODO);
        tasks.add(task);
        saveTasksSafely();

        return "Gotcha, added this to the todo list: " + task;
    }

    /**
     * handles "deadline ... /...yyyy-mm-dd"
     *
     * @param arguments
     */
    private String handleDeadline(String arguments) {
        String[] parts = arguments.split(" /by ", 2);

        if (parts.length < 2) {
            return ui.showError("Follow the correct format man. like: deadline taskname1 /by yyyy-mm-dd");
        }

        String taskDescription = parts[0].trim();
        String dateString = parts[1].trim();

        if (taskDescription.isEmpty()) {
            return ui.showError("I feel like some deadline is coming, but I cant tell what it is.\n"
                    + "Hmm... do you know whats coming?");
        }

        Task task = new Task(taskDescription, Task.TaskTypes.DEADLINE);

        try {
            task.setByDateFromString(dateString);
        } catch (IllegalArgumentException e) {
            return ui.showError(e.getMessage());
        }

        tasks.add(task);
        saveTasksSafely();
        return "Roger in the dodger, ya better hurry up for this: " + task;
    }

    /**
     * handles "event ... /...yyyy-mm-dd"
     *
     * @param arguments
     */
    private String handleEvent(String arguments) {
        String[] fromParts = arguments.split(" /from ", 2);

        if (fromParts.length < 2) {
            return ui.showError("Try using the proper format: event <task> /from yyyy-mm-dd /to yyyy-mm-dd");
        }

        String taskDescription = fromParts[0].trim();
        String fromAndToPart = fromParts[1].trim();

        String[] toParts = fromAndToPart.split(" /to ", 2);

        if (toParts.length < 2) {
            return ui.showError("Try using the proper format: event <task> /from yyyy-mm-dd /to yyyy-mm-dd");
        }

        String fromString = toParts[0].trim();
        String toString = toParts[1].trim();

        if (taskDescription.isEmpty()) {
            return ui.showError("Ya got me excited for a nothing event, how sad.");
        }

        if (fromString.isEmpty() || toString.isEmpty()) {
            return ui.showError("Event needs both a start and end date.");
        }

        Task task = new Task(taskDescription, Task.TaskTypes.EVENT);

        try {
            task.setFromDateFromString(fromString);
            task.setToDateFromString(toString);
        }
        catch (IllegalArgumentException e) {
            return ui.showError(e.getMessage());
        }

        tasks.add(task);
        saveTasksSafely();
        return "Ooh, this thing is coming up man: " + task;
    }



    /**
     * handles "mark ..."
     * marks specified numbered task as done
     *
     * @param arguments
     */

    private String handleFind(String arguments) {
        if (arguments.isBlank()) {
            return ui.showError("Finding nothing is not exactly my specialty, ya know?");
        }

        TaskList matchingTasks = tasks.findMatching(arguments);

        if (matchingTasks.size() == 0) {
            return "Heres what I found:\nNothing matched.";
        }

        StringBuilder sb = new StringBuilder("Heres what I found:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append(i + 1).append(". ").append(matchingTasks.get(i));
            if (i < matchingTasks.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private String handleDone(String arguments) {
        int index = parseIndex(arguments);
        if (index == -1) {
            return "";
        }

        Task task = tasks.get(index);
        task.markAsDone();
        saveTasksSafely();

        return "Nice, get this outta the way: " + task;
    }

    /**
     * handles "unmark ..."
     * marks specified numbered task as undone (tasks are undone by default)
     *
     * @param arguments
     */
    private String handleUndo(String arguments) {
        int index = parseIndex(arguments);
        if (index == -1) {
            return "";
        }

        Task task = tasks.get(index);
        task.markAsUndone();
        saveTasksSafely();

        return "You lied? How is this not done yet?: " + task;
    }

    /**
     * handles "delete ..."
     * deletes specified numbered task from the list of tasks.
     *
     * @param arguments
     */
    private String handleDelete(String arguments) {
        int index = parseIndex(arguments);
        if (index == -1) {
            return "";
        }

        Task removed = tasks.remove(index);
        saveTasksSafely();

        return "I'll give you one last look of this, say goodbye to: " + removed;
    }

    // -------------------- helpers --------------------

    /**
     * finds index from mark, unmark, delete operations
     *
     * @param arguments
     * @return
     */
    private int parseIndex(String arguments) {
        String trimmed = arguments.trim();

        if (trimmed.isEmpty()) {
            return -1;
        }

        int oneBasedIndex;

        try {
            oneBasedIndex = Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            return -1;
        }

        int zeroBasedIndex = oneBasedIndex - 1;

        if (zeroBasedIndex < 0 || zeroBasedIndex >= tasks.size()) {
            return -1;
        }

        return zeroBasedIndex;
    }

    /**
     * save task into .txt save file
     */
    private void saveTasksSafely() {
        try {
            storage.saveTasks(tasks);
        } catch (IOException e) {
            ui.showError("Some problem with the save data, check it out mate.");
        }
    }
}