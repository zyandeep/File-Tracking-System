// this class will validate a given date
package bca.fts.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.*;

public class DateValidator {

    // the logger object
    private static final Logger logger = Logger.getLogger(DateValidator.class);

    // this method wii validate a date
    public static boolean validateDate(String date) {

        String[] tokens = null;
        int day = 0, month = 0, year = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int currentYear = Integer.valueOf(sdf.format(new Date()));

        boolean result = false;

        if (date.isEmpty() == false) {

            if (date.length() == 10) {

                tokens = date.split("[-]");

                if (tokens.length == 3 && tokens[0].length() == 2 && tokens[1].length() == 2 && tokens[2].length() == 4) {

                    // convert the strings to number
                    try {
                        day = Integer.valueOf(tokens[0]);
                        month = Integer.valueOf(tokens[1]);
                        year = Integer.valueOf(tokens[2]);

                        if ((day >= 1 && day <= 31) && (month >= 1 && month <= 12) && (year > 1960 && year <= currentYear)) {

                            // check for february
                            if (month == 2) {

                                if ((year % 4 == 0) && day <= 29) {
                                    result = true;
                                } else if ((year % 4 != 0) && day <= 28) {
                                    result = true;
                                }

                            } // check for 30-days months
                            else if ((month == 4 || month == 6 || month == 9 || month == 11) && day <= 30) {
                                result = true;
                            } // check for 31-days months
                            else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                                    || month == 10 || month == 12) {

                                result = true;
                            }

                        }
                    } catch (NumberFormatException e) {
                        logger.error(e);

                        result = false;
                    }
                }
            }

        }

        return result;
    }

}
