import java.util.Scanner ;

public class Wertinator {
    public static void main(String[] args) {
        System.out.println("Wassup guys! Wuchu guys doin? \n"
        + "This is Wertinator, back doing some more werting action! \n"
        + "What are we doing today? \n" );

        Scanner input = new Scanner(System.in);
        boolean bye = false ;

        while(!bye) {
            String line = input.nextLine();
            System.out.println(line);

            if(line.equals("bye")){
                bye = true;
            }
        }

        System.out.println("See you guys next time. \n"
        + "Peace out. \n" );
    }
}
