package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class clientSide {
    private static Socket socket;

    public static void setConnection() throws IOException {
        final int PORT=3000;
         socket =new Socket("192.168.8.226",PORT); //localhost
    }

    public static void sendMassage() throws IOException {
        PrintWriter writer=new PrintWriter(socket.getOutputStream());//send data from socket
        writer.println("Hello Server!");
        writer.flush();
    }

    public static void main(String[] args) throws IOException {
        setConnection();
        sendMassage();
    }


}
