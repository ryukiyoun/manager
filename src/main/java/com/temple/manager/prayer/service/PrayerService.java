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
    private final PrayerMapper prayerMapper;

    public List<PrayerDTO> getAllPrayers() {
        return prayerMapper.entityListToDTOList(prayerRepository.findAll());
    }

    public List<PrayerDTO> getPrayersByBelieverId(long believerId) {
        return prayerMapper.entityListToDTOList(prayerRepository.findAllByBeliever_BelieverId(believerId));
    }

    @Transactional
    public PrayerDTO appendPrayer(PrayerDTO prayerDTO) {
        return prayerMapper.entityToDTO(prayerRepository.save(prayerMapper.DTOToEntity(prayerDTO)));
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
