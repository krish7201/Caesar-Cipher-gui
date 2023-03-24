import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class Authentication{

    public static boolean passwordAuthentication(String enteredUsername, String enteredPassword) throws NoSuchAlgorithmException {
        String encryptedUsername = "NTrOS64vqCprCCxKZNGysw==";
        String encryptedPassword = "Q3uTDbhLgHnC3YBKcZNrXw==";
        return hashString(enteredPassword).equals(encryptedPassword)
                && hashString(enteredUsername).equals(encryptedUsername);
    }

    private static String hashString(String enteredPassword) throws NoSuchAlgorithmException {
        //I probably adapted the code from here: https://www.baeldung.com/java-md5
        byte[] bytesOfMessage = enteredPassword.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theMD5digest = md.digest(bytesOfMessage);

        return Base64.getEncoder().encodeToString(theMD5digest);
    }
}
