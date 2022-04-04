package com.temple.manager.mapper;

import com.temple.manager.config.TestMapperConfig;
import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.prayer.entity.Prayer;
import com.temple.manager.prayer.mapper.PrayerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestMapperConfig.class})
public class PrayerMapperTest {
    @Autowired
    PrayerMapper prayerMapper;

    Prayer fixture;

    PrayerDTO fixtureDTO;

    List<Prayer> fixtureList;

    List<PrayerDTO> fixtureDTOList;

    @BeforeEach
    void init(){
        fixture = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(1)
                .prayerTypeCodeId(1)
                .build();

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture);

        fixtureDTO = PrayerDTO.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(2)
                .prayerTypeCodeId(2)
                .build();

        fixtureDTOList = new ArrayList<>();
        fixtureDTOList.add(fixtureDTO);
    }

    @Test
    @DisplayName("가족 Entity에서 DTO로 변경 테스트")
    void entityToDTO(){
        //when
        PrayerDTO result = prayerMapper.entityToDTO(fixture);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBelieverId(), is(1L));

        assertThat(result.getPrayerTypeCodeId(), is(1L));
    }

    @Test
    @DisplayName("가족 Entity에서 DTO로 변경 테스트 Entity Null")
    void entityToDTONull(){
        //when
        PrayerDTO result = prayerMapper.entityToDTO(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("기도 Entity에서 DTO로 변경 테스트 Believer Null")
    void entityToDTOBelieverNull(){
        //given
        Prayer fixture = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(0L)
                .prayerTypeCodeId(1)
                .build();

        //when
        PrayerDTO result = prayerMapper.entityToDTO(fixture);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBelieverId(), is(0L));

        assertThat(result.getPrayerTypeCodeId(), is(1L));
    }

    @Test
    @DisplayName("기도 Entity에서 DTO로 변경 테스트 Code Null")
    void entityToDTOCodeNull(){
        //given
        Prayer fixture = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(1L)
                .prayerTypeCodeId(0L)
                .build();

        //when
        PrayerDTO result = prayerMapper.entityToDTO(fixture);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBelieverId(), is(1L));

        assertThat(result.getPrayerTypeCodeId(), is(0L));
    }

    @Test
    @DisplayName("가족 Entity에서 DTO로 변경 테스트")
    void DTOToEntity(){
        //when
        Prayer result = prayerMapper.DTOToEntity(fixtureDTO);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBelieverId(), is(2L));

        assertThat(result.getPrayerTypeCodeId(), is(2L));
    }

    @Test
    @DisplayName("가족 Entity에서 DTO로 변경 테스트 Entity Null")
    void DTOToEntityNull(){
        //when
        Prayer result = prayerMapper.DTOToEntity(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("기도 Entity에서 DTO로 변경 테스트 Believer Null")
    void DTOToEntityBelieverNull(){
        //given
        PrayerDTO fixtureDTO = PrayerDTO.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(0L)
                .prayerTypeCodeId(1L)
                .build();

        //when
        Prayer result = prayerMapper.DTOToEntity(fixtureDTO);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBelieverId(), is(0L));

        assertThat(result.getPrayerTypeCodeId(), is(1L));
    }

    @Test
    @DisplayName("기도 Entity에서 DTO로 변경 테스트 Code Null")
    void DTOToEntityCodeNull(){
        //given
        PrayerDTO fixtureDTO = PrayerDTO.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believerId(1L)
                .prayerTypeCodeId(0L)
                .build();

        //when
        Prayer result = prayerMapper.DTOToEntity(fixtureDTO);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBelieverId(), is(1L));

        assertThat(result.getPrayerTypeCodeId(), is(0L));
    }

    @Test
    @DisplayName("기도 EntityList에서 DTOList로 변경 테스트")
    void entityListToDTOList(){
        //when
        List<PrayerDTO> result = prayerMapper.entityListToDTOList(fixtureList);

        //then
        assertThat(result.get(0).getPrayerId(), is(1L));
        assertThat(result.get(0).getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.get(0).getBelieverId(), is(1L));

        assertThat(result.get(0).getPrayerTypeCodeId(), is(1L));
    }

    @Test
    @DisplayName("기도 EntityList에서 DTOList로 변경 테스트 Entity Null")
    void entityListToDTOListNull(){
        //when
        List<PrayerDTO> result = prayerMapper.entityListToDTOList(null);

        //then
        assertThat(result, is(nullValue()));
    }
}
