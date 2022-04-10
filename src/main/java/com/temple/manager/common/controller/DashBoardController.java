package com.temple.manager.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashBoardController {

    @GetMapping("/")
    public ModelAndView accessPage(ModelAndView mav){
        mav.setViewName("page/dashboard");
        return mav;
    }
}
