package com.example.community.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtmodified;
    private Integer creator;
    private Integer viewcount;
    private Integer commentCount;
    private Integer likeCount;
}
