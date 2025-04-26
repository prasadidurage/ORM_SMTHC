package lk.ijse.ormsmhtc.util;

import java.util.regex.Pattern;

public class Validation {
    static String gmail = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    static String time = "^([01]\\d|2[0-3]):[0-5]\\d$";
    static String phone = "^\\d{10}$";
    static String address = "^[a-zA-Z0-9\\s,.'/-]{5,100}$";
    static String price = "^\\d+(\\.\\d{1,2})?$";
// Cleaned version

    public static boolean isValid(String input, String type) {
        String regex;

        switch (type.toLowerCase()) {
            case "gmail":
                regex = gmail;
                break;
            case "time":
                regex = time;
                break;
            case "phone":
                regex = phone;
                break;
            case "address":
                regex = address;
                break;
            case "price":
                regex = price;
                break;
            default:
                return false;
        }

        return Pattern.matches(regex, input);
    }
}
