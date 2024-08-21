package com.dev.vue_tabulator_jpa.controller;

import com.dev.vue_tabulator_jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    @PostMapping("/users")
    public int addOrUpdateUser(@RequestBody List<User> userList) {
        return userService.saveUser(userList).size();
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestBody List<User> userList) {
        userService.deleteUser(userList);
    }
}
