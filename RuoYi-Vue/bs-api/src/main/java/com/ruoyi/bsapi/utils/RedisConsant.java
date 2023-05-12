package com.ruoyi.bsapi.utils;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.domain.User;
import com.ruoyi.bsapi.to.SkuUploadImgItemTo;
import com.ruoyi.common.utils.my.StringUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 保存进 redis 中的key
 */
public class RedisConsant {

    /**
     * 添加时，展示属性
     */
    public static final String SAVE_CATELOG_SHOW_ATTRS_KEY = "save:attrs:catelog:id:";

    /**
     * 新增商品时，上传文件暂存地方
     */
    public static final String UPLOAD_TEMPLATE_PRODUCT_PIC = "upload:template:product:pic";

    /**
     * 新增商品时，上传商品详情图
     */
    public static final String UPLOAD_PRODUCT_DETAIL_URL = "upload:product:detail:url";

    /**
     * 新增商品时，上传商品缩略图
     */
    public static final String UPLOAD_PRODUCT_THUMBNAI_URL = "upload:product:thumbnai:url";

    /**
     * 新增商品时，sku 图片
     */
    public static final String UPLOAD_PRODUCT_SKU_PIC_ID = "upload:product:sku:pic";

    /**
     * 上传店铺图标
     */
    public static final String UPLOAD_SHOP_ICON = "upload:shop:icon";

    /**
     * 上传轮播图 key
     */
    public static final String UPLOAD_SWIPER_IMG = "upload:swiper";

    /**
     * 客户端登录使用到的key
     */
    public static final String CLIENT_CURRENT_USER = "client:current:user:token:";

    /**
     * 发送验证码到redis中保存
     */
    public static final String VALIDATE_CODE_KEY = "validate:code:key:email:";

    /**
     * 当前用户下，商品存放购物车在redis中的key
     */
    public static final String CART_CURRENT_USER_KEY = "cart:current:user:id:";

    /**
     * 当订单未支付成功，存入的订单号
     */
    public static final String ORDER_TEMPLATE_KEY = "order:template:order:sn:";

    /**
     * 计算销量使用到的key
     */
    public static final String PRODUCT_MONTH_PIN_KEY ="month:pin:product:id:";

    private static User currentUser;

    /**
     * 当前用户保存 sku信息
     */
    public static final String CART_CURRENT_USER_SKU_KEY(Long userId, Long productId){
        return "cart:current:user:id"+userId+":sku:product:id:"+productId;
    }

    /**
     * 获取添加时，全部属性
     * @param redisTemplate
     * @param catelogId
     * @return
     */
    public static BoundHashOperations<String, String, String> getCatelogAttrsInstant(StringRedisTemplate redisTemplate, Long catelogId) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SAVE_CATELOG_SHOW_ATTRS_KEY + catelogId);
        return hashOps;
    }

    /**
     * 获取文件多选
     * @param redisTemplate
     */
    public static BoundListOperations<String, String> getFileMulitInstant(StringRedisTemplate redisTemplate, String type) {
        BoundListOperations<String, String> ops = redisTemplate.boundListOps(type.equals("d")?UPLOAD_PRODUCT_DETAIL_URL:UPLOAD_PRODUCT_THUMBNAI_URL);
        ops.expire(2L, TimeUnit.MINUTES);
        return ops;
    }

    /**
     * 获取 sku 上传文件在redis暂存文件名的实例
     * @param redisTemplate
     * @return
     */
    public static BoundListOperations<String, String> getSkuUploadImg(StringRedisTemplate redisTemplate) {
        return redisTemplate.boundListOps(UPLOAD_PRODUCT_SKU_PIC_ID);
    }


    /**
     * 获取当前用户
     * @param token
     * @return
     */
    public static User getUser(StringRedisTemplate stringRedisTemplate,String token) {
        String userStr = stringRedisTemplate.opsForValue().get(CLIENT_CURRENT_USER + token);
        User user = JSON.parseObject(userStr, User.class);
        return user;
    }

    /**
     * 获取当前用户下在购物车中的实例
     * @param redisTemplate
     * @param token
     * @return
     */
    public static BoundHashOperations<String, String, String> getCurrentUserCart(StringRedisTemplate redisTemplate, String token) {
        User user = getUser(redisTemplate, token);
        if (StringUtils.isNotNull(user)) {
            currentUser = user;
            return redisTemplate.boundHashOps(CART_CURRENT_USER_KEY + user.getUserId());
        }
        throw new RuntimeException("user token is null...");
    }

    /**
     * 获取 购物车下redis保存的列表实例
     * @param redisTemplate
     * @param productId
     * @return
     */
    public static BoundListOperations<String, String> getCurrentUserCartSkuList(StringRedisTemplate redisTemplate, Long productId){
         return redisTemplate.boundListOps(CART_CURRENT_USER_SKU_KEY(currentUser.getUserId(), productId));
    }


}
