package com.temple.manager.prayer.controller;

import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.prayer.dto.PrayerTypeGroupCntDTO;
import com.temple.manager.prayer.service.PrayerService;
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
    public ModelAndView accessPage(ModelAndView mav) {
        mav.setViewName("page/prayer");
        return mav;
    }

    @GetMapping("/prayers")
    public ResponseEntity<List<PrayerDTO>> getAllPrayers() {
        return ResponseEntity.ok(prayerService.getAllPrayers());
    }

    @GetMapping("/prayers/{believerId}")
    public ResponseEntity<List<PrayerDTO>> getPrayersByBelieverId(@PathVariable long believerId) {
        return ResponseEntity.ok(prayerService.getPrayersByBelieverId(believerId));
    }

    @GetMapping("/prayers/chart/count")
    public ResponseEntity<List<PrayerTypeGroupCntDTO>> getPrayersTypeGroupCnt() {
        return ResponseEntity.ok(prayerService.getPrayersTypeGroupCnt());
    }

    @PostMapping("/prayer")
    public ResponseEntity<PrayerDTO> appendPrayer(@RequestBody PrayerDTO prayerDTO) {
        return ResponseEntity.ok(prayerService.appendPrayer(prayerDTO));
    }

    @PutMapping("/prayer/{id}")
    public ResponseEntity<String> updatePrayer(@PathVariable long id, @RequestBody PrayerDTO prayerDTO) {
        prayerService.updatePrayer(id, prayerDTO);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/prayer/{id}")
    public ResponseEntity<String> deletePrayer(@PathVariable long id) {
        prayerService.deletePrayer(id);
        return ResponseEntity.ok("Success");
    }
}
