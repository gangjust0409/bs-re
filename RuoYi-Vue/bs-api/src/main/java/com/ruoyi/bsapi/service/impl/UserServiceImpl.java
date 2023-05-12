package com.ruoyi.bsapi.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.utils.FileUtil;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.utils.TokenUtils;
import com.ruoyi.bsapi.vo.ClientUser;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.my.StringUtils;
import com.ruoyi.common.utils.sign.Md5Utils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.UserMapper;
import com.ruoyi.bsapi.domain.User;
import com.ruoyi.bsapi.service.IUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 用户Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class UserServiceImpl implements IUserService 
{
    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //@Resource
    //private JavaMailSenderImpl javaMailSender;

    /**
     * 查询用户
     * 
     * @param userId 用户主键
     * @return 用户
     */
    @Override
    public User selectUserByUserId(Long userId)
    {
        return userMapper.selectUserByUserId(userId);
    }

    /**
     * 查询用户列表
     * 
     * @param user 用户
     * @return 用户
     */
    @Override
    public List<User> selectUserList(User user) {
        List<User> users = userMapper.selectUserList(user);
        List<User> userList = users.stream().map(u -> {
            u.setUserPic(FileUtil.LOCALHOST_SERVER_UPLOAD + u.getUserPic());
            return u;
        }).collect(Collectors.toList());
        return userList;
    }

    /**
     * 新增用户
     * 
     * @param user 用户
     * @return 结果
     */
    @Override
    public int insertUser(User user) throws Exception {
        user.setUserPic(StringUtils.generateDefaultPic());
        user.setAccount("tb_" + StringUtils.generateNum9());
        user.setCreateDate(new Date());
        return userMapper.insertUser(user);
    }

    /**
     * 修改用户
     * 
     * @param user 用户
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        return userMapper.updateUser(user);
    }

    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserById(user);
    }

    /**
     * 批量删除用户
     * 
     * @param userIds 需要删除的用户主键
     * @return 结果
     */
    @Override
    public int deleteUserByUserIds(Long[] userIds)
    {
        return userMapper.deleteUserByUserIds(userIds);
    }

    /**
     * 删除用户信息
     * 
     * @param userId 用户主键
     * @return 结果
     */
    @Override
    public int deleteUserByUserId(Long userId){

        return userMapper.deleteUserByUserId(userId);
    }

    @Transactional
    @Override
    public int register(User user) throws Exception {
        //密码加密
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newPassword = passwordEncoder.encode(user.getPassword());
        //重新设置进去
        user.setPassword(newPassword);
        //分配默认用户数据
        user.setAccount("tb_"+StringUtils.generateNum9());
        user.setCreateDate(new Date());
        user.setSex(3L);
        user.setUserPic(StringUtils.generateDefaultPic());
        //保存
        int i = this.insertUser(user);
        if (i == 0) {
            throw new Exception("数据库保存失败！");
        }
        return i;
    }

    @Override
    public Long selectUserCount(User user) {
        return userMapper.selectUserCount(user);
    }

    @Override
    public int batchDel(Long[] ids) {
        return userMapper.deleteUserByUserIds(ids);
    }

    @Override
    public String clientLogin(ClientUser user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String token = null;
        String phoneEmail = user.getPhoneEmail();
        User userParams = new User();
        //判断需不需要验证码登录
        if (StringUtils.isNotNull(user.getAccount())) {
            //账号登录
            userParams.setAccount(user.getAccount());
            userParams.setNickName(user.getAccount());
        } else {
            //邮箱、手机号登录
            if (phoneEmail.indexOf("@") != -1) {
                //邮箱
                //根据邮箱进行查询
                userParams.setEmail(phoneEmail);
            } else {
                //TODO 手机号
            }
        }
        //查询
        List<User> users = this.selectUserList(userParams);
        if (!CollectionUtils.isEmpty(users)) {
            //获取验证码
            String code = stringRedisTemplate.opsForValue().get(RedisConsant.VALIDATE_CODE_KEY+phoneEmail);
            if (!StringUtils.isNotNull(code)) {

            }
            //获取到查询到的对象
            User currentUser = users.get(0);
            //密码判断
            if ((!StringUtils.isNotNull(user.getPassword())
                    || passwordEncoder.matches(user.getPassword(), currentUser.getPassword())) ||
                    (users.size() == 1 && StringUtils.isNotNull(phoneEmail)))
                //邮箱登录也需要进入此
            //生成一个token
            token = TokenUtils.createClientToken(currentUser);
            String json = JSON.toJSONString(currentUser);
            stringRedisTemplate.opsForValue().setIfAbsent(RedisConsant.CLIENT_CURRENT_USER+token, json, 3L, TimeUnit.DAYS);
        }
        return token;
    }

    @Override
    public User getUserInfoByToken(String token) {
        //获取redis中的数据
        String userStr = stringRedisTemplate.opsForValue().get(RedisConsant.CLIENT_CURRENT_USER + token);
        if (StringUtils.isNotNull(userStr)) {
            //转为用户对象
            User user = JSON.parseObject(userStr, User.class);
            //查询最新的信息
            User currentUser = userMapper.selectUserByUserId(user.getUserId());
            currentUser.setUserPic(FileUtil.LOCALHOST_SERVER_UPLOAD+currentUser.getUserPic());
            return currentUser;
        }
        return null;
    }

    @Override
    public boolean validateAccountUnique(String account) {
        User userParams = new User();
        userParams.setAccount(account);
        List<User> users = this.selectUserList(userParams);
        if (CollectionUtils.isEmpty(users)) {
            return false;
        }
        return true;
    }

    @Override
    public void uploadUserPic(String token, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                //获取当前用户
                User currenetUser = RedisConsant.getUser(stringRedisTemplate, token);
                //获取最新的用户信息，防止redis没有同步
                User user = userMapper.selectUserByUserId(currenetUser.getUserId());
                String originalFilename = file.getOriginalFilename();
                String prefix = com.ruoyi.common.utils.my.FileUtil.getPrefix(originalFilename);
                String newName = StringUtils.randomUUID() + "." + prefix;
                //获取路径
                String profile = RuoYiConfig.getProfile();
                File dirFile = new File(profile, newName);
                InputStream is = file.getInputStream();
                com.ruoyi.common.utils.my.FileUtil.transformTo(is, dirFile);
                user.setUserPic(newName);
                //修改数据
                userMapper.updateUser(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean validateEmail(String email) {
        User userParams = new User();
        userParams.setEmail(email);
        List<User> users = userMapper.selectUserList(userParams);
        if (!CollectionUtils.isEmpty(users)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean validateUsername(String username) {
        User userParams = new User();
        userParams.setNickName(username);
        List<User> users = userMapper.selectUserList(userParams);
        if (!CollectionUtils.isEmpty(users)) {
            return true;
        }
        return false;
    }
}
