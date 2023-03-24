import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;

public class Login extends JFrame {
    private JPanel myPanel;
    private JButton loginButton;
    private JLabel userLabel, passLabel, invalid1Text, invalid2Text;
    private JTextField usernameField;
    private JPasswordField passwordField;
    public static boolean isAdmin;
    private int attemptsTaken = 0;
    public Login() {
        setTitle("Welcome!");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(250, 250);
        setVisible(true);
        setContentPane(myPanel);
        setDefaultLookAndFeelDecorated(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attemptsTaken = attemptsTaken + 1;
                try {
                    isAdmin = Authentication.passwordAuthentication(
                    usernameField.getText(), passwordField.getText());
                } catch (NoSuchAlgorithmException ex) {throw new RuntimeException(ex);}

                if (isAdmin) {
                    dispose();
                    new GUI().setVisible(true);
                }

                if (attemptsTaken == 1) {
                    invalid1Text.setForeground(Color.red); invalid2Text.setForeground(Color.red);
                    invalid1Text.setVisible(true); invalid2Text.setVisible(true);
                    usernameField.setForeground(Color.red); passwordField.setForeground(Color.red);
                } else if (attemptsTaken == 2) {
                    invalid1Text.setText("There is only one"); invalid2Text.setText("attempt remaining.");
                    invalid1Text.setForeground(Color.red); invalid2Text.setForeground(Color.red);
                    invalid1Text.setVisible(true); invalid2Text.setVisible(true);
                    usernameField.setForeground(Color.red); passwordField.setForeground(Color.red);
                } else if (attemptsTaken == 3) {
                    dispose();
                    new GUI().setVisible(true);
                }
            }
        });

        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) { //if key typed in username field
                super.keyTyped(e);
                usernameField.setForeground(Color.black);
                invalid1Text.setVisible(false); invalid2Text.setVisible(false);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) { //if key typed in password field
                super.keyTyped(e);
                passwordField.setForeground(Color.black);
                invalid1Text.setVisible(false); invalid2Text.setVisible(false);
            }
        });
    }
    public static void main(String[] args) {
        // Set cross-platform Java L&F
        try {UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        // handle exception
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {}
        new Login();
    }
}
