import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringTokensInDictionary {
    public static String stringTokenizer(String encryptedText) {

        // ================================================================
        //  Initializing variables and its datatype
        // ================================================================
        StringBuilder strNoSpecialChar = new StringBuilder();
        String wordsInFile = "";
        int countSpecialChar = 0;

        // ============================================================================================================================
        //  Loop to remove all numbers and special characters (excluding spaces unless multiple spaces are in sequence) in the string
        // ============================================================================================================================
        for (int i = 0; i < encryptedText.length(); i++) {

            // =====================================================================================================
            //  Checks if the first index of the String is a special character or an alphabet (for corner case(s))
            // =====================================================================================================
            if (i == 0) {
                if (Character.isAlphabetic(encryptedText.charAt(i))) //if first element contains a alphabetic letter
                {
                    strNoSpecialChar.append(encryptedText.charAt(i)); //Append the character to the StringBuilder if it is an alphabetic letter
                } else //first element is a special character
                {
                    countSpecialChar = 1; //set countSpecialChar to 1 and do nothing else
                }

            }

            // ==========================================================================
            //  Checks if the character at i > 0 is a special character or not
            // ==========================================================================
            else {

                if (!Character.isAlphabetic(encryptedText.charAt(i))) //if character at index i is a special character
                {
                    countSpecialChar++; //increment countSpecialChar by 1

                    if (countSpecialChar == 1) //When countSpecialChar is equal to 1
                    {
                        strNoSpecialChar.append(" "); //Append a blank space to the StringBuilder at the first instance of a special character
                    } else {
                    } //does nothing
                } else //character at index i is an alphabetic letter
                {
                    countSpecialChar = 0; //Reset the counter for special characters back to 0 when the element at index i is an alphabetic letter
                    strNoSpecialChar.append(encryptedText.charAt(i)); //Append the character at index i to the StringBuilder
                }
            }

        }

        wordsInFile = strNoSpecialChar.toString(); //converts StringBuilder into a String in preparation to fit into an array variable

        //System.out.println(wordsInFile); //Print String where all special characters are removed

        String[] parseEncryptedText = wordsInFile.split(" "); //tokenizes the String without special characters into an array


        return dictionaryTokenFinder(parseEncryptedText);
    }

    private static String dictionaryTokenFinder(String[] tokens) {
        int tokensInDict = 0;
        int tokensInArray = tokens.length;
        List<String> dictionaryList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "/dictionary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {dictionaryList.add(line.toLowerCase());}
        }
        catch (IOException e) {throw new RuntimeException(e);}

        Collections.sort(dictionaryList);

        for (String token : tokens) {
            if (dictionaryBinarySearch(dictionaryList, token.toLowerCase(), dictionaryList.size() - 1, 0)) {
                tokensInDict = tokensInDict + 1;
            }
        }
        return ("[" + String.format("%.2f", (((double) tokensInDict / (double) tokensInArray) * 100)) + "%]");
    }

    private static boolean dictionaryBinarySearch(List<String> dictionaryList, String word, int upperBoundIndex, int lowerBoundIndex) {
        if (dictionaryList.size() >= 1) {
            word = word.toLowerCase();
            if (dictionaryList.size() % 2 == 1) {
                int middleWordIndex = dictionaryList.size() / 2;
                String middleWord = dictionaryList.get(dictionaryList.size() / 2).toLowerCase();
                int middleComparison = word.compareTo(middleWord);

                if (middleComparison == 0) {return true;}
                else if (middleComparison > 0) {//search right
                    lowerBoundIndex = middleWordIndex + 1;
                    return dictionaryBinarySearch(dictionaryList.subList(lowerBoundIndex, upperBoundIndex + 1), word, dictionaryList.subList(lowerBoundIndex, upperBoundIndex + 1).size() - 1, 0);
                } else {//search left
                    upperBoundIndex = middleWordIndex - 1;
                    return dictionaryBinarySearch(dictionaryList.subList(lowerBoundIndex, upperBoundIndex + 1), word, dictionaryList.subList(lowerBoundIndex, upperBoundIndex + 1).size() - 1, 0);
                }
            } else {
                int leftWordIndex = dictionaryList.size() / 2 - 1;
                int rightWordIndex = dictionaryList.size() / 2;
                String leftWord = dictionaryList.get(dictionaryList.size() / 2 - 1).toLowerCase();
                String rightWord = dictionaryList.get(dictionaryList.size() / 2).toLowerCase();
                int leftComparison = word.compareTo(leftWord);
                int rightComparison = word.compareTo(rightWord);

                if (leftComparison == 0 || rightComparison == 0) {return true;}
                else if (leftComparison > 0 && rightComparison > 0) {//search right
                    lowerBoundIndex = rightWordIndex + 1;
                    return dictionaryBinarySearch(dictionaryList.subList(lowerBoundIndex, upperBoundIndex + 1), word, dictionaryList.subList(lowerBoundIndex, upperBoundIndex + 1).size() - 1, 0);
                } else if (leftComparison < 0 && rightComparison < 0) {//search left
                    upperBoundIndex = leftWordIndex - 1;
                    return dictionaryBinarySearch(dictionaryList.subList(lowerBoundIndex, upperBoundIndex + 1), word, dictionaryList.subList(lowerBoundIndex, upperBoundIndex + 1).size() - 1, 0);
                }
                else {return false;}
            }
        }
        else {return false;}
    }
}
