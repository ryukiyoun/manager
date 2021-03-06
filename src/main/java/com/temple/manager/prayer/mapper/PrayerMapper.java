package com.temple.manager.prayer.mapper;

import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.prayer.entity.Prayer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrayerMapper {
    PrayerDTO entityToDTO(Prayer prayer);

    Prayer DTOToEntity(PrayerDTO prayerDTO);

    List<PrayerDTO> entityListToDTOList(List<Prayer> entityList);
}
