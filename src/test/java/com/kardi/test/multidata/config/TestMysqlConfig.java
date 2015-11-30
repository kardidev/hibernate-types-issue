package com.kardi.test.multidata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(AppConfig.class)
@PropertySource("/mysql.properties")
public class TestMysqlConfig {
}
