package com.temple.manager.family.service;

import com.temple.manager.family.dto.FamilyDTO;
import com.temple.manager.family.entity.Family;
import com.temple.manager.family.mapper.FamilyMapper;
import com.temple.manager.family.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;

    public List<FamilyDTO> getFamiliesByBelieverId(long believerId){
        List<Family> familyList = familyRepository.findAllByBeliever_BelieverId(believerId);

        return FamilyMapper.INSTANCE.entityListToDTOList(familyList);
    }

    @Transactional
    public FamilyDTO appendFamily(FamilyDTO familyDTO){
        return FamilyMapper.INSTANCE.entityToDTO(familyRepository.save(FamilyMapper.INSTANCE.DTOToEntity(familyDTO)));
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
