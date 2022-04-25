package com.rebirth.veterinaryexample.app.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BootstrapRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Iniciando la aplicacion!!");
    }
}
