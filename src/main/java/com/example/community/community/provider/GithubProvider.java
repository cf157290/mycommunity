package com.example.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.example.community.community.dto.AccessTokenDTO;
import com.example.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component//把当前类初始化到Spring容器的上下文
public class GithubProvider {
    public String getaccesstoken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //笔记：  post请求的时候需要用到requestbody，赋值给request对象，get请求的时候则不需要
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        //System.out.println(body+"----------");
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
/*            String[] split=string.split("&");在变量位置按Ctrl+alt+n下一行同理会生成下面代码，把当前变量放到原文中取
            String tokenstr=split[0];
            String token=tokenstr.split("=")[0];*/
            String token= string.split("&")[0].split("=")[0];
            return token;
            //System.out.println(string);
            //return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getuser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
//            JSON.parseObject(string,GithubUser.class);ctrl+alt+v生成下面代码
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
