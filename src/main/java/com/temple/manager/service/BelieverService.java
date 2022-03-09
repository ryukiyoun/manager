package com.temple.manager.service;

import com.temple.manager.dto.BelieverDTO;
import com.temple.manager.entity.Believer;
import com.temple.manager.mapper.BelieverMapper;
import com.temple.manager.repository.BelieverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BelieverService {
    private final BelieverRepository believerRepository;

    public List<BelieverDTO> getAllBelievers(){
        List<Believer> believerList = believerRepository.findAllByActive("99999999999999");

        return BelieverMapper.INSTANCE.entityListToDTOList(believerList);
    }

    public List<BelieverDTO> getBelieversByName(String name){
        List<Believer> believerList = believerRepository.findAllByActiveAndBelieverNameContains("99999999999999", name);

        return BelieverMapper.INSTANCE.entityListToDTOList(believerList);
    }

    @Transactional
    public void updateBeliever(long id, BelieverDTO believerDTO){
        Believer believer = believerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Fount Believer"));
        believer.update(believerDTO);
    }

    @Transactional
    public void deleteBeliever(long id){
        Believer believer = believerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Fount Believer"));
        believer.delete();
    }
}
