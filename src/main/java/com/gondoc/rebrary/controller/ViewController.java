package com.gondoc.rebrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/main")
    public String view() {
        return "index";
    }
}
