package com.temple.manager.income.controller;

import com.temple.manager.income.dto.IncomeDTO;
import com.temple.manager.income.dto.IncomeStatisticsDTO;
import com.temple.manager.income.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    public ResponseEntity<List<IncomeStatisticsDTO>> getIncomeDailyStatistics(@PathVariable String date){
        return ResponseEntity.ok(incomeService.getIncomeDailyStatistics(date));
    }

    @GetMapping("/income/chart/statistics/month/{date}")
    public ResponseEntity<List<IncomeStatisticsDTO>> getIncomeMonthStatistics(@PathVariable String date){
        return ResponseEntity.ok(incomeService.getIncomeMonthStatistics(date));
    }

    @GetMapping("/income/chart/statistics/year/{date}")
    public ResponseEntity<List<IncomeStatisticsDTO>> getIncomeYearStatistics(@PathVariable String date){
        return ResponseEntity.ok(incomeService.getIncomeYearStatistics(date));
    }

    @GetMapping("/income/compare/today")
    public ResponseEntity<List<IncomeStatisticsDTO>> getIncomeCompareToDay(){
        return ResponseEntity.ok(incomeService.getIncomeCompareToDay());
    }

    @GetMapping("/income/compare/thismonth")
    public ResponseEntity<List<IncomeStatisticsDTO>> getIncomeCompareThisMonth(){
        return ResponseEntity.ok(incomeService.getIncomeCompareThisMonth());
    }

    @GetMapping("/income/compare/thisyear")
    public ResponseEntity<List<IncomeStatisticsDTO>> getIncomeCompareThisYear(){
        return ResponseEntity.ok(incomeService.getIncomeCompareThisYear());
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
