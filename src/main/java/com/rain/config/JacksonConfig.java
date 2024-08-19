package com.rain.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * jackson json配置
 *
 * @author xueyu
 */
@Slf4j
@Configuration
public class JacksonConfig {

    /**
     * Date格式化字符串
     * yyyy-MM-dd
     */
    private static final String DATE_FORMAT = DatePattern.NORM_DATE_PATTERN;
    /**
     * DateTime格式化字符串
     * yyyy-MM-dd HH:mm:ss
     */
    private static final String DATETIME_FORMAT = DatePattern.NORM_DATETIME_PATTERN;
    /**
     * Time格式化字符串
     * HH:mm:ss
     */
    private static final String TIME_FORMAT = DatePattern.NORM_TIME_PATTERN;

    /**
     * jackson 配置
     *
     * @return {@link Jackson2ObjectMapperBuilderCustomizer}
     */
    public static Jackson2ObjectMapperBuilderCustomizer getJackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(dateTimeFormatter);
            LocalDateSerializer dateSerializer = new LocalDateSerializer(dateFormatter);
            LocalTimeSerializer timeSerializer = new LocalTimeSerializer(timeFormatter);
            LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(dateTimeFormatter);
            LocalDateDeserializer dateDeserializer = new LocalDateDeserializer(dateFormatter);
            LocalTimeDeserializer timeDeserializer = new LocalTimeDeserializer(timeFormatter);
            builder.serializers(dateTimeSerializer, dateSerializer, timeSerializer);
            builder.deserializers(dateTimeDeserializer, dateDeserializer, timeDeserializer);
            //格式化日期时使用的时区
            builder.timeZone("GMT+8");
            //用于格式化的区域设置
            builder.locale("zh_CN");
            //日期格式字符串或完全限定的日期格式类名称。例如，'yyyy-mm-dd HH: MM: ss'。
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //long类型序列号处理 对于超大类型的数字 使用字符串序列返回
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            //对象不含任何字段时是否报错
            builder.failOnEmptyBeans(false);
            //遇到未知属性 (不映射到属性的属性，并且没有可以处理它的 “任何setter” 或处理程序) 静默处理
            builder.failOnUnknownProperties(false);
            //循环引用报错
            builder.featuresToDisable(SerializationFeature.FAIL_ON_SELF_REFERENCES);
            //捕获并且包装异常信息
            builder.featuresToEnable(SerializationFeature.WRAP_EXCEPTIONS);
            //字符数组输出json数组
            builder.featuresToEnable(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS);
            //枚举值的标准序列化机制 是否使用Enum.toString() 的返回值
            builder.featuresToDisable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            //在输入中遇到已显式标记为可忽略的属性时,静默处理
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            //控制是否启用对注解的支持
            builder.featuresToEnable(MapperFeature.USE_ANNOTATIONS);
            //控制是否允许使用getter方法来进行反序列化,通常用于那些没有提供显式 setter方法的类
            builder.featuresToEnable(MapperFeature.USE_GETTERS_AS_SETTERS);
            //使用字符串表示其精确值BigDecimal
            builder.featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
            //忽略JSON 中未在 Java 类中定义的属性
            builder.featuresToEnable(JsonGenerator.Feature.IGNORE_UNKNOWN);
            // 定义在序列化过程中如何处理 Java 对象中的默认属性（即没有任何注解指定的属性）,该值指示将始终包含属性，而与属性的值无关
            builder.serializationInclusion(JsonInclude.Include.ALWAYS);
        };
    }


    /**
     * jackson客制化配置信息
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        log.info("personalization configuration jackson2ObjectMapperBuilderCustomizer");
        return getJackson2ObjectMapperBuilderCustomizer();
    }


}
