package com.temple.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temple.manager.config.RedisConfig;
import com.temple.manager.config.RedisProperties;
import com.temple.manager.dto.BelieverDTO;
import com.temple.manager.dto.CodeDTO;
import com.temple.manager.dto.ExpenditureDTO;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.service.ExpenditureService;
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
@WebMvcTest(controllers = ExpenditureController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
class ExpenditureControllerTest {
    @MockBean
    ExpenditureService expenditureService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    ExpenditureDTO fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = ExpenditureDTO.builder()
                .expenditureId(1)
                .expenditureDate(LocalDate.now())
                .cashAmount(111)
                .cardAmount(222)
                .bankBookAmount(333)
                .installment(10)
                .code(CodeDTO.builder().codeId(1).build())
                .believer(BelieverDTO.builder().believerId(1).build())
                .paymentType(PaymentType.BELIEVER)
                .build();

        fixture2 = ExpenditureDTO.builder()
                .expenditureId(2)
                .expenditureDate(LocalDate.now())
                .cashAmount(444)
                .cardAmount(555)
                .bankBookAmount(666)
                .installment(3)
                .code(CodeDTO.builder().codeId(2).build())
                .believer(BelieverDTO.builder().believerId(2).build())
                .paymentType(PaymentType.TEMPLE)
                .build();
    }

    @Test
    @DisplayName("화면 접속 Page 반환 테스트")
    void accessPage() throws Exception{
        //given, when, then
        mockMvc.perform(get("/expenditure"))
                .andExpect(view().name("page/expenditure"))
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 지출 정보 반환 테스트")
    void getAllExpenditureByActive() throws Exception{
        //given
        List<ExpenditureDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(expenditureService.getAllExpenditure()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/expenditures"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 지출 수정 테스트")
    void updateExpenditure() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(fixture1);

        doNothing().when(expenditureService).updateExpenditure(anyLong(), any(ExpenditureDTO.class));

        //when, then
        mockMvc.perform(put("/expenditure/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(content().string("Success"))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 지출 수정 테스트")
    void deleteExpenditure() throws Exception{
        //given
        doNothing().when(expenditureService).deleteExpenditure(anyLong());

        //when, then
        mockMvc.perform(delete("/expenditure/1")
                .with(csrf()))
                .andExpect(content().string("Success"))
                .andDo(print());
    }
}