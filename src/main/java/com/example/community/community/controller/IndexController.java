package com.example.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller//把当前类作为路由api的承载者
public class IndexController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
