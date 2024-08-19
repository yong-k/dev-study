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

    public int findById(String id) {
        return userMapper.findById(id);
    }

    public int addUser(UserVo user) {
        return userMapper.addUser(user);
    }

    public int updateUser(UserVo user) {
        return userMapper.updateUser(user);
    }

    public int deleteUser(List<String> idList) {
        return userMapper.deleteUser(idList);
    }
}
