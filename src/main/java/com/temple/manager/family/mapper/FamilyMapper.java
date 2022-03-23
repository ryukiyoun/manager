package com.temple.manager.family.mapper;

import com.temple.manager.family.dto.FamilyDTO;
import com.temple.manager.family.entity.Family;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FamilyMapper {
    FamilyMapper INSTANCE = Mappers.getMapper(FamilyMapper.class);

    FamilyDTO entityToDTO(Family entity);

    Family DTOToEntity(FamilyDTO entity);

    List<FamilyDTO> entityListToDTOList(List<Family> entityList);
}
