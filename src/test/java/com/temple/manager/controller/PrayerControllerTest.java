package com.temple.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temple.manager.config.PasswordEncoderConfig;
import com.temple.manager.config.RedisConfig;
import com.temple.manager.config.RedisProperties;
import com.temple.manager.prayer.controller.PrayerController;
import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.prayer.dto.PrayerGridListDTO;
import com.temple.manager.prayer.dto.PrayerTypeGroupCntDTO;
import com.temple.manager.prayer.service.PrayerService;
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

@Import({RedisConfig.class, RedisProperties.class, PasswordEncoderConfig.class})
@WebMvcTest(controllers = PrayerController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
class PrayerControllerTest {
    @MockBean
    PrayerService prayerService;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    PrayerDTO fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = PrayerDTO.builder()
                .prayerId(1)
                .believerId(1)
                .prayerTypeCodeId(1)
                .prayerStartDate(LocalDate.now())
                .build();

        fixture2 = PrayerDTO.builder()
                .prayerId(2)
                .believerId(2)
                .prayerTypeCodeId(2)
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
        List<PrayerGridListDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new PrayerGridListDTO(1, LocalDate.now(), 1, "111111", "tester", 1, "testCodeName"));
        fixtureList.add(new PrayerGridListDTO(2, LocalDate.now(), 2, "222222", "tester2", 2, "testCodeName2"));

        given(prayerService.getAllPrayers()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/prayers"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 기도 신도ID 검색 정보 반환 테스트")
    void getPrayersByBelieverId() throws Exception {
        //given
        List<PrayerGridListDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new PrayerGridListDTO(1, LocalDate.now(), 1, "111111", "tester", 1, "testCodeName"));
        fixtureList.add(new PrayerGridListDTO(2, LocalDate.now(), 2, "222222", "tester2", 2, "testCodeName2"));

        given(prayerService.getPrayersByBelieverId(anyLong())).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/prayers/1"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("기도별 등록 건수 정보 반환 테스트")
    void getPrayersTypeGroupCnt() throws Exception {
        //given
        List<PrayerTypeGroupCntDTO> fixtureList = new ArrayList<>();
        fixtureList.add(PrayerTypeGroupCntDTO.builder()
                .prayerName("testPrayer1")
                .prayerCnt(10)
                .build());

        fixtureList.add(PrayerTypeGroupCntDTO.builder()
                .prayerName("testPrayer1")
                .prayerCnt(20)
                .build());

        given(prayerService.getPrayersTypeGroupCnt()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/prayers/chart/count"))
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