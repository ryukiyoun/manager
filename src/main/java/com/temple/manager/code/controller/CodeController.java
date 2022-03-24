package com.temple.manager.code.controller;

import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;

    @GetMapping("/code/{parentCodeValue}")
    public ResponseEntity<List<CodeDTO>> getCodesByParentCodeValue(@PathVariable String parentCodeValue){
        return ResponseEntity.ok(codeService.getCodeByParentCodeValue(parentCodeValue));
    }
}
