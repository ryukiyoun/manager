package com.temple.manager.family.mapper;

import com.temple.manager.family.dto.FamilyDTO;
import com.temple.manager.family.entity.Family;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FamilyMapper {
    FamilyDTO entityToDTO(Family entity);

    Family DTOToEntity(FamilyDTO entity);

    List<FamilyDTO> entityListToDTOList(List<Family> entityList);
}
