package com.example.community.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountid;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
