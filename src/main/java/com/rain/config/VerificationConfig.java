package com.rain.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 验证配置
 *
 * @author xueyu
 */
@Slf4j
@Configuration
public class VerificationConfig {

    /**
     * 数据校验
     *
     * @return Validator
     */
    @Bean
    public Validator validator() {
        log.info("配置数据校验器====》");
        try (ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败返回模式：failFast = true
                .failFast(true)
                .buildValidatorFactory()) {
            return validatorFactory.getValidator();
        }
    }
}
