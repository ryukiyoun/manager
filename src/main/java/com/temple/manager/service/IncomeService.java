package com.temple.manager.service;

import com.temple.manager.dto.IncomeDTO;
import com.temple.manager.entity.Income;
import com.temple.manager.mapper.IncomeMapper;
import com.temple.manager.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public List<IncomeDTO> getAllIncome(){
        List<Income> incomeList = incomeRepository.findAll();

        return IncomeMapper.INSTANCE.entityListToDTOList(incomeList);
    }

    @Transactional
    public void updateIncome(long id, IncomeDTO incomeDTO){
        Income income = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Income"));
        income.update(incomeDTO);
    }

    @Transactional
    public void deleteIncome(long id){
        Income income = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Income"));
        income.delete();
    }
}
