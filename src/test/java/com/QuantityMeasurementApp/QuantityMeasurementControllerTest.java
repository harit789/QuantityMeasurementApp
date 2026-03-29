package com.QuantityMeasurementApp;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test 1 - Compare two equal quantities
    @Test
    void testCompare_ShouldReturnTrue() throws Exception {
        String requestBody = "{\"q1\": {\"value\": 1.0, \"unit\": \"FEET\"}," +
                              "\"q2\": {\"value\": 12.0, \"unit\": \"INCH\"}}";

        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // Test 2 - Compare two unequal quantities
    @Test
    void testCompare_ShouldReturnFalse() throws Exception {
        String requestBody = "{\"q1\": {\"value\": 1.0, \"unit\": \"FEET\"}," +
                              "\"q2\": {\"value\": 1.0, \"unit\": \"INCH\"}}";

        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    // Test 3 - Add two quantities
    @Test
    void testAdd_ShouldReturnCorrectSum() throws Exception {
        String requestBody = "{\"q1\": {\"value\": 1.0, \"unit\": \"FEET\"}," +
                              "\"q2\": {\"value\": 12.0, \"unit\": \"INCH\"}}";

        mockMvc.perform(post("/api/v1/quantities/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(2.0))
                .andExpect(jsonPath("$.unit").value("FEET"));
    }

    // Test 4 - Convert quantities
    @Test
    void testConvert_ShouldReturnCorrectValue() throws Exception {
        String requestBody = "{\"q\": {\"value\": 1.0, \"unit\": \"FEET\"}," +
                              "\"targetUnit\": \"INCH\"}";

        mockMvc.perform(post("/api/v1/quantities/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(12.0))
                .andExpect(jsonPath("$.unit").value("INCH"));
    }

    // Test 5 - Invalid unit should return 400
    @Test
    void testCompare_InvalidUnit_ShouldReturn400() throws Exception {
        String requestBody = "{\"q1\": {\"value\": 1.0, \"unit\": \"FOOT\"}," +
                              "\"q2\": {\"value\": 12.0, \"unit\": \"INCH\"}}";

        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    // Test 6 - Get history returns a list
    @Test
    void testGetHistory_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/api/v1/quantities/history/operation/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
