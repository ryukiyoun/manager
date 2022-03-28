package com.temple.manager.expenditure.controller;

import com.temple.manager.expenditure.dto.ExpenditureDTO;
import com.temple.manager.expenditure.dto.ExpenditureStatisticsDTO;
import com.temple.manager.expenditure.service.ExpenditureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenditureController {
    private final ExpenditureService expenditureService;

    @GetMapping("/expenditure")
    public ModelAndView accessPage(ModelAndView mav) {
        mav.setViewName("page/expenditure");
        return mav;
    }

    @GetMapping("/expenditures")
    public ResponseEntity<List<ExpenditureDTO>> getAllExpenditure() {
        return ResponseEntity.ok(expenditureService.getAllExpenditures());
    }

    @GetMapping("/expenditures/{believerId}")
    public ResponseEntity<List<ExpenditureDTO>> getExpenditureByBelieverId(@PathVariable long believerId) {
        return ResponseEntity.ok(expenditureService.getExpendituresByBelieverId(believerId));
    }

    @GetMapping("/expenditures/top/5")
    public ResponseEntity<List<ExpenditureDTO>> getRecent5Expenditures() {
        return ResponseEntity.ok(expenditureService.getRecent5Expenditures());
    }

    @GetMapping("/expenditure/chart/statistics/daily/{date}")
    public ResponseEntity<List<ExpenditureStatisticsDTO>> getIncomeDailyStatistics(@PathVariable String date){
        return ResponseEntity.ok(expenditureService.getExpenditureDailyStatistics(date));
    }

    @GetMapping("/expenditure/chart/statistics/month/{date}")
    public ResponseEntity<List<ExpenditureStatisticsDTO>> getIncomeMonthStatistics(@PathVariable String date){
        return ResponseEntity.ok(expenditureService.getExpenditureMonthStatistics(date));
    }

    @GetMapping("/expenditure/chart/statistics/year/{date}")
    public ResponseEntity<List<ExpenditureStatisticsDTO>> getIncomeYearStatistics(@PathVariable String date){
        return ResponseEntity.ok(expenditureService.getExpenditureYearStatistics(date));
    }

    @PostMapping("/expenditure")
    public ResponseEntity<ExpenditureDTO> updateExpenditure(@RequestBody ExpenditureDTO expenditureDTO) {
        return ResponseEntity.ok(expenditureService.appendExpenditure(expenditureDTO));
    }

    @PutMapping("/expenditure/{id}")
    public ResponseEntity<String> updateExpenditure(@PathVariable long id, @RequestBody ExpenditureDTO expenditureDTO) {
        expenditureService.updateExpenditure(id, expenditureDTO);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/expenditure/{id}")
    public ResponseEntity<String> deleteExpenditure(@PathVariable long id) {
        expenditureService.deleteExpenditure(id);
        return ResponseEntity.ok("Success");
    }
}
