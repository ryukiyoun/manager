package com.temple.manager.family.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temple.manager.common.config.PasswordEncoderConfig;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.family.dto.FamilyDTO;
import com.temple.manager.family.service.FamilyService;
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
@WebMvcTest(controllers = FamilyController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
class FamilyControllerTest {
    @MockBean
    FamilyService familyService;

    @MockBean
    UserService userService;

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
                .believerId(1)
                .lunarSolarType(LunarSolarType.LUNAR)
                .birthOfYear("111111")
                .etcValue("etc1")
                .build();

        fixture2 = FamilyDTO.builder()
                .familyId(2)
                .familyName("tester2")
                .familyType(FamilyType.MOTHER)
                .believerId(1)
                .lunarSolarType(LunarSolarType.SOLAR)
                .birthOfYear("222222")
                .etcValue("etc2")
                .build();
    }

    @Test
    @DisplayName("?????? ?????? Page ?????? ?????????")
    void accessPage() throws Exception{
        //given, when, then
        mockMvc.perform(get("/family"))
                .andExpect(view().name("page/family"))
                .andDo(print());
    }

    @Test
    @DisplayName("????????? ?????? ??????ID ?????? ?????? ?????? ?????????")
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
    @DisplayName("?????? ?????? ?????????")
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
    @DisplayName("?????? ?????? ?????? ?????????")
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
    @DisplayName("?????? ?????? Soft Delete ?????????")
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