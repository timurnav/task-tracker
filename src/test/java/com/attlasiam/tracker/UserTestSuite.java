package com.attlasiam.tracker;

import com.attlasiam.tracker.service.users.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTestSuite extends BaseTaskTrackerSpringContextTestSuite {

    public static final String USERS = "/users";

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(get(USERS).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        UserDTO request = new UserDTO(null, "User Name", "admin@gmail.com", false);
        MvcResult result = mockMvc.perform(put(USERS).content(asJson(request))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserDTO response = fromResponse(result, UserDTO.class);

        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo(request.getName());
        Assertions.assertThat(response.getEmail()).isEqualTo(request.getEmail());

        MvcResult resultAfterSave = mockMvc.perform(get(USERS).accept(APPLICATION_JSON))
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

        MvcResult resultAfterSave = mockMvc.perform(get(USERS).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserDTO[] allAfterSave = fromResponse(resultAfterSave, UserDTO[].class);
        UserDTO userDTO = allAfterSave[0];

        mockMvc.perform(delete(USERS + "/" + userDTO.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get(USERS).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
