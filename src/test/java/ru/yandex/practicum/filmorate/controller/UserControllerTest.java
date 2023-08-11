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
class UserControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @Test
    void returnUsers() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"login\": \"dolore\",\n" +
                        "  \"name\": \"Nick Name\",\n" +
                        "  \"email\": \"mail@mail.ru\",\n" +
                        "  \"birthday\": \"1946-08-20\"\n" +
                        "}"));

        MvcResult result = mvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals("[{\"id\":1,\"email\":\"mail@mail.ru\",\"login\":\"dolore\",\"name\":\"Nick Name\",\"birthday\":\"1946-08-20T00:00:00.000+00:00\",\"nameNull\":false}]", result.getResponse().getContentAsString());
    }

    @Test
    void createUser() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"login\": \"dolore\",\n" +
                        "  \"name\": \"Nick Name\",\n" +
                        "  \"email\": \"mail@mail.ru\",\n" +
                        "  \"birthday\": \"1946-08-20\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nick Name"))
                .andExpect(jsonPath("$.email").value("mail@mail.ru"));
    }

    @Test
    void createUserWithInvalidLogin() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"login\": \"dolore ullamco\",\n" +
                        "  \"email\": \"yandex@mail.ru\",\n" +
                        "  \"birthday\": \"2446-08-20\"\n" +
                        "}"))
                .andExpect(status().is(400));
    }


    @Test
    void createUserWithInvalidEmail() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"login\": \"dolore ullamco\",\n" +
                        "  \"name\": \"\",\n" +
                        "  \"email\": \"mail.ru\",\n" +
                        "  \"birthday\": \"1980-08-20\"\n" +
                        "}"))
                .andExpect(status().is(400));
    }


    @Test
    void createUserWithInvalidBirthday() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"login\": \"dolore ullamco\",\n" +
                        "  \"name\": \"\",\n" +
                        "  \"email\": \"mail.ru\",\n" +
                        "  \"birthday\": \"1980-08-20\"\n" +
                        "}"))
                .andExpect(status().is(400));
    }

    @Test
    void createUserWithEmptyName() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"login\": \"common\",\n" +
                        "  \"email\": \"friend@common.ru\",\n" +
                        "  \"birthday\": \"2000-08-20\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("common"));
    }

    @Test
    void updateUser() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"login\": \"dolore\",\n" +
                        "  \"name\": \"Nick Name\",\n" +
                        "  \"email\": \"mail@mail.ru\",\n" +
                        "  \"birthday\": \"1946-08-20\"\n" +
                        "}"));

        mvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"login\": \"doloreUpdate\",\n" +
                        "  \"name\": \"est adipisicing\",\n" +
                        "  \"id\": 1,\n" +
                        "  \"email\": \"mail@yandex.ru\",\n" +
                        "  \"birthday\": \"1976-09-20\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("est adipisicing"))
                .andExpect(jsonPath("$.login").value("doloreUpdate"));


    }
}