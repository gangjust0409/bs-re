package com.ruoyi.bsapi.quart;

import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.service.IProductService;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.common.utils.my.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 定时任务，计算月销量
 */
@Component
public class CountMonthPinQuart {

    @Resource
    private IProductService productService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public void countPin(){
        //获取redis中的数据
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            if (key.indexOf(RedisConsant.PRODUCT_MONTH_PIN_KEY) != -1) {
                String val = stringRedisTemplate.opsForValue().get(key);
                if (StringUtils.isNotNull(val)) {
                    //获取商品id
                    Long productId = Long.parseLong(key.substring(key.lastIndexOf(":") + 1));
                    Product product = productService.selectProductByProductId(productId);
                    product.setMonthPin(Long.parseLong(val));
                    productService.updateProduct(product);
                }
            }
        }

    }

}
