package com.wik.util.sms;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static com.wik.util.sms.SmsUtils.checkBatchSmsTemplate;
import static com.wik.util.sms.SmsUtils.checkNotEmpty;
import static com.wik.util.sms.SmsUtils.checkPhoneNumber;
import static com.wik.util.sms.SmsUtils.checkSmsResponse;
import static com.wik.util.sms.SmsUtils.checkSmsTemplate;

/**
 * AliYunSmsClient
 *
 * @author lky
 * @version 1.0.0
 * @description 阿里云短信发送客户端
 * @date 2021/8/24 15:54
 */
public class AliYunSmsClient {

    private final IAcsClient acsClient;
    private final Map<String, SmsTemplate> smsTemplates;

    private static final String SMS_TEMPLATE_NOT_NULL = "SmsTemplate must be not null, key:";

    /**
     * AliYunSmsClient
     *
     * @param accessKeyId     accessKeyId
     * @param accessKeySecret accessKeySecret
     * @description 初始化阿里云配置信息
     * @author luokangyuan
     * @date 2021/8/24 16:12
     * @version 1.0.0
     */
    public AliYunSmsClient(final String accessKeyId, final String accessKeySecret) {
        this(accessKeyId, accessKeySecret, Collections.emptyMap());
    }

    /**
     * AliYunSmsClient
     *
     * @param accessKeyId     阿里云短信 accessKeyId
     * @param accessKeySecret 阿里云短信 accessKeySecret
     * @param smsTemplates    预置短信模板
     * @description 实例化一个新的AliYunSmsClient
     * @author luokangyuan
     * @date 2021/8/24 16:14
     * @version 1.0.0
     */
    public AliYunSmsClient(final String accessKeyId,
                           final String accessKeySecret,
                           final Map<String, SmsTemplate> smsTemplates) {
        checkNotEmpty(accessKeyId, "'accessKeyId' must be not empty");
        checkNotEmpty(accessKeySecret, "'accessKeySecret' must be not empty");

        final IClientProfile clientProfile = DefaultProfile.getProfile(
                "default", accessKeyId, accessKeySecret);
        this.acsClient = new DefaultAcsClient(clientProfile);
        this.smsTemplates = smsTemplates;
    }

    /**
     * AliYunSmsClient
     *
     * @param acsClient    IAcsClient
     * @param smsTemplates 预置短信模板
     * @description Instantiates a new AliYunSmsClient.
     * @author luokangyuan
     * @date 2021/8/24 16:21
     * @version 1.0.0
     */
    public AliYunSmsClient(final IAcsClient acsClient, final Map<String, SmsTemplate> smsTemplates) {
        this.acsClient = acsClient;
        this.smsTemplates = smsTemplates;
    }

    /**
     * sendVerificationCode
     *
     * @param smsTemplateKey 预置短信模板key
     * @param phoneNumber    手机号码(中国)
     * @return 6为短信验证码
     * @description 发送短信验证码.
     * @author luokangyuan
     * @date 2021/8/24 16:22
     * @version 1.0.0
     */
    public int sendVerificationCode(final String smsTemplateKey, final String phoneNumber) {
        checkPhoneNumber(phoneNumber);
        final SmsTemplate smsTemplate = this.smsTemplates.get(smsTemplateKey);
        Objects.requireNonNull(smsTemplate, () -> SMS_TEMPLATE_NOT_NULL + smsTemplateKey);

        final int code = SmsUtils.randomCode();
        smsTemplate.setTemplateParam(Collections.singletonMap("code", String.valueOf(code)));
        smsTemplate.setPhoneNumbers(Collections.singletonList(phoneNumber));
        send(smsTemplate);
        return code;
    }

    /**
     * send
     *
     * @param smsTemplateKey 预置短信模板 key
     * @description 发送短信
     * @author luokangyuan
     * @date 2021/8/24 17:10
     * @version 1.0.0
     */
    public void send(final String smsTemplateKey) {
        final SmsTemplate smsTemplate = this.smsTemplates.get(smsTemplateKey);
        Objects.requireNonNull(smsTemplate, () -> SMS_TEMPLATE_NOT_NULL + smsTemplateKey);
        send(smsTemplate);
    }

    /**
     * send
     *
     * @param smsTemplateKey 预置短信模板 key
     * @param phoneNumbers   电话号码，优先于预置短信模板中配置的手机号码
     * @description 发送短信
     * @author luokangyuan
     * @date 2021/8/24 16:35
     * @version 1.0.0
     */
    public void send(final String smsTemplateKey, final String... phoneNumbers) {
        final SmsTemplate smsTemplate = this.smsTemplates.get(smsTemplateKey);
        Objects.requireNonNull(smsTemplate, () -> SMS_TEMPLATE_NOT_NULL + smsTemplateKey);
        smsTemplate.setPhoneNumbers(Arrays.asList(phoneNumbers));
        send(smsTemplate);
    }

    /**
     * send
     *
     * @param smsTemplate 短信魔板
     * @description 发送短信
     * @author luokangyuan
     * @date 2021/8/24 16:37
     * @version 1.0.0
     */
    public void send(final SmsTemplate smsTemplate) {
        Objects.requireNonNull(smsTemplate);
        checkSmsTemplate(smsTemplate);
        final var request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(SmsConstants.SMS_SYS_DOMAIN);
        request.setSysVersion("2017-05-25");
        request.setSysAction(SmsConstants.SMS_SYS_ACTION_NORMAL);
        request.putQueryParameter("PhoneNumbers", String.join(",", smsTemplate.getPhoneNumbers()));
        request.putQueryParameter("SignName", smsTemplate.getSignName());
        request.putQueryParameter("TemplateCode", smsTemplate.getTemplateCode());
        request.putQueryParameter("TemplateParam", SmsUtils.toJsonStr(smsTemplate.getTemplateParam()));
        try {
            final var response = this.acsClient.getCommonResponse(request);
            checkSmsResponse(response);
        } catch (final ClientException e) {
            throw new SmsException(e);
        }
    }

    /**
     * send
     *
     * @param batchSmsTemplate 批量发送短信模板
     * @description 批量发送短信
     * @author luokangyuan
     * @date 2021/8/24 16:37
     * @version 1.0.0
     */
    public void send(final BatchSmsTemplate batchSmsTemplate) {
        Objects.requireNonNull(batchSmsTemplate);
        checkBatchSmsTemplate(batchSmsTemplate);
        final var request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(SmsConstants.SMS_SYS_DOMAIN);
        request.setSysVersion("2017-05-25");
        request.setSysAction(SmsConstants.SMS_SYS_ACTION_PATH);
        request.putQueryParameter("PhoneNumberJson", JSON.toJSONString(batchSmsTemplate.getPhoneNumbers()));
        request.putQueryParameter("SignNameJson", JSON.toJSONString(batchSmsTemplate.getSignNames()));
        request.putQueryParameter("TemplateCode", batchSmsTemplate.getTemplateCode());
        request.putQueryParameter("TemplateParamJson", JSON.toJSONString(batchSmsTemplate.getTemplateParams()));
        try {
            final var response = this.acsClient.getCommonResponse(request);
            checkSmsResponse(response);
        } catch (final ClientException e) {
            throw new SmsException(e);
        }
    }
}
