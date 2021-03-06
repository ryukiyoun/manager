package com.temple.manager.family.controller;

import com.temple.manager.family.dto.FamilyDTO;
import com.temple.manager.family.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FamilyController {
    private final FamilyService familyService;

    @GetMapping("/family")
    public ModelAndView accessPage(ModelAndView mav) {
        mav.setViewName("page/family");
        return mav;
    }

    @GetMapping("/families/{believerId}")
    public ResponseEntity<List<FamilyDTO>> getFamiliesByBelieverId(@PathVariable long believerId){
        return ResponseEntity.ok(familyService.getFamiliesByBelieverId(believerId));
    }

    @PostMapping("/family")
    public ResponseEntity<FamilyDTO> appendFamily(@RequestBody FamilyDTO familyDTO){
        return ResponseEntity.ok(familyService.appendFamily(familyDTO));
    }

    @PutMapping("/family/{id}")
    public ResponseEntity<String> updateFamily(@PathVariable long id, @RequestBody FamilyDTO familyDTO){
        familyService.updateFamily(id, familyDTO);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/family/{id}")
    public ResponseEntity<String> deleteFamily(@PathVariable long id){
        familyService.deleteFamily(id);
        return ResponseEntity.ok("Success");
    }
}
