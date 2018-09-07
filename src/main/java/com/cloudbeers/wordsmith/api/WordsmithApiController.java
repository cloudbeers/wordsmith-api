package com.cloudbeers.wordsmith.api;

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
        return "Greetings from Wordsmith application!";
    }

    @RequestMapping("/noun")
    public String noun() {
        return wordService.generate("nouns");
    }

    @RequestMapping("/verb")
    public String verb() {
        return wordService.generate("verbs");
    }

    @RequestMapping("/adjective")
    public String adjective() {
        return wordService.generate("adjectives");
    }

}