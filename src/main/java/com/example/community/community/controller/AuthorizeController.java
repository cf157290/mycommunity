package com.example.community.community.controller;

import com.example.community.community.dto.AccessTokenDTO;
import com.example.community.community.dto.GithubUser;
import com.example.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired//自动把GitHubProvider实例放入
    private GithubProvider githubProvider;
    @Value("${github.client.id}")//自动读取配置文件
    private String clientId;
    @Value("${github,client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state
                            ){
//        githubProvider.getaccesstoken(new AccessTokenDTO());ctrl+alt+v生成下面两行代码
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //githubProvider.getaccesstoken(accessTokenDTO);//ctrl+alt+v抽取变量选第二项
        String getaccesstoken = githubProvider.getaccesstoken(accessTokenDTO);//传入字段
        GithubUser user=githubProvider.getuser(getaccesstoken);//传入token
        System.out.println(user.getName());
        return "index";
    }
}
