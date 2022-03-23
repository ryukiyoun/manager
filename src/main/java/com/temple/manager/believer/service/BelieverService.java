package com.temple.manager.believer.service;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.entity.Family;
import com.temple.manager.believer.mapper.BelieverMapper;
import com.temple.manager.believer.repository.BelieverRepository;
import com.temple.manager.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BelieverService {
    private final BelieverRepository believerRepository;
    private final FamilyRepository familyRepository;

    public List<BelieverDTO> getAllBelievers() {
        List<Believer> believerList = believerRepository.findAll();

        return BelieverMapper.INSTANCE.entityListToDTOList(believerList);
    }

    public List<BelieverDTO> getBelieversByName(String name) {
        List<Believer> believerList = believerRepository.findAllByBelieverNameContains(name);

        return BelieverMapper.INSTANCE.entityListToDTOList(believerList);
    }

    public BelieverDTO getBelieverByNameAndBirtOfYear(String believerName, String birthOfYear) {
        Believer believer = believerRepository.findAllByBelieverNameAndBirthOfYear(believerName, birthOfYear).orElse(Believer.builder().build());
        return BelieverMapper.INSTANCE.entityToDTO(believer);
    }

    @Transactional
    public BelieverDTO appendBeliever(BelieverDTO believerDTO) {
        return BelieverMapper.INSTANCE.entityToDTO(believerRepository.save(BelieverMapper.INSTANCE.DTOToEntity(believerDTO)));
    }

    @Transactional
    public void updateBeliever(long id, BelieverDTO believerDTO) {
        Believer believer = believerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Fount Believer"));
        believer.update(believerDTO);
    }

    @Transactional
    public void deleteBeliever(long id) {
        Believer believer = believerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Fount Believer"));
        believer.delete();

        List<Family> families = familyRepository.findAllByBeliever_BelieverId(believer.getBelieverId());

        for (Family family : families)
            family.delete();
    }
}
