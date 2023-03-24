import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GUI extends JFrame {

    protected JPanel GUIPanel;
    protected JLabel browseLabel, plainTextLabel, keyLabel, encryptLabel, decryptLabel, decryptOutputKey1, decryptOutputKey2, decryptOutputKey3, decryptOutputKey4, decryptOutputKey5, decryptOutputKey6;
    protected JButton browseButton, encryptButton, decryptButton, logoutButton;
    protected JTextArea plainTextArea, encryptedTextArea, decryptedTextArea;
    protected JSpinner keySpinner;
    protected JScrollPane decryptedTextPane;
    private final JFileChooser FC = new JFileChooser();

    public GUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(550, 525);
        setVisible(true);
        setContentPane(GUIPanel);
        JFrame.setDefaultLookAndFeelDecorated(true);

        if (Login.isAdmin) { //GUI settings on launch
            setTitle("Caesar Cipher Cryptographic System (Admin)");
            plainTextSectionVisibility(true);
        } else {
            setTitle("Caesar Cipher Cryptographic System (User)");
            encryptTextSectionVisibility(true);
            userToolTipText();
        }

        browseButton.addActionListener(e -> { //browse button is pressed
            FC.setCurrentDirectory(new File(System.getProperty("user.dir") + "/src/test files"));
            FC.showOpenDialog(null);
            File file = FC.getSelectedFile();

            if (file != null && file.getName().contains(".txt")) {
                String content;

                try {content = Files.readAllLines(file.toPath()).toString();}
                catch (IOException ex) {throw new RuntimeException(ex);}

                System.out.println(content);

                if (content.length() > 30) {
                    plainTextArea.setText(content.substring(0, 30) + "...");
                    encryptTextSectionVisibility(true);
                } else {
                    plainTextArea.setText(content);
                    encryptTextSectionVisibility(true);
                }
            }
        });

        encryptButton.addActionListener(e -> { //if encrypt button is pressed
            if (Login.isAdmin) {
                File file = FC.getSelectedFile();
                String content;

                try {content = Files.readAllLines(file.toPath()).toString();}
                catch (IOException ex) {throw new RuntimeException(ex);}

                String encryptedText = Encryption.encrypt(content, (Integer) keySpinner.getValue());

                if (encryptedText.length() > 30) {
                    encryptedTextArea.setText(Encryption.encrypt(content, (Integer) keySpinner.getValue()).substring(0, 30) + "...");
                    plainTextSectionVisibility(false);
                    decryptTextSectionVisibility(true);
                } else {
                    encryptedTextArea.setText(Encryption.encrypt(content, (Integer) keySpinner.getValue()));
                    plainTextSectionVisibility(false);
                    decryptTextSectionVisibility(true);
                }

            } else {
                if (plainTextArea.getText().length() > 30) {
                    encryptedTextArea.setText(Encryption.encrypt(plainTextArea.getText(), (Integer) keySpinner.getValue()).substring(0, 30) + "...");
                    encryptTextSectionVisibility(false);
                    decryptTextSectionVisibility(true);
                } else {
                    encryptedTextArea.setText(Encryption.encrypt(plainTextArea.getText(), (Integer) keySpinner.getValue()));
                    encryptTextSectionVisibility(false);
                    decryptTextSectionVisibility(true);
                }
            }
        });

        decryptButton.addActionListener(e -> { //if decrypt button is pressed
            if (Login.isAdmin) {
                File file = FC.getSelectedFile();
                String content;

                try {content = Files.readAllLines(file.toPath()).toString();}
                catch (IOException ex) {throw new RuntimeException(ex);}

                String stringToBeDecrypted = Encryption.encrypt(content, (Integer) keySpinner.getValue());

                if (stringToBeDecrypted.length() > 30) {
                    shortenedOutput(stringToBeDecrypted);
                    encryptTextSectionVisibility(false);
                    decryptTextSectionVisibility(false);
                    plainTextSectionVisibility(true);
                } else {
                    fullOutput(stringToBeDecrypted);
                    encryptTextSectionVisibility(false);
                    decryptTextSectionVisibility(false);
                    plainTextSectionVisibility(true);
                }

            } else {
                String stringToBeDecrypted = encryptedTextArea.getText();

                if (stringToBeDecrypted.length() > 30) {
                    shortenedOutput(stringToBeDecrypted);
                    decryptTextSectionVisibility(false);

                } else {
                    fullOutput(stringToBeDecrypted);
                    decryptTextSectionVisibility(false);
                }
            }
        });

        logoutButton.addActionListener(e -> { //if logout button is pressed
            dispose();
            new Login().setVisible(true);
        });
    }

    private void fullOutput(String stringToBeDecrypted) {
        String output = StringTokensInDictionary.stringTokenizer(stringToBeDecrypted) + " Key: 0 ~ "
                + stringToBeDecrypted + "\n";

        for (int i = 1; i < 26; i++) {
            stringToBeDecrypted = Encryption.encrypt(stringToBeDecrypted, -1);
            output = output + StringTokensInDictionary.stringTokenizer(stringToBeDecrypted) + " Key: -"
                    + (i) % 26 + " ~ " + stringToBeDecrypted + "\n";

            decryptedTextArea.setText(output);
        }
    }

    private void shortenedOutput(String stringToBeDecrypted) {
        String output = StringTokensInDictionary.stringTokenizer(stringToBeDecrypted) + " Key: 0 ~ "
                + stringToBeDecrypted.substring(0, 30) + "..." + "\n";

        for (int i = 1; i < 26; i++) {
            stringToBeDecrypted = Encryption.encrypt(stringToBeDecrypted, -1);
            output = output + StringTokensInDictionary.stringTokenizer(stringToBeDecrypted) + " Key: -"
                    + (i) % 26 + " ~ " + stringToBeDecrypted.substring(0, 30) + "..." + "\n";

            decryptedTextArea.setText(output);
        }
    }

    private void plainTextSectionVisibility(boolean ToF) {
        browseLabel.setEnabled(ToF);
        plainTextLabel.setEnabled(ToF);
        browseButton.setEnabled(ToF);
        plainTextArea.setEnabled(ToF);
    }

    private void encryptTextSectionVisibility(boolean ToF) {
        if (Login.isAdmin) {
            keyLabel.setEnabled(ToF);
            keySpinner.setEnabled(ToF);
            encryptLabel.setEnabled(ToF);
            encryptButton.setEnabled(ToF);
            encryptedTextArea.setEnabled(ToF);
        } else {
            plainTextArea.setText("Ham is great!");
            keySpinner.setValue(4);
            encryptLabel.setEnabled(ToF);
            encryptButton.setEnabled(ToF);
            encryptedTextArea.setEnabled(ToF);
        }
    }

    private void decryptTextSectionVisibility(boolean ToF) {
        decryptButton.setEnabled(ToF);
        decryptLabel.setEnabled(ToF);
        decryptOutputKey1.setEnabled(ToF);
        decryptOutputKey2.setEnabled(ToF);
        decryptOutputKey3.setEnabled(ToF);
        decryptOutputKey4.setEnabled(ToF);
        decryptOutputKey5.setEnabled(ToF);
        decryptOutputKey6.setEnabled(ToF);
        decryptedTextPane.setEnabled(ToF);
        decryptedTextArea.setEnabled(ToF);
    }

    private void userToolTipText() {
        browseLabel.setToolTipText("This is an admin only feature.");
        plainTextLabel.setToolTipText("This is an admin only feature.");
        browseButton.setToolTipText("This is an admin only feature.");
        plainTextArea.setToolTipText("This is an admin only feature.");
        keyLabel.setToolTipText("This is an admin only feature.");
        keySpinner.setToolTipText("This is an admin only feature.");
    }

}
