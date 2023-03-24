import java.io.*;
import java.util.regex.Pattern;

public class fixTheDictionary {
    public static String consonants = "bcdfghjklmnpqrstvwxz";
    public static String vowels = "aeiouy";
    public fixTheDictionary() {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/words_alpha.txt"))) {
            String line;
            BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" + "new_words_alpha.txt"));
            while ((line = br.readLine()) != null) {
                int countConsonants = 0;

                for(int i = 0; i < line.length(); i++) {
                    if(consonants.indexOf(line.charAt(i)) != -1) {countConsonants++;}
                }

                //1 off cases to keep
                if (line.length() == 1 && (line.contains("i") || line.contains("a"))) {
                    //System.out.println(line);
                    writer.write(line + "\n");

                } else if (line.length() > 1 &&
                        !(Pattern.matches("[" + "a" + "]" + "+", line) ||
                        Pattern.matches("[" + "e" + "]" + "+", line) ||
                        Pattern.matches("[" + "i" + "]" + "+", line) ||
                        Pattern.matches("[" + "o" + "]" + "+", line) ||
                        Pattern.matches("[" + "u" + "]" + "+", line) ||
                        Pattern.matches("[" + "y" + "]" + "+", line) ||
                        Pattern.matches("[" + vowels + "]" + "+", line)) &&
                        countConsonants != line.length()) {
                    //System.out.println(line);
                    writer.write(line + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
}
