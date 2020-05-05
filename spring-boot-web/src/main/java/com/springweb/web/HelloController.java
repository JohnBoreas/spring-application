package com.springweb.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * @author boreas
 * @create 2020-02-01 下午 3:57
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(Locale locale, Model model) {
        return "Hello World";
    }

}