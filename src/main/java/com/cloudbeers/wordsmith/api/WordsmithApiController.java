package com.cloudbeers.wordsmith.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

@RestController
public class WordsmithApiController {

    @Autowired
    private WordService wordService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Wordsmith Spring Boot application!";
    }

    @RequestMapping(path = "/noun", produces=MediaType.APPLICATION_JSON_VALUE)
    public String noun() {
        return "{\"word\": \"" + wordService.generateNoun() + "\"}";
    }

    @RequestMapping(path = "/verb", produces=MediaType.APPLICATION_JSON_VALUE)
    public String verb() {
        return "{\"word\": \"" + wordService.generateVerb() + "\"}";
    }

    @RequestMapping(path = "/adjective", produces=MediaType.APPLICATION_JSON_VALUE)
    public String adjective() {
        return "{\"word\": \"" + wordService.generateAdjective() + "\"}";
    }

}
