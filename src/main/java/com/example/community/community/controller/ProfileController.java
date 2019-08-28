package com.example.community.community.controller;

import com.example.community.community.dto.PaginationDTO;
import com.example.community.community.mapper.UserMapper;
import com.example.community.community.model.User;
import com.example.community.community.service.NotificationService;
import com.example.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")// 动态的切换路径来做内容和样式的替换
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action")String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        if ("question".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if ("replies".equals(action)){
            PaginationDTO paginationDTO=notificationService.list(user.getId(),page,size);
            Long unreadCount=notificationService.unreadCount(user.getId());
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("unreadCount",unreadCount);
        }

        return "profile";
    }
}
