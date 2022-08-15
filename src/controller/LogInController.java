package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class LogInController {
    public AnchorPane logInFormContext;
    public TextField txtUserName;
    Socket socket = null;


    public void logInOnAction(ActionEvent actionEvent) throws IOException {
        ChatFormController.userName=txtUserName.getText();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/chatForm.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) logInFormContext.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();



        // set Connection
    }
}
