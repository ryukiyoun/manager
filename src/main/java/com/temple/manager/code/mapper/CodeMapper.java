package com.temple.manager.code.mapper;

import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CodeMapper {
    CodeMapper INSTANCE = Mappers.getMapper(CodeMapper.class);

    List<CodeDTO> entityListToDTOList(List<Code> entityList);
}
