package com.sat.flux.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.sat.api.flux.TacoFluxRestController;
import com.sat.data.tacos.TacoRepository;
import com.sat.tacos.Ingredient;
import com.sat.tacos.Ingredient.Type;
import com.sat.tacos.Taco;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TacoControllerFluxTest {
	
	//mock数据库测试，只需要调用这里指定的类，不需要再服务器中运行，也不会触发taco-c11中的总配置类，所以没有数据库写入记录
	@Test
	public void testReturnRecentTacos() {
		Taco[] tacos = {
				testTaco(1L), testTaco(2L),
		        testTaco(3L), testTaco(4L),
		        testTaco(5L), testTaco(6L),
		        testTaco(7L), testTaco(8L),
		        testTaco(9L), testTaco(10L),
		        testTaco(11L), testTaco(12L),
		        testTaco(13L), testTaco(14L),
		        testTaco(15L), testTaco(16L)
		};
		//c11 数据层面还没有使用webflux
//		Flux<Taco> tacoFlux = Flux.just(tacos);
		List<Taco> tacoList = new ArrayList<Taco>();
		Collections.addAll(tacoList, tacos);
		
		TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
		Mockito.when(tacoRepo.findAll()).thenReturn(tacoList);
		
		WebTestClient testClient = WebTestClient.bindToController(
		        new TacoFluxRestController(tacoRepo))
		        .build();
		    
		    testClient.get().uri("/design/recent")
		      .exchange()
		      .expectStatus().isOk()
		      .expectBody()
		        .jsonPath("$").isArray()
		        .jsonPath("$").isNotEmpty()
		        //c11中持久层没有使用webflux，id的持久策略仍然使用的jpa而不是webflux的持久策略，所以这里不验证id
//		        .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
		        .jsonPath("$[0].name").isEqualTo("Taco 1")
//		        .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
		        .jsonPath("$[1].name").isEqualTo("Taco 2")
//		        .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
		        .jsonPath("$[11].name").isEqualTo("Taco 12")
		        .jsonPath("$[12]").doesNotExist();
		
	}
	
	//也是一个Mock测试不过是验证储存数据方法
	@Test
	public void shouldSaveATaco() {
		TacoRepository tacoRepo = Mockito.mock(
		            TacoRepository.class);
		//c11 数据层面返回的忍让是普通的object
//		Mono<Taco> unsavedTacoMono = Mono.just(testTaco(null));
		Mono<Taco> unsavedTacoMono = Mono.just(testTaco(22l));
		
		//模拟数据库保存后的，多了id,因为根据Taco的主键策略id是由数据库产生
		Taco savedTaco = testTaco(22l);
		savedTaco.setId(1l);
//		Mono<Taco> savedTacoMono = Mono.just(savedTaco);
		
		Mockito.when(tacoRepo.save(ArgumentMatchers.any())).thenReturn(savedTaco);
		
		WebTestClient testClient = WebTestClient.bindToController(
		    new TacoFluxRestController(tacoRepo)).build();
		
		testClient.post()
		    .uri("/design")
		    .contentType(MediaType.APPLICATION_JSON)
		    //注意这边body中第一个参数应该是一个Publisher,所以得传入Mono或者Flux类型
		    .body(unsavedTacoMono, Taco.class)
		  .exchange()
		  .expectStatus().isCreated()
		  .expectBody(Taco.class)
		  //而这边虽然controllerAPI中返回的是Mono类型，但是这边可以直接转为指定的实体类
		  .isEqualTo(savedTaco);
	}
	
	
	
	private Taco testTaco(Long number) {
		Taco taco = new Taco();
		taco.setName("Taco " + number);
	    List<Ingredient> ingredients = new ArrayList<>();
	    ingredients.add(
	        new Ingredient("Ingredient A","Ingredient A", Type.WRAP));
	    ingredients.add(
	        new Ingredient("Ingredient A","Ingredient B", Type.PROTEIN));
	    taco.setIngredients(ingredients);
	    return taco;
	}
	
	
}
