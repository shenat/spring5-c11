package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
@RunWith(SpringRunner.class)
@SpringBootTest
class Spring5DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
