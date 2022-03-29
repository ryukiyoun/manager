package com.temple.manager.believer.service;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.family.entity.Family;
import com.temple.manager.believer.mapper.BelieverMapper;
import com.temple.manager.believer.repository.BelieverRepository;
import com.temple.manager.family.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BelieverService {
    private final BelieverRepository believerRepository;
    private final FamilyRepository familyRepository;
    private final BelieverMapper believerMapper;

    public List<BelieverDTO> getAllBelievers() {
        return believerMapper.entityListToDTOList(believerRepository.findAll());
    }

    public List<BelieverDTO> getBelieversByName(String name) {
        return believerMapper.entityListToDTOList(believerRepository.findAllByBelieverNameContains(name));
    }

    public List<BelieverDTO> getRecent5Believers() {
        return believerMapper.entityListToDTOList(believerRepository.findTop5ByOrderByBelieverIdDesc());
    }

    public BelieverDTO getBelieverByNameAndBirtOfYear(String believerName, String birthOfYear) {
        return believerMapper.entityToDTO(believerRepository.findAllByBelieverNameAndBirthOfYear(believerName, birthOfYear).orElse(Believer.builder().build()));
    }

    @Transactional
    public BelieverDTO appendBeliever(BelieverDTO believerDTO) {
        return believerMapper.entityToDTO(believerRepository.save(believerMapper.DTOToEntity(believerDTO)));
    }

    @Transactional
    public void updateBeliever(long id, BelieverDTO believerDTO) {
        Believer believer = believerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Fount Believer"));
        believer.update(believerDTO);
    }

    @Transactional
    public void deleteBeliever(long id) {
        believerRepository.deleteById(id);

        familyRepository.deleteByBeliever_BelieverId(id);
    }
}
