package com.temple.manager.income.mapper;

import com.temple.manager.income.dto.IncomeDTO;
import com.temple.manager.income.entity.Income;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IncomeMapper {
    IncomeMapper INSTANCE = Mappers.getMapper(IncomeMapper.class);

    IncomeDTO entityToDTO(Income income);

    Income DTOToEntity(IncomeDTO incomeDTO);

    List<IncomeDTO> entityListToDTOList(List<Income> entityList);
}
