package model;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSide {


    //static Socket acceptedSocket =null;
    static ArrayList<Socket> socketArrayList = new ArrayList<>();

    public static void main(String[] args) throws IOException {



        new Thread(() -> {
            try {


                final int PORT = 5000;
                ServerSocket serverSocket = new ServerSocket(PORT);
                System.out.println("Server Started");


                while (true) {
                    socketArrayList.add(serverSocket.accept());
                    System.out.println("Client Connected");



                        for (Socket acceptedSocket : socketArrayList) {

                                new Thread(() -> {
                                    try {
                                        InputStreamReader inputStreamReader = new InputStreamReader(acceptedSocket.getInputStream()); //get data from socket
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //read data
                                        String clientMessage = bufferedReader.readLine(); // read line by line
                                        if (!clientMessage.equals("EXIT")) {
                                            //Distribute message
                                            for (Socket socket : socketArrayList) {
                                                PrintWriter writer = new PrintWriter(new OutputStreamWriter(acceptedSocket.getOutputStream(), "UTF8"));//send data from socket
                                                writer.println(clientMessage);
                                                writer.flush();
                                            }

                                        } else {
                                            //  acceptedSocket.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }).start();

                        }




                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
