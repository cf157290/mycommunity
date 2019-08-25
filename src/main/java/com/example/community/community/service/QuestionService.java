package com.example.community.community.service;

import com.example.community.community.dto.PaginationDTO;
import com.example.community.community.dto.QuestionDTO;
import com.example.community.community.mapper.QuestionMapper;
import com.example.community.community.mapper.UserMapper;
import com.example.community.community.model.Question;
import com.example.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {//组装question和user需要用到中间层service来做
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        if (page<1){
            page=1;
        }
        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        Integer offset=size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);//只写等号右边按Ctrl+alt+v快速生成左边内容
        List<QuestionDTO>questionDTOSList=new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//可以快速把question的属性复制到questionDTO上
            questionDTO.setUser(user);
            questionDTOSList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOSList);


        return paginationDTO;
    }
}
//查询question同时循环查询user并赋值给question