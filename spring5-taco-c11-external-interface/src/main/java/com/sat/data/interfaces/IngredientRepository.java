package com.sat.data.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.sat.bean.Ingredient;



public interface IngredientRepository extends CrudRepository<Ingredient, String> {
	
}
