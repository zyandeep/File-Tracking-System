// this class will validate a given password
package bca.fts.util;

import java.util.regex.*;

public class PasswordValidator {

    public static String validatePassword(String pass) {

        String message = "";
        String alphabate = "[a-zA-z]+";
        String digit = "\\d+";
        String special = "[^a-zA-Z0-9]+";

        Pattern digitPattern = Pattern.compile(digit);
        Pattern specPattern = Pattern.compile(special);
        Pattern alphaPattern = Pattern.compile(alphabate);

        if (pass.length() < 8) {
            message = "Password is less then 8 characters";
            return message;
        }

        // check for digits
        if (digitPattern.matcher(pass).find() == false) {
            message = "Password does not contain a digit";
            return message;
        }

        // check for special character
        if (specPattern.matcher(pass).find() == false) {
            message = "Password does not contain a special character";
            return message;
        }

        // check for alphabates
        if (alphaPattern.matcher(pass).find() == false) {
            message = "Password does not contain an alphabate";
            return message;
        }

        return message;
    }
}
