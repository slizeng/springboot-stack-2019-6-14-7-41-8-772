package com.tw.apistackbase.config;

import com.tw.apistackbase.dto.Gender;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class GenderConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(String value) {
        return Gender.valueOf(value);
    }
}