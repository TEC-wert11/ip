import java.util.ArrayList;
import java.util.List;
import java.util.Scanner ;
import java.io.IOException;

public class Wertinator {

    private ArrayList<Task> listOfTasks = new ArrayList<>();
    private static final String DATA_PATH = "data/Wertinator.txt";
    private final Storage storage = new Storage(DATA_PATH);

    public static void main(String[] args) {
        new Wertinator().run();
    }

    private void run(){
        System.out.println("Wassup guys! Wuchu guys doin? \n"
        + "This is Wertinator, back doing some more werting action! \n"
        + "What ya wanna do today?" );
        System.out.println();

        Scanner input = new Scanner(System.in);

        try {
            List<String> lines = storage.loadLines();
            for (String savedLine : lines) {
                Task t = parseTaskFromLine(savedLine);
                if (t != null) {
                    listOfTasks.add(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load saved tasks.");
        }

        while(true) {
            String line = input.nextLine();

            String[] sentence = splitInstruction(line);
            String command = sentence[0];
            boolean listHasBeenModified = false;

            if (sentence.length == 1){
                if (line.equals("bye")) {
                    break;
                }
                else if (command.equals("list")) {
                    this.printList();
                }
                else {
                    System.out.println("What did you say again?");
                }
            }

            if (sentence.length == 2) {
                String taskInformation = sentence[1];

                if (command.equals("mark")) {
                    try {
                        int index = Integer.parseInt(taskInformation) - 1;
                        if (index > listOfTasks.size() || index < 0 ){
                            System.out.println("Check the Index again?");
                        }
                        else {
                            this.doneTask(index);
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println("Bro, thats not a number");
                        continue;
                    }
                }
                else if (command.equals("unmark")) {
                    try {
                        int index = Integer.parseInt(taskInformation) - 1;
                        if (index > listOfTasks.size() || index < 0){
                            System.out.println("Check your number again");
                        }
                        else{
                            this.undoTask(index);
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println("Key a number. A number do you know it?");
                        continue;
                    }
                }
                else if (command.equals("delete")){
                    if (listOfTasks.size()==0){
                        System.out.println("Theres nothing. What you trying to delete?");
                    }
                    else{
                        try {
                            int index = Integer.parseInt(taskInformation) - 1;
                            if (index > listOfTasks.size() || index < 0){
                                System.out.println("Cant delete something thats not there Bro.");
                            }
                            else{
                                System.out.println("removing this one. You sure you dont need it no more?");
                                System.out.println(listOfTasks.get(index));
                                this.deleteTask(index);
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Key a number. A number do you know it?");
                            continue;
                        }
                    }
                }
                else if (command.equals("todo")) {
                    System.out.println("Aight man, one more thing on the to do list.");
                    String taskName = taskInformation.trim();
                    Task newTask = new Task(taskName, Task.TaskTypes.TODO);
                    this.listOfTasks.add(newTask);
                    saveSafely();
                }
                else if (command.equals("deadline")) {
                    String[] information = taskInformation.trim().split("/", 2);
                    String taskName = information[0].trim();
                    if (information.length==2) {
                        String remarks = information[1].trim();
                        if (remarks.isBlank()){
                            System.out.println("You didn't tell me when its due man.");
                        }
                        else {
                            System.out.println("Deadline coming, ya better hurry de hail up.");
                            Task newTask = new Task(taskName, Task.TaskTypes.DEADLINE);
                            newTask.setRemarks(remarks);
                            this.listOfTasks.add(newTask);
                            saveSafely();
                        }
                    }
                    else {
                        System.out.println("Whats the deadline bro?");
                    }
                }
                else if (command.equals("event")) {
                    String[] information = taskInformation.trim().split("/", 2);
                    String taskName = information[0].trim();
                    if (information.length == 2) {
                        String remarks = information[1].trim();
                        if (remarks.isBlank()){
                            System.out.println("When is the event broman?");
                        }
                        else {
                            System.out.println("Betta prep for this event brotherman.");
                            Task newTask = new Task(taskName, Task.TaskTypes.EVENT);
                            newTask.setRemarks(remarks);
                            this.listOfTasks.add(newTask);
                            saveSafely();
                        }
                    }
                    else {
                        System.out.println("When's the thing happening?");
                    }
                }
                else {
                    System.out.println("You speaking gibberish or something bro?");
                }

            }

            System.out.println();

        }
        System.out.println("See you next time. \n"
        + "Peace out. \n" );
    }

    public void printList(){
        for (int i = 1; i < listOfTasks.size()+1 ; i++){
            System.out.println( i + ". " + listOfTasks.get(i-1));
        }
    }

//    public void printALine(){
//        System.out.println("____________________________________________________________");
//    }

    public String[] splitInstruction(String line){
        return line.trim().split(" ",2);
    }

    private void saveSafely() {
        try {
            ArrayList<String> out = new ArrayList<>();
            for (Task t : listOfTasks) {
                out.add(toLine(t));
            }
            storage.saveLines(out);
        } catch (IOException e) {
            System.out.println("Could not save tasks.");
        }
    }

    private String toLine(Task task) {

        String doneFlag;
        if (task.isDone()) {
            doneFlag = "1";
        } else {
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
                    } catch (IllegalArgumentException e) {
                        //ignore
                    }
                }
            }
        }
        return task;
    }

    public void doneTask(int index){
        listOfTasks.get(index).markAsDone();
        saveSafely();
    }
    public void undoTask(int index){
        listOfTasks.get(index).markAsUndone();
        saveSafely();
    }
    public void deleteTask(int index){
        listOfTasks.remove(index);
        saveSafely();
    }

}


