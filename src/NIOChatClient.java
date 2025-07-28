import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOChatClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 5000));
        System.out.println("Connected to Chat Server...");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter message: ");
            String message = scanner.nextLine();

            // Send message to server
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            socketChannel.write(buffer);

            // Read response from server
            buffer.clear();
            socketChannel.read(buffer);
            System.out.println(new String(buffer.array()).trim());
            buffer.clear();
        }
    }
}
