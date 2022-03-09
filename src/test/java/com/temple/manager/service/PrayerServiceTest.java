package com.temple.manager.service;

import com.temple.manager.dto.PrayerDTO;
import com.temple.manager.entity.Believer;
import com.temple.manager.entity.Code;
import com.temple.manager.entity.Prayer;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.repository.PrayerRepository;
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
class PrayerServiceTest {
    @Mock
    PrayerRepository prayerRepository;

    @InjectMocks
    PrayerService prayerService;

    Prayer fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believer(Believer.builder()
                        .believerId(1)
                        .believerName("tester1")
                        .birthOfYear("120512")
                        .address("서울시")
                        .lunarSolarType(LunarSolarType.LUNAR)
                        .build())
                .code(Code.builder()
                        .codeId(1)
                        .codeName("testCode1")
                        .codeValue("C-1")
                        .parentCodeValue("P-1")
                        .build())
                .build();

        fixture2 = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believer(Believer.builder()
                        .believerId(2)
                        .believerName("tester2")
                        .birthOfYear("194512")
                        .address("부산시")
                        .lunarSolarType(LunarSolarType.SOLAR)
                        .build())
                .code(Code.builder()
                        .codeId(2)
                        .codeName("testCode2")
                        .codeValue("C-2")
                        .parentCodeValue("P-1")
                        .build())
                .build();
    }

    @Test
    @DisplayName("등록된 모든 기도 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getAllPrayers() {
        //given
        List<Prayer> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(prayerRepository.findAllByActive("99999999999999")).willReturn(fixtureList);

        //when
        List<PrayerDTO> result = prayerService.getAllPrayers();

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixture1);
        checkEntity(result.get(1), fixture2);
    }

    @Test
    @DisplayName("특정 기도 수정 테스트")
    void updatePrayer() {
        //given
        Prayer mockPrayer = mock(Prayer.class);

        given(prayerRepository.findById(anyLong())).willReturn(Optional.of(mockPrayer));

        //when
        prayerService.updatePrayer(1, PrayerDTO.builder().build());

        //then
        verify(prayerRepository, times(1)).findById(anyLong());
        verify(mockPrayer, times(1)).update(any(PrayerDTO.class));
    }

    @Test
    @DisplayName("특정 기도 수정 조회 시 Empty 테스트")
    void updatePrayerEmpty() {
        //given
        given(prayerRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () -> prayerService.updatePrayer(0, PrayerDTO.builder().build()));
    }

    @Test
    @DisplayName("특정 기도 Soft Delete 테스트")
    void deletePrayer() {
        //given
        Prayer mockPrayer = mock(Prayer.class);

        given(prayerRepository.findById(anyLong())).willReturn(Optional.of(mockPrayer));

        //when
        prayerService.deletePrayer(1);

        //then
        verify(prayerRepository, times(1)).findById(anyLong());
        verify(mockPrayer, times(1)).delete();
    }

    @Test
    @DisplayName("특정 기도 삭제 시 Empty 테스트")
    void deletePrayerEmpty() {
        //given
        given(prayerRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () -> prayerService.deletePrayer(0));
    }

    void checkEntity(PrayerDTO resultDTO, Prayer fixtureEntity){
        assertThat(resultDTO.getPrayerId(), is(fixtureEntity.getPrayerId()));
        assertThat(resultDTO.getPrayerStartDate(), is(fixtureEntity.getPrayerStartDate()));
        assertThat(resultDTO.getBeliever().getBelieverId(), is(fixtureEntity.getBeliever().getBelieverId()));
        assertThat(resultDTO.getCode().getCodeId(), is(fixtureEntity.getCode().getCodeId()));
    }
}