package com.zhao.wxloginservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.zhao.baseservice.exception.GuliException;
import com.zhao.commonutils.utils.JwtUtils;
import com.zhao.wxloginservice.entity.UcenterMember;
import com.zhao.wxloginservice.service.UcenterMemberService;
import com.zhao.wxloginservice.utils.ConstantPropertiesUtil;
import com.zhao.wxloginservice.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sun.awt.SunHints;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-10-13
 */
@Api(description = "微信登录")
@Controller
@RequestMapping("/api/ucenter/wx")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "微信生成二维码")
    @GetMapping("login")
    public String login() {
        //1直接拼写url
        //https://open.weixin.qq.com/connect/qrconnect?
        // appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
        //2利用占位符（%s）拼写url
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "wxzhao");

        return "redirect:" + qrcodeUrl;
    }

    @ApiOperation(value = "扫二维码后回调")
    @GetMapping("callback")
    public String callback(String code, String state){

        try {
            //1.获取参数
            System.out.println("code="+code);
            System.out.println("state="+state);

            //2向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);

            //3请求固定地址，拿到返回的json字符串
            String result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("result ="+result);

            //4把json串转化成map
            Gson gson = new Gson();
            HashMap<String, Object> map = gson.fromJson(result, HashMap.class);
            String accessToken =(String) map.get("access_token");
            String openid =(String) map.get("openid");
            System.out.println("access_token ="+accessToken);
            System.out.println("openid ="+openid);

            //5根据accessToken、openid访问微信地址，获取userinfo
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = HttpClientUtils.get(userInfoUrl);
            System.out.println("resultUserInfo ="+resultUserInfo);
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String)mapUserInfo.get("nickname");
            String headimgurl = (String)mapUserInfo.get("headimgurl");

            System.out.println("nickname="+nickname);
            System.out.println("headimgurl="+headimgurl);
            //6判断用户是否已注册
            QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
            wrapper.eq("openid",openid);
            UcenterMember amember = memberService.getOne(wrapper);
            if(amember==null){
                //6.1注册用户
                amember = new UcenterMember();
                amember.setOpenid(openid);
                amember.setAvatar(headimgurl);
                amember.setNickname(nickname);
                amember.setIsDisabled(false);
                memberService.save(amember);
            }

            //6.1登录
            String token = JwtUtils.getJwtToken(amember.getId(),amember.getNickname());

            //重定向
            return "redirect:http://localhost:3000?token="+token;
        } catch (Exception e) {
            throw new GuliException(20001,"微信扫码登录失败"+e.getMessage());
        }

    }
}

