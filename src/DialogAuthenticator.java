import java.net.Authenticator;
import java.net.PasswordAuthentication;
import javax.swing.*;

public class DialogAuthenticator extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message,
                "Enter credentials for " + getRequestingSite(),
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            return new PasswordAuthentication(username, password.toCharArray());
        } else {
            return null;
        }
    }
}
