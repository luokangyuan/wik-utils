package com.wik.util.sms;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonResponse;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * SmsUtils
 *
 * @author lky
 * @version 1.0.0
 * @description 短信相关工具类
 * @date 2021/8/24 16:16
 */
public final class SmsUtils {

    private SmsUtils() {}

    private static final String PHONE_NUMBER_REGEX = "\\d{5,}";

    private static final String SUCCESS_CODE = "OK";

    /**
     * randomCode
     *
     * @description 生成6位随机短信验证码
     * @return 6位随机短信验证码
     * @author luokangyuan
     * @date 2021/8/24 17:03
     * @version 1.0.0
     */
    static int randomCode() {
        return 100_000 + ThreadLocalRandom.current().nextInt(1_000_000 - 100_000);
    }

    /**
     * checkNotEmpty
     *
     * @description 字符非空校验
     * @param str 需要校验的字符串
     * @param message 提示信息
     * @author luokangyuan
     * @date 2021/8/24 16:23
     * @version 1.0.0
     */
    static void checkNotEmpty(final String str, final String message) {
        if (null == str || str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * checkNotEmpty
     *
     * @description 校验集合不为空
     * @param coll 集合
     * @param message 提示信息
     * @author luokangyuan
     * @date 2021/8/24 17:22
     * @version 1.0.0
     */
    static void checkNotEmpty(final Collection coll, final String message) {
        if (null == coll || coll.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * checkPhoneNumber
     *
     * @description 简单校验电话号码
     * @param phoneNumber 需要校验的电话号码
     * @author luokangyuan
     * @date 2021/8/24 16:25
     * @version 1.0.0
     */
    static void checkPhoneNumber(final String phoneNumber) {
        if (null == phoneNumber || !phoneNumber.matches(PHONE_NUMBER_REGEX)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    /**
     * toJsonStr
     *
     * @description map2json的简单实现
     * @param map map
     * @return json字符串
     * @author luokangyuan
     * @date 2021/8/24 17:24
     * @version 1.0.0
     */
    static String toJsonStr(final Map<String, String> map) {
        if (null == map || map.isEmpty()) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            sb.append('"')
                    .append(entry.getKey().replace("\"", "\\\""))
                    .append('"')
                    .append(':')
                    .append('"')
                    .append(entry.getValue().replace("\"", "\\\""))
                    .append('"')
                    .append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append('}');
        return sb.toString();
    }

    /**
     * checkSmsResponse
     *
     * @description  校验 SendSmsResponse 状态
     * @param response response
     * @author luokangyuan
     * @date 2021/8/24 17:25
     * @version 1.0.0
     */
    static void checkSmsResponse(final CommonResponse response) {
        if (null == response) {
            throw new SmsException("Response is null");
        }
        final Map<String, String> json = JSON.parseObject(response.getData(), Map.class);
        if (!SUCCESS_CODE.equalsIgnoreCase(json.get("Code"))) {
            throw new SmsException("Http status: " + response.getHttpStatus() + ", response: " + response.getData());
        }
    }

    /**
     * checkSmsTemplate
     *
     * @description 校验模板信息
     * @param template 短信模板
     * @author luokangyuan
     * @date 2021/8/24 17:21
     * @version 1.0.0
     */
    static void checkSmsTemplate(final SmsTemplate template) {
        checkNotEmpty(template.getSignName(), "SmsTemplate signName must be not empty");
        checkNotEmpty(template.getTemplateCode(), "SmsTemplate templateCode must be not empty");
        checkNotEmpty(template.getPhoneNumbers(), "SmsTemplate phoneNumbers must be not empty");
    }

    /**
     * checkBatchSmsTemplate
     *
     * @description 校验批量短信模板
     * @param template 短信模板信息
     * @author luokangyuan
     * @date 2021/8/24 17:42
     * @version 1.0.0
     */
    static void checkBatchSmsTemplate(final BatchSmsTemplate template) {
        checkNotEmpty(template.getSignNames(), "BatchSmsTemplate signNames must be not empty");
        checkNotEmpty(template.getPhoneNumbers(), "BatchSmsTemplate phoneNumbers must be not empty");
        checkNotEmpty(template.getTemplateCode(), "BatchSmsTemplate templateCode must be not empty");
        checkNotEmpty(template.getTemplateParams(), "BatchSmsTemplate templateParams must be not empty");
        if (template.getSignNames().size() != template.getPhoneNumbers().size()
                && template.getPhoneNumbers().size() != template.getTemplateParams().size()) {
            throw new IllegalArgumentException("BatchSmsTemplate phoneNumbers, signNames, templateParams size must be the same");
        }
    }
}

