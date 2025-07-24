import java.io.InputStream;
import java.net.Socket;
import java.util.Date;

public class TimeClient {

    public static void main(String[] args) {
        String server = args.length > 0 ? args[0] : "localhost";
        final int PORT = 37;
        final long DIFFERENCE_BETWEEN_EPOCHS = 2208988800L; // seconds between 1900 and 1970

        try (Socket socket = new Socket(server, PORT)) {
            InputStream in = socket.getInputStream();

            byte[] data = new byte[4];
            for (int i = 0; i < 4; i++) {
                int b = in.read();
                if (b == -1) throw new RuntimeException("Unexpected end of stream");
                data[i] = (byte) b;
            }

            // Convert 4 bytes to an unsigned 32-bit integer
            long secondsSince1900 = ((data[0] & 0xFFL) << 24)
                    | ((data[1] & 0xFFL) << 16)
                    | ((data[2] & 0xFFL) << 8)
                    | (data[3] & 0xFFL);

            long secondsSince1970 = secondsSince1900 - DIFFERENCE_BETWEEN_EPOCHS;
            long msSince1970 = secondsSince1970 * 1000;

            Date time = new Date(msSince1970);
            System.out.println("Server time: " + time);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
