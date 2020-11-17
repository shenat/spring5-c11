package com.sat.message.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.sat.message.receive.JmsOrderReceiver;
import com.sat.tacos.Order;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: OrderListener
 * @Description: JMS之监听器模式，不堵塞
 * @author: sat
 * @date: 2020年9月15日 下午7:32:48
 * @param:
 */
@Slf4j
@Component
public class OrderListener {
	
	//该地址有消息的时候会自动转为方法的参数类型，并执行方法
	@JmsListener(destination = "taco.message.order.queue.string")
	public void receiveOrder(Order order) {
		log.info("监听到有消息进来，转为Order:" + order);
	}
	
}
