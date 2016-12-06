package ru.sbrf.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataBase.class)
public class MainConfig {
}
