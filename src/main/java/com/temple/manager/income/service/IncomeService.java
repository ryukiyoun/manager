package com.temple.manager.income.service;

import com.temple.manager.income.dto.IncomeDTO;
import com.temple.manager.income.dto.IncomeStatisticsDTO;
import com.temple.manager.income.entity.Income;
import com.temple.manager.income.mapper.IncomeMapper;
import com.temple.manager.income.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public List<IncomeStatisticsDTO> getIncomeDailyStatistics(String date) {
        return incomeRepository.getDailyStatistics(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    public List<IncomeStatisticsDTO> getIncomeMonthStatistics(String date) {
        return incomeRepository.getMonthStatistics(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    public List<IncomeStatisticsDTO> getIncomeYearStatistics(String date) {
        return incomeRepository.getYearStatistics(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    public List<IncomeStatisticsDTO> getIncomeCompareToDay(){
        return incomeRepository.getCompareToDay();
    }

    public List<IncomeStatisticsDTO> getIncomeCompareThisMonth(){
        return incomeRepository.getCompareThisMonth();
    }

    public List<IncomeStatisticsDTO> getIncomeCompareThisYear(){
        return incomeRepository.getCompareThisYear();
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
