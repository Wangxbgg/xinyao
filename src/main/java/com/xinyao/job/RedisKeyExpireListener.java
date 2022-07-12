package com.xinyao.job;

import com.xinyao.bean.common.GlobalField;
import com.xinyao.service.sale.IOrderService;
import com.xinyao.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * Redis监听key过期
 * @author ZhangFZ
 * @date 2020/10/31 10:41
 **/
@Slf4j
@Component
public class RedisKeyExpireListener extends KeyExpirationEventMessageListener {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisUtil redisUtil;

    public RedisKeyExpireListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expireKey = message.toString();
        //标示当前的key 是订单的Token
        //支付完成要删除Key
        if (expireKey.startsWith(GlobalField.ORDER_CONFIG)){
            //由于监听的key过期取不到value,从copy的key中取合同信息
            Long orderId = Long.parseLong(redisUtil.get(GlobalField.COPY_KEY + expireKey).toString());
            log.info("超时自动处理的订单id：" + orderId);
            orderService.cancelOrder(orderId);
            //回收 copy的key
            redisUtil.remove(GlobalField.COPY_KEY + expireKey);
        }
    }
}
