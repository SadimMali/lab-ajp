import java.io.*;
import java.net.*;

public class DictClient {

    public static final String SERVER = "dict.org";
    public static final int PORT = 2628;
    public static final int TIMEOUT = 15000;

    public static void main(String[] args) {

        Socket socket = null;

        try {
            socket = new Socket(SERVER, PORT);
            socket.setSoTimeout(TIMEOUT);

            OutputStream out = socket.getOutputStream();
            Writer writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            // Read and ignore the server welcome message
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("220 ")) break; // End of banner
            }

            for (String word : args) {
                define(word, writer, reader);
            }

            writer.write("quit\r\n");
            writer.flush();

        } catch (IOException ex) {
            System.err.println("Connection error: " + ex);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    // ignore
                }
            }
        }
    }

    static void define(String word, Writer writer, BufferedReader reader) throws IOException {
        writer.write("DEFINE * " + word + "\r\n");
        writer.flush();

        String line;
        boolean foundDefinition = false;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("250 ")) {
                // End of definition
                break;
            } else if (line.startsWith("552 ")) {
                System.out.println("No definition found for '" + word + "'.");
                break;
            } else if (line.matches("\\d\\d\\d .*")) {
                // Skip status codes like 150, 151, etc.
                continue;
            } else if (line.trim().equals(".")) {
                // End of block
                continue;
            } else {
                if (!foundDefinition) {
                    System.out.println("Definition of '" + word + "':");
                    foundDefinition = true;
                }
                System.out.println(line);
            }
        }

        if (foundDefinition) {
            System.out.println(); // Separate definitions
        }
    }
}
