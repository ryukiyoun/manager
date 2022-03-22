package com.temple.manager.mapper;

import com.temple.manager.dto.IncomeDTO;
import com.temple.manager.entity.Income;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IncomeMapper {
    IncomeMapper INSTANCE = Mappers.getMapper(IncomeMapper.class);

    List<IncomeDTO> entityListToDTOList(List<Income> entityList);
}
