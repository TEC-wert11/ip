package wertinator.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import wertinator.Wertinator;

public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    private Wertinator wertinator;

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Wertinator instance.
     *
     * @param w Wertinator instance
     */
    public void setWertinator(Wertinator w) {
        wertinator = w;
        dialogContainer.getChildren().add(new Label("Wassup guys! Wuchu guys doin?"));
        dialogContainer.getChildren().add(new Label("This is Wertinator, back doing some more werting action!"));
        dialogContainer.getChildren().add(new Label("What ya wanna do today?"));
    }

    /**
     * Handles user input from the text field or send button.
     */
    @FXML
    private void handleUserInput() {

        String input = userInput.getText();

        if (input == null || input.isBlank()) {
            return;
        }

        String response = wertinator.getResponse(input);

        dialogContainer.getChildren().add(new Label("You: " + input));
        dialogContainer.getChildren().add(new Label("Wertinator: " + response));

        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }
}