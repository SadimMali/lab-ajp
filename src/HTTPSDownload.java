import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class HTTPSDownload {
    public static void main(String[] args) {
        try {
            // Specify the HTTPS URL
            URL url = new URL("https://www.usps.com");

            // Open an HTTPS connection
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Display response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the content
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append("\n");
            }

            // Close streams
            in.close();
            connection.disconnect();

            // Print the downloaded content
            System.out.println("Website Content:");
            System.out.println(content.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
