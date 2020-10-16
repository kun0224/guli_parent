package com.zhao.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.cmsservice.entity.CrmBanner;
import com.zhao.cmsservice.service.CrmBannerService;
import com.zhao.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-10-09
 */
@Api(description = "banner管理")
@RestController
@RequestMapping("/cmsservice/crm-banner")
//@CrossOrigin
public class CrmBannerController {

    @Autowired
    CrmBannerService bannerService;

    @ApiOperation(value = "分页查询所有banner")
    @GetMapping("getPageBanner/{page}/{limit}")
    public R getPageBanner(@PathVariable Long page, @PathVariable Long limit){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        bannerService.page(bannerPage, null);
        List<CrmBanner> records = bannerPage.getRecords();
        long total = bannerPage.getTotal();
        return R.ok().data("records",records).data("total",total);
    }

    @ApiOperation(value = "根据id删除banner")
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id){
        bannerService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "新增banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "根据id查询banner")
    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable String id){
        CrmBanner crmBanner = bannerService.getById(id);
        return R.ok().data("crmBanner",crmBanner);
    }

    @ApiOperation(value = "更新banner")
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return R.ok();
    }

}

