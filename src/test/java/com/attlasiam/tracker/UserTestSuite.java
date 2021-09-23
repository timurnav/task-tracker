package com.attlasiam.tracker;

import com.attlasiam.tracker.service.users.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaskTrackerApplication.class)
@AutoConfigureMockMvc
public class UserTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        UserDTO request = new UserDTO(null, "User Name", "admin@gmail.com", false);
        MvcResult result = mockMvc.perform(put("/users").content(asJson(request))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserDTO response = fromResponse(result, UserDTO.class);

        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo(request.getName());
        Assertions.assertThat(response.getEmail()).isEqualTo(request.getEmail());

        MvcResult resultAfterSave = mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserDTO[] allAfterSave = fromResponse(resultAfterSave, UserDTO[].class);
        Assertions.assertThat(allAfterSave).hasSize(1);
        UserDTO theOne = allAfterSave[0];
        Assertions.assertThat(theOne.getId()).isNotNull();
        Assertions.assertThat(theOne.getName()).isEqualTo(request.getName());
        Assertions.assertThat(theOne.getEmail()).isEqualTo(request.getEmail());
        Assertions.assertThat(theOne.isDeleted()).isFalse();
    }

    @Test
    public void deleteUser() throws Exception {
        createUser();

        MvcResult resultAfterSave = mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserDTO[] allAfterSave = fromResponse(resultAfterSave, UserDTO[].class);
        UserDTO userDTO = allAfterSave[0];

        mockMvc.perform(delete("/users/" + userDTO.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    public static String asJson(Object object) throws Exception {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(object);
    }

    public static <T> T fromResponse(MvcResult result, Class<T> type) throws Exception {
        return new ObjectMapper()
                .readerFor(type)
                .readValue(result.getResponse().getContentAsString());
    }

    public static <T> T fromJson(String json, Class<T> type) throws Exception {
        return new ObjectMapper()
                .readerFor(type)
                .readValue(json);
    }

}
