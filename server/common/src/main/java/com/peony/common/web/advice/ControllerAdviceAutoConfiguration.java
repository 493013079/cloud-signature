package com.peony.common.web.advice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author 辛毅
 * @date 2019/11/23
 */
@Configuration
@ComponentScan
@ConditionalOnWebApplication
@ConditionalOnClass(ControllerAdvice.class)
public class ControllerAdviceAutoConfiguration {

}
