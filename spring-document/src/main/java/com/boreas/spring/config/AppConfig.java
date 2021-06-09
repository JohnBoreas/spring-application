package com.boreas.spring.config;

import com.boreas.spring.bean.MyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    private MyBean myBean;

    @Bean(name = "myBean")
    public MyBean myBean(MyBean myBean) {
        // instantiate, configure and return bean ...
        this.myBean = myBean;
        return myBean;
    }
}