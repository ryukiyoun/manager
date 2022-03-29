package com.temple.manager.service;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.believer.mapper.BelieverMapper;
import com.temple.manager.believer.repository.BelieverRepository;
import com.temple.manager.believer.service.BelieverService;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.family.entity.Family;
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

    @Mock
    BelieverMapper believerMapper;

    @InjectMocks
    BelieverService believerService;

    Believer fixture1, fixture2;

    BelieverDTO fixtureDTO1, fixtureDTO2;

    List<Believer> fixtureList;

    List<BelieverDTO> fixtureDTOList;

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

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        fixtureDTO1 = BelieverDTO.builder()
                .believerId(1)
                .believerName("홍길동")
                .address("부산시")
                .birthOfYear("596928")
                .lunarSolarType(LunarSolarType.LUNAR)
                .build();

        fixtureDTO2 = BelieverDTO.builder()
                .believerId(2)
                .believerName("홍길순")
                .address("서울시")
                .birthOfYear("598274")
                .lunarSolarType(LunarSolarType.SOLAR)
                .build();

        fixtureDTOList = new ArrayList<>();
        fixtureDTOList.add(fixtureDTO1);
        fixtureDTOList.add(fixtureDTO2);
    }

    @Test
    @DisplayName("등록된 모든 신도 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getBelievers() {
        //given
        given(believerRepository.findAll()).willReturn(fixtureList);
        given(believerMapper.entityListToDTOList(anyList())).willReturn(fixtureDTOList);

        //when
        List<BelieverDTO> result = believerService.getAllBelievers();

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    @Test
    @DisplayName("등록된 신도 중 특정 이름으로 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getBelieversByName() {
        //given
        given(believerRepository.findAllByBelieverNameContains(anyString())).willReturn(fixtureList);
        given(believerMapper.entityListToDTOList(anyList())).willReturn(fixtureDTOList);

        //when
        List<BelieverDTO> result = believerService.getBelieversByName("testName");

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    @Test
    @DisplayName("등록된 신도 중 특정 이름 생년월일로 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getBelieversByNameAndBirthOfYear() {
        //given
        given(believerRepository.findAllByBelieverNameAndBirthOfYear(anyString(), anyString())).willReturn(Optional.of(fixture1));
        given(believerMapper.entityToDTO(any(Believer.class))).willReturn(fixtureDTO1);

        //when
        BelieverDTO result = believerService.getBelieverByNameAndBirtOfYear("testName", "testBirthOfYear");

        //then
        checkEntity(result, fixtureDTO1);
    }

    @Test
    @DisplayName("신도 추가 테스트")
    void appendBeliever(){
        //given
        given(believerRepository.save(any(Believer.class))).willReturn(fixture1);
        given(believerMapper.DTOToEntity(any(BelieverDTO.class))).willReturn(fixture1);
        given(believerMapper.entityToDTO(any(Believer.class))).willReturn(fixtureDTO1);

        //when
        BelieverDTO result = believerService.appendBeliever(fixtureDTO1);

        //then
        checkEntity(result, fixtureDTO1);
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
        doNothing().when(believerRepository).deleteById(anyLong());
        given(familyRepository.deleteByBeliever_BelieverId(anyLong())).willReturn(2L);

        //when
        believerService.deleteBeliever(1);

        //then
        verify(believerRepository, times(1)).deleteById(anyLong());
        verify(familyRepository, times(1)).deleteByBeliever_BelieverId(anyLong());
    }

    void checkEntity(BelieverDTO resultDTO, BelieverDTO compareDTO){
        assertThat(resultDTO.getBelieverId(), is(compareDTO.getBelieverId()));
        assertThat(resultDTO.getAddress(), is(compareDTO.getAddress()));
        assertThat(resultDTO.getBelieverName(), is(compareDTO.getBelieverName()));
        assertThat(resultDTO.getBirthOfYear(), is(compareDTO.getBirthOfYear()));
        assertThat(resultDTO.getLunarSolarType(), is(compareDTO.getLunarSolarType()));
    }
}