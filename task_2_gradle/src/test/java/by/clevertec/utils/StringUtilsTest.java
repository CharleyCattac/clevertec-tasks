package by.clevertec.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void singlePositiveNumberTest() {
        assertTrue(StringUtils.isPositiveNumber("123"));
        String[] numbers = {"0", "1", "01", "2343534333", "13"};
        assertAll("numbers",
                () -> assertFalse(StringUtils.isPositiveNumber(numbers[0])),
                () -> assertTrue(StringUtils.isPositiveNumber(numbers[1])),
                () -> assertFalse(StringUtils.isPositiveNumber(numbers[2])),
                () -> assertTrue(StringUtils.isPositiveNumber(numbers[3])),
                () -> assertTrue(StringUtils.isPositiveNumber(numbers[4]))
        );
    }

    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            StringUtils.isPositiveNumber(null);
        });
        assertEquals(NullPointerException.class, exception.getClass());
    }
}
