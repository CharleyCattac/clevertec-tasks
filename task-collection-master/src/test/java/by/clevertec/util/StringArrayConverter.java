package by.clevertec.util;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class StringArrayConverter extends SimpleArgumentConverter {

    public static final String SPLITTERATOR = "\\s*,\\s*";

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if (source instanceof String && String[].class.isAssignableFrom(targetType)) {
            if (((String) source).isEmpty()) {
                return new String[0];
            } else {
                return ((String) source).split(SPLITTERATOR);
            }
        } else {
            throw new IllegalArgumentException("Conversion from " + source.getClass() + " to "
                    + targetType + " not supported.");
        }
    }

}
