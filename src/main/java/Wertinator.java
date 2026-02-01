import java.util.ArrayList;
import java.util.Scanner ;

public class Wertinator {

    private ArrayList<Task> listOfTasks = new ArrayList<>();

    public static void main(String[] args) {
        new Wertinator().run();
    }

    private void run(){
        System.out.println("Wassup guys! Wuchu guys doin? \n"
        + "This is Wertinator, back doing some more werting action! \n"
        + "What ya wanna do today?" );
        System.out.println();

        Scanner input = new Scanner(System.in);

        while(true) {
            String line = input.nextLine();

            String[] sentence = splitInstruction(line);
            String command = sentence[0];

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
                    int index = Integer.parseInt(taskInformation) - 1;
                    this.doneTask(index);
                }
                else if (command.equals("unmark")) {
                    int index = Integer.parseInt(taskInformation) - 1;
                    this.undoTask(index);
                }
                else if (command.equals("todo")) {
                    System.out.println("Aight man, one more thing on the to do list.");
                    String taskName = taskInformation;
                    Task newTask = new Task(taskName, Task.TaskTypes.T);
                    this.listOfTasks.add(newTask);
                }
                else if (command.equals("deadline")) {
                    String[] information = taskInformation.trim().split("/", 2);
                    String taskName = information[0];
                    if (information.length==2) {
                        String remarks = information[1];

                        System.out.println("Deadline coming, ya better hurry de hail up.");
                        Task newTask = new Task(taskName, Task.TaskTypes.D);
                        newTask.setRemarks(remarks);
                        this.listOfTasks.add(newTask);
                    }
                    else {
                        System.out.println("Whats the deadline bro?");
                    }
                }
                else if (command.equals("event")) {
                    String[] information = taskInformation.trim().split("/", 2);
                    String taskName = information[0];
                    if (information.length == 2) {
                        String remarks = information[1];

                        System.out.println("Betta prep for this event brotherman.");
                        Task newTask = new Task(taskName, Task.TaskTypes.E);
                        newTask.setRemarks(remarks);
                        this.listOfTasks.add(newTask);
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

    public void doneTask(int index){
        listOfTasks.get(index).markAsDone();
    }
    public void undoTask(int index){
        listOfTasks.get(index).markAsUndone();
    }

    //class Task to track tasks
    public static class Task{

        private enum TaskTypes {T, D, E}

        private String name ;
        private boolean doneness ;
        private TaskTypes taskType ;
        private String remarks = "";

        public Task(String name, TaskTypes taskType){
            this.name = name;
            this.doneness = false;
            this.taskType = taskType;
        }

        public void markAsDone(){
            this.doneness = true;
            System.out.println("Nice one mate!");
            System.out.println(this);
        }
        public void markAsUndone(){
            this.doneness = false;
            System.out.println("Aww wadehail ?! Theres more to that thing?!");
            System.out.println(this);
        }
        public boolean isDone(){
            return doneness;
        }
        public String getName(){
            return this.name;
        }

        public TaskTypes getTaskType(){
            return this.taskType;
        }

        public void setRemarks(String remark){
            this.remarks = remark;
        }
        public String getRemarks(){
            return this.remarks;
        }

        @Override
        public String toString() {
            String donenessIcon = " ";
            if (this.isDone()){
                donenessIcon = "X";
            }
            if (this.remarks != ""){
                return "[" + this.getTaskType() + "] [" + donenessIcon + "] " + this.getName() + " (" + this.getRemarks() +") ";
            }
            return "[" + this.getTaskType() + "] [" + donenessIcon + "] " + this.getName() ;
        }
    }
}


