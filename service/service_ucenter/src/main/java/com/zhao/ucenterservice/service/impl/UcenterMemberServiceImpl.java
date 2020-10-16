package com.zhao.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.baseservice.exception.GuliException;
import com.zhao.commonutils.utils.JwtUtils;
import com.zhao.ucenterservice.entity.UcenterMember;
import com.zhao.ucenterservice.entity.Vo.LoginVo;
import com.zhao.ucenterservice.entity.Vo.RegisterVo;
import com.zhao.ucenterservice.mapper.UcenterMemberMapper;
import com.zhao.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.ucenterservice.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-10-12
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 会员注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //1.取出参数
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //2.验证参数不为空
        if (StringUtils.isEmpty(nickname)||StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)||StringUtils.isEmpty(code)){
            throw new GuliException(20001, "注册参数有误");
        }

        //3.根据手机号验证是否注册过
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenter = baseMapper.selectOne(wrapper);
        if (ucenter!=null){
            throw new GuliException(20001, "手机号不能重复注册");
        }

        //4.根据手机号从redis获取验证码进行比较
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001, "验证码有误");
        }

        //5.加密密码
        String passwordMD5 = MD5.encrypt(password);
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setNickname(nickname);
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(passwordMD5);

        //6.补全会员信息
        ucenterMember.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        ucenterMember.setIsDisabled(false);

        //7.插入数据库
        baseMapper.insert(ucenterMember);

    }

    /**
     * 会员登录
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        //1.取出参数
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //2.验证参数不为空
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001, "用户手机号或密码有误");
        }

        //3.验证会员是否存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucnter = baseMapper.selectOne(wrapper);
        if (ucnter==null){
            throw new GuliException(20001, "用户手机号或密码有误");
        }

        //4.验证密码
        String passwordMD5 = MD5.encrypt(password);
        if (!passwordMD5.equals(ucnter.getPassword())){
            throw new GuliException(20001, "用户手机号或密码有误");
        }

        //5.生成token字符串
        String token = JwtUtils.getJwtToken(ucnter.getId(), ucnter.getNickname());

        return token;
    }


}
