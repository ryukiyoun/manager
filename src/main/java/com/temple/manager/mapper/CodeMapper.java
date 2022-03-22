package com.temple.manager.mapper;

import com.temple.manager.dto.CodeDTO;
import com.temple.manager.entity.Code;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CodeMapper {
    CodeMapper INSTANCE = Mappers.getMapper(CodeMapper.class);

    List<CodeDTO> entityListToDTOList(List<Code> entityList);
}
