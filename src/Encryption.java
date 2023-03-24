public class Encryption {

    public static String encrypt(String txt1, int passedKey) {

        // ===================================================================================
        //  String of alphabets for both uppercase and lowercase
        // ===================================================================================
        String alphabetUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetLower = "abcdefghijklmnopqrstuvwxyz";

        // ===================================================================================
        //  Variables and objects
        // ===================================================================================
        StringBuilder encryptText = new StringBuilder();
        int shiftToLetter, index;

        // ===================================================================================
        //  Encryption block for a positive key
        // ===================================================================================
        if(passedKey >= 0)
        {
            for(int i = 0; i < txt1.length(); i++) //loops through the string(s) in the file 1 character at a time
            {
                if(Character.isUpperCase(txt1.charAt(i))) //Checks if the letter is an uppercase letter
                {
                    index = alphabetUpper.indexOf(txt1.charAt(i)); //index of the alphabet string where it matches with the letter

                    shiftToLetter = (index + passedKey) % 26; //Index where the encrypted letter is in the alphabet
                    encryptText.append(alphabetUpper.charAt(shiftToLetter)); //Encrypted text string builder
                }

                else if(Character.isLowerCase(txt1.charAt(i)))
                {
                    index = alphabetLower.indexOf(txt1.charAt(i)); //index of the alphabet string where it matches with the letter

                    shiftToLetter = (index + passedKey) % 26;
                    encryptText.append(alphabetLower.charAt(shiftToLetter));
                }

                else
                {

                    encryptText.append(txt1.charAt(i));
                }

            }
        }

        else
        {
            passedKey = (passedKey % 26) + 26; //(passedKey + 26) % 26
            for (int i = 0; i < txt1.length(); i++) //loops through the string(s) in the file 1 character at a time
            {
                if (Character.isUpperCase(txt1.charAt(i))) //Checks if the letter is an uppercase letter
                {
                    index = alphabetUpper.indexOf(txt1.charAt(i)); //index of the alphabet string where it matches with the letter

                    shiftToLetter = (index + passedKey) % 26;
                    encryptText.append(alphabetUpper.charAt(shiftToLetter));
                }
                else if (Character.isLowerCase(txt1.charAt(i))) {
                    index = alphabetLower.indexOf(txt1.charAt(i)); //index of the alphabet string where it matches with the letter

                    shiftToLetter = (index + passedKey) % 26;
                    encryptText.append(alphabetLower.charAt(shiftToLetter));
                } else {
                    encryptText.append(txt1.charAt(i));
                }
            }
        }
        return encryptText.toString();
    }

}