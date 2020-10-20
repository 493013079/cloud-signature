package com.peony.data.config;

import com.peony.common.web.helper.AuthHelper;
import com.peony.data.entity.UserPO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * @author 辛毅
 * @date 2019/11/23
 */
@Configuration
@ComponentScan("com.peony.data")
@EntityScan("com.peony.data.entity")
@EnableJpaRepositories("com.peony.data.repository")
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class TemplateDataAutoConfiguration {

    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }

    @Bean
    @ConditionalOnBean(AuthHelper.class)
    public AuditorAware<UserPO> auditorProvider(AuthHelper authHelper) {
        return new AuditorAwareImpl(authHelper);
    }

}
