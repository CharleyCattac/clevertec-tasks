package by.clevertec.core.utils;

import by.clevertec.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

    @Test
    void allPositiveNumbersTest() {
        assertTrue(Utils.isAllPositiveNumbers("12", "434534", "2323"), "All numbers should be positive");
    }

    @Test
    void notAllPositiveNumbersTest() {
        assertFalse(Utils.isAllPositiveNumbers("3", "-16", "7"),"Some numbers wouldn't pass as positive");
    }
}
