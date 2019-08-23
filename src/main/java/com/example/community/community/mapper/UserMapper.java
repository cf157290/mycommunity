package com.example.community.community.mapper;

import com.example.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountid},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
