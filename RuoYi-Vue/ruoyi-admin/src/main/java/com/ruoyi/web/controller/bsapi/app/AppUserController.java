package com.ruoyi.web.controller.bsapi.app;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.domain.Address;
import com.ruoyi.bsapi.domain.MyCollect;
import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.domain.User;
import com.ruoyi.bsapi.service.IAddressService;
import com.ruoyi.bsapi.service.IMyCollectService;
import com.ruoyi.bsapi.service.IMyHistoryService;
import com.ruoyi.bsapi.service.IUserService;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.AddressUserVo;
import com.ruoyi.bsapi.vo.ClientUser;
import com.ruoyi.bsapi.vo.ProductHistory;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.my.StringUtils;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequestMapping("/app/user")
@RestController
public class AppUserController {

    @Autowired
    private IUserService userService;

    @Resource
    private IAddressService addressService;

    @Resource
    private IMyHistoryService myHistoryService;

    @Resource
    private IMyCollectService myCollectService;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //注册
    @Log(title = "注册用户", businessType = BusinessType.INSERT)
    @PostMapping("/register")
    public R register(@RequestBody User user) {
        try {
            userService.register(user);
        } catch (Exception e) {
            return R.fail("注册出现异常！");
        }
        return R.ok();
    }

    //验证邮箱是否存在
    @GetMapping("/validate/email/{email}")
    public AjaxResult validateEmail(@PathVariable String email){
        boolean hasExists = userService.validateEmail(email);
        return AjaxResult.success(hasExists);
    }

    //注册时，验证用户名
    @GetMapping("/validate/username/{username}")
    public AjaxResult validateUsername(@PathVariable String username){
        boolean hasExists  = userService.validateUsername(username);
        return AjaxResult.success(hasExists);
    }

    //向邮箱发送验证码
    @GetMapping("/email/send")
    @RepeatSubmit
    public AjaxResult sendEmailCode(@RequestParam String email) {
        //一个简单地邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("仿淘宝");
        //生成一个6位数随机验证码
        String code = StringUtils.generateValidateCode();
        stringRedisTemplate.opsForValue().setIfAbsent(RedisConsant.VALIDATE_CODE_KEY+email, code, 60L, TimeUnit.SECONDS);
        simpleMailMessage.setText("仿淘宝给你发了一条验证码："+code +"\n不要发给陌生人，1分钟内有效哦！");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("3139725211@qq.com");
        javaMailSender.send(simpleMailMessage);
        System.out.println("发送邮件完毕！" + code);
        return AjaxResult.success();
    }

    //客户端登录
    @PostMapping("/client/login")
    public AjaxResult clientLogin(@RequestBody ClientUser user){
        String token = userService.clientLogin(user);
        return AjaxResult.success(token);
    }

    //根据登录后获取当前用户信息
    @GetMapping("/info")
    public AjaxResult getUserInfoByToken(@RequestHeader("X-Token") String token){
        User user = userService.getUserInfoByToken(token);
        if (user == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success(user);
    }

    //验证账号唯一
    @GetMapping("/validate/account/{account}")
    public AjaxResult validateAccountUnique(@PathVariable String account){
        boolean flag = userService.validateAccountUnique(account);
        return AjaxResult.success(flag);
    }

    //修改用户信息
    @PutMapping("/edit")
    public AjaxResult editUserInfo(@RequestHeader("X-Token") String token,@RequestBody User user){
        User currentUser = RedisConsant.getUser(stringRedisTemplate, token);
        user.setUserId(currentUser.getUserId());
        userService.updateUser(user);
        //同时修改redis
        List<User> users = userService.selectUserList(currentUser);
        String key = RedisConsant.CLIENT_CURRENT_USER + token;
        if (!CollectionUtils.isEmpty(users)) {
            User newCurrentUser = users.get(0);
            String userJson = JSON.toJSONString(newCurrentUser);
            Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set(key, userJson, expire, TimeUnit.SECONDS);
        }
        return AjaxResult.success();
    }

    //注销
    @GetMapping("/login/out")
    public AjaxResult loginOut(@RequestHeader("X-Token") String token){
        stringRedisTemplate.delete(RedisConsant.CLIENT_CURRENT_USER+token);
        return AjaxResult.success();
    }

    //添加地址
    @PostMapping("/save/address")
    public AjaxResult addAddress(@RequestHeader("X-Token") String token, @RequestBody Address address) {
        addressService.addAddress(token, address);
        return AjaxResult.success();
    }

    //修改地址
    @PutMapping("/edit/address")
    public AjaxResult editAddress(@RequestHeader("X-Token") String token, @RequestBody Address address) {
        addressService.editAddress(address);
        return AjaxResult.success();
    }

    //删除地址
    @DeleteMapping("/del/address/{addressId}")
    public AjaxResult delAddress(@PathVariable Long addressId) {
        addressService.deleteAddressByAddressId(addressId);
        return AjaxResult.success();
    }

    //查询当前用户的地址信息
    @GetMapping("/query/user/address")
    public AjaxResult queryCurrentUserAddress(@RequestHeader("X-Token") String token){
        List<AddressUserVo> address = addressService.queryCurrentUserAddress(token);
        return AjaxResult.success(address);
    }

    //改变地址
    @GetMapping("/start/address/{addressId}")
    public AjaxResult startAddress( @RequestHeader("X-Token") String token,@PathVariable Long addressId){
        addressService.startAddress(token, addressId);
        return AjaxResult.success();
    }

    //查询当前用户的历史记录
    @GetMapping("/my/history")
    public AjaxResult getCurrentUserHistoryProducts(@RequestHeader("X-Token") String token){
        List<ProductHistory> historys = myHistoryService.getCurrentUserHistoryProducts(token);
        return AjaxResult.success(historys);
    }

    //点击收藏
    @GetMapping("/click/collect")
    public AjaxResult collectionProducts(@RequestHeader("X-Token") String token, @RequestParam Long productId) {
        MyCollect myCollect = new MyCollect();
        myCollect.setProductId(productId);
        myCollect.setUserId(RedisConsant.getUser(stringRedisTemplate, token).getUserId());
        List<MyCollect> myCollects = myCollectService.selectMyCollectList(myCollect);
        if (myCollects.size() == 1) {
            myCollectService.deleteMyCollectByMyCollectId(myCollects.get(0).getMyCollectId());
            return AjaxResult.success("取消收藏");
        }
        myCollectService.insertMyCollect(myCollect);
        return AjaxResult.success("收藏成功！");
    }

    //收藏列表
    @GetMapping("/collect/list")
    public AjaxResult collectList(@RequestHeader("X-Token") String token){
        List<Product> products = myCollectService.collectList(token);
        return AjaxResult.success(products);
    }

    //上传头像
    @PostMapping("/upload/pic")
    public AjaxResult uploadUserPic(@RequestHeader("X-Token") String token, @RequestPart("file")MultipartFile file){
        userService.uploadUserPic(token, file);
        return AjaxResult.success();
    }

}
