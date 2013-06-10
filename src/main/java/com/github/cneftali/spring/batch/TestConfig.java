package com.github.cneftali.spring.batch;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.cneftali.spring.batch.ex01.HelloTaskletConf;

@Configuration
@ComponentScan(basePackages = "org.springframework.batch.test")
@Import(value = { HelloTaskletConf.class })
public class TestConfig {

}
