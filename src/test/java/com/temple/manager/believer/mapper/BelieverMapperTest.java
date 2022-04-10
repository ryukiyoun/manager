package com.temple.manager.believer.mapper;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.common.config.TestMapperConfig;
import com.temple.manager.enumable.LunarSolarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestMapperConfig.class})
public class BelieverMapperTest {
    @Autowired
    BelieverMapper believerMapper;

    Believer fixture;

    BelieverDTO fixtureDTO;

    List<Believer> fixtureList;

    List<BelieverDTO> fixtureDTOList;

    @BeforeEach
    void init(){
        fixture = Believer.builder()
                .believerId(1)
                .believerName("tester")
                .lunarSolarType(LunarSolarType.LUNAR)
                .address("서울")
                .birthOfYear("198492")
                .build();

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture);

        fixtureDTO = BelieverDTO.builder()
                .believerId(1)
                .believerName("tester")
                .lunarSolarType(LunarSolarType.LUNAR)
                .address("서울")
                .birthOfYear("198492")
                .build();

        fixtureDTOList = new ArrayList<>();
        fixtureDTOList.add(fixtureDTO);
    }

    @Test
    @DisplayName("신도 Entity에서 DTO로 변경 테스트")
    void entityToDTO(){
        //when
        BelieverDTO result = believerMapper.entityToDTO(fixture);

        //then
        assertThat(result.getBelieverId(), is(1L));
        assertThat(result.getBelieverName(), is("tester"));
        assertThat(result.getBirthOfYear(), is("198492"));
        assertThat(result.getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getAddress(), is("서울"));
    }

    @Test
    @DisplayName("신도 Entity에서 DTO로 변경 테스트 Entity Null")
    void entityToDTONull(){
        //when
        BelieverDTO result = believerMapper.entityToDTO(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("신도 DTO에서 Entity로 변경 테스트")
    void DTOToEntity(){
        //when
        Believer result = believerMapper.DTOToEntity(fixtureDTO);

        //then
        assertThat(result.getBelieverId(), is(1L));
        assertThat(result.getBelieverName(), is("tester"));
        assertThat(result.getBirthOfYear(), is("198492"));
        assertThat(result.getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getAddress(), is("서울"));
    }

    @Test
    @DisplayName("신도 DTO에서 Entity로 변경 테스트 Entity Null")
    void DTOToEntityNull(){
        //when
        Believer result = believerMapper.DTOToEntity(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("신도 EntityList에서 DTOList로 변경 테스트")
    void entityListToDTOList(){
        //when
        List<BelieverDTO> result = believerMapper.entityListToDTOList(fixtureList);

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getBelieverId(), is(1L));
        assertThat(result.get(0).getBelieverName(), is("tester"));
        assertThat(result.get(0).getBirthOfYear(), is("198492"));
        assertThat(result.get(0).getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.get(0).getAddress(), is("서울"));
    }

    @Test
    @DisplayName("신도 EntityList에서 DTOList로 변경 테스트 Entity Null")
    void entityListToDTOListNull(){
        //when
        List<BelieverDTO> result = believerMapper.entityListToDTOList(null);

        //then
        assertThat(result, is(nullValue()));
    }
}
