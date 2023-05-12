package com.ruoyi.framework.interceptor.impl;

import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.interceptor.ClientLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 子类实现拦截器
 */
@Component
public class SimpleClientLoginInterceptor extends ClientLoginInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean initUrlInterceptors(HttpServletRequest request, HttpServletResponse response) {
        init();
        //当前路径
        String currentUrl = request.getRequestURI();
        if (super.urlSet.contains(currentUrl)) {
            //X-Token 不能为空
            String token = request.getHeader("X-Token");
            if (Objects.nonNull(token)) {
                String userInfo = stringRedisTemplate.opsForValue().get(RedisConsant.CLIENT_CURRENT_USER + token);
                if (Objects.nonNull(userInfo)) {
                    return true;
                }
            }
            ServletUtils.renderString(response, "{\"msg\":\"请您先登录在可以做此操作，谢谢！☺\",\"code\":403}");
            return false;
        }
        return true;
    }

    /**
     * 哪些路径需要拦截
     *
     * @return
     */
    private void init() {
        List<String> urls = Arrays.asList("/app/user/info", "/app/user/click/collect",
                "/app/menu/add/cart","/app/menu/my/order/list","/app/user/query/user/address",
                "/app/user/collect/list","/app/user/my/history", "/app/menu/carts");
        super.urlSet.addAll(urls);
    }

}
