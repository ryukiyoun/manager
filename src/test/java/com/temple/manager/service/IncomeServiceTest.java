package com.temple.manager.service;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.income.dto.IncomeDTO;
import com.temple.manager.income.dto.IncomeStatisticsDTO;
import com.temple.manager.income.entity.Income;
import com.temple.manager.income.mapper.IncomeMapper;
import com.temple.manager.income.repository.IncomeRepository;
import com.temple.manager.income.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncomeServiceTest {
    @Mock
    IncomeRepository incomeRepository;

    @Mock
    IncomeMapper incomeMapper;

    @InjectMocks
    IncomeService incomeService;

    Income fixture1, fixture2;

    IncomeDTO fixtureDTO1, fixtureDTO2;

    List<Income> fixtureList;

    List<IncomeDTO> fixtureDTOList;

    @BeforeEach
    void init(){
        fixture1 = Income.builder()
                .incomeId(1)
                .incomeDate(LocalDate.now())
                .cashAmount(111)
                .cardAmount(222)
                .bankBookAmount(333)
                .installment(10)
                .code(Code.builder().codeId(1).build())
                .believer(Believer.builder().believerId(1).build())
                .paymentType(PaymentType.BELIEVER)
                .build();

        fixture2 = Income.builder()
                .incomeId(2)
                .incomeDate(LocalDate.now())
                .cashAmount(444)
                .cardAmount(555)
                .bankBookAmount(666)
                .installment(3)
                .code(Code.builder().codeId(2).build())
                .believer(Believer.builder().believerId(2).build())
                .paymentType(PaymentType.TEMPLE)
                .build();

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        fixtureDTO1 = IncomeDTO.builder()
                .incomeId(1)
                .incomeDate(LocalDate.now())
                .cashAmount(111)
                .cardAmount(222)
                .bankBookAmount(333)
                .installment(10)
                .code(CodeDTO.builder()
                        .codeId(1)
                        .build())
                .believer(BelieverDTO.builder()
                        .believerId(1)
                        .build())
                .paymentType(PaymentType.BELIEVER)
                .build();

        fixtureDTO2 = IncomeDTO.builder()
                .incomeId(2)
                .incomeDate(LocalDate.now())
                .cashAmount(444)
                .cardAmount(555)
                .bankBookAmount(666)
                .installment(3)
                .code(CodeDTO.builder()
                        .codeId(2)
                        .build())
                .believer(BelieverDTO.builder()
                        .believerId(2)
                        .build())
                .paymentType(PaymentType.TEMPLE)
                .build();

        fixtureDTOList = new ArrayList<>();
        fixtureDTOList.add(fixtureDTO1);
        fixtureDTOList.add(fixtureDTO2);
    }

    @Test
    @DisplayName("등록된 모든 수입 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getAllIncomeByActive() {
        //given
        given(incomeRepository.findAll()).willReturn(fixtureList);
        given(incomeMapper.entityListToDTOList(anyList())).willReturn(fixtureDTOList);

        //when
        List<IncomeDTO> result = incomeService.getAllIncomes();

        //then
        assertThat(result.size(), is(2));

        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    @Test
    @DisplayName("등록된 모든 수입 신도ID 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getIncomesByBelieverId() {
        //given
        given(incomeRepository.findAllByBeliever_BelieverId(anyLong())).willReturn(fixtureList);
        given(incomeMapper.entityListToDTOList(anyList())).willReturn(fixtureDTOList);

        //when
        List<IncomeDTO> result = incomeService.getIncomesByBelieverId(1);

        //then
        assertThat(result.size(), is(2));

        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    @Test
    @DisplayName("최근 등록 수입 5건 DTO 타입으로 조회 테스트")
    void getRecent5Incomes() {
        //given
        given(incomeRepository.findTop5ByOrderByIncomeIdDesc(any(Pageable.class))).willReturn(fixtureList);
        given(incomeMapper.entityListToDTOList(anyList())).willReturn(fixtureDTOList);

        //when
        List<IncomeDTO> result = incomeService.getRecent5Incomes();

        //then
        assertThat(result.size(), is(2));

        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    @Test
    @DisplayName("이번달 일자별 통계 조회 테스트")
    void getIncomeDailyStatistics() {
        //given
        List<IncomeStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "01";
            }

            @Override
            public long getAmount() {
                return 1000;
            }
        });
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "02";
            }

            @Override
            public long getAmount() {
                return 3000;
            }
        });

        given(incomeRepository.getDailyStatistics(any(LocalDate.class))).willReturn(fixtureList);

        //when
        List<IncomeStatisticsDTO> result = incomeService.getIncomeDailyStatistics("20220101");

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDay(), is("01"));
        assertThat(result.get(0).getAmount(), is(1000L));
        assertThat(result.get(1).getDay(), is("02"));
        assertThat(result.get(1).getAmount(), is(3000L));
    }

    @Test
    @DisplayName("1년전부터 매월 통계 조회 테스트")
    void getIncomeMonthStatistics() {
        //given
        List<IncomeStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2022-01";
            }

            @Override
            public long getAmount() {
                return 1000;
            }
        });
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2021-12";
            }

            @Override
            public long getAmount() {
                return 3000;
            }
        });

        given(incomeRepository.getMonthStatistics(any(LocalDate.class))).willReturn(fixtureList);

        //when
        List<IncomeStatisticsDTO> result = incomeService.getIncomeMonthStatistics("20220101");

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDay(), is("2022-01"));
        assertThat(result.get(0).getAmount(), is(1000L));
        assertThat(result.get(1).getDay(), is("2021-12"));
        assertThat(result.get(1).getAmount(), is(3000L));
    }

    @Test
    @DisplayName("10년전부터 매년 통계 조회 테스트")
    void getIncomeYearStatistics() {
        //given
        List<IncomeStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2022";
            }

            @Override
            public long getAmount() {
                return 1000;
            }
        });
        fixtureList.add(new IncomeStatisticsDTO() {
            @Override
            public String getDay() {
                return "2021";
            }

            @Override
            public long getAmount() {
                return 3000;
            }
        });

        given(incomeRepository.getYearStatistics(any(LocalDate.class))).willReturn(fixtureList);

        //when
        List<IncomeStatisticsDTO> result = incomeService.getIncomeYearStatistics("20220101");

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDay(), is("2022"));
        assertThat(result.get(0).getAmount(), is(1000L));
        assertThat(result.get(1).getDay(), is("2021"));
        assertThat(result.get(1).getAmount(), is(3000L));
    }

    @Test
    @DisplayName("오늘과 어제 수입 비교 조회 테스트")
    void getIncomeCompareToDay() {
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
                return "31";
            }

            @Override
            public long getAmount() {
                return 20000;
            }
        });

        given(incomeRepository.getCompareToDay()).willReturn(fixtureList);

        //when
        List<IncomeStatisticsDTO> result = incomeService.getIncomeCompareToDay();

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDay(), is("01"));
        assertThat(result.get(0).getAmount(), is(10000L));
        assertThat(result.get(1).getDay(), is("31"));
        assertThat(result.get(1).getAmount(), is(20000L));
    }

    @Test
    @DisplayName("이번달과 저번달 수입 비교 조회 테스트")
    void getIncomeCompareThisMonth() {
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

        given(incomeRepository.getCompareThisMonth()).willReturn(fixtureList);

        //when
        List<IncomeStatisticsDTO> result = incomeService.getIncomeCompareThisMonth();

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDay(), is("2022-01"));
        assertThat(result.get(0).getAmount(), is(10000L));
        assertThat(result.get(1).getDay(), is("2021-12"));
        assertThat(result.get(1).getAmount(), is(20000L));
    }

    @Test
    @DisplayName("올해와 작년 수입 비교 조회 테스트")
    void getIncomeCompareThisYear() {
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

        given(incomeRepository.getCompareThisYear()).willReturn(fixtureList);

        //when
        List<IncomeStatisticsDTO> result = incomeService.getIncomeCompareThisYear();

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDay(), is("2022"));
        assertThat(result.get(0).getAmount(), is(10000L));
        assertThat(result.get(1).getDay(), is("2021"));
        assertThat(result.get(1).getAmount(), is(20000L));
    }

    @Test
    @DisplayName("수입 추가 테스트")
    void appendIncome() {
        //given
        given(incomeRepository.save(any(Income.class))).willReturn(fixture1);
        given(incomeMapper.DTOToEntity(any(IncomeDTO.class))).willReturn(fixture1);
        given(incomeMapper.entityToDTO(any(Income.class))).willReturn(fixtureDTO1);

        //when
        IncomeDTO result = incomeService.appendIncome(fixtureDTO1);

        //then
        verify(incomeRepository, times(1)).save(any(Income.class));
        checkEntity(result, fixtureDTO1);
    }

    @Test
    @DisplayName("특정 수입 수정 테스트")
    void updateIncome() {
        //given
        Income spyIncome = spy(Income.class);

        IncomeDTO fixture = IncomeDTO.builder()
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

        given(incomeRepository.findById(anyLong())).willReturn(Optional.of(spyIncome));

        //when
        incomeService.updateIncome(1, fixture);

        //then
        verify(incomeRepository, times(1)).findById(anyLong());
        verify(spyIncome, times(1)).update(any(IncomeDTO.class));

        assertThat(spyIncome.getCode().getCodeId(), is(fixture.getCode().getCodeId()));
        assertThat(spyIncome.getCashAmount(), is(fixture.getCashAmount()));
        assertThat(spyIncome.getCardAmount(), is(fixture.getCardAmount()));
        assertThat(spyIncome.getBankBookAmount(), is(fixture.getBankBookAmount()));
        assertThat(spyIncome.getIncomeDate(), is(fixture.getIncomeDate()));
    }

    @Test
    @DisplayName("특정 수입 수정 조회 시 Empty 테스트")
    void updateIncomeEmpty() {
        //given
        given(incomeRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () -> incomeService.updateIncome(1, IncomeDTO.builder().build()));
    }

    @Test
    @DisplayName("특정 수입 Soft Delete 테스트")
    void deleteIncome() {
        //given
        doNothing().when(incomeRepository).deleteById(anyLong());

        //when
        incomeService.deleteIncome(1);

        //then
        verify(incomeRepository, times(1)).deleteById(anyLong());
    }

    void checkEntity(IncomeDTO resultDTO, IncomeDTO compareDTO){
        assertThat(resultDTO.getIncomeId(), is(compareDTO.getIncomeId()));
        assertThat(resultDTO.getIncomeDate(), is(compareDTO.getIncomeDate()));
        assertThat(resultDTO.getCashAmount(), is(compareDTO.getCashAmount()));
        assertThat(resultDTO.getCardAmount(), is(compareDTO.getCardAmount()));
        assertThat(resultDTO.getBankBookAmount(), is(compareDTO.getBankBookAmount()));
        assertThat(resultDTO.getInstallment(), is(compareDTO.getInstallment()));
        assertThat(resultDTO.getPaymentType(), is(compareDTO.getPaymentType()));
        assertThat(resultDTO.getBeliever().getBelieverId(), is(compareDTO.getBeliever().getBelieverId()));
        assertThat(resultDTO.getCode().getCodeId(), is(compareDTO.getCode().getCodeId()));
    }
}