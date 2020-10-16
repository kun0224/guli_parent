package com.zhao.cmsservice.controller;


import com.zhao.cmsservice.entity.CrmBanner;
import com.zhao.cmsservice.service.CrmBannerService;
import com.zhao.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "banner前台展示")
@RestController
@RequestMapping("/cmsservice/bannerapi")
//@CrossOrigin
public class BannerApiController {

    @Autowired
    CrmBannerService bannerService;

    @ApiOperation(value = "查询所有Banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.getAllBanner();
        return R.ok().data("list", list);
    }
}
