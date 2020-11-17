package com.sat.message.send;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;

import com.sat.message.interfaces.OrderMessagingService;
import com.sat.tacos.Order;

@Service
public class JmsOrderMessagingService implements OrderMessagingService{
	
	private JmsTemplate jms;

	public JmsOrderMessagingService(JmsTemplate jms) {
		this.jms = jms;
	}
	
	
	//send方法中需要用到Destinaton参数的时候用到下面这个构造函数
//	private Destination orderQueue;
//	public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
//		this.jms = jms;
//		this.orderQueue = orderQueue;
//	}


	@Override
	public void sendOrder(Order order) {
		//调用JmsTemplate发送消息的几种方式
		//1.将订单发送至默认目的地 默认目的地通过配置文件中的spring.jms.template.default-destination设置
//		jms.send(new MessageCreator() {
//
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				return session.createObjectMessage(order);
//			}
//			
//		});
//		jms.send(session -> session.createObjectMessage(order));
		
//		//2.send方法也可以添加一个Destination参数
//		jms.send(orderQueue, session -> session.createObjectMessage(order));
//		
//		//3.如果目的地不需要设置其他参数只需要名称的时候，则不需要Destinaton类型，只需要String就行
//		jms.send("taco.message.order.queue.string", session -> session.createObjectMessage(order));
		
		//4.使用convertAndSend发送对象，不需要转为Message
//		jms.convertAndSend(order);
		
		//5.添加自定义消息头部信息
//		jms.send("taco.message.order.queue.string", session -> {
//			Message message = session.createObjectMessage(order);
//			message.setStringProperty("X_ORDER_SOURCE", "WEB");
//			return message;
//		});
		//使用convertAndSend的时候添加自定义消息头
//		jms.convertAndSend("taco.message.order.queue.string", order, new MessagePostProcessor() {
//			
//			@Override
//			public Message postProcessMessage(Message message) throws JMSException {
//				// TODO Auto-generated method stub
//				message.setStringProperty("X_ORDER_SOURCE", "WEB");
//				return message;
//			}
//		});
		//lambada简化
//		jms.convertAndSend("taco.message.order.queue.string", order, message -> {
//			message.setStringProperty("X_ORDER_SOURCE", "WEB");
//			return message;
//		});
		//避免代码重复的简化
		jms.convertAndSend("taco.message.order.queue.string",order,this::addOrderSource);
		
		
		
	}
	
	
	private Message addOrderSource(Message message) throws JMSException {
		message.setStringProperty("X_ORDER_SOURCE", "WEB");
		//增加这个是为了配合MappingJackson2MessageConverter，但是在同一个项目中order类都是一个，所以不需要弄一个合成类
		//将order执行Order类了
		//message.setStringProperty("_typeId", "order");
		return message;
	}
	
}
