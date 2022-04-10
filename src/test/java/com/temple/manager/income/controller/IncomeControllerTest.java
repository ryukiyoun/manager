package com.temple.manager.income.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.common.config.PasswordEncoderConfig;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.income.dto.IncomeDTO;
import com.temple.manager.income.dto.IncomeStatisticsDTO;
import com.temple.manager.income.service.IncomeService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Import({RedisConfig.class, RedisProperties.class, PasswordEncoderConfig.class})
@WebMvcTest(controllers = IncomeController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
class IncomeControllerTest {
    @MockBean
    IncomeService incomeService;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    IncomeDTO fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = IncomeDTO.builder()
                .incomeId(1)
                .incomeDate(LocalDate.now())
                .cashAmount(111)
                .cardAmount(222)
                .bankBookAmount(333)
                .installment(10)
                .code(CodeDTO.builder().codeId(1).build())
                .believer(BelieverDTO.builder().believerId(1).build())
                .paymentType(PaymentType.BELIEVER)
                .build();

        fixture2 = IncomeDTO.builder()
                .incomeId(2)
                .incomeDate(LocalDate.now())
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
        mockMvc.perform(get("/income"))
                .andExpect(view().name("page/income"))
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 수입 정보 반환 테스트")
    void getAllIncomeByActive() throws Exception{
        //given
        List<IncomeDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(incomeService.getAllIncomes()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/incomes"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 수입 신도ID 검색 정보 반환 테스트")
    void getIncomeByBelieverId() throws Exception{
        //given
        List<IncomeDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(incomeService.getIncomesByBelieverId(anyLong())).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/incomes/1"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("최근 등록 수입 5건 조회 테스트")
    void getRecent5Incomes() throws Exception{
        //given
        List<IncomeDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(incomeService.getRecent5Incomes()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/incomes/top/5"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("이번달 일자별 통계 조회 테스트")
    void getIncomeDailyStatistics() throws Exception{
        //given
        List<IncomeStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "01";
            }

            @Override
            public long getAmount() {
                return 10000;
            }
        });
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "02";
            }

            @Override
            public long getAmount() {
                return 20000;
            }
        });

        given(incomeService.getIncomeDailyStatistics(anyString())).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/income/chart/statistics/daily/20220101"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("1년전부터 매월 통계 조회 테스트")
    void getIncomeMonthStatistics() throws Exception{
        //given
        List<IncomeStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2022-01";
            }

            @Override
            public long getAmount() {
                return 10000;
            }
        });
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2021-12";
            }

            @Override
            public long getAmount() {
                return 20000;
            }
        });

        given(incomeService.getIncomeMonthStatistics(anyString())).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/income/chart/statistics/month/20220101"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("10년전부터 매년 통계 조회 테스트")
    void getIncomeYearStatistics() throws Exception{
        //given
        List<IncomeStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2022";
            }

            @Override
            public long getAmount() {
                return 10000;
            }
        });
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2021";
            }

            @Override
            public long getAmount() {
                return 20000;
            }
        });

        given(incomeService.getIncomeYearStatistics(anyString())).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/income/chart/statistics/year/20220101"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("오늘과 어제 수입 비교 조회 테스트")
    void getIncomeCompareToDay() throws Exception{
        //given
        List<IncomeStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2022-01-01";
            }

            @Override
            public long getAmount() {
                return 10000;
            }
        });
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2021-12-31";
            }

            @Override
            public long getAmount() {
                return 20000;
            }
        });

        given(incomeService.getIncomeCompareToDay()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/income/compare/today"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("이번달과 저번달 수입 비교 조회 테스트")
    void getIncomeCompareThisMonth() throws Exception{
        //given
        List<IncomeStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2022-01";
            }

            @Override
            public long getAmount() {
                return 10000;
            }
        });
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2021-12";
            }

            @Override
            public long getAmount() {
                return 20000;
            }
        });

        given(incomeService.getIncomeCompareThisMonth()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/income/compare/thismonth"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("올해와 작년 수입 비교 조회 테스트")
    void getIncomeCompareThisYear() throws Exception{
        //given
        List<IncomeStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2022";
            }

            @Override
            public long getAmount() {
                return 10000;
            }
        });
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2021";
            }

            @Override
            public long getAmount() {
                return 20000;
            }
        });

        given(incomeService.getIncomeCompareThisYear()).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/income/compare/thisyear"))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("수입 추가 테스트")
    void appendIncome() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(fixture1);

        given(incomeService.appendIncome(any(IncomeDTO.class))).willReturn(fixture1);

        //when, then
        mockMvc.perform(post("/income")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 수입 수정 테스트")
    void updateIncome() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(fixture1);

        doNothing().when(incomeService).updateIncome(anyLong(), any(IncomeDTO.class));

        //when, then
        mockMvc.perform(put("/income/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(content().string("Success"))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 수입 삭제 테스트")
    void deleteIncome() throws Exception{
        //given
        doNothing().when(incomeService).deleteIncome(anyLong());

        //when, then
        mockMvc.perform(delete("/income/1")
                .with(csrf()))
                .andExpect(content().string("Success"))
                .andDo(print());
    }
}