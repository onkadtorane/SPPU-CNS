import java.io.*;
import java.net.*;
import java.util.Scanner;
public class CalculatorClient {
    public static void main(String[] args) {
        String host = "localhost"; // Change to the server's IP address if needed
        int port = 65432;
        try (Socket socket = new Socket(host, port);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connected to the calculator server");
            String request;
            while (true) {
                System.out.print("Enter calculation (or 'exit' to quit): ");
                request = scanner.nextLine();
                if ("exit".equalsIgnoreCase(request)) {
                    break;
                }
                output.println(request);
                String response = input.readLine();
                System.out.println("Result: " + response);
            }
        } 
        catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}