package com.temple.manager.mapper;

import com.temple.manager.dto.FamilyDTO;
import com.temple.manager.entity.Family;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FamilyMapper {
    FamilyMapper INSTANCE = Mappers.getMapper(FamilyMapper.class);

    List<FamilyDTO> entityListToDTOList(List<Family> entityList);
}
