package com.temple.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @GetMapping("/registration")
    public ModelAndView accessPage(@RequestParam(required = false) String believerName,
                                   @RequestParam(required = false) String birthOfYear,
                                   ModelAndView mav){
        mav.addObject("believerName", believerName != null ? believerName : "");
        mav.addObject("birthOfYear", birthOfYear != null ? birthOfYear : "");
        mav.setViewName("page/registration");
        return mav;
    }
}
