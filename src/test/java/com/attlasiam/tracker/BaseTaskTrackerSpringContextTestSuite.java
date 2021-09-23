package com.attlasiam.tracker;

import com.attlasiam.tracker.utils.Resetable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaskTrackerApplication.class)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // do not use it
public abstract class BaseTaskTrackerSpringContextTestSuite {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private List<Resetable> resetables;

    @After
    public void clearUp() {
        resetables.forEach(Resetable::reset);
    }

    public String asJson(Object object) throws Exception {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(object);
    }

    public <T> T fromResponse(MvcResult result, Class<T> type) throws Exception {
        return new ObjectMapper()
                .readerFor(type)
                .readValue(result.getResponse().getContentAsString());
    }

    public <T> T fromJson(String json, Class<T> type) throws Exception {
        return new ObjectMapper()
                .readerFor(type)
                .readValue(json);
    }
}
