package com.temple.manager.expenditure.controller;

import com.temple.manager.expenditure.dto.ExpenditureDTO;
import com.temple.manager.expenditure.dto.ExpenditureDailyStatisticsDTO;
import com.temple.manager.expenditure.service.ExpenditureService;
import com.temple.manager.income.dto.IncomeDailyStatisticsDTO;
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

    @GetMapping("/expenditure/chart/statistics/daily/{date}")
    public ResponseEntity<List<ExpenditureDailyStatisticsDTO>> getIncomeDailyStatistics(@PathVariable String date){
        return ResponseEntity.ok(expenditureService.getIncomeDailyStatistics(date));
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
