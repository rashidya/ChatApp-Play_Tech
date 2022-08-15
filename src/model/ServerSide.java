package model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {


    static Socket acceptedSocket =null;

    public static void main(String[] args) throws IOException {
        FileWriter fileWriter =new FileWriter("src/db/message.txt");

        new Thread(()->{
            try {

                final int PORT = 5000;
                ServerSocket serverSocket = new ServerSocket(PORT);
                System.out.println("Server Started");
                acceptedSocket = serverSocket.accept(); //wait
                System.out.println("Client Connected");
                L1:while (true) {
                    InputStreamReader inputStreamReader = new InputStreamReader(acceptedSocket.getInputStream()); //get data from socket
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //read data
                    String clientMessage = bufferedReader.readLine(); // read line by line
                    if (!clientMessage.equals("EXIT")) {


                        fileWriter.write(clientMessage);
                        fileWriter.flush();


                    }else {
                      //  acceptedSocket.close();
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }




}
