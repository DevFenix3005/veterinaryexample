package com.rebirth.veterinaryexample.app.bdd;

import com.rebirth.veterinaryexample.app.VeterinaryApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(classes = VeterinaryApplication.class)
class VeterinaryApplicationTests {

    @BeforeAll
    static void loadContext() {
        log.info("----------------  Iniciando pruebas BDD  ----------------");
    }

}
