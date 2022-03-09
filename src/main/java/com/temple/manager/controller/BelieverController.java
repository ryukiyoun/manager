package com.temple.manager.controller;

import com.temple.manager.dto.BelieverDTO;
import com.temple.manager.service.BelieverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BelieverController {
    private final BelieverService believerService;

    @GetMapping("/believer")
    public ModelAndView accessPage(ModelAndView mav){
        mav.setViewName("/page/believer");
        return mav;
    }

    @GetMapping("/believers")
    public ResponseEntity<List<BelieverDTO>> getAllBelievers(){
        return ResponseEntity.ok(believerService.getAllBelievers());
    }

    @GetMapping("/believers/{name}")
    public ResponseEntity<List<BelieverDTO>> getBelieversByName(@PathVariable String name){
        return ResponseEntity.ok(believerService.getBelieversByName(name));
    }

    @PutMapping("/believer/{id}")
    public ResponseEntity<String> updateBeliever(@PathVariable long id, @RequestBody BelieverDTO believerDTO){
        believerService.updateBeliever(id, believerDTO);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/believer/{id}")
    public ResponseEntity<String> deleteBeliever(@PathVariable long id){
        believerService.deleteBeliever(id);
        return ResponseEntity.ok("Success");
    }
}
