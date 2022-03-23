package com.temple.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temple.manager.config.RedisConfig;
import com.temple.manager.config.RedisProperties;
import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.dto.CodeDTO;
import com.temple.manager.prayer.controller.PrayerController;
import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.prayer.service.PrayerService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Import({RedisConfig.class, RedisProperties.class})
@WebMvcTest(controllers = PrayerController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
class PrayerControllerTest {
    @MockBean
    PrayerService prayerService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    PrayerDTO fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = PrayerDTO.builder()
                .prayerId(1)
                .believer(BelieverDTO.builder()
                        .believerId(1)
                        .believerName("tester1")
                        .build())
                .code(CodeDTO.builder()
                        .codeId(1)
                        .codeName("testCode1")
                        .build())
                .prayerStartDate(LocalDate.now())
                .build();

        fixture2 = PrayerDTO.builder()
                .prayerId(2)
                .believer(BelieverDTO.builder()
                        .believerId(2)
                        .believerName("tester2")
                        .build())
                .code(CodeDTO.builder()
                        .codeId(2)
                        .codeName("testCode2")
                        .build())
                .prayerStartDate(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("화면 접속 Page 반환 테스트")
    void accessPage() throws Exception{
        //given, when, then
        mockMvc.perform(get("/prayer"))
                .andExpect(view().name("page/prayer"))
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 기도 정보 반환 테스트")
    void getAllPrayers() throws Exception{
        //given
        List<PrayerDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(prayerService.getAllPrayers()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/prayers"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 가족 신도ID 검색 정보 반환 테스트")
    void getPrayersByBelieverId() throws Exception {
        //given
        List<PrayerDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(prayerService.getPrayersByBelieverId(anyLong())).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/prayers/1"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("기도 추가 테스트")
    void appendPrayer() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(fixture1);

        given(prayerService.appendPrayer(any(PrayerDTO.class))).willReturn(fixture1);

        //when, then
        mockMvc.perform(post("/prayer")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 기도 수정 테스트")
    void updatePrayers() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(fixture1);

        doNothing().when(prayerService).updatePrayer(anyLong(), any(PrayerDTO.class));

        //when, then
        mockMvc.perform(put("/prayer/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(content().string("Success"))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 기도 삭제 테스트")
    void deletePrayers() throws Exception{
        //given
        doNothing().when(prayerService).deletePrayer(anyLong());

        //when, then
        mockMvc.perform(delete("/prayer/1")
                .with(csrf()))
                .andExpect(content().string("Success"))
                .andDo(print());
    }
}