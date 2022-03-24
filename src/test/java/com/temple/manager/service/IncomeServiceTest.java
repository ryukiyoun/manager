package com.temple.manager.service;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.income.dto.IncomeDTO;
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
        Income spyIncome = spy(Income.class);
        given(incomeRepository.findById(anyLong())).willReturn(Optional.of(spyIncome));

        //when
        incomeService.deleteIncome(1);

        //then
        verify(incomeRepository, times(1)).findById(anyLong());
        verify(spyIncome, times(1)).delete();
    }

    @Test
    @DisplayName("특정 수입 삭제 조회 시 Empty 테스트")
    void deleteIncomeEmpty() {
        //given
        given(incomeRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () -> incomeService.deleteIncome(1));
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