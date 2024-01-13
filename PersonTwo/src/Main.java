import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    private static final String EXIT = "h";

    public static void main(String[] args) {
        boolean isFinished;
        try {
            InetAddress group = InetAddress.getByName("224.0.0.0");
            int port = 5002;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your name:");
            String name = scanner.nextLine();
            MulticastSocket socket = new MulticastSocket(port);
            socket.setTimeToLive(0);
            socket.joinGroup(group);

            TwoP readThread = new TwoP(socket, group, port);
            Thread thread = new Thread(readThread);
            thread.start();

            System.out.println("type your message.");

            String message;
            while (true) {
                message = scanner.nextLine();
                if (message.equals(EXIT)) {
                    isFinished = true;
                    socket.leaveGroup(group);
                    socket.close();
                    break;
                }
                message = name + ": " + message;
                byte[] buffer = message.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(datagramPacket);
            }
        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}