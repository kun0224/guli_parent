package com.zhao.ucenterservice.service;

import com.zhao.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.ucenterservice.entity.Vo.LoginVo;
import com.zhao.ucenterservice.entity.Vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zhao
 * @since 2020-10-12
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);
}
