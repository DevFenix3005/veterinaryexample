package com.rebirth.veterinaryexample.app.configuration;

import com.rebirth.veterinaryexample.app.utils.UnirestWrapper;
import kong.unirest.UnirestInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;


@Configuration
public class AppConfiguration {

    @Bean
    @Scope(SCOPE_SINGLETON)
    public UnirestInstance unirestInstance() {
        return UnirestWrapper.INSTANCE;
    }


}
