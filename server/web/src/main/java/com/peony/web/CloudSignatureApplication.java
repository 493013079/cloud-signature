package com.peony.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hk
 * @date 2019/11/19
 */
@SpringBootApplication
@ImportResource(locations = {"classpath*:applicationContext-cache-aop.xml"})
public class CloudSignatureApplication  implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(CloudSignatureApplication.class, args);
    }

}

