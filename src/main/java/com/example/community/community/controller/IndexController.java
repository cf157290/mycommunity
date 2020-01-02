package com.example.community.community.controller;

import com.example.community.community.dto.PaginationDTO;
import com.example.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller//把当前类作为路由api的承载者
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size,
                        @RequestParam(name = "search",required = false)String search){
        PaginationDTO pagination=questionService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
