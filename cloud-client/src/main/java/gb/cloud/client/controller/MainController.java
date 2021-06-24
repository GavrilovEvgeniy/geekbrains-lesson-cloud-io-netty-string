package gb.cloud.client.controller;

import gb.cloud.client.factory.Factory;
import gb.cloud.client.service.NetworkService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TextField commandTextField;
    public TextArea commandResultTextArea;

    public NetworkService networkService;

    @FXML
    public ListView<String> cloudContent;
    @FXML
    public ListView<String> localContent;
    @FXML
    public Button btnDownload;
    @FXML
    public Button btnUpload;

    public static String curCloudPath = "Cloud";
    public static String curLocalPath = "C:\\";

    public static String fullName1 = "";
    public static String fullName2 = "";

    public static String gotResult = "";
    public static String userName = "";

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = Factory.getNetworkService();
        createCommandResultHandler();

        showDir(localContent, curLocalPath);
        showDir(cloudContent, curCloudPath);
    }

    private void createCommandResultHandler() {
        new Thread(() -> {
            byte[] buffer = new byte[50000];
            while (true) {
                int countReadBytes = networkService.readCommandResult(buffer);
                String resultCommand = new String(buffer, 0, countReadBytes);
                gotResult = resultCommand;
                Platform.runLater(() -> commandResultTextArea.appendText(resultCommand + System.lineSeparator()));

            }

        }).start();
    }

    public void sendCommand(ActionEvent actionEvent) throws InterruptedException {
        networkService.sendCommand(commandTextField.getText().trim());
        commandTextField.clear();

        Thread.sleep(200);
        if (gotResult.startsWith("Logged")) {
            userName = gotResult.substring(7);
        }
        showDir(localContent, curLocalPath);
        showDir(cloudContent, curCloudPath);
    }

    public void shutdown() {
        networkService.closeConnection();
    }

    private void preparePath(ListView<String> content, String path1, String path2)  {
        String currentItemSelected = content.getSelectionModel().getSelectedItem();
        String onlyFileName = currentItemSelected.substring(0, currentItemSelected.indexOf(" | "));
        fullName1 = path1 + "\\" + onlyFileName;
        fullName2 = path2 + "\\" + onlyFileName;
    }

    public void btnDownload(ActionEvent actionEvent) throws InterruptedException {

        preparePath(cloudContent, curCloudPath, curLocalPath);
        networkService.sendCommand("tocloud=" + fullName1 + "=" + fullName2);

        Thread.sleep(200);
        showDir(localContent, curLocalPath);
    }

    public void btnUpload(ActionEvent actionEvent) throws InterruptedException {

        preparePath(localContent, curLocalPath, curCloudPath);
        networkService.sendCommand("tocloud=" + fullName1 + "=" + fullName2);

        Thread.sleep(200);
        showDir(cloudContent, curCloudPath);
    }

    private void showDir(ListView<String> content, String path) throws InterruptedException {

        networkService.sendCommand("ls=" + path + "=" + userName);

        Thread.sleep(500); // без задержки окна каталогов пустые, сервер не успевает.

        String In = gotResult;
        String[] cFiles = In.split(System.lineSeparator());

        content.setItems(FXCollections.observableArrayList(cFiles));
        content.getSelectionModel().selectFirst();

    }

    private String changeDir(ListView<String> content, String curPath) throws InterruptedException {
        String changedPath;
        String currentItemSelected = content.getSelectionModel().getSelectedItem();
        if (currentItemSelected.contains("FILE"))
            return null;
        String onlyFileName = currentItemSelected.substring(0, currentItemSelected.indexOf(" | "));

        if (onlyFileName.equals("..")) {
            int lastSlash = curPath.lastIndexOf("\\");
            changedPath = curPath.substring(0, lastSlash);
        }
        else changedPath = curPath + "\\" + onlyFileName;

        showDir(content, changedPath);
        return changedPath;
    }


    public void handle(javafx.scene.input.MouseEvent mouseEvent) throws InterruptedException {

        if (mouseEvent.getClickCount() == 2) {

            if (localContent.isFocused())
                curLocalPath = changeDir(localContent, curLocalPath);
            if (cloudContent.isFocused())
                curCloudPath = changeDir(cloudContent, curCloudPath);

        }
    }

}
