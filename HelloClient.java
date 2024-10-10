import java.io.*;
import java.net.*;
public class HelloClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
            DataInputStream in = new DataInputStream(socket.getInputStream())) {
// Receive the greeting message from the server
            String message = in.readUTF();
            System.out.println("Message from server: " + message);
        } 
        catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
        }
    }
}