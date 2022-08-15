package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.ServerSide;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatFormController {
    public AnchorPane chatFormContext;
    public TextArea txtArea;
    public TextField txtMessage;
    public AnchorPane imageContext;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public static String userName;


    public void initialize(){
        try {
            this.socket=new Socket("192.168.8.226",5000);
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF8"));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF8"));

            this.bufferedWriter.write(userName);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();


            //Update Message
            new Thread(()-> {
                String chatMessages;
                while (socket.isConnected()){
                    try{
                        chatMessages = bufferedReader.readLine();
                        txtArea.appendText(chatMessages+"\n");




                    }catch (IOException e){
                        close(socket,bufferedWriter,bufferedReader);
                    }
                }

            }).start();

        } catch (IOException e) {
            close(socket,bufferedWriter,bufferedReader);
        }
    }


    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
            String message = txtMessage.getText();
            txtArea.appendText("Me : " + message+"\n");
            bufferedWriter.write(userName + " : " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            txtMessage.clear();


    }


    public void close(Socket socket,BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
            if (bufferedReader!=null){
                bufferedReader.close();
            }

            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
            if (socket!=null){
                socket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void sendPhotoOnAction(ActionEvent actionEvent) throws IOException {
       // Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Image");
        File file = fileChooser.showOpenDialog(null);

        bufferedWriter.write(userName + " : " + file.getPath());
        bufferedWriter.newLine();
        bufferedWriter.flush();

        Image image = new Image("src/view/img/chat.png");
        //Creating the image view
        ImageView imageView = new ImageView();
        //Setting image to the image view
        imageView.setImage(image);
        //Setting the image view parameters
        imageView.setX(5);
        imageView.setY(0);
        imageView.setFitWidth(55);
        imageView.setPreserveRatio(true);
        imageContext.getChildren().addAll(imageView);



        //txtClientMessage.setText(filePath.getPath());
    }


}
