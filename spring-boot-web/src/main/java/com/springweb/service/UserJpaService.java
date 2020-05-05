package com.springweb.service;

import com.springweb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

/**
 * @author boreas
 * @create 2020-02-05 下午 2:41
 */
public interface UserJpaService {

    User findById(Long userId);

    User saveAndFlush(User user);

    void deleteById(Long userId);

    User save(User user);

    Page<User> findAll(PageRequest of);
}
