import java.util.ArrayList;
import java.util.Scanner ;

public class Wertinator {

    private ArrayList<Task> list = new ArrayList<>();

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

            String[] sentence = line.split(" ");
            String command = sentence[0];
            boolean didSomething = false;

            if(line.equals("bye")){
                break;
            }

            if (command.equals("list")){
                this.printList();
                didSomething = true;
            }
            if(command.equals("mark")){
                int index = Integer.parseInt(sentence[1])-1;
                this.doneTask(index);
                didSomething = true;
            }
            if (command.equals("unmark")){
                int index = Integer.parseInt(sentence[1])-1;
                this.undoTask(index);
                didSomething = true;
            }
            if (!didSomething){
                this.list.add(new Task(line));
                System.out.println("added: "+line);
            }
            System.out.println();
        }

        System.out.println("See you next time. \n"
        + "Peace out. \n" );
    }

    public void printList(){
        for (int i=1 ; i < list.size()+1 ; i++){
            System.out.println( i + ". " + list.get(i-1));
        }
    }

//    public void printALine(){
//        System.out.println("____________________________________________________________");
//    }

    public void doneTask(int index){
        list.get(index).markAsDone();
    }
    public void undoTask(int index){
        list.get(index).markAsUndone();
    }

    //class Task to track tasks
    public static class Task{
        private String name;
        private boolean doneness;

        public Task(String name){
            this.name = name;
            this.doneness = false;
        }

        public void markAsDone(){
            this.doneness = true;
            System.out.println("Nice one mate! Now we can get this shit outta the way!");
            System.out.println(this);
        }
        public void markAsUndone(){
            this.doneness = false;
            System.out.println("Aww wadehail ?! You Lied to me!");
            System.out.println(this);
        }
        public boolean isDone(){
            return doneness;
        }
        public String getName(){
            return this.name;
        }

        @Override
        public String toString(){
            if (this.isDone()) {
                return "[X] " + this.name ;
            }
            return "[ ] " + this.name;
        }

    }
}


