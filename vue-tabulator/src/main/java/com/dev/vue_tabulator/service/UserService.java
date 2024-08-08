package com.dev.vue_tabulator.service;

import com.dev.vue_tabulator.mapper.UserMapper;
import com.dev.vue_tabulator.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<UserVo> getAllUser() {
        return userMapper.getAllUser();
    }
}
