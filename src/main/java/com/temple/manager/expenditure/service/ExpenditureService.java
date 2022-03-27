package com.temple.manager.expenditure.service;

import com.temple.manager.expenditure.dto.ExpenditureDTO;
import com.temple.manager.expenditure.dto.ExpenditureDailyStatisticsDTO;
import com.temple.manager.expenditure.entity.Expenditure;
import com.temple.manager.expenditure.mapper.ExpenditureMapper;
import com.temple.manager.expenditure.repository.ExpenditureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenditureService {
    private final ExpenditureRepository expenditureRepository;
    private final ExpenditureMapper expenditureMapper;

    public List<ExpenditureDTO> getAllExpenditures() {
        return expenditureMapper.entityListToDTOList(expenditureRepository.findAll());
    }

    public List<ExpenditureDTO> getExpendituresByBelieverId(long believerId) {
        return expenditureMapper.entityListToDTOList(expenditureRepository.findAllByBeliever_BelieverId(believerId));
    }

    public List<ExpenditureDailyStatisticsDTO> getIncomeDailyStatistics(String date){
        return expenditureRepository.getDailyStatistics(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Transactional
    public ExpenditureDTO appendExpenditure(ExpenditureDTO expenditureDTO) {
        return expenditureMapper.entityToDTO(expenditureRepository.save(expenditureMapper.DTOToEntity(expenditureDTO)));
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
