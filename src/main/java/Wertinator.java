import java.io.IOException;

public class Wertinator {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Wertinator(String filePath){
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

    public static void main(String[] args) {
        new Wertinator("data/Wertinator.txt").run();
    }

    public void run(){
        ui.showWelcome();

        boolean sayGoodBye = false;

        while (!sayGoodBye){
            String fullCommand = ui.readCommand();
            String commandWord = Parser.getCommandWord(fullCommand);
            String arguments = Parser.getArguments(fullCommand);

            ui.showLine();

            if (commandWord.equals("bye")) {
                ui.showGoodbye();
                sayGoodBye = true;
            }
            else if (commandWord.equals("list")) {
                handleList();
            }
            else if (commandWord.equals("todo")) {
                handleTodo(arguments);
            }
            else if (commandWord.equals("deadline")) {
                handleDeadline(arguments);
            }
            else if (commandWord.equals("event")) {
                handleEvent(arguments);
            }
            else if (commandWord.equals("done")) {
                handleDone(arguments);
            }
            else if (commandWord.equals("undo")) {
                handleUndo(arguments);
            }
            else if (commandWord.equals("delete")) {
                handleDelete(arguments);
            }
            else {
                ui.showError("Unknown command.");
            }
        }
    }

    private void handleList() {
        if (tasks.size() == 0) {
            System.out.println("Nothing to do yet. How nice!");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private void handleTodo(String arguments) {
        if (arguments.isBlank()) {
            ui.showError("To do what? Broman.");
            return;
        }

        Task task = new Task(arguments.trim(), Task.TaskTypes.TODO);
        tasks.add(task);

        System.out.println("Gotcha, added this to the todo list: " + task);
        saveTasksSafely();
    }

    private void handleDeadline(String arguments) {
        String[] parts = arguments.split(" /by ", 2);

        if (parts.length < 2) {
            ui.showError("Follow the correct format man. like: deadline taskname1 /by yyyy-mm-dd");
            return;
        }

        String taskDescription = parts[0].trim();
        String dateString = parts[1].trim();

        if (taskDescription.isEmpty()) {
            ui.showError("I feel like some deadline is coming, but I cant tell what it is.\n"
                    + "Hmm... do you know whats coming?");
            return;
        }

        Task task = new Task(taskDescription, Task.TaskTypes.DEADLINE);

        try {
            task.setDateFromString(dateString);
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
            return;
        }

        tasks.add(task);
        System.out.println("Roger in the dodger, ya better hurry up for this: " + task);
        saveTasksSafely();
    }

    private void handleEvent(String arguments) {
        String[] parts = arguments.split(" /at ", 2);

        if (parts.length < 2) {
            ui.showError("Try using the proper format: event <task> /at yyyy-mm-dd");
            return;
        }

        String taskDescription = parts[0].trim();
        String dateString = parts[1].trim();

        if (taskDescription.isEmpty()) {
            ui.showError("Ya got me excited for a nothing event, how sad.");
            return;
        }

        Task task = new Task(taskDescription, Task.TaskTypes.EVENT);

        try {
            task.setDateFromString(dateString);
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
            return;
        }

        tasks.add(task);
        System.out.println("Ooh, this thing is coming up man: " + task);
        saveTasksSafely();
    }

    private void handleDone(String arguments) {
        int index = parseIndex(arguments);
        if (index == -1) {
            return;
        }

        Task task = tasks.get(index);
        task.markAsDone();

        System.out.println("Nice, get this outta the way: " + task);
        saveTasksSafely();
    }

    private void handleUndo(String arguments) {
        int index = parseIndex(arguments);
        if (index == -1) {
            return;
        }

        Task task = tasks.get(index);
        task.markAsUndone();

        System.out.println("You lied? How is this not done yet?: " + task);
        saveTasksSafely();
    }

    private void handleDelete(String arguments) {
        int index = parseIndex(arguments);
        if (index == -1) {
            return;
        }

        Task removed = tasks.remove(index);
        System.out.println("I'll give you one last look of this, say goodbye to: " + removed);
        saveTasksSafely();
    }

    // -------------------- helpers --------------------

    private int parseIndex(String arguments) {
        String trimmed = arguments.trim();

        if (trimmed.isEmpty()) {
            ui.showError("There ain't no Task number?");
            return -1;
        }

        int oneBasedIndex;

        try {
            oneBasedIndex = Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            ui.showError("Number! Number do you know it.");
            return -1;
        }

        int zeroBasedIndex = oneBasedIndex - 1;

        if (zeroBasedIndex < 0 || zeroBasedIndex >= tasks.size()) {
            ui.showError("Neh, I'm pretty sure that's not on our list.");
            return -1;
        }

        return zeroBasedIndex;
    }

    private void saveTasksSafely() {
        try {
            storage.saveTasks(tasks);
        } catch (IOException e) {
            ui.showError("Some problem with the save data, check it out mate.");
        }
    }
}



