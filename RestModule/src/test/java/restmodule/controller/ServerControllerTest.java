package restmodule.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = "/test/serverControllerTest.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("/applicationtest.properties")
public class ServerControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void serverAddTest() throws Exception {
         mockMvc.perform(
                MockMvcRequestBuilders.post("/server")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test2\",\"owner\":{\"id\":1,\"displayName\":\"tom Kaathoven\",\"email\":\"tomdatbeniksteam@gmail.com\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    void serverAddNoNameTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/server")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"owner\":{\"id\":166,\"displayName\":\"tom Kaathoven\",\"email\":\"tomdatbeniksteam@gmail.com\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void serverGetByEmailTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/server/getbyuser?email=test@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    void serverGetByEmailNoEmailTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/server/getbyuser?email=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void serverJoinSuccessTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/server/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"id\":1,\"displayName\":\"\",\"email\":\"\"},\"code\":\"512\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void serverNoUserTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/server/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"id\":0,\"displayName\":\"\",\"email\":\"\"},\"code\":\"123\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void serverNoServerTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/server/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"id\":1,\"displayName\":\"\",\"email\":\"\"},\"code\":\"0\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void serverLeaveServerTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/server/leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"id\":1,\"displayName\":\"\",\"email\":\"test@test.com\"},\"server_id\":4}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}