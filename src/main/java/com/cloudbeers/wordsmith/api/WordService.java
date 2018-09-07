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
  
  public String generate(String table) {
    String sql = "SELECT word FROM " + table + " ORDER BY random() LIMIT 1";

    String retour = (String)jdbcTemplate.queryForObject(sql, String.class);

    return retour;
  }

}