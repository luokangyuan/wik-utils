package com.wik.util.sms;

/**
 * SmsException
 *
 * @author lky
 * @version 1.0.0
 * @description 自定义短信异常信息
 * @date 2021/8/24 17:27
 */
public class SmsException extends RuntimeException{


    public SmsException(final String message) {
        super(message);
    }


    public SmsException(final Throwable cause) {
        super(cause);
    }
}
