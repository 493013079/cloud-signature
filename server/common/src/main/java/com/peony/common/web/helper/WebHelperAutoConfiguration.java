package com.peony.common.web.helper;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 辛毅
 * @date 2019/11/23
 */
@Configuration
@ComponentScan
@ConditionalOnWebApplication
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class WebHelperAutoConfiguration {

}
