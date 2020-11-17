package com.sat.message.receive;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.sat.message.interfaces.OrderReceiver;
import com.sat.tacos.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JmsOrderReceiver implements OrderReceiver{

	private JmsTemplate jms;
	//显示注入消息转换器,当需要接收原始信息并还需要转换的时候使用
//	private MessageConverter converter;
	
//	public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
//		super();
//		this.jms = jms;
//		this.converter = converter;
//	}
	
	public JmsOrderReceiver() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public JmsOrderReceiver(JmsTemplate jms) {
		super();
		this.jms = jms;
	}

	






	@Override
	public Order receiveOrder() {
		// TODO Auto-generated method stub
		//1.使用jms.receive接收原始消息，在转为实体，
		//需要注入MessageConverter，就是在@Configuration中配置名为messageConverter的bean
//		Message message = jms.receive("taco.message.order.queue.string");
//		Order receiveOrder = null;
//		try {
//			receiveOrder = (Order)converter.fromMessage(message);
//		} catch (MessageConversionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JMSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return receiveOrder;
		
		//2.使用jms.receiveAndConvert接收消息并自动转为实体类，但是只能获取到载荷，消息头获取不到
		Object obj = jms.receiveAndConvert("taco.message.order.queue.string");
		if(obj != null) {
			return (Order)obj;
		}
		return null;
//		return (Order)jms.receiveAndConvert("taco.message.order.queue.string");
	}


	
	
}
