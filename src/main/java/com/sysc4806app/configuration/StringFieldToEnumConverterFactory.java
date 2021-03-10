package com.sysc4806app.configuration;

import com.sysc4806app.model.PathVariableEnum;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * @version milestone 1
 *
 * A factory that creates a path variable enum mapping from a string.
 */
@Component
public class StringFieldToEnumConverterFactory implements ConverterFactory<String, PathVariableEnum> {

    /**
     * A converter that maps a string to a particular path variable enum.
     * @param <T> The path variable enum that shall be mapped onto.
     */
    private static class StringFieldToEnumConverter<T extends Enum<T> & PathVariableEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        /**
         * Creates a new converter.
         * @param enumType The enum type to map onto.
         */
        public StringFieldToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            return PathVariableEnum.urlSegmentToEnum(this.enumType, source);
        }
    }

    @Override
    @SuppressWarnings("all")
    public <P extends PathVariableEnum> Converter<String, P> getConverter(Class<P> type) {
        return new StringFieldToEnumConverter(type);
    }
}
