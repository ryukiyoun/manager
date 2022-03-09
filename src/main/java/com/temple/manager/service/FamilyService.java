package com.temple.manager.service;

import com.temple.manager.dto.FamilyDTO;
import com.temple.manager.entity.Family;
import com.temple.manager.mapper.FamilyMapper;
import com.temple.manager.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;

    public List<FamilyDTO> getFamiliesByBelieverId(long believerId){
        List<Family> familyList = familyRepository.findAllByActiveAndBeliever_BelieverId("99999999999999", believerId);

        return FamilyMapper.INSTANCE.entityListToDTOList(familyList);
    }

    @Transactional
    public void updateFamily(long id, FamilyDTO familyDTO){
        Family family = familyRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Family"));
        family.update(familyDTO);
    }

    @Transactional
    public void deleteFamily(long id){
        Family family = familyRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Family"));
        family.delete();
    }
}
