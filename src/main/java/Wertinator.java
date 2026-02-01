import java.util.ArrayList;
import java.util.Scanner ;

public class Wertinator {

    private ArrayList<String> list = new ArrayList<>();

    public static void main(String[] args) {
        new Wertinator().run();
    }

    private void run(){
        System.out.println("Wassup guys! Wuchu guys doin? \n"
        + "This is Wertinator, back doing some more werting action! \n"
        + "What ya wanna do today?" );
        printALine();

        Scanner input = new Scanner(System.in);

        while(true) {
            String line = input.nextLine();

            if(line.equals("bye")){
                break;
            }

            if (line.equals("list")){
                this.printList();
            }
            else{
                this.list.add(line);
                printALine();
                System.out.println("added: "+line);
                printALine();
            }
        }

        printALine();
        System.out.println("See you next time. \n"
        + "Peace out. \n" );
    }

    public void printList(){
        printALine();
        for (int i=1 ; i < list.size()+1 ; i++){
            System.out.println( i + ". " + list.get(i-1));
        }
        printALine();
    }

    public void printALine(){
        System.out.println("____________________________________________________________");
    }
}
