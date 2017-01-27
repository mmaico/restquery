package restquery.helpers;


public class ExceptionMessageUtils {

    public static String message(String invalidToken, String options) {
        return new StringBuilder().append("Invalid token found: ")
                .append("[").append(invalidToken).append("] ")
                .append("Expected: ").append(options).toString();
    }
}
