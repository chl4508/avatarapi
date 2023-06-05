package com.morpheus.avatarapi.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class ConfigUtil {

    private final Environment environment;

    public String getProperty(String key){
        return environment.getProperty(key);
    }
}
