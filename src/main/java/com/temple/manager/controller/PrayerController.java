package com.temple.manager.controller;

import com.temple.manager.dto.PrayerDTO;
import com.temple.manager.service.PrayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PrayerController {
    private final PrayerService prayerService;

    @GetMapping("/prayer")
    public ModelAndView accessPage(ModelAndView mav){
        mav.setViewName("/page/prayer");
        return mav;
    }

    @GetMapping("/prayers")
    public ResponseEntity<List<PrayerDTO>> getAllPrayers(){
        return ResponseEntity.ok(prayerService.getAllPrayers());
    }

    @PutMapping("/prayer/{id}")
    public ResponseEntity<String> updatePrayer(@PathVariable long id, @RequestBody PrayerDTO prayerDTO) {
        prayerService.updatePrayer(id, prayerDTO);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/prayer/{id}")
    public ResponseEntity<String> deletePrayer(@PathVariable long id){
        prayerService.deletePrayer(id);
        return ResponseEntity.ok("Success");
    }
}
