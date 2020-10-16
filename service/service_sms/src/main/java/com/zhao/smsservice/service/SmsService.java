package com.zhao.smsservice.service;

import org.springframework.stereotype.Service;

import java.util.Map;


public interface SmsService {
    boolean sendSmsPhone(String phone, Map<String, String> map);
}
