package com.demo.tlias.config;

import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wjh
 * @date 2024/3/6 19:34
 */
@Configuration
public class CommonConfig {
    @Bean
    public SAXReader saxReader() {
        return new SAXReader();
    }
}
