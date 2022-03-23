package com.temple.manager.code.service;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.believer.repository.BelieverRepository;
import com.temple.manager.believer.service.BelieverService;
import com.temple.manager.family.entity.Family;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.family.repository.FamilyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class BelieverServiceTest {
    @Mock
    BelieverRepository believerRepository;

    @Mock
    FamilyRepository familyRepository;

    @InjectMocks
    BelieverService believerService;

    Believer fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = Believer.builder()
                .believerId(1)
                .believerName("홍길동")
                .address("부산시")
                .birthOfYear("596928")
                .lunarSolarType(LunarSolarType.LUNAR)
                .build();

        fixture2 = Believer.builder()
                .believerId(2)
                .believerName("홍길순")
                .address("서울시")
                .birthOfYear("598274")
                .lunarSolarType(LunarSolarType.SOLAR)
                .build();
    }

    @Test
    @DisplayName("등록된 모든 신도 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getBelievers() {
        //given
        List<Believer> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(believerRepository.findAll()).willReturn(fixtureList);

        //when
        List<BelieverDTO> result = believerService.getAllBelievers();

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixture1);
        checkEntity(result.get(0), fixture1);
    }

    @Test
    @DisplayName("등록된 신도 중 특정 이름으로 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getBelieversByName() {
        //given
        List<Believer> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(believerRepository.findAllByBelieverNameContains(anyString())).willReturn(fixtureList);

        //when
        List<BelieverDTO> result = believerService.getBelieversByName("testName");

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixture1);
        checkEntity(result.get(0), fixture1);
    }

    @Test
    @DisplayName("등록된 신도 중 특정 이름 생년월일로 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getBelieversByNameAndBirthOfYear() {
        //given
        given(believerRepository.findAllByBelieverNameAndBirthOfYear(anyString(), anyString())).willReturn(Optional.of(fixture1));

        //when
        BelieverDTO result = believerService.getBelieverByNameAndBirtOfYear("testName", "testBirthOfYear");

        //then
        checkEntity(result, fixture1);
    }

    @Test
    @DisplayName("신도 추가 테스트")
    void appendBeliever(){
        //given
        BelieverDTO fixtureDTO = BelieverDTO.builder()
                .believerId(1)
                .believerName("홍길동")
                .address("부산시")
                .birthOfYear("596928")
                .lunarSolarType(LunarSolarType.LUNAR)
                .build();

        given(believerRepository.save(any(Believer.class))).willReturn(fixture1);

        //when
        believerService.appendBeliever(fixtureDTO);

        //then
        checkEntity(fixtureDTO, fixture1);
    }

    @Test
    @DisplayName("특정 신도 수정 테스트")
    void updateBeliever(){
        //given
        Believer spyBeliever = spy(Believer.class);

        given(believerRepository.findById(anyLong())).willReturn(Optional.of(spyBeliever));

        //when
        believerService.updateBeliever(1, BelieverDTO.builder().build());

        //then
        verify(believerRepository, times(1)).findById(anyLong());
        verify(spyBeliever, times(1)).update(any(BelieverDTO.class));
    }

    @Test
    @DisplayName("특정 신도 수정 조회 시 Empty 테스트")
    void updateBelieverEmpty(){
        //given
        given(believerRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () ->believerService.updateBeliever(1, BelieverDTO.builder().build()));
    }

    @Test
    @DisplayName("특정 신도 Soft Delete 테스트")
    void deleteBeliever(){
        //given
        Believer spyBeliever = spy(Believer.class);
        Family spyFamily1 = spy(Family.class);
        Family spyFamily2 = spy(Family.class);

        List<Family> fixtureList = new ArrayList<>();
        fixtureList.add(spyFamily1);
        fixtureList.add(spyFamily2);

        given(believerRepository.findById(anyLong())).willReturn(Optional.of(spyBeliever));
        given(familyRepository.findAllByBeliever_BelieverId(anyLong())).willReturn(fixtureList);

        //when
        believerService.deleteBeliever(1);

        //then
        verify(believerRepository, times(1)).findById(anyLong());
        verify(spyBeliever, times(1)).delete();
        verify(spyFamily1, times(1)).delete();
        verify(spyFamily2, times(1)).delete();
    }

    @Test
    @DisplayName("특정 신도 삭제 조회 시 Empty 테스트")
    void deleteBelieverEmpty(){
        //given
        given(believerRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () ->believerService.deleteBeliever(1));
    }

    void checkEntity(BelieverDTO resultDTO, Believer fixtureEntity){
        assertThat(resultDTO.getBelieverId(), is(fixtureEntity.getBelieverId()));
        assertThat(resultDTO.getAddress(), is(fixtureEntity.getAddress()));
        assertThat(resultDTO.getBelieverName(), is(fixtureEntity.getBelieverName()));
        assertThat(resultDTO.getBirthOfYear(), is(fixtureEntity.getBirthOfYear()));
        assertThat(resultDTO.getLunarSolarType(), is(fixtureEntity.getLunarSolarType()));
    }
}