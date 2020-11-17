package com.sat.message.config;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;

import com.sat.tacos.Order;

/**
 * 
 * @ClassName: MessagingConfig
 * @Description: 使用JmsTemplate配置类
 * @author: sat
 * @date: 2020年8月14日 下午2:06:48
 * @param:
 */
@Configuration
public class MessagingConfig {
	
	//使用JmsTemplate的send方法含有Destination参数的时候用到这个
	@Bean
	public Destination orderQueue() {
		return new ActiveMQQueue("taco.message.order.queue.custom");
	}
	
	//测试的时候发送可以用sat-activeMQArtemis-test项目中的ArtemisClient来发送，然后这边接收并用改转换器转换
	//使用JmsTemplate的convertAndSend和receiveAndConvert方法是默认是SimpleMessageConverter但可以通过申明如下bean改变消息转换器
	@Bean
	public MappingJackson2MessageConverter messageConverter() {
		MappingJackson2MessageConverter messageConverter =
                new MappingJackson2MessageConverter();
		//意思是：设置了消息中需包含一个名为_typeId的属性名，该属性名对应的值为需要将消息转换为实体类的全限定类名
		messageConverter.setTypeIdPropertyName("_typeId");
		
		//加了这个后，消息中的_typeId属性不必是全限定类名，而为order即可，会自动转为Order类的
		Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
		typeIdMappings.put("order", Order.class);
		messageConverter.setTypeIdMappings(typeIdMappings);
		
		return messageConverter;
	}
	
}
