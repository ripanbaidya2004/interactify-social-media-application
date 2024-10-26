package com.ripan.production.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String homeControllerHandler(){
        return "home Controller handler response";
    }

    @GetMapping("/home")
    public String homeControllerHandler2(){
        return "home Controller handler response 2";
    }
}
