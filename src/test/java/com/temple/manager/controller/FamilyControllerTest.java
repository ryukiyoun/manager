package com.temple.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temple.manager.config.RedisConfig;
import com.temple.manager.config.RedisProperties;
import com.temple.manager.dto.BelieverDTO;
import com.temple.manager.dto.FamilyDTO;
import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.service.FamilyService;
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
@WebMvcTest(controllers = FamilyController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
class FamilyControllerTest {
    @MockBean
    FamilyService familyService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    FamilyDTO fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = FamilyDTO.builder()
                .familyId(1)
                .familyName("tester1")
                .familyType(FamilyType.FATHER)
                .believer(BelieverDTO.builder()
                        .believerId(1)
                        .believerName("believer1")
                        .birthOfYear("111111")
                        .address("부산")
                        .build())
                .lunarSolarType(LunarSolarType.LUNAR)
                .birthOfYear("111111")
                .etcValue("etc1")
                .build();

        fixture2 = FamilyDTO.builder()
                .familyId(2)
                .familyName("tester2")
                .familyType(FamilyType.MOTHER)
                .believer(BelieverDTO.builder()
                        .believerId(1)
                        .believerName("believer2")
                        .birthOfYear("222222")
                        .address("서울")
                        .build())
                .lunarSolarType(LunarSolarType.SOLAR)
                .birthOfYear("222222")
                .etcValue("etc2")
                .build();
    }

    @Test
    @DisplayName("화면 접속 Page 반환 테스트")
    void accessPage() throws Exception{
        //given, when, then
        mockMvc.perform(get("/family"))
                .andExpect(view().name("page/family"))
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 가족 신도ID 검색 정보 반환 테스트")
    void getFamiliesByBelieverId() throws Exception{
        //given
        List<FamilyDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(familyService.getFamiliesByBelieverId(anyLong())).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/families/1")
                .with(csrf()))
                .andExpect(content().json(content))
                .andDo(print());

    }

    @Test
    @DisplayName("가족 추가 테스트")
    void appendBeliever() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(fixture1);

        given(familyService.appendFamily(any(FamilyDTO.class))).willReturn(fixture1);

        //when, then
        mockMvc.perform(post("/family")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf()))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 가족 수정 테스트")
    void updateBeliever() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(fixture1);

        doNothing().when(familyService).updateFamily(anyLong(), any(FamilyDTO.class));

        //when, then
        mockMvc.perform(put("/family/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .with(csrf()))
                .andExpect(content().string("Success"))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 가족 Soft Delete 테스트")
    void deleteBeliever() throws Exception{
        //given
        doNothing().when(familyService).deleteFamily(anyLong());

        //when, then
        mockMvc.perform(delete("/family/1")
                .with(csrf()))
                .andExpect(content().string("Success"))
                .andDo(print());
    }
}