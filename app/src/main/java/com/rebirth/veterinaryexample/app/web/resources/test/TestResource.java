package com.rebirth.veterinaryexample.app.web.resources.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestResource {

    @GetMapping("/version")
    public String getVersion() {
        return "1.0";
    }

}
