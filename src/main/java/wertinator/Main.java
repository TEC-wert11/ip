package wertinator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wertinator.gui.MainWindow;

public class Main extends Application {

    private Wertinator wertinator = new Wertinator("data/wertinator.txt");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        MainWindow controller = fxmlLoader.getController();
        controller.setWertinator(wertinator);

        stage.setScene(scene);
        stage.setTitle("Wertinator");
        stage.show();
    }
}