package model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientsHandler implements Runnable{
    public static ArrayList<ClientsHandler> clients = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public ClientsHandler(Socket socket){

        try {
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName=bufferedReader.readLine();
            clients.add(this);
            broadcastMessage(userName + " has joined the chat");
        } catch (IOException e) {
            close(socket,bufferedWriter,bufferedReader);
        }
    }

    @Override
    public void run() {
            String messageFromClient ;

        while (socket.isConnected()) {
            try {

                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);

            } catch (IOException e) {
                close(socket,bufferedWriter,bufferedReader);
                break;
            }
        }

    }

    public void broadcastMessage(String message){
        for (ClientsHandler client : clients) {
            try {
                if (!client.userName.equals(userName)){
                    client.bufferedWriter.write(message);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }

            }catch (IOException e) {
                close(socket,bufferedWriter,bufferedReader);
                break;
            }
        }
    }

    public void removeClient(){
        clients.remove(this);
        broadcastMessage(userName + " has left the chat");
    }

    public void close(Socket socket,BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        removeClient();
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
}
