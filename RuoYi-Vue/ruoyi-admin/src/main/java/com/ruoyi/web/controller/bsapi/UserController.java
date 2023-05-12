package com.ruoyi.web.controller.bsapi;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bsapi.domain.User;
import com.ruoyi.bsapi.service.IUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/user")
public class UserController extends BaseController
{
    @Autowired
    private IUserService userService;

    /**
     * 查询用户列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(User user)
    {
        //开始分页
        startPage();
        List<User> list = userService.selectUserList(user);
        //得到table对象
        TableDataInfo dataTable = getDataTable(list);
        //重新设置总条数
        Long total = userService.selectUserCount(user);
        dataTable.setTotal(total);
        return dataTable;
    }

    /**
     * 导出用户列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:user:export')")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, User user)
    {
        List<User> list = userService.selectUserList(user);
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        util.exportExcel(response, list, "用户数据");
    }

    /**
     * 获取用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:user:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(userService.selectUserByUserId(userId));
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('bsapi:user:add')")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody User user)
    {
        try {
            return toAjax(userService.insertUser(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('bsapi:user:edit')")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody User user)
    {
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('bsapi:user:remove')")
    @Log(title = "用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(userService.deleteUserByUserIds(userIds));
    }

    //批量删除
    @PreAuthorize("@ss.hasPermi('bsapi:user:batch:remove')")
    @Log(title = "用户批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/batch/user/delete")
    public AjaxResult batchDel(Long[] ids) {

        return toAjax(userService.batchDel(ids));
    }

}
