package com.springweb.web;

import com.springweb.entity.UserEntity;
import com.springweb.entity.UserXml;
import com.springweb.mapper.UserMapper;
import com.springweb.mapper.UserXmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author boreas
 * @create 2020-02-13 下午 10:24
 */
@RestController
@RequestMapping("/user-mybatis")
public class UserMybatisController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserXmlMapper userXmlMapper;

    @RequestMapping("/getAll")
    public List<UserEntity> getAll() {
        return userMapper.getAll();
    }

    @RequestMapping("/id-{userId}")
    public UserXml getUserById(@PathVariable Long userId) {
        return userXmlMapper.selectByPrimaryKey(userId);
    }
}
