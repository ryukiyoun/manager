package com.temple.manager.service;

import com.temple.manager.dto.ExpenditureDTO;
import com.temple.manager.entity.Expenditure;
import com.temple.manager.mapper.ExpenditureMapper;
import com.temple.manager.repository.ExpenditureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenditureService {
    private final ExpenditureRepository expenditureRepository;

    public List<ExpenditureDTO> getAllExpenditure() {
        List<Expenditure> expenditureList = expenditureRepository.findAll();

        return ExpenditureMapper.INSTANCE.entityListToDTOList(expenditureList);
    }

    @Transactional
    public void updateExpenditure(long id, ExpenditureDTO expenditureDTO){
        Expenditure expenditure = expenditureRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Expenditure"));
        expenditure.update(expenditureDTO);
    }

    @Transactional
    public void deleteExpenditure(long id){
        Expenditure expenditure = expenditureRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Expenditure"));
        expenditure.delete();
    }
}
