package com.temple.manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("/login")
    public ModelAndView loginPageAccess(ModelAndView mav){
        mav.setViewName("page/login");
        return mav;
    }
}
