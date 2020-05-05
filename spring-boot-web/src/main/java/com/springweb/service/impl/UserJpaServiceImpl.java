package com.springweb.service.impl;

import com.springweb.entity.User;
import com.springweb.repository.UserRepository;
import com.springweb.service.UserJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author boreas
 * @create 2020-02-05 下午 2:55
 */
@Service
public class UserJpaServiceImpl implements UserJpaService {

    @Resource
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User findById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public Page<User> findAll(PageRequest of) {
        return userRepository.findAll(of);
    }
}
