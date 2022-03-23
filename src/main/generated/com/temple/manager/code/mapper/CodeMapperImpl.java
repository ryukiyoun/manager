package com.temple.manager.code.mapper;

import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.dto.CodeDTO.CodeDTOBuilder;
import com.temple.manager.code.entity.Code;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-23T15:31:59+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 11.0.9 (Oracle Corporation)"
)
public class CodeMapperImpl implements CodeMapper {

    @Override
    public List<CodeDTO> entityListToDTOList(List<Code> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CodeDTO> list = new ArrayList<CodeDTO>( entityList.size() );
        for ( Code code : entityList ) {
            list.add( codeToCodeDTO( code ) );
        }

        return list;
    }

    protected CodeDTO codeToCodeDTO(Code code) {
        if ( code == null ) {
            return null;
        }

        CodeDTOBuilder codeDTO = CodeDTO.builder();

        codeDTO.codeId( code.getCodeId() );
        codeDTO.codeName( code.getCodeName() );
        codeDTO.parentCodeValue( code.getParentCodeValue() );
        codeDTO.codeValue( code.getCodeValue() );
        codeDTO.att1( code.getAtt1() );
        codeDTO.att2( code.getAtt2() );
        codeDTO.att3( code.getAtt3() );

        return codeDTO.build();
    }
}
