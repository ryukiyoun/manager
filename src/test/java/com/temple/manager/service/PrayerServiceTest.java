package com.temple.manager.service;

import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.prayer.dto.PrayerGridListDTO;
import com.temple.manager.prayer.entity.Prayer;
import com.temple.manager.prayer.mapper.PrayerMapper;
import com.temple.manager.prayer.repository.PrayerRepository;
import com.temple.manager.prayer.repository.PrayerRepositorySupport;
import com.temple.manager.prayer.service.PrayerService;
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

    @Mock
    PrayerMapper prayerMapper;

    @Mock
    PrayerRepositorySupport prayerRepositorySupport;

    @InjectMocks
    PrayerService prayerService;

    Prayer fixture1, fixture2;

    PrayerDTO fixtureDTO1, fixtureDTO2;

    List<Prayer> fixtureList;

    List<PrayerDTO> fixtureDTOList;

    @BeforeEach
    void init(){
        fixture1 = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(1)
                .prayerTypeCodeId(1)
                .build();

        fixture2 = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(2)
                .prayerTypeCodeId(2)
                .build();

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        fixtureDTO1 = PrayerDTO.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(1)
                .prayerTypeCodeId(1)
                .build();

        fixtureDTO2 = PrayerDTO.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(2)
                .prayerTypeCodeId(2)
                .build();

        fixtureDTOList = new ArrayList<>();
        fixtureDTOList.add(fixtureDTO1);
        fixtureDTOList.add(fixtureDTO2);
    }

    @Test
    @DisplayName("등록된 모든 기도 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getAllPrayers() {
        //given
        List<PrayerGridListDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new PrayerGridListDTO(1, LocalDate.now(), 1, "111111", "tester", 1, "testCodeName"));
        fixtureList.add(new PrayerGridListDTO(2, LocalDate.now(), 2, "222222", "tester2", 2, "testCodeName2"));

        given(prayerRepositorySupport.getPrayers()).willReturn(fixtureList);

        //when
        List<PrayerGridListDTO> result = prayerService.getAllPrayers();

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getPrayerId(), is(1L));
        assertThat(result.get(0).getPrayerStartDate(), is(LocalDate.now()));
        assertThat(result.get(0).getBelieverId(), is(1L));
        assertThat(result.get(0).getBelieverName(), is("tester"));
        assertThat(result.get(0).getBirthOfYear(), is("111111"));
        assertThat(result.get(0).getCodeId(), is(1L));
        assertThat(result.get(0).getPrayerTypeCodeName(), is("testCodeName"));

        assertThat(result.get(1).getPrayerId(), is(2L));
        assertThat(result.get(1).getPrayerStartDate(), is(LocalDate.now()));
        assertThat(result.get(1).getBelieverId(), is(2L));
        assertThat(result.get(1).getBelieverName(), is("tester2"));
        assertThat(result.get(1).getBirthOfYear(), is("222222"));
        assertThat(result.get(1).getCodeId(), is(2L));
        assertThat(result.get(1).getPrayerTypeCodeName(), is("testCodeName2"));
    }

    @Test
    @DisplayName("등록된 기도 중 특정 신도로 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getPrayersByBelieverId() {
        //given
        List<PrayerGridListDTO> fixtureList = new ArrayList<>();
        fixtureList.add(new PrayerGridListDTO(1, LocalDate.now(), 1, "111111", "tester", 1, "testCodeName"));
        fixtureList.add(new PrayerGridListDTO(2, LocalDate.now(), 2, "222222", "tester2", 2, "testCodeName2"));

        given(prayerRepositorySupport.getPrayersByBelieverId(anyLong())).willReturn(fixtureList);

        //when
        List<PrayerGridListDTO> result = prayerService.getPrayersByBelieverId(1);

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getPrayerId(), is(1L));
        assertThat(result.get(0).getPrayerStartDate(), is(LocalDate.now()));
        assertThat(result.get(0).getBelieverId(), is(1L));
        assertThat(result.get(0).getBelieverName(), is("tester"));
        assertThat(result.get(0).getBirthOfYear(), is("111111"));
        assertThat(result.get(0).getCodeId(), is(1L));
        assertThat(result.get(0).getPrayerTypeCodeName(), is("testCodeName"));

        assertThat(result.get(1).getPrayerId(), is(2L));
        assertThat(result.get(1).getPrayerStartDate(), is(LocalDate.now()));
        assertThat(result.get(1).getBelieverId(), is(2L));
        assertThat(result.get(1).getBelieverName(), is("tester2"));
        assertThat(result.get(1).getBirthOfYear(), is("222222"));
        assertThat(result.get(1).getCodeId(), is(2L));
        assertThat(result.get(1).getPrayerTypeCodeName(), is("testCodeName2"));
    }

    @Test
    @DisplayName("등록된 기도 Group DTO 타입으로 조회 테스트")
    void getPrayersTypeGroupCnt() {
        //when
        prayerService.getPrayersTypeGroupCnt();

        //then
        verify(prayerRepositorySupport, times(1)).getPrayersTypeGroupCnt();
    }

    @Test
    @DisplayName("기도 추가 테스트")
    void appendPrayer() {
        //given
        given(prayerRepository.save(any(Prayer.class))).willReturn(fixture1);
        given(prayerMapper.DTOToEntity(any(PrayerDTO.class))).willReturn(fixture1);
        given(prayerMapper.entityToDTO(any(Prayer.class))).willReturn(fixtureDTO1);

        //when
        PrayerDTO result = prayerService.appendPrayer(fixtureDTO1);

        //then
        verify(prayerRepository, times(1)).save(any(Prayer.class));

        checkEntity(result, fixtureDTO1);
    }

    @Test
    @DisplayName("특정 기도 수정 테스트")
    void updatePrayer() {
        //given
        Prayer spyPrayer = spy(Prayer.class);

        PrayerDTO fixture = PrayerDTO.builder()
                .prayerId(1)
                .believerId(1)
                .prayerTypeCodeId(1)
                .prayerStartDate(LocalDate.now())
                .build();

        given(prayerRepository.findById(anyLong())).willReturn(Optional.of(spyPrayer));

        //when
        prayerService.updatePrayer(1, fixture);

        //then
        verify(prayerRepository, times(1)).findById(anyLong());
        verify(spyPrayer, times(1)).update(any(PrayerDTO.class));

        assertThat(spyPrayer.getBelieverId(), is(fixture.getBelieverId()));
        assertThat(spyPrayer.getPrayerTypeCodeId(), is(fixture.getPrayerTypeCodeId()));
        assertThat(spyPrayer.getPrayerStartDate(), is(fixture.getPrayerStartDate()));
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
        //when
        prayerService.deletePrayer(1);

        //then
        verify(prayerRepository, times(1)).deleteById(anyLong());
    }

    void checkEntity(PrayerDTO resultDTO, PrayerDTO compareDTO){
        assertThat(resultDTO.getPrayerId(), is(compareDTO.getPrayerId()));
        assertThat(resultDTO.getPrayerStartDate(), is(compareDTO.getPrayerStartDate()));
        assertThat(resultDTO.getBelieverId(), is(compareDTO.getBelieverId()));
        assertThat(resultDTO.getPrayerTypeCodeId(), is(compareDTO.getPrayerTypeCodeId()));
    }
}