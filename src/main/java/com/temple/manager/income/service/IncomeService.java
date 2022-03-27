package com.temple.manager.income.service;

import com.temple.manager.income.dto.IncomeDTO;
import com.temple.manager.income.dto.IncomeDailyStatisticsDTO;
import com.temple.manager.income.entity.Income;
import com.temple.manager.income.mapper.IncomeMapper;
import com.temple.manager.income.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;

    public List<IncomeDTO> getAllIncomes() {
        List<Income> incomeList = incomeRepository.findAll();

        return incomeMapper.entityListToDTOList(incomeList);
    }

    public List<IncomeDTO> getIncomesByBelieverId(long believerId) {
        List<Income> incomeList = incomeRepository.findAllByBeliever_BelieverId(believerId);

        return incomeMapper.entityListToDTOList(incomeList);
    }

    public List<IncomeDailyStatisticsDTO> getIncomeDailyStatistics(String date) {
        return incomeRepository.getDailyStatistics(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Transactional
    public IncomeDTO appendIncome(IncomeDTO incomeDTO) {
        return incomeMapper.entityToDTO(incomeRepository.save(incomeMapper.DTOToEntity(incomeDTO)));
    }

    @Transactional
    public void updateIncome(long id, IncomeDTO incomeDTO) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Income"));
        income.update(incomeDTO);
    }

    @Transactional
    public void deleteIncome(long id) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Income"));
        income.delete();
    }
}
