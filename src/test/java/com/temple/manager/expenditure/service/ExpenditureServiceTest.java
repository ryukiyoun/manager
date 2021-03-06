package com.temple.manager.expenditure.service;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.expenditure.dto.ExpenditureDTO;
import com.temple.manager.expenditure.dto.ExpenditureStatisticsDTO;
import com.temple.manager.expenditure.entity.Expenditure;
import com.temple.manager.expenditure.mapper.ExpenditureMapper;
import com.temple.manager.expenditure.repository.ExpenditureRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenditureServiceTest {
    @Mock
    ExpenditureRepository expenditureRepository;

    @Mock
    ExpenditureMapper expenditureMapper;

    @InjectMocks
    ExpenditureService expenditureService;

    Expenditure fixture1, fixture2;

    ExpenditureDTO fixtureDTO1, fixtureDTO2;

    List<Expenditure> fixtureList;

    List<ExpenditureDTO> fixtureDTOList;

    @BeforeEach
    void init() {
        fixture1 = Expenditure.builder()
                .expenditureId(1)
                .expenditureDate(LocalDate.now())
                .cashAmount(111)
                .cardAmount(222)
                .bankBookAmount(333)
                .installment(10)
                .code(Code.builder().codeId(1).build())
                .believer(Believer.builder().believerId(1).build())
                .paymentType(PaymentType.BELIEVER)
                .build();

        fixture2 = Expenditure.builder()
                .expenditureId(2)
                .expenditureDate(LocalDate.now())
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

        fixtureDTO1 = ExpenditureDTO.builder()
                .expenditureId(1)
                .expenditureDate(LocalDate.now())
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

        fixtureDTO2 = ExpenditureDTO.builder()
                .expenditureId(2)
                .expenditureDate(LocalDate.now())
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
    @DisplayName("????????? ?????? ?????? ?????? ??? Entity?????? DTO ???????????? ?????? ?????????")
    void getAllExpenditureByActive() {
        //given
        given(expenditureRepository.findAll()).willReturn(fixtureList);
        given(expenditureMapper.entityListToDTOList(anyList())).willReturn(fixtureDTOList);

        //when
        List<ExpenditureDTO> result = expenditureService.getAllExpenditures();

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    @Test
    @DisplayName("????????? ?????? ?????? ??????ID ?????? ??? Entity?????? DTO ???????????? ?????? ?????????")
    void getExpendituresByBelieverId() {
        //given
        given(expenditureRepository.findAllByBeliever_BelieverId(anyLong())).willReturn(fixtureList);
        given(expenditureMapper.entityListToDTOList(anyList())).willReturn(fixtureDTOList);

        //when
        List<ExpenditureDTO> result = expenditureService.getExpendituresByBelieverId(1);

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    @Test
    @DisplayName("?????? ?????? ?????? 5??? DTO ???????????? ?????? ?????????")
    void getRecent5Expenditures() {
        //given
        given(expenditureRepository.findTop5ByOrderByExpenditureIdDesc(any(Pageable.class))).willReturn(fixtureList);
        given(expenditureMapper.entityListToDTOList(anyList())).willReturn(fixtureDTOList);

        //when
        List<ExpenditureDTO> result = expenditureService.getRecent5Expenditures();

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    @Test
    @DisplayName("????????? ????????? ?????? ?????? ?????????")
    void getExpenditureDailyStatistics() {
        //given
        List<ExpenditureStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new ExpenditureStatisticsDTO() {
            @Override
            public String getDay() {
                return "01";
            }

            @Override
            public long getAmount() {
                return 1000;
            }
        });
        fixtureList.add(new ExpenditureStatisticsDTO() {
            @Override
            public String getDay() {
                return "02";
            }

            @Override
            public long getAmount() {
                return 3000;
            }
        });

        given(expenditureRepository.getDailyStatistics(any(LocalDate.class))).willReturn(fixtureList);

        //when
        List<ExpenditureStatisticsDTO> result = expenditureService.getExpenditureDailyStatistics("20220101");

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDay(), is("01"));
        assertThat(result.get(0).getAmount(), is(1000L));
        assertThat(result.get(1).getDay(), is("02"));
        assertThat(result.get(1).getAmount(), is(3000L));
    }

    @Test
    @DisplayName("1???????????? ?????? ?????? ?????? ?????????")
    void getExpenditureMonthStatistics() {
        //given
        List<ExpenditureStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new ExpenditureStatisticsDTO() {
            @Override
            public String getDay() {
                return "2022-01";
            }

            @Override
            public long getAmount() {
                return 1000;
            }
        });
        fixtureList.add(new ExpenditureStatisticsDTO() {
            @Override
            public String getDay() {
                return "2021-12";
            }

            @Override
            public long getAmount() {
                return 3000;
            }
        });

        given(expenditureRepository.getMonthStatistics(any(LocalDate.class))).willReturn(fixtureList);

        //when
        List<ExpenditureStatisticsDTO> result = expenditureService.getExpenditureMonthStatistics("20220101");

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDay(), is("2022-01"));
        assertThat(result.get(0).getAmount(), is(1000L));
        assertThat(result.get(1).getDay(), is("2021-12"));
        assertThat(result.get(1).getAmount(), is(3000L));
    }

    @Test
    @DisplayName("10???????????? ?????? ?????? ?????? ?????????")
    void getExpenditureYearStatistics() {
        //given
        List<ExpenditureStatisticsDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new ExpenditureStatisticsDTO() {
            @Override
            public String getDay() {
                return "2022";
            }

            @Override
            public long getAmount() {
                return 1000;
            }
        });
        fixtureList.add(new ExpenditureStatisticsDTO() {
            @Override
            public String getDay() {
                return "2021";
            }

            @Override
            public long getAmount() {
                return 3000;
            }
        });

        given(expenditureRepository.getYearStatistics(any(LocalDate.class))).willReturn(fixtureList);

        //when
        List<ExpenditureStatisticsDTO> result = expenditureService.getExpenditureYearStatistics("20220101");

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDay(), is("2022"));
        assertThat(result.get(0).getAmount(), is(1000L));
        assertThat(result.get(1).getDay(), is("2021"));
        assertThat(result.get(1).getAmount(), is(3000L));
    }

    @Test
    @DisplayName("?????? ?????? ?????????")
    void appendExpenditure() {
        //given
        ExpenditureDTO fixtureDTO = ExpenditureDTO.builder()
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

        given(expenditureRepository.save(any(Expenditure.class))).willReturn(fixture1);
        given(expenditureMapper.DTOToEntity(any(ExpenditureDTO.class))).willReturn(fixture1);
        given(expenditureMapper.entityToDTO(any(Expenditure.class))).willReturn(fixtureDTO1);

        //when
        ExpenditureDTO result = expenditureService.appendExpenditure(fixtureDTO);

        //then
        verify(expenditureRepository, times(1)).save(any(Expenditure.class));

        checkEntity(result, fixtureDTO1);
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    void updateExpenditure() {
        //given
        Expenditure spyExpenditure = spy(Expenditure.class);

        ExpenditureDTO fixture = ExpenditureDTO.builder()
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

        given(expenditureRepository.findById(anyLong())).willReturn(Optional.of(spyExpenditure));

        //when
        expenditureService.updateExpenditure(1, fixture);

        //then
        verify(expenditureRepository, times(1)).findById(anyLong());
        verify(spyExpenditure, times(1)).update(any(ExpenditureDTO.class));

        assertThat(spyExpenditure.getCode().getCodeId(), is(fixture.getCode().getCodeId()));
        assertThat(spyExpenditure.getCashAmount(), is(fixture.getCashAmount()));
        assertThat(spyExpenditure.getCardAmount(), is(fixture.getCardAmount()));
        assertThat(spyExpenditure.getBankBookAmount(), is(fixture.getBankBookAmount()));
        assertThat(spyExpenditure.getExpenditureDate(), is(fixture.getExpenditureDate()));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ??? Empty ?????????")
    void updateExpenditureEmpty(){
        //given
        given(expenditureRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () -> expenditureService.updateExpenditure(1, ExpenditureDTO.builder().build()));
    }

    @Test
    @DisplayName("?????? ?????? Soft Delete ?????????")
    void deleteExpenditure() {
        //given
        doNothing().when(expenditureRepository).deleteById(anyLong());

        //when
        expenditureService.deleteExpenditure(1);

        //then
        verify(expenditureRepository, times(1)).deleteById(anyLong());
    }

    void checkEntity(ExpenditureDTO resultDTO, ExpenditureDTO compareDTO){
        assertThat(resultDTO.getExpenditureId(), is(compareDTO.getExpenditureId()));
        assertThat(resultDTO.getExpenditureDate(), is(compareDTO.getExpenditureDate()));
        assertThat(resultDTO.getCashAmount(), is(compareDTO.getCashAmount()));
        assertThat(resultDTO.getCardAmount(), is(compareDTO.getCardAmount()));
        assertThat(resultDTO.getBankBookAmount(), is(compareDTO.getBankBookAmount()));
        assertThat(resultDTO.getInstallment(), is(compareDTO.getInstallment()));
        assertThat(resultDTO.getPaymentType(), is(compareDTO.getPaymentType()));
        assertThat(resultDTO.getBeliever().getBelieverId(), is(compareDTO.getBeliever().getBelieverId()));
        assertThat(resultDTO.getCode().getCodeId(), is(compareDTO.getCode().getCodeId()));
    }
}