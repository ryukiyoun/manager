package com.temple.manager.income.controller;

import com.temple.manager.income.dto.IncomeDTO;
import com.temple.manager.income.dto.IncomeDailyStatisticsDTO;
import com.temple.manager.income.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    @GetMapping("/income")
    public ModelAndView accessPage(ModelAndView mav) {
        mav.setViewName("page/income");
        return mav;
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<IncomeDTO>> getAllIncomes() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    @GetMapping("/incomes/{believerId}")
    public ResponseEntity<List<IncomeDTO>> getIncomeByBelieverId(@PathVariable long believerId) {
        return ResponseEntity.ok(incomeService.getIncomesByBelieverId(believerId));
    }

    @GetMapping("/income/chart/statistics/daily/{date}")
    public ResponseEntity<List<IncomeDailyStatisticsDTO>> getIncomeDailyStatistics(@PathVariable String date){
        return ResponseEntity.ok(incomeService.getIncomeDailyStatistics(date));
    }

    @PostMapping("/income")
    public ResponseEntity<IncomeDTO> appendIncome(@RequestBody IncomeDTO incomeDTO) {
        return ResponseEntity.ok(incomeService.appendIncome(incomeDTO));
    }

    @PutMapping("/income/{id}")
    public ResponseEntity<String> updateIncome(@PathVariable long id, @RequestBody IncomeDTO incomeDTO) {
        incomeService.updateIncome(id, incomeDTO);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/income/{id}")
    public ResponseEntity<String> deleteIncome(@PathVariable long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.ok("Success");
    }
}
