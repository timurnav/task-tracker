package com.attlasiam.tracker;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JunitTest {

    @Test
    public void tryJunit() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
        org.junit.jupiter.api.Assertions.assertEquals(map.size(), 1, "Add some message here");
    }

    @Test
    public void tryAssertjJunit() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
//        map.put("2", "1");
        Assertions.assertThat(map)
                .as("Map expected to have only %s element", 1)
                .hasSize(1);
    }
}
