package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.ServerSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatFormController {
    public AnchorPane chatFormContext;
 
    public static String userName ="Rashmi";
    public TextArea txtArea;
    public TextField txtMessage;
    Socket socket = null;

    public void initialize(){

        new Thread(()->{
            try {
                socket = new Socket("192.168.8.226", 3000); //localhost
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream()); //get data from socket
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //read data
                String messages = bufferedReader.readLine(); // read line by line
                System.out.println(messages);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }

    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream());//send data from socket
        writer.println(userName + " : " + txtMessage.getText());
        writer.flush();
        txtMessage.clear();
    }


}
