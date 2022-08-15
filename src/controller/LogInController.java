package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

public class LogInController {
    public AnchorPane logInFormContext;
    public TextField txtUserName;


    public void logInOnAction(ActionEvent actionEvent) throws IOException {
        ChatFormController.userName=txtUserName.getText();
        URL resource = getClass().getResource("../view/chatForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = new Stage();
        window.setScene(new Scene(load));
        // set Connection
    }
}