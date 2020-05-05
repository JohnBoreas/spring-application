package com.springweb.web;

import com.springweb.entity.User;
import com.springweb.service.UserJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author boreas
 * @create 2020-02-05 下午 2:40
 */
@RestController
@RequestMapping("/userjpa")
public class UserJpaController {

    @Resource
    private UserJpaService userJpaService;

    @PostMapping("/save")// 映射 URL 绑定的占位符
    public User saveUser(@RequestBody User user) {
        return userJpaService.save(user);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userJpaService.deleteById(userId);
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
        user.setId(userId);
        return userJpaService.saveAndFlush(user);
    }

    @GetMapping("/get/{userId}")
    public User getUserInfo(@PathVariable("userId") Long userId) {
        User user = userJpaService.findById(userId);
        return user;
    }

    @GetMapping("/list")
    public Page<User> pageQuery(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return userJpaService.findAll(PageRequest.of(pageNum - 1, pageSize));
    }
}
