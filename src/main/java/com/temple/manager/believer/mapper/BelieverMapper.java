package com.temple.manager.believer.mapper;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BelieverMapper {
    BelieverDTO entityToDTO(Believer believer);

    Believer DTOToEntity(BelieverDTO believerDTO);

    List<BelieverDTO> entityListToDTOList(List<Believer> entityList);
}
