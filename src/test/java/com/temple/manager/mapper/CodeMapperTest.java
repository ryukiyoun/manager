package com.temple.manager.mapper;

import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import com.temple.manager.code.mapper.CodeMapper;
import com.temple.manager.config.TestMapperConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestMapperConfig.class})
public class CodeMapperTest {
    @Autowired
    CodeMapper codeMapper;

    Code fixture;

    CodeDTO fixtureDTO;

    List<Code> fixtureList;

    List<CodeDTO> fixtureListDTO;

    @BeforeEach
    void init() {
        fixture = Code.builder()
                .codeId(1)
                .codeName("testCode")
                .codeValue("testValue")
                .parentCodeValue("P_TEST_CODE")
                .att1("1")
                .att2("2")
                .att3("3")
                .build();

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture);

        fixtureDTO = CodeDTO.builder()
                .codeId(1)
                .codeName("testCode")
                .codeValue("testValue")
                .parentCodeValue("P_TEST_CODE")
                .att1("1")
                .att2("2")
                .att3("3")
                .build();

        fixtureListDTO = new ArrayList<>();
        fixtureListDTO.add(fixtureDTO);
    }

    @Test
    @DisplayName("코드 Entity에서 DTO로 변경 테스트")
    void entityToDTO(){
        //when
        CodeDTO result = codeMapper.entityToDTO(fixture);

        //then
        assertThat(result.getCodeId(), is(1L));
        assertThat(result.getCodeName(), is("testCode"));
        assertThat(result.getCodeValue(), is("testValue"));
        assertThat(result.getParentCodeValue(), is("P_TEST_CODE"));
        assertThat(result.getAtt1(), is("1"));
        assertThat(result.getAtt2(), is("2"));
        assertThat(result.getAtt3(), is("3"));
    }

    @Test
    @DisplayName("코드 Entity에서 DTO로 변경 테스트 Entity Null")
    void entityToDTONull(){
        //when
        CodeDTO result = codeMapper.entityToDTO(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("코드 EntityList에서 DTOList로 변경 테스트")
    void entityListToDTOList(){
        //when
        List<CodeDTO> result = codeMapper.entityListToDTOList(fixtureList);

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getCodeId(), is(1L));
        assertThat(result.get(0).getCodeName(), is("testCode"));
        assertThat(result.get(0).getCodeValue(), is("testValue"));
        assertThat(result.get(0).getParentCodeValue(), is("P_TEST_CODE"));
        assertThat(result.get(0).getAtt1(), is("1"));
        assertThat(result.get(0).getAtt2(), is("2"));
        assertThat(result.get(0).getAtt3(), is("3"));
    }

    @Test
    @DisplayName("코드 EntityList에서 DTOList로 변경 테스트 Entity Null")
    void entityListToDTOListNull(){
        //when
        List<CodeDTO> result = codeMapper.entityListToDTOList(null);

        //then
        assertThat(result, is(nullValue()));
    }
}
