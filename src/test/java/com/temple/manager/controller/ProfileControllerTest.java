package com.temple.manager.controller;

import com.temple.manager.config.RedisConfig;
import com.temple.manager.config.RedisProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Import({RedisConfig.class, RedisProperties.class})
@WebMvcTest(controllers = ProfileController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
class ProfileControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getActiveProfile() throws Exception{
        mockMvc.perform(get("/profile"))
                .andExpect(content().string("test_local"))
                .andDo(print());
    }
}