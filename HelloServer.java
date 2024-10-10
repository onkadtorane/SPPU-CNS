import java.io.*;
import java.net.*;
public class HelloServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is listening on port 12345...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
// Handle the client in a separate thread
                new Thread(new ClientHandler(socket)).start();
            }
        } 
        catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
@Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            out.writeUTF("Hello, client!");
        } 
        catch (IOException e) {
            System.out.println("Client handler exception: " + e.getMessage());
        } 
        finally {
            try {
                socket.close();
            } 
            catch (IOException e) {
                System.out.println("Socket close exception: " + e.getMessage());
            }
        }
    }
}