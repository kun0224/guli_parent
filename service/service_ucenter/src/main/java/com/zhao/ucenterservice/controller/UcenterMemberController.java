package com.zhao.ucenterservice.controller;


import com.zhao.commonutils.R;
import com.zhao.commonutils.utils.JwtUtils;
import com.zhao.ucenterservice.entity.UcenterMember;
import com.zhao.ucenterservice.entity.Vo.LoginVo;
import com.zhao.ucenterservice.entity.Vo.RegisterVo;
import com.zhao.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-10-12
 */
@Api(description = "会员管理")
@RestController
@RequestMapping("/ucenterservice/ucenter-member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    UcenterMemberService memberService;

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return R.ok().data("token",token);
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = memberService.getById(memberId);
        return R.ok().data("ucenterMember", ucenterMember);
    }
}

