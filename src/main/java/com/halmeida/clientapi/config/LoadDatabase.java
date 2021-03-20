package com.halmeida.clientapi.config;

import com.halmeida.clientapi.models.Client;
import com.halmeida.clientapi.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ClientRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Client("Antonio", "Nail", "an@gmail.com", LocalDate.parse("1990-10-10"))));
            log.info("Preloading " + repository.save(new Client("Mario", "Axe", "ma@gmail.com", LocalDate.parse("2000-05-05"))));
            log.info("Preloading " + repository.save(new Client("Rodrigo", "Hammer", "rh@gmail.com", LocalDate.parse("2010-02-02"), false)));
        };
    }
}
