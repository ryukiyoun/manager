package com.temple.manager.common.controller;

import com.temple.manager.common.config.PasswordEncoderConfig;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({RedisConfig.class, RedisProperties.class, PasswordEncoderConfig.class})
@WebMvcTest(controllers = RegistrationController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
public class RegistrationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("화면 접속 Page 반환 테스트")
    void accessPage() throws Exception {
        //given, when, then
        mockMvc.perform(get("/registration"))
                .andExpect(model().attribute("believerName", ""))
                .andExpect(model().attribute("birthOfYear", ""))
                .andExpect(view().name("page/registration"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("화면 접속 Page 반환 파라미터 전달 테스트")
    void accessPageParam() throws Exception {
        //given, when, then
        mockMvc.perform(get("/registration?believerName=tester&birthOfYear=123123"))
                .andExpect(model().attribute("believerName", "tester"))
                .andExpect(model().attribute("birthOfYear", "123123"))
                .andExpect(view().name("page/registration"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
