package com.wik.util.sms;

/**
 * SmsConstants
 *
 * @author lky
 * @version 1.0.0
 * @description 短信工具类常量
 * @date 2021/8/24 17:47
 */
public final class SmsConstants {

    private SmsConstants() {
    }

    /**
     * 阿里云-SMS-Domain
     */
    public static final String SMS_SYS_DOMAIN = "dysmsapi.aliyuncs.com";


    /**
     * 阿里云-短信-Action-单次
     */
    public static final String SMS_SYS_ACTION_NORMAL = "SendSms";

    /**
     * 阿里云-短信-Action-批量
     */
    public static final String SMS_SYS_ACTION_PATH = "SendBatchSms";
}
