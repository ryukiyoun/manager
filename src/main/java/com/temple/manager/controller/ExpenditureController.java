package com.temple.manager.controller;

import com.temple.manager.dto.ExpenditureDTO;
import com.temple.manager.service.ExpenditureService;
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
    public ModelAndView accessPage(ModelAndView mav){
        mav.setViewName("page/expenditure");
        return mav;
    }

    @GetMapping("/expenditures")
    public ResponseEntity<List<ExpenditureDTO>> getAllExpenditureByActive(){
        return ResponseEntity.ok(expenditureService.getAllExpenditure());
    }

    @PutMapping("/expenditure/{id}")
    public ResponseEntity<String> updateExpenditure(@PathVariable long id, @RequestBody ExpenditureDTO expenditureDTO){
        expenditureService.updateExpenditure(id, expenditureDTO);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/expenditure/{id}")
    public ResponseEntity<String> deleteExpenditure(@PathVariable long id) {
        expenditureService.deleteExpenditure(id);
        return ResponseEntity.ok("Success");
    }
}
