package com.temple.manager.mapper;

import com.temple.manager.dto.BelieverDTO;
import com.temple.manager.entity.Believer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BelieverMapper {
    BelieverMapper INSTANCE = Mappers.getMapper(BelieverMapper.class);

    BelieverDTO entityToDTO(Believer believer);

    Believer DTOToEntity(BelieverDTO believerDTO);

    List<BelieverDTO> entityListToDTOList(List<Believer> entityList);
}
