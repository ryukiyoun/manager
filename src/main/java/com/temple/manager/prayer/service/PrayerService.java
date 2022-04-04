package com.temple.manager.prayer.service;

import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.prayer.dto.PrayerGridListDTO;
import com.temple.manager.prayer.dto.PrayerTypeGroupCntDTO;
import com.temple.manager.prayer.entity.Prayer;
import com.temple.manager.prayer.mapper.PrayerMapper;
import com.temple.manager.prayer.repository.PrayerRepository;
import com.temple.manager.prayer.repository.PrayerRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrayerService {
    private final PrayerRepository prayerRepository;
    private final PrayerRepositorySupport prayerRepositorySupport;
    private final PrayerMapper prayerMapper;

    public List<PrayerGridListDTO> getAllPrayers() {
        return prayerRepositorySupport.getPrayers();
    }

    public List<PrayerGridListDTO> getPrayersByBelieverId(long believerId) {
        return prayerRepositorySupport.getPrayersByBelieverId(believerId);
    }

    public List<PrayerTypeGroupCntDTO> getPrayersTypeGroupCnt() {
        return prayerRepositorySupport.getPrayersTypeGroupCnt();
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
        prayerRepository.deleteById(id);
    }
}
