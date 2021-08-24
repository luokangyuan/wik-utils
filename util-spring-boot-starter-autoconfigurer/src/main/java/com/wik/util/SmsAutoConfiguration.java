package com.wik.util;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * SmsAutoConfiguration
 *
 * @author lky
 * @version 1.0.0
 * @description 阿里云短信自动配置
 * @date 2021/8/24 18:09
 */
@Configuration
@ConditionalOnClass(name = "com.aliyuncs.IAcsClient")
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {
}
