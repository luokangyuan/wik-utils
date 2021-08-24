package com.wik.util.sms;

import java.util.List;
import java.util.Map;

/**
 * SmsTemplate
 *
 * @author lky
 * @version 1.0.0
 * @description 阿里云短信模板
 * @date 2021/8/24 16:11
 */
public class SmsTemplate {

    /** 短信签名*/
    private String signName;

    /** 短信模板code*/
    private String templateCode;

    /** 短信模板参数*/
    private Map<String, String> templateParam;

    /** 电话号码集合*/
    private List<String> phoneNumbers;

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, String> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(Map<String, String> templateParam) {
        this.templateParam = templateParam;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
