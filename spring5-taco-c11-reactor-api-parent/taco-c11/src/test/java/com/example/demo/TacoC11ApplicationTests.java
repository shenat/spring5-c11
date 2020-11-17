package com.example.demo;

import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.sat.config.TacoC11Application;
import com.sat.message.receive.JmsOrderReceiver;
import com.sat.message.send.JmsOrderMessagingService;
import com.sat.tacos.Order;
import com.sat.tacos.User;

import lombok.extern.slf4j.Slf4j;

/**
 * 这个是测试类，测试spring context能正常加载
 * Spring Boot 2.2.0以上版本用的Junit5.0,
 * pom文件中spring-boot-starter-test会有exclusion junit-vintage-engine
 * 此时会排除依赖的JUnit4，此时不需要@RunWith(SpringRunner.class)了
 * 如果需要用@RunWith,则将exclusion注释掉即可
 * 
 * @RunWith作用：JUnit将调用它所引用的类来运行该类中的测试而不是开发者去在junit内部去构建它
 * SpringRunner：这是一个Spring提供的测试运行器，它会创建测试运行所需的Spring应用上下文
 * @SpringBootTest：@SpringBootTest会告诉JUnit在启动测试的时候要添加上SpringBoot的功能。
 *
 */
@Slf4j
@RunWith(SpringRunner.class)
//可以参考springboot测试 文档  TacoC11Application为主配置类不可少
@SpringBootTest(classes = TacoC11Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TacoC11ApplicationTests {
	
	//c8 增加的jms测试
	/**
	@Autowired
	private JmsOrderMessagingService jmsOrderMessagingService;
	@Autowired
	private JmsOrderReceiver jmsOrderReceiver;

	//测试spring context是否能正常加载，只要不报错就行
	@Test
	void contextLoads() {
		
	}
	
	@Test
	public void testJmsTemplate() {
		Order order = new Order();
		User user = new User("sat","pwd","satfull","street1","city","1","zip1","1123424242");
		user.setId(1001L);
		
		order.setId(11111L);
		order.setName("test");
		order.setPlacedAt(new Date());
		order.setUser(user);
		//发送消息
		log.info("开始发送消息");
		//经测试，在发送之前只是有个名为taco.message.order.queue.string的地址
		//只有在发送消息的时候才会在该地址上建立一个同名的queue
		jmsOrderMessagingService.sendOrder(order);
		log.info("发送完成");
		//receive消息后在queue上会看到一个ackonwledged增加了一，若没有下面while循环，receive一个就结束了
		//添加while循环后会发现receive方法一直堵塞着
		log.info("开始接收消息");
		Order order_receive = jmsOrderReceiver.receiveOrder();
		while(order_receive != null) {
			log.info("接收完成");
			Assert.assertNotNull(order_receive);
			log.info("接收到消息:" + order_receive);
			order_receive = jmsOrderReceiver.receiveOrder();
		}
		
	}
	**/
	
	//c11 增加对反应式Flux的集成测试
	@Autowired
	private WebTestClient testClient;
	
	@Test
	public void shouldReturnRecentTacos() {
		testClient.get().uri("/design/recent")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			//下面使用的jsonPath，表示如果当前元素的id为1则比较它的name是不是与后面的内容相等
			.jsonPath("$[?(@.id=='3')].name").isEqualTo("Carnivore")
			.jsonPath("$[?(@.id=='4')].name").isEqualTo("Bovine Bounty")
			.jsonPath("$[?(@.id=='5')].name").isEqualTo("Veg-Out");
	}
	
	
	
	
	
	
	

}
