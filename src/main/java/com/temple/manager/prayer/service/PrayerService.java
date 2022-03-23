package com.temple.manager.prayer.service;

import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.prayer.entity.Prayer;
import com.temple.manager.prayer.mapper.PrayerMapper;
import com.temple.manager.prayer.repository.PrayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrayerService {
    private final PrayerRepository prayerRepository;

    public List<PrayerDTO> getAllPrayers() {
        List<Prayer> prayerList = prayerRepository.findAll();

        return PrayerMapper.INSTANCE.entityListToDTOList(prayerList);
    }

    public List<PrayerDTO> getPrayersByBelieverId(long believerId) {
        List<Prayer> prayerList = prayerRepository.findAllByBeliever_BelieverId(believerId);

        return PrayerMapper.INSTANCE.entityListToDTOList(prayerList);
    }

    @Transactional
    public PrayerDTO appendPrayer(PrayerDTO prayerDTO) {
        return PrayerMapper.INSTANCE.entityToDTO(prayerRepository.save(PrayerMapper.INSTANCE.DTOToEntity(prayerDTO)));
    }

    @Transactional
    public void updatePrayer(long id, PrayerDTO prayerDTO) {
        Prayer prayer = prayerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Prayer"));
        prayer.update(prayerDTO);
    }

    @Transactional
    public void deletePrayer(long id) {
        Prayer prayer = prayerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Prayer"));
        prayer.delete();
    }
}
