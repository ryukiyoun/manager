package com.temple.manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final Environment environment;

    @GetMapping("/profile")
    public ResponseEntity<String> getActiveProfile(){
        return ResponseEntity.ok(Arrays.stream(environment.getActiveProfiles()).findFirst().orElse(""));
    }
}
