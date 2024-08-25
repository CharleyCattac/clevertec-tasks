package by.clevertec.utils;

public class StringUtils {

    public static final String POSITIVE_INTEGER_REGEX = "[1-9]\\d*";

    /**
     * Verifies if provided string is a valid positive integer
     * @param str should contain no extra symbols apart from the number
     * @return match result
     */
    public static boolean isPositiveNumber(String str) {
        return str.matches(POSITIVE_INTEGER_REGEX);
    }

    private StringUtils() {}
}
