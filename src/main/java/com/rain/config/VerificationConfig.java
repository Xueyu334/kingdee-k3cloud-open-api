package com.rain.config;

import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;


/**
 * 数据校验配置类。
 * 该类负责配置并提供一个基于Hibernate Validator的数据校验器Bean，该校验器启用了快速失败模式。
 * 快速失败模式意味着在校验过程中，一旦发现第一个校验失败，就会立即停止后续校验并返回错误信息，从而提高校验效率。
 * 该配置类使用Spring的配置注解，确保校验器能够被Spring容器正确管理和注入。
 *
 * @author xueyu
 */
@Slf4j
@Configuration
public class VerificationConfig {

    /**
     * 配置并返回一个数据校验器Bean。
     * 该校验器使用Hibernate Validator作为提供者，并启用快速失败模式。
     * 快速失败模式意味着在校验过程中，一旦发现第一个约束违反，校验将立即停止并返回结果，而不会继续检查后续约束。
     * 这有助于提高校验性能，特别是在处理复杂对象或大量校验规则时。
     *
     * @return 配置好的Validator实例，用于执行数据校验。
     */
    @Bean
    public Validator validator() {
        log.info("配置数据校验器====》");
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        Map<String, String> properties = new HashMap<>();
        // 快速失败返回模式：failFast = true
        properties.put("hibernate.validator.fail_fast", "true");
        factoryBean.setValidationPropertyMap(properties);
        return factoryBean;
    }
}
