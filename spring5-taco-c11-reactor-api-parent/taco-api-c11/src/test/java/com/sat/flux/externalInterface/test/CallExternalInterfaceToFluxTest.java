package com.sat.flux.externalInterface.test;

import java.rmi.ServerException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import com.sat.tacos.Ingredient;
import com.sat.tacos.Ingredient.Type;
import com.sat.tacos.Taco;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * 
 * @ClassName: CallExternalInterfaceToFluxTest
 * @Description: 调用外部接口转为反应式流的测试，外部接口是调用的taco-c11-external-interface项目
 * @author: sat
 * @date: 2020年10月29日 下午3:30:16
 * @param:
 */
@Slf4j
public class CallExternalInterfaceToFluxTest {
	WebClient webClient;
	
	
	//模拟实际应用时将WebClient实例化一个bean从而使用外部API的基础URL
	@Before
	public void before() {
		System.out.println("----------------创建webclient----------------");
		webClient = WebClient.create("http://localhost:8080");
	}
	
	@Test
	public void test() {
		Long tacoId = 1l;
		//发现访问远程API的时候将返回数据内容转为本应用的类竟然可以成功转化过来
		Mono<Taco> taco = webClient.get()
								.uri("/{id}", tacoId)
								.retrieve()
								.onStatus(HttpStatus :: is4xxClientError, response -> Mono.just(new ServerException("Taco not found")))
								.bodyToMono(Taco.class);
//		taco.subscribe(
//				i -> {
//					System.out.println("get taco info: " + i.toString());
//				},
//				error -> {
//					System.out.println("error!error!" + error.getMessage());
//				}
//		);
		
		//测试上面的taco，不能通过taco.subscribe测试，需要真要通过subscribe则需要一步步调试，结果才可能出来，因为是异步的
		Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
        Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
        Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
        Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
        Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
        Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
        Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
        Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
        Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
        Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
		Taco tacoResult = new Taco();
		tacoResult.setId(1l);
		tacoResult.setName("Carnivore");
		tacoResult.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
		
		//正常测试
//		StepVerifier.create(taco).expectNext(tacoResult).verifyComplete();
		
		//抛异常测试
		StepVerifier.create(taco).expectError(ServerException.class).log();
		
		
	}
}
