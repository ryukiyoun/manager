package com.temple.manager.mapper;

import com.temple.manager.dto.PrayerDTO;
import com.temple.manager.entity.Prayer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PrayerMapper {
    PrayerMapper INSTANCE = Mappers.getMapper(PrayerMapper.class);

    List<PrayerDTO> entityListToDTOList(List<Prayer> entityList);
}