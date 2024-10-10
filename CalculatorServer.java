import java.io.*;
import java.net.*;
public class CalculatorServer {
    public static void main(String[] args) {
        int port = 65432;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Calculator Server is listening on port " + port);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("New client connected");
// Create input and output streams
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                    String request;
                    while ((request = input.readLine()) != null) {
                        System.out.println("Received: " + request);
                        String response = calculate(request);
                        output.println(response);
                    }
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
    private static String calculate(String request) {
        String[] tokens = request.split(" ");
        if (tokens.length != 3) {
            return "Invalid request. Use: <number1> <operator> <number2>";
        }
        try {
            double num1 = Double.parseDouble(tokens[0]);
            String operator = tokens[1];
            double num2 = Double.parseDouble(tokens[2]);
            switch (operator) {
                case "+":
                    return String.valueOf(num1 + num2);
                case "-":
                    return String.valueOf(num1 - num2);
                case "*":
                    return String.valueOf(num1 * num2);
                case "/":
                    return num2 != 0 ? String.valueOf(num1 / num2) : "Error: Division by zero";
                default:
                    return "Invalid operator. Use +, -, *, or /.";
            }
        } 
        catch (NumberFormatException e) {
            return "Invalid number format.";
        }
    }
}