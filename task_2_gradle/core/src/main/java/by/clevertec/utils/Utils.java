package by.clevertec.utils;

import java.util.Arrays;

public class Utils {

    public static boolean isAllPositiveNumbers(String... args) {
        return Arrays.stream(args).allMatch(StringUtils::isPositiveNumber);
    }

    private Utils() {}
}
