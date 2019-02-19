package com.pocopay.minibackoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = "/api")
    public String showSwaggerApi() {
        return "redirect:/swagger-ui.html";
    }
}
