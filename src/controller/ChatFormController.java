package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    public VBox vboxMessage;
    public ScrollPane scrollPane;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public static String userName;


    public void initialize() {
        try {
            this.socket = new Socket("192.168.8.226", 5000);
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));

            this.bufferedWriter.write(userName);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();


            vboxMessage.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    scrollPane.setVvalue((Double) newValue);
                }
            });

            //Update Message
            new Thread(() -> {
                String chatMessages;
                while (socket.isConnected()) {
                    try {
                        chatMessages = bufferedReader.readLine();


                        HBox hBox =new HBox();
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setPadding(new Insets(5,5,5,10));

                        if (chatMessages.startsWith("SERVER")){
                            hBox.setAlignment(Pos.CENTER);
                            chatMessages=chatMessages.replaceFirst("SERVER"," ");
                        }
                        Text text=new Text(chatMessages);
                        TextFlow textFlow=new TextFlow(text);

                        textFlow.setStyle("-fx-font-weight: bold;"+"-fx-background-color:#8b49d2;"+ "-fx-background-radius:10px");

                        if (hBox.getAlignment()==Pos.CENTER){
                            textFlow.setStyle("-fx-font-weight: bold;"+"-fx-background-color:#c7c6c6;"+ "-fx-background-radius:10px");

                        }

                        textFlow.setPadding(new Insets(5,10,5,10));
                        text.setFill(Color.color(1,1,1,1));

                        hBox.getChildren().add(textFlow);


                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                vboxMessage.getChildren().add(hBox);
                            }
                        });
                    } catch (IOException e) {
                        close(socket, bufferedWriter, bufferedReader);
                    }
                }

            }).start();

        } catch (IOException e) {
            close(socket, bufferedWriter, bufferedReader);
        }
    }


    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
        String message = txtMessage.getText();

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text("Me : " + message);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-font-weight: bold;"+"-fx-background-color:#cf8bf6;" + "-fx-background-radius:10px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(1, 1, 1, 1));

        hBox.getChildren().add(textFlow);
        vboxMessage.getChildren().add(hBox);
        bufferedWriter.write(userName + " : " + message);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        txtMessage.clear();


    }


    public void close(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
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

        /*Image image = new Image("src/view/img/chat.png");
        //Creating the image view
        ImageView imageView = new ImageView();
        //Setting image to the image view
        imageView.setImage(image);
        //Setting the image view parameters
        imageView.setX(5);
        imageView.setY(0);
        imageView.setFitWidth(55);
        imageView.setPreserveRatio(true);
        imageContext.getChildren().addAll(imageView);*/
        //txtClientMessage.setText(filePath.getPath());
    }


}
