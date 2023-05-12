package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.User;

/**
 * 用户Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface UserMapper 
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
    public int insertUser(User user);

    /**
     * 修改用户
     * 
     * @param user 用户
     * @return 结果
     */
    public int updateUser(User user);
    public int updateUserById(User user);

    /**
     * 删除用户
     * 
     * @param userId 用户主键
     * @return 结果
     */
    public int deleteUserByUserId(Long userId);

    /**
     * 批量删除用户
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserByUserIds(Long[] userIds);

    /**
     * 获取用户总条数
     * @param user
     * @return
     */
    Long selectUserCount(User user);

}
