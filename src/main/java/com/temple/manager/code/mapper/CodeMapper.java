package com.temple.manager.code.mapper;

import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CodeMapper {
    CodeDTO entityToDTO(Code believer);

    List<CodeDTO> entityListToDTOList(List<Code> entityList);
}
