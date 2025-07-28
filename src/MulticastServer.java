import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServer {
    public static void main(String[] args) {
        String multicastAddress = "230.0.0.0"; // Multicast IP (must be 224.0.0.0 to 239.255.255.255)
        int port = 4446;

        try (MulticastSocket socket = new MulticastSocket()) {
            InetAddress group = InetAddress.getByName(multicastAddress);
            System.out.println("Multicast Server started. Sending messages to group...");

            int count = 1;
            while (true) {
                String message = "Hello from Multicast Server! Message " + count++;
                byte[] buffer = message.getBytes();

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(packet);

                System.out.println("Sent: " + message);

                Thread.sleep(2000); // Send message every 2 seconds
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
