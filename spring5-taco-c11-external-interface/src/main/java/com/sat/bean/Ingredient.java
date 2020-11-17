package com.sat.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.server.core.Relation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data//用的lombok的自动生成缺少方法
//如果得写一个有参构造器，就一定要有一个无参构造器，不然向上回溯会发生错误
//如果不需要有参构造器，那就不必那么麻烦，可以什么构造器都不写
@RequiredArgsConstructor//用的lombok的自动生成参数构造器
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)//jpa必须要有一个无参构造器，但是这里还设置为private和初始化空的
@Entity//jpa注解
//该注解用于spring data rest将Resource接口自动配置为rest,为其设置路径和关系名,和采用spring hateoas中给model的注解@Relation有些类似
@RestResource(rel = "ingredients",path = "ingredients")
public class Ingredient {
	
	/**
	 * 使用final修饰会报：The blank final field id may not have been  initialized
	 * 但是如果构造器中对其初始化了，则不会报这个错，且不能有无参构造器了
	 * 因为无参构造器会初始化的时候不将final变量初始化
	 */
	@Id//jpa注解，主键
	private final String id;
	private final String name;
	private final Type type;
  
 	public static enum Type {
 		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
 	}

 	

 	
 	
  
  
}
