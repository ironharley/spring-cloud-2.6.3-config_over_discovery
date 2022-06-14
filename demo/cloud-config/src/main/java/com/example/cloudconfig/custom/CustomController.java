package com.example.cloudconfig.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class CustomController {

    private final H2Service h2Service;

    @Autowired public CustomController(H2Service h2Service) {
        this.h2Service = h2Service;
    }

    @GetMapping(value = "/add/{app}/{profile}/{key}/{value}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String up(@PathVariable("app") String app,
                     @PathVariable("profile") String profile,
                     @PathVariable("key") String key,
                     @PathVariable("value") String value) {

        return this.h2Service.create(app, profile, key, value);
    }

}
