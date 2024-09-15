package by.clevertec.util;

import by.clevertec.model.*;
import by.clevertec.util.reader.JsonReader;
import by.clevertec.util.reader.Reader;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class TestUtil {

    private static final String ANIMALS_DATA_FILE_TASK_1 = "src/test/resources/test-json/results/task1_animals.json";

    private static final Reader reader = new JsonReader();

    public static List<Animal> getAnimalsForTask1() {
        return reader.getModelData(ANIMALS_DATA_FILE_TASK_1, new TypeReference<>() {
        });
    }
}
