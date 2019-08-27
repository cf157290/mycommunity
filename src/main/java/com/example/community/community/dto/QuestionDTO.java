package com.example.community.community.dto;

import com.example.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtmodified;
    private Long creator;
    private Integer viewcount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
