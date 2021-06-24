package geekbrains.L2L4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// import java.awt.*;
import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(loadFxml("chat.fxml"), 600, 800);
        primaryStage.setTitle("My chat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static Parent loadFxml(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

}
