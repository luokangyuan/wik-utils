package com.wik.util.sms;

import java.util.List;
import java.util.Map;

/**
 * BatchSmsTemplate
 *
 * @author lky
 * @version 1.0.0
 * @description 批量短信
 * @date 2021/8/24 17:47
 */
public class BatchSmsTemplate {

    private List<String> signNames;

    private String templateCode;

    private List<Map<String, String>> templateParams;

    private List<String> phoneNumbers;

    public List<String> getSignNames() {
        return signNames;
    }

    public void setSignNames(List<String> signNames) {
        this.signNames = signNames;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public List<Map<String, String>> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(List<Map<String, String>> templateParams) {
        this.templateParams = templateParams;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
