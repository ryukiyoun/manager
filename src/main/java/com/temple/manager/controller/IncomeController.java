package com.temple.manager.controller;

import com.temple.manager.dto.IncomeDTO;
import com.temple.manager.service.IncomeService;
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
    public ModelAndView accessPage(ModelAndView mav){
        mav.setViewName("page/income");
        return mav;
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<IncomeDTO>> getAllIncomeByActive(){
        return ResponseEntity.ok(incomeService.getAllIncome());
    }

    @PutMapping("/income/{id}")
    public ResponseEntity<String> updateIncome(@PathVariable long id, @RequestBody IncomeDTO incomeDTO){
        incomeService.updateIncome(id, incomeDTO);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/income/{id}")
    public ResponseEntity<String> deleteIncome(@PathVariable long id){
        incomeService.deleteIncome(id);
        return ResponseEntity.ok("Success");
    }
}
