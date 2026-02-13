package wertinator;

import java.util.Scanner;

/**
 * Ui class
 * handles formatted prints
 */
public class Ui {
    private Scanner scannedInput;

    public Ui() {
        scannedInput = new Scanner(System.in);
    }

    public String readCommand() {
        return scannedInput.nextLine();
    }

    public void showWelcome() {
        System.out.println("Wassup guys! Wuchu guys doin? \n"
                + "This is wertinator.Wertinator, back doing some more werting action! \n"
                + "What ya wanna do today?");
    }

    public void showGoodbye() {
        System.out.println("See ya next time. \n"
                + "Peace.");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showLine() {
        System.out.println("________________________________________");
    }
}
