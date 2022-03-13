package com.temple.manager.service;

import com.temple.manager.dto.PrayerDTO;
import com.temple.manager.entity.Prayer;
import com.temple.manager.mapper.PrayerMapper;
import com.temple.manager.repository.PrayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrayerService {
    private final PrayerRepository prayerRepository;

    public List<PrayerDTO> getPrayersByActive(){
        List<Prayer> prayerList = prayerRepository.findAllByActive("99999999999999");

        return PrayerMapper.INSTANCE.entityListToDTOList(prayerList);
    }

    @Transactional
    public void updatePrayer(long id, PrayerDTO prayerDTO){
        Prayer prayer = prayerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Prayer"));
        prayer.update(prayerDTO);
    }

    @Transactional
    public void deletePrayer(long id) {
        Prayer prayer = prayerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Prayer"));
        prayer.delete();
    }
}
