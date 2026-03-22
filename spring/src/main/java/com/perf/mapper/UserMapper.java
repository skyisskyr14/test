package com.perf.mapper;

import com.perf.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select id, username, password, real_name, department_id, role_id, email, status from user where username = #{username} limit 1")
    User findByUsername(String username);
}
