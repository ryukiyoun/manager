package com.temple.manager.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temple.manager.common.config.PasswordEncoderConfig;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.notification.dto.NotificationDTO;
import com.temple.manager.notification.service.NotificationService;
import com.temple.manager.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({RedisConfig.class, RedisProperties.class, PasswordEncoderConfig.class})
@WebMvcTest(controllers = NotificationController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
public class NotificationControllerTest {
    @MockBean
    NotificationService notificationService;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Slack 메시지 전달 EndPoint 테스트")
    void sendRequestMessage() throws Exception{
        //given
        doNothing().when(notificationService).sendFunctionRequestMessage(anyString());

        //when, then
        mockMvc.perform(post("/slack/notification")
                .content("{message: \"test\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Slack Interactive 결과 EndPoint 테스트")
    void sendResponseMessage() throws Exception{
        //given
        doNothing().when(notificationService).sendResponseMessage(anyString());

        //when, then
        mockMvc.perform(post("/slack/callback")
                .param("payload", "test"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Notification 확인 EndPoint 테스트")
    void notificationConfirm() throws Exception{
        //given
        NotificationDTO content = NotificationDTO.builder()
                .notificationId(1)
                .requestMessage("request")
                .responseTitle("title")
                .responseMessage("message")
                .build();

        given(notificationService.setNotificationConfirm(anyLong())).willReturn(content);

        //when, then
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(content);

        mockMvc.perform(get("/notification/confirm/1"))
                .andExpect(content().json(result))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("")
    void getRecentNotification() throws Exception{
        //given
        List<NotificationDTO> fixture = new ArrayList<>();
        fixture.add(NotificationDTO.builder()
                .notificationId(1)
                .requestMessage("test")
                .responseTitle("title")
                .responseMessage("message")
                .confirm(false)
                .build());

        given(notificationService.recentNotification(any(UserDetails.class))).willReturn(fixture);

        //when, then
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(fixture);

        mockMvc.perform(get("/notification/recent"))
                .andExpect(content().json(result))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
