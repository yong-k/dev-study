package com.dev.vue_tabulator_jpa.controller;

import com.dev.vue_tabulator_jpa.entity.User;
import com.dev.vue_tabulator_jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public List<User> saveUser(List<User> userList) {
        return userRepository.saveAll(userList);
    }

    public void deleteUser(List<User> userList) {
        userRepository.deleteAll(userList);
    }
}
