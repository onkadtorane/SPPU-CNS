import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
public class DnsLookup {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an IP address or hostname:");
        String input = scanner.nextLine().trim();
        if (isValidIP(input)) {
            try {
                InetAddress address = InetAddress.getByName(input);
                String hostName = address.getHostName();
                System.out.println("Hostname: " + hostName);
            } 
            catch (UnknownHostException e) {
                System.out.println("Could not find hostname for IP: " + input);
            }
        } 
        else {
            try {
                InetAddress address = InetAddress.getByName(input);
                String ipAddress = address.getHostAddress();
                System.out.println("IP Address: " + ipAddress);
            } 
            catch (UnknownHostException e) {
                System.out.println("Could not find IP address for hostname: " + input);
            }
        }
        scanner.close();
    }
    private static boolean isValidIP(String ip) {
    String ipPattern = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    return ip.matches(ipPattern);
    }
}