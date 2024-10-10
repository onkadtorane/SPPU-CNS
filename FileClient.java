import java.io.*;
import java.net.*;
public class FileClient {
    public static void main(String[] args) {
        String host = "localhost"; // Change to the server's IP address if needed
        int port = 65432;
        String filePath = "C:/Users/91884/Desktop/5th Sem Books/CNS Lab/file.txt"; // Change to your file path
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to the server");
// Send the filename
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(new File(filePath).getName());
// Send the file
            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
            }
            bis.close();
            System.out.println("File sent successfully.");
        } 
        catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}