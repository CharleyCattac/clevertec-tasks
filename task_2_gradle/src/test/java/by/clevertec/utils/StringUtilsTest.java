package by.clevertec.utils;

import by.clevertec.utils.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @Test
    void singlePositiveNumberTest() {
        assertTrue(StringUtils.isPositiveNumber(("123")));
    }
}
