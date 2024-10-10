import java.io.*;
import java.net.*;
public class FileServer {
    public static void main(String[] args) {
        int port = 65432;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("New client connected");
// Receive the filename
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    String fileName = dataInputStream.readUTF();
                    System.out.println("Receiving file: " + fileName);
// Create a file output stream to save the received file
                    FileOutputStream fos = new FileOutputStream(fileName);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
// Read from the socket and write to the file
                    while ((bytesRead = dataInputStream.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                    bos.close();
                    System.out.println("File received successfully.");
                }
                catch (IOException e) {
                System.out.println("Connection error: " + e.getMessage());
                }
            }
        }
        catch (IOException e) {
        System.out.println("Server error: " + e.getMessage());
        }
    }
}