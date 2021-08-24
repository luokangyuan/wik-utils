package com.wik.util;

import com.wik.util.sms.SmsTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;


/**
 * SmsProperties
 *
 * @author lky
 * @version 1.0.0
 * @description 阿里云配置属性
 * @date 2021/8/24 18:09
 */
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties implements InitializingBean {


    /**
     * 阿里云-accessKeyId
     */
    private String accessKeyId;

    /**
     * 阿里云-accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 阿里云-signName
     */
    private String signName;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null != this.templates) {
            for (final SmsTemplate smsTemplate : this.templates.values()) {
                if (null == smsTemplate.getSignName()) {
                    smsTemplate.setSignName(this.signName);
                }
            }
        }
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Map<String, SmsTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, SmsTemplate> templates) {
        this.templates = templates;
    }

    private Map<String, SmsTemplate> templates;


}
