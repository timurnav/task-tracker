package com.attlasiam.tracker;

import com.attlasiam.tracker.service.exceptions.ValidationException;
import com.attlasiam.tracker.service.users.UserDTO;
import com.attlasiam.tracker.service.users.UserValidator;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SpyBeanUserTestSuite extends BaseTaskTrackerSpringContextTestSuite {

    @SpyBean
//    @MockBean
    private UserValidator validator;

    @Test
    public void validationFailureHacked() throws Exception {
        doThrow(new ValidationException("Test text"))
                .when(validator).validateNew(any(UserDTO.class));

        UserDTO request = new UserDTO(123L, "User Name", "admin@gmail.com", false);
        String errorMessage = mockMvc.perform(put("/users").content(asJson(request))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertThat(errorMessage).isEqualTo("Test text");
    }

    @Test
    public void serverError() throws Exception {
        doThrow(new NullPointerException("Test text"))
                .when(validator).validateNew(any(UserDTO.class));
        UserDTO request = new UserDTO(null, "User Name", "admin@gmail.com", false);
        mockMvc.perform(put("/users").content(asJson(request))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void validationFailure() throws Exception {
        UserDTO request = new UserDTO(1L, "User Name", "admin@gmail.com", false);
        String errorMessage = mockMvc.perform(put("/users").content(asJson(request))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertThat(errorMessage).isEqualTo("User id must be null");
    }

}
