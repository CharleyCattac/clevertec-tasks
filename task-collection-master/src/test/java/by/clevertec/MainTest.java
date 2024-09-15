package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.util.StringArrayConverter;
import by.clevertec.util.TestUtil;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

class MainTest {

    @Test
    void task1_returnsAnimalsForThirdZoo() {
        // given
        List<Animal> expectedAnimals = TestUtil.getAnimalsForTask1();
        // when
        List<Animal> actualAnimals = Main.task1();
        // then
        assertArrayEquals(expectedAnimals.toArray(), actualAnimals.toArray());
    }

    @Test
    void task3_returnsOrigins() {
        // given
        List<String> expectedOrigin = Arrays.asList("Azeri", "Aymara", "Afrikaans", "Arabic",
                "Armenian", "Amharic", "Assamese", "Albanian");
        // when
        List<String> actualOrigin = Main.task3();
        // then
        assertArrayEquals(expectedOrigin.toArray(), actualOrigin.toArray());
    }

    @Test
    void task4_returnsFemaleAnimalsCount() {
        // given
        long expectedCount = 476;
        // when
        long actualCount = Main.task4();
        // then
        assertEquals(expectedCount, actualCount);
    }

    @ParameterizedTest
    @CsvSource({"P-1,'Adams, Carter, Jonson'", "C-3,'Smith, Jonson, Williams'", "A-7,''"})
    void task19_returnsStudentWhoPassedThirdExam(String groupName, @ConvertWith(StringArrayConverter.class) String[] students) {
        // given
        List<String> expectedStudents = Arrays.asList(students);
        System.out.println(expectedStudents);
        // when
        List<String> actualStudents = Main.task19(groupName);
        // then
        assertArrayEquals(expectedStudents.toArray(), actualStudents.toArray());
    }

    @Test
    void task19_throwsExceptionDueToNullGroup() {
        // given
        String npeMessage = "out of bound";
        // when
        Exception actualException = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Main.task19();
        });
        // then
        assertTrue(actualException.getMessage().contains(npeMessage));
    }
}
