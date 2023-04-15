package com.andyron.educenter.service.impl;

import com.andyron.common.utils.JwtUtils;
import com.andyron.common.utils.MD5;
import com.andyron.educenter.entity.UcenterMember;
import com.andyron.educenter.entity.vo.RegisterVo;
import com.andyron.educenter.mapper.UcenterMemberMapper;
import com.andyron.educenter.service.UcenterMemberService;
import com.andyron.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author andyron
 * @since 2023-04-06
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null) {
            throw new GuliException(20001, "登录失败");
        }
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new GuliException(20001, "登录失败");
        }
        // 判断用户是否被禁用
        if (ucenterMember.getIsDisabled()) {
            throw new GuliException(20001, "登录失败");
        }
        return JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) ||
        StringUtils.isEmpty(code) || StringUtils.isEmpty(password)) {
            throw  new GuliException(20001, "注册失败");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "注册失败");
        }
        // 相同手机不能注册
        LambdaQueryWrapper<UcenterMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UcenterMember::getMobile, mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "注册失败");
        }

        // 添加数据到数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setIsDisabled(false);
        // TODO 默认头像写到配置文件中
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        member.setPassword(MD5.encrypt(password));
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        LambdaQueryWrapper<UcenterMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UcenterMember::getOpenid, openid);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
