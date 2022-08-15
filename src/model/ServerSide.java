package model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {


    static Socket acceptedSocket =null;

    public static void main(String[] args) {


        new Thread(()->{
            try {

                final int PORT = 3000;
                ServerSocket serverSocket = new ServerSocket(PORT);
                System.out.println("Server Started");
                acceptedSocket = serverSocket.accept(); //wait
                System.out.println("Client Connected");
                L1:while (true) {
                    InputStreamReader inputStreamReader = new InputStreamReader(acceptedSocket.getInputStream()); //get data from socket
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //read data
                    String clientMessage = bufferedReader.readLine(); // read line by line
                    if (!clientMessage.equals("EXIT")) {
                        System.out.println(clientMessage);
                        FileWriter fileWriter =new FileWriter("src/db/message.txt");
                        fileWriter.write(clientMessage);
                        fileWriter.flush();

                       /* PrintWriter writer = new PrintWriter(acceptedSocket.getOutputStream());//send data from socket
                        FileReader fileReader =new FileReader("src/db/message.txt");
                        BufferedReader bufferedReader1 =new BufferedReader(fileReader);
                        String line = bufferedReader1.readLine();

                        while (line!=null) {
                            writer.println(line);
                            System.out.println(line);
                            writer.flush();
                            line=bufferedReader.readLine();
                        }*/

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
