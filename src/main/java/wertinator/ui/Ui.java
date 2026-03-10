package wertinator.ui;

import java.util.Scanner;

/**
 * Ui class
 * handles formatted prints
 */
public class Ui {
    private final Scanner scannedInput;

    public Ui() {
        scannedInput = new Scanner(System.in);
    }

    public String readCommand() {
        return scannedInput.nextLine();
    }

    public String showWelcome() {
        return "Wassup guys! Wuchu guys doin? \n"
                + "This is wertinator.Wertinator, back doing some more werting action! \n"
                + "What ya wanna do today?";
    }

    public String showGoodbye() {
        return ("See ya next time. \n"
                + "Peace.");
    }

    public String showError(String message) {
        return(message);
    }

    public String showCheer(String quote) {
        return (quote);
    }


    public String showLine() {
        return("________________________________________");
    }
}
