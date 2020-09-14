package com.peony.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hk
 * @date 2019/11/19
 */
@SpringBootApplication
/*@EnableWebMvc*/
public class CloudSignatureApplication  implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(CloudSignatureApplication.class, args);
    }

   /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }*/

}
