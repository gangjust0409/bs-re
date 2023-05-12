package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.User;
import com.ruoyi.bsapi.vo.ClientUser;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface IUserService 
{
    /**
     * 查询用户
     * 
     * @param userId 用户主键
     * @return 用户
     */
    public User selectUserByUserId(Long userId);

    /**
     * 查询用户列表
     * 
     * @param user 用户
     * @return 用户集合
     */
    public List<User> selectUserList(User user);

    /**
     * 新增用户
     * 
     * @param user 用户
     * @return 结果
     */
    public int insertUser(User user) throws Exception;

    /**
     * 修改用户
     * 
     * @param user 用户
     * @return 结果
     */
    public int updateUser(User user);

    public int updateUserById(User user);

    /**
     * 批量删除用户
     * 
     * @param userIds 需要删除的用户主键集合
     * @return 结果
     */
    public int deleteUserByUserIds(Long[] userIds);

    /**
     * 删除用户信息
     * 
     * @param userId 用户主键
     * @return 结果
     */
    public int deleteUserByUserId(Long userId);

    /**
     * 注册用户
     * @param user
     */
    int register(User user) throws Exception;

    /**
     * 获取总条数
     * @param user
     * @return
     */
    Long selectUserCount(User user);

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    int batchDel(Long[] ids);

    /**
     * 客户端登录
     * @param user
     * @return
     */
    String clientLogin(ClientUser user);

    /**
     * 根据登录后获取当前用户信息
     * @param token
     * @return
     */
    User getUserInfoByToken(String token);

    /**
     * 验证账号唯一
     * @param account
     * @return
     */
    boolean validateAccountUnique(String account);

    /**
     * 上传头像
     * @param token
     * @param file
     */
    void uploadUserPic(String token, MultipartFile file);

    /**
     * 发送邮件之前，验证邮箱是否存在
     * @param email
     * @return
     */
    boolean validateEmail(String email);

    /**
     * 注册时，验证用户名
     * @param username
     * @return
     */
    boolean validateUsername(String username);
}
