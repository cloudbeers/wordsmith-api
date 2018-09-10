package com.cloudbeers.wordsmith.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isIn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class WordsmithApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Wordsmith application!")));
    }

    @Test
    public void getNoun() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/noun").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(isIn(Arrays.asList("{word: 'Developer'}", "{word: 'Project Manager'}", "{word: 'Tester'}"))));
    }

    @Test
    public void getVerb() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/verb").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(isIn(Arrays.asList("{word: 'loves'}", "{word: 'hates'}", "{word: 'cares about'}"))));
    }

    @Test
    public void getAdjective() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/adjective").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(isIn(Arrays.asList("{word: 'Beautiful'}", "{word: 'Ugly'}"))));
    }
}