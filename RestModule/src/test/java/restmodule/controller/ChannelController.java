package restmodule.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = "/test/serverTest.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("/applicationtest.properties")
public class ChannelController {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void channelAddSuccess() throws Exception {
         mockMvc.perform(
                MockMvcRequestBuilders.post("/channel/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"server_id\": 1, \"name\": \"test\", \"type\": \"VOICE\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    void channelAddNoServer() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/channel/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"server_id\": 999, \"name\": \"test\", \"type\": \"VOICE\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    void channelAddShortName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/channel/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"server_id\": 1, \"name\": \"t\", \"type\": \"VOICE\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(notNullValue()));
    }
}