package com.temple.manager.believer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temple.manager.common.config.PasswordEncoderConfig;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.believer.service.BelieverService;
import com.temple.manager.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Import({RedisConfig.class, RedisProperties.class, PasswordEncoderConfig.class})
@WebMvcTest(controllers = BelieverController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
class BelieverControllerTest {
    @MockBean
    BelieverService believerService;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    BelieverDTO fixture1, fixture2;

    @BeforeEach
    void init() {
        fixture1 = BelieverDTO.builder()
                .believerId(1)
                .believerName("?????????")
                .address("?????????")
                .birthOfYear("596928")
                .lunarSolarType(LunarSolarType.LUNAR)
                .build();

        fixture2 = BelieverDTO.builder()
                .believerId(2)
                .believerName("?????????")
                .address("?????????")
                .birthOfYear("598274")
                .lunarSolarType(LunarSolarType.SOLAR)
                .build();
    }

    @Test
    @DisplayName("?????? ?????? Page ?????? ?????????")
    void accessPage() throws Exception {
        //given, when, then
        mockMvc.perform(get("/believer"))
                .andExpect(view().name("page/believer"))
                .andDo(print());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????? ?????????")
    void getBelievers() throws Exception {
        //given
        List<BelieverDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(believerService.getAllBelievers()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/believers")
                .with(csrf()))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("????????? ?????? ?????? Like ?????? ?????? ?????? ?????????")
    void getBelieversByName() throws Exception {
        //given
        List<BelieverDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(believerService.getBelieversByName(anyString())).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/believers/tester")
                .with(csrf()))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ???????????? ?????? ?????? ?????? ?????????")
    void getBelieversByNameAndBirthOfYear() throws Exception {
        //given
        given(believerService.getBelieverByNameAndBirtOfYear(anyString(), anyString())).willReturn(fixture1);

        //when, then
        String content = objectMapper.writeValueAsString(fixture1);

        mockMvc.perform(get("/believer/search")
                .with(csrf())
                .param("believerName", "tester")
                .param("birthOfYear", "123123"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ?????? ?????? 5??? ?????? ?????????")
    void getRecent5Believer() throws Exception {
        //given
        List<BelieverDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(believerService.getRecent5Believers()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/believers/top/5")
                .with(csrf()))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ?????? ?????????")
    void appendBeliever() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(fixture1);

        given(believerService.appendBeliever(any(BelieverDTO.class))).willReturn(fixture1);

        //when, then
        mockMvc.perform(post("/believer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf()))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    void updateBeliever() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(fixture1);

        doNothing().when(believerService).updateBeliever(anyLong(), any(BelieverDTO.class));

        //when, then
        mockMvc.perform(put("/believer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf()))
                .andExpect(content().string("Success"))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ?????? Soft Delete ?????????")
    void deleteBeliever() throws Exception {
        //given
        doNothing().when(believerService).deleteBeliever(anyLong());

        //when, then
        mockMvc.perform(delete("/believer/1")
                .with(csrf()))
                .andExpect(content().string("Success"))
                .andDo(print());
    }
}