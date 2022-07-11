package com.xinyao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Auther: BuziYang
 * @Date: 2019/10/29 11:14
 * @Description:
 */
@Configuration
public class PasswordConfig {
    @Value("${salt}")
    private String salt;
    @Value("${strength}")
    private int strength;


    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        //加盐
        SecureRandom secureRandom = new SecureRandom(salt.getBytes());
        //初始化 BCryptPasswordEncoder
        return new BCryptPasswordEncoder(strength, secureRandom);
    }
}
