package com.temple.manager.service;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.dto.CodeDTO;
import com.temple.manager.expenditure.dto.ExpenditureDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.entity.Code;
import com.temple.manager.expenditure.entity.Expenditure;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.expenditure.repository.ExpenditureRepository;
import com.temple.manager.expenditure.service.ExpenditureService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenditureServiceTest {
    @Mock
    ExpenditureRepository expenditureRepository;

    @InjectMocks
    ExpenditureService expenditureService;

    Expenditure fixture1, fixture2;

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
    }

    @Test
    @DisplayName("등록된 모든 지출 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getAllExpenditureByActive() {
        //given
        List<Expenditure> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(expenditureRepository.findAll()).willReturn(fixtureList);

        //when
        List<ExpenditureDTO> result = expenditureService.getAllExpenditures();

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixture1);
        checkEntity(result.get(1), fixture2);
    }

    @Test
    @DisplayName("등록된 모든 지출 신도ID 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getExpendituresByBelieverId() {
        //given
        List<Expenditure> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(expenditureRepository.findAllByBeliever_BelieverId(anyLong())).willReturn(fixtureList);

        //when
        List<ExpenditureDTO> result = expenditureService.getExpendituresByBelieverId(anyLong());

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixture1);
        checkEntity(result.get(1), fixture2);
    }

    @Test
    @DisplayName("지출 추가 테스트")
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

        //when
        expenditureService.appendExpenditure(fixtureDTO);

        //then
        verify(expenditureRepository, times(1)).save(any(Expenditure.class));

        checkEntity(fixtureDTO, fixture1);
    }

    @Test
    @DisplayName("특정 지출 수정 테스트")
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
    @DisplayName("특정 지출 수정 조회 시 Empty 테스트")
    void updateExpenditureEmpty(){
        //given
        given(expenditureRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () -> expenditureService.updateExpenditure(1, ExpenditureDTO.builder().build()));
    }

    @Test
    @DisplayName("특정 지출 Soft Delete 테스트")
    void deleteExpenditure() {
        //given
        Expenditure spyExpenditure = spy(Expenditure.class);

        given(expenditureRepository.findById(anyLong())).willReturn(Optional.of(spyExpenditure));

        //when
        expenditureService.deleteExpenditure(1);

        //then
        verify(expenditureRepository, times(1)).findById(anyLong());
        verify(spyExpenditure, times(1)).delete();
    }

    @Test
    @DisplayName("특정 지출 삭제 조회 시 Empty 테스트")
    void deleteExpenditureEmpty(){
        //given
        given(expenditureRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () -> expenditureService.deleteExpenditure(1));
    }

    void checkEntity(ExpenditureDTO resultDTO, Expenditure fixtureEntity){
        assertThat(resultDTO.getExpenditureId(), is(fixtureEntity.getExpenditureId()));
        assertThat(resultDTO.getExpenditureDate(), is(fixtureEntity.getExpenditureDate()));
        assertThat(resultDTO.getCashAmount(), is(fixtureEntity.getCashAmount()));
        assertThat(resultDTO.getCardAmount(), is(fixtureEntity.getCardAmount()));
        assertThat(resultDTO.getBankBookAmount(), is(fixtureEntity.getBankBookAmount()));
        assertThat(resultDTO.getInstallment(), is(fixtureEntity.getInstallment()));
        assertThat(resultDTO.getPaymentType(), is(fixtureEntity.getPaymentType()));
        assertThat(resultDTO.getBeliever().getBelieverId(), is(fixtureEntity.getBeliever().getBelieverId()));
        assertThat(resultDTO.getCode().getCodeId(), is(fixtureEntity.getCode().getCodeId()));
    }
}