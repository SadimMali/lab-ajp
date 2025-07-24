import java.io.*;
import java.net.*;
import java.util.logging.*;

// Concrete subclass of UDPServer
public class MyUDPServer extends UDPServer {

    public MyUDPServer(int port) {
        super(port);
    }

    @Override
    public void respond(DatagramSocket socket, DatagramPacket request) throws IOException {
        // Example implementation: Echo the received message back to the client
        String received = new String(request.getData(), 0, request.getLength());
        System.out.println("Received: " + received);

        // Create a response packet
        byte[] responseData = ("Echo: " + received).getBytes();
        DatagramPacket response = new DatagramPacket(
                responseData,
                responseData.length,
                request.getAddress(),
                request.getPort()
        );
        socket.send(response);
    }

    // Main method to run the server
    public static void main(String[] args) {
        int port = 12345; // Default port, you can change this or use args
        MyUDPServer server = new MyUDPServer(port);
        Thread serverThread = new Thread(server);
        serverThread.start();
        System.out.println("UDP Server started on port " + port);
    }
}