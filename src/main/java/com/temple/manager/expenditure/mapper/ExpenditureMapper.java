package com.temple.manager.expenditure.mapper;

import com.temple.manager.expenditure.dto.ExpenditureDTO;
import com.temple.manager.expenditure.entity.Expenditure;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenditureMapper {
    ExpenditureMapper INSTANCE = Mappers.getMapper(ExpenditureMapper.class);

    ExpenditureDTO entityToDTO(Expenditure expenditure);

    Expenditure DTOToEntity(ExpenditureDTO expenditureDTO);

    List<ExpenditureDTO> entityListToDTOList(List<Expenditure> entityList);
}
