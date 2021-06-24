package geekbrains.L2L4;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    public TextArea chatArea;
    @FXML
    public ListView<String> onlineUsers;
    @FXML
    public Button btnSendMessage;
    @FXML
    public TextField input;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onlineUsers.setItems(FXCollections.observableArrayList("Vasya", "Petya", "Masha", "Анатолий"));
//        btnSendMessage.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void mockAction(ActionEvent actionEvent) {
    }

    public void showHelp(ActionEvent actionEvent) throws URISyntaxException, IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI("https://www.jetbrains.com/help/idea/2020.3/getting-started.html"));
    }

    public void pressEnter(ActionEvent actionEvent) {
        appendTextFromTF();
    }

    public void btnSend(ActionEvent actionEvent) {
        appendTextFromTF();
    }

    private void appendTextFromTF() {
        String msg = input.getText();
        if (msg.length() > 0) {
            Date date = new Date();
            chatArea.appendText(msg + System.lineSeparator() + date.getTime() + System.lineSeparator());
            input.clear();
        }
    }


}
