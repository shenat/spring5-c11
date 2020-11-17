package com.sat.test;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sat.bean.Ingredient;
import com.sat.bean.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonTest {
	
	@Test
	public void toStringTest() {
		Ingredient ingre1 = new Ingredient("1", "name1", Ingredient.Type.CHEESE);
		System.out.println(ingre1.toString());
	}
	
	@Test
	public void testSaveOrderDetails() {
		Order order = new Order();
		order.setPlacedAt(new Date());
		order.setCcCVV("ccvvv");
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> values = objectMapper.convertValue(order, Map.class);
		System.out.println(values);
		values.put("placedAt", order.getPlacedAt());
		log.info(values.toString());
	}
}
