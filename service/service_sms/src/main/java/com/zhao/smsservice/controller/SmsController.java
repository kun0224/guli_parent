package com.zhao.smsservice.controller;

import com.zhao.commonutils.R;
import com.zhao.smsservice.service.SmsService;
import com.zhao.smsservice.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(description = "短信管理")
@RestController
@RequestMapping("/edumsm/send")
//@CrossOrigin
public class SmsController {

    @Autowired
    SmsService smsService;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value = "根据手机号发送短信")
    @GetMapping("sendSmsPhone/{phone}")
    public R sendSmsPhone(@PathVariable String phone){

        //1 从redis根据手机号获取验证码
        String rphone = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(rphone)){
            return R.ok();
        }

        //2 确认该手机号没有发送验证码，生成验证码
        String code = RandomUtil.getFourBitRandom();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);

        //3 调用接口发送短信
        boolean isSend = smsService.sendSmsPhone(phone,map);

        if (isSend){
            //4 把验证码存入redis
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error();
        }
    }
}
