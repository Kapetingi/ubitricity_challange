package com.ubitricity.carpark;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void parkCar() throws Exception {
        this.mockMvc
                .perform(post("/car/park")
                        .header("Accept", "application/json")
                        .content("{\"car_id\":\"123\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void parkCarTwice() throws Exception {
        this.mockMvc
                .perform(post("/car/park")
                        .header("Accept", "application/json")
                        .content("{\"car_id\":\"1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc
                .perform(post("/car/park")
                        .header("Accept", "application/json")
                        .content("{\"car_id\":\"1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void unparkCar() throws Exception {
        this.mockMvc
                .perform(post("/car/park")
                        .header("Accept", "application/json")
                        .content("{\"car_id\":\"12\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc
                .perform(post("/car/unpark")
                        .header("Accept", "application/json")
                        .content("{\"car_id\":\"12\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void unparkNotRegisteredCar() throws Exception {

        this.mockMvc
                .perform(post("/car/unpark")
                        .header("Accept", "application/json")
                        .content("{\"car_id\":\"19\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
