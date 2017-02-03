package restquery.helpers;


public class ExceptionMessageUtils {

    public static String message(String invalidToken, String options) {
        return new StringBuilder().append("Invalid token found: ")
                .append("[").append(invalidToken).append("] ")
                .append("Expected: ").append(options).toString();
    }

    public static String messageAfter(String token, String options) {
        return new StringBuilder().append("Expected operator after: ")
                .append("[").append(token).append("] ")
                .append(options).toString();
    }
}
