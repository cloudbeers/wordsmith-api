package com.cloudbeers.wordsmith.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class WordService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WordService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
  }
  
    public String generateNoun() {
        String sql = "SELECT word FROM nouns ORDER BY random() LIMIT 1";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public String generateVerb() {
        String sql = "SELECT word FROM verbs ORDER BY random() LIMIT 1";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public String generateAdjective() {
        String sql = "SELECT word FROM adjectives ORDER BY random() LIMIT 1";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

}