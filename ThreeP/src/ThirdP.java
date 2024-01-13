
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class ThirdP extends Thread {

    private static final int MAX_LEN = 1000;
    private MulticastSocket socket;
    private InetAddress group;
    private int port;

    public ThirdP(MulticastSocket socket, InetAddress group, int port){
        this.socket = socket;
        this.port = port;
        this.group = group;
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] buffer = new byte[MAX_LEN];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);

                String receivedMessage = new String(buffer, 0, datagramPacket.getLength());
                System.out.println(receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}