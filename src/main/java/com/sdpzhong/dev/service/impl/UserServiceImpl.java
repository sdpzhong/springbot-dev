package com.sdpzhong.dev.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdpzhong.dev.common.HttpReturnCode;
import com.sdpzhong.dev.entity.dto.UserLoginFormDto;
import com.sdpzhong.dev.entity.dto.UserRegisterFormDto;
import com.sdpzhong.dev.entity.po.User;
import com.sdpzhong.dev.entity.vo.UserLoginResponseVo;
import com.sdpzhong.dev.http.BusinessException;
import com.sdpzhong.dev.mapper.UserMapper;
import com.sdpzhong.dev.service.UserService;
import com.sdpzhong.dev.utils.PasswordCipher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;

/**
 * @author zhongqing
 * @description 针对表【t_user(用户表)】的数据库操作Service实现
 * @createDate 2024-07-15 17:46:44
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public UserLoginResponseVo userLogin(UserLoginFormDto userLoginForm) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper
                .select(User::getPassword, User::getPasswordSalt, User::getUid, User::getUsername)
                .eq(User::getUsername, userLoginForm.getUsername());

        User user = getOne(queryWrapper);

        if (user == null) {
            throw new BusinessException(HttpReturnCode.RC_NO_REGISTERED);
        }

        // 生成密钥进行比对
        if (!PasswordCipher.comparePassword(userLoginForm.getPassword(), user.getPassword(), user.getPasswordSalt())) {
            throw new BusinessException(HttpReturnCode.RC_LOGIN_REJECTED);
        }

        // 存储用户id，并生成 token
        StpUtil.login(user.getUid());

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        SaSession session = StpUtil.getSession();

        return new UserLoginResponseVo()
                .setToken(tokenInfo.tokenValue)
                .setTimeout(tokenInfo.tokenTimeout)
                .setExpires(session.getCreateTime() + tokenInfo.tokenTimeout * 1000);
    }

    @Override
    public boolean userRegister(UserRegisterFormDto registerForm) {
        // 判断用户名是否被注册
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getUsername, registerForm.getUsername());

        if (count(queryWrapper) > 0) {
            throw new BusinessException(HttpReturnCode.RC_ACCOUNT_REGISTERED);
        }

        // 正常注册流程
        User user = new User();

        // 生成加密密码和盐
        String password = registerForm.getPassword();
        byte[] salt = PasswordCipher.generateSalt();
        String hashedPassword = PasswordCipher.hashPassword(password, salt);

        user.setUsername(registerForm.getUsername())
                .setPassword(hashedPassword)
                .setPasswordSalt(Base64.getEncoder().encodeToString(salt))
                .setEmail(registerForm.getEmail());

        return save(user);
    }

    @Override
    public User getUserInfo() {
        // 通过token查询出用户id
        Object userId = StpUtil.getLoginId();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUid, userId);
        User user = getOne(queryWrapper);

        if (user == null) {
            throw new BusinessException(HttpReturnCode.RC_NO_REGISTERED);
        }

        return user;
    }

}





