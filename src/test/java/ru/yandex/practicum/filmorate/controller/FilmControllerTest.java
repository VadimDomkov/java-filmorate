package ru.yandex.practicum.filmorate.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @Test
    void createFilm() throws Exception {
        mvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Film name\", \"description\": \"Film descr\", \"releaseDate\": \"1967-03-25\", \"duration\": 100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Film name"))
                .andExpect(jsonPath("$.description").value("Film descr"));
    }

    @Test
    void createFilmWithBadDate() throws Exception {
        mvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Film name\", \"description\": \"Film descr\", \"releaseDate\": \"1890-03-25\", \"duration\": 100}"))
                .andExpect(status().is(400));
    }

    @Test
    void createFilmWithTooLongDescription() throws Exception {
        mvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Film name\", \"description\": \"Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.\", \"releaseDate\": \"1990-03-25\", \"duration\": 100}"))
                .andExpect(status().is(400));
    }

    @Test
    void createFilmWithEmptyName() throws Exception {
        mvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\", \"description\": \"Descr\", \"releaseDate\": \"1990-03-25\", \"duration\": 100}"))
                .andExpect(status().is(400));
    }

    @Test
    void createFilmWithNegativeDuration() throws Exception {
        mvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Film name\", \"description\": \"Film descr\", \"releaseDate\": \"1967-03-25\", \"duration\": -100}"))
                .andExpect(status().is(400));
    }

    @Test
    void updateFilm() throws Exception {
        mvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Film name\", \"description\": \"Film descr\", \"releaseDate\": \"1967-03-25\", \"duration\": 100}"));

        mvc.perform(put("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"name\": \"Film new name\", \"description\": \"Film new descr\", \"releaseDate\": \"1967-03-25\", \"duration\": 100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Film new name"))
                .andExpect(jsonPath("$.description").value("Film new descr"));
    }

//    @Test
//    void returnAllFilms() throws Exception {
//        mvc.perform(post("/films")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\": \"Film name\", \"description\": \"Film descr\", \"releaseDate\": \"1967-03-25\", \"duration\": 100}"));
//
//        MvcResult result = mvc.perform(get("/films")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn();
//        Assertions.assertEquals("[{\"id\":1,\"name\":\"Film name\",\"description\":\"Film descr\",\"releaseDate\":\"1967-03-25\",\"duration\":100}]", result.getResponse().getContentAsString());
//    }
}