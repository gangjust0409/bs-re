package com.ruoyi.bsapi.listener;

import com.ruoyi.bsapi.service.IOrderService;
import com.ruoyi.bsapi.utils.RedisConsant;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisKeyExipreListener extends KeyExpirationEventMessageListener {

    @Resource
    private IOrderService orderService;
    /**
     * Creates new {@link} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisKeyExipreListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //获取失效的 key
        String key = message.toString();
        //只要和订单相关联的，把数据删除
        if (key.indexOf(RedisConsant.ORDER_TEMPLATE_KEY) != -1) {
            //获取订单号
            String orderSn = key.substring(key.lastIndexOf(":") + 1);
            //删除
            orderService.deleteOrder(orderSn);
        }
    }
}
