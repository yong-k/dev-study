package com.dev.vue_tabulator.controller;

import com.dev.vue_tabulator.service.UserService;
import com.dev.vue_tabulator.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserVo> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/users")
    public void save (@RequestBody List<UserVo> userList) {
        for (UserVo user : userList) {
            int isExist = userService.findById(user.getId());
            if (isExist == 0) userService.addUser(user);
            else userService.updateUser(user);

        }
    }

    @DeleteMapping("/users")
    public int deleteUser(@RequestBody List<String> idList) {
        return userService.deleteUser(idList);
    }
}
