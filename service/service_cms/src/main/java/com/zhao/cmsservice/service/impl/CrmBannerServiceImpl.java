package com.zhao.cmsservice.service.impl;

import com.zhao.cmsservice.entity.CrmBanner;
import com.zhao.cmsservice.mapper.CrmBannerMapper;
import com.zhao.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-10-09
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 查询所有banner
     * @return
     */
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getAllBanner() {
        List<CrmBanner> crmBannerList = baseMapper.selectList(null);
        return crmBannerList;
    }
}
