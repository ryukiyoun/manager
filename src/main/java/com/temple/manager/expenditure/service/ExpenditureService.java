package com.temple.manager.expenditure.service;

import com.temple.manager.expenditure.dto.ExpenditureDTO;
import com.temple.manager.expenditure.entity.Expenditure;
import com.temple.manager.expenditure.mapper.ExpenditureMapper;
import com.temple.manager.expenditure.repository.ExpenditureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenditureService {
    private final ExpenditureRepository expenditureRepository;

    public List<ExpenditureDTO> getAllExpenditures() {
        List<Expenditure> expenditureList = expenditureRepository.findAll();

        return ExpenditureMapper.INSTANCE.entityListToDTOList(expenditureList);
    }

    public List<ExpenditureDTO> getExpendituresByBelieverId(long believerId) {
        List<Expenditure> expenditureList = expenditureRepository.findAllByBeliever_BelieverId(believerId);

        return ExpenditureMapper.INSTANCE.entityListToDTOList(expenditureList);
    }

    @Transactional
    public ExpenditureDTO appendExpenditure(ExpenditureDTO expenditureDTO) {
        return ExpenditureMapper.INSTANCE.entityToDTO(expenditureRepository.save(ExpenditureMapper.INSTANCE.DTOToEntity(expenditureDTO)));
    }

    @Transactional
    public void updateExpenditure(long id, ExpenditureDTO expenditureDTO) {
        Expenditure expenditure = expenditureRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Expenditure"));
        expenditure.update(expenditureDTO);
    }

    @Transactional
    public void deleteExpenditure(long id) {
        Expenditure expenditure = expenditureRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Expenditure"));
        expenditure.delete();
    }
}
