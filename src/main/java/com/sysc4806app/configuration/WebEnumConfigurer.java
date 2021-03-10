package com.sysc4806app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version milestone 1
 *
 * Configuration to support the string field to enum converter.
 */
@Configuration
public class WebEnumConfigurer implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverterFactory(new StringFieldToEnumConverterFactory());
    }
}
