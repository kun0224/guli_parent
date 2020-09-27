package com.zhao.eduservice.controller;

import com.zhao.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@Api(description = "登录管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class LoginBackstageController {

    /**
     * {"code":20000,"data":{"token":"admin"}}
     *
     * @return
     */
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    /**
     * {"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
     *
     * @return
     */
    @ApiOperation(value = "用户信息")
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "admin").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
