package com.temple.manager.mapper;

import com.temple.manager.dto.BelieverDTO;
import com.temple.manager.entity.Believer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BelieverMapper {
    BelieverMapper INSTANCE = Mappers.getMapper(BelieverMapper.class);

    List<BelieverDTO> entityListToDTOList(List<Believer> entityList);
}
