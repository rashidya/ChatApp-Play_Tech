package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerSide {


    public static void main(String[] args) throws IOException {
        final int PORT = 5000;
        ServerSocket serverSocket = new ServerSocket(PORT);

        new Thread(() -> {
            try {

                while (!serverSocket.isClosed()) {
                    Socket acceptedSocket = serverSocket.accept();
                    System.out.println("A New Client Connected");

                    ClientsHandler clientsHandler =new ClientsHandler(acceptedSocket);

                    new Thread(clientsHandler).start();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }



}
