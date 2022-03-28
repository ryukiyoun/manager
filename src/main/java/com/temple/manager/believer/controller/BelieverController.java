package com.temple.manager.believer.controller;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.service.BelieverService;
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
    public ModelAndView accessPage(ModelAndView mav) {
        mav.setViewName("page/believer");
        return mav;
    }

    @GetMapping("/believer/search")
    public ResponseEntity<BelieverDTO> getBelieverByNameAndBirth(@RequestParam String believerName,
                                                                 @RequestParam String birthOfYear) {
        return ResponseEntity.ok(believerService.getBelieverByNameAndBirtOfYear(believerName, birthOfYear));
    }

    @GetMapping("/believers")
    public ResponseEntity<List<BelieverDTO>> getAllBelievers() {
        return ResponseEntity.ok(believerService.getAllBelievers());
    }

    @GetMapping("/believers/top/5")
    public ResponseEntity<List<BelieverDTO>> getRecent5Believer() {
        return ResponseEntity.ok(believerService.getRecent5Believers());
    }

    @GetMapping("/believers/{name}")
    public ResponseEntity<List<BelieverDTO>> getBelieversByName(@PathVariable String name) {
        return ResponseEntity.ok(believerService.getBelieversByName(name));
    }

    @PostMapping("/believer")
    public ResponseEntity<BelieverDTO> appendBeliever(@RequestBody BelieverDTO believerDTO) {
        return ResponseEntity.ok(believerService.appendBeliever(believerDTO));
    }

    @PutMapping("/believer/{id}")
    public ResponseEntity<String> updateBeliever(@PathVariable long id, @RequestBody BelieverDTO believerDTO) {
        believerService.updateBeliever(id, believerDTO);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/believer/{id}")
    public ResponseEntity<String> deleteBeliever(@PathVariable long id) {
        believerService.deleteBeliever(id);
        return ResponseEntity.ok("Success");
    }
}
