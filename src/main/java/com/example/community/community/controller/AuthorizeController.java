package com.example.community.community.controller;

import com.example.community.community.dto.AccessTokenDTO;
import com.example.community.community.dto.GithubUser;
import com.example.community.community.mapper.UserMapper;
import com.example.community.community.model.User;
import com.example.community.community.provider.GithubProvider;
import com.example.community.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j//lombok打日志用
public class AuthorizeController {
    @Autowired//自动把GitHubProvider实例放入
    private GithubProvider githubProvider;
    @Value("${github.client.id}")//自动读取配置文件
    private String clientId;
    @Value("${github,client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
   // @Autowired
   // private UserMapper userMapper;
    @Autowired
    UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                            //HttpServletRequest request,
                            HttpServletResponse response){//Spring会自动把上下文中的reponse放入这里
//        githubProvider.getaccesstoken(new AccessTokenDTO());ctrl+alt+v生成下面两行代码
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //githubProvider.getaccesstoken(accessTokenDTO);//ctrl+alt+v抽取变量选第二项
        String getaccesstoken = githubProvider.getAccessToken(accessTokenDTO);//传入字段
        GithubUser githubUser =githubProvider.getUser(getaccesstoken);//传入token
        //System.out.println(user.getName());
        if(githubUser!=null/*&& githubUser.getId()!=0*/){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            //userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            //登录成功,写cookie和session
            //request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
            //redirect是一个前缀，会把地址中多余的内容去掉，重定向到这个页面，不写的话会多出操作过的一些内容
        }else{
            //登录失败重新登录
            log.error("callback get github error,{}",githubUser);//打日志
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){//request可以操作session,response可以操作cookie
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
