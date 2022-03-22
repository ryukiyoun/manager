package com.temple.manager.mapper;

import com.temple.manager.dto.ExpenditureDTO;
import com.temple.manager.entity.Expenditure;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenditureMapper {
    ExpenditureMapper INSTANCE = Mappers.getMapper(ExpenditureMapper.class);

    List<ExpenditureDTO> entityListToDTOList(List<Expenditure> entityList);
}
