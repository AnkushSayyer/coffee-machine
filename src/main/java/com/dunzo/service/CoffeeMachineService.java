package com.dunzo.service;

import java.util.List;

import com.dunzo.domain.Beverage;
import com.dunzo.domain.Ingredient;

public interface CoffeeMachineService {

	public String makeBeverage(String beverageName);
	
	public List<String> getLowIngredients();
	
	public void addIngredientQuantity(String name, Double quantity);
	
	public void addBeverageRecipe(Beverage beverage);
	
	public void addIngredients(List<Ingredient> ingredients);
	
	public void addIngredients(Ingredient ingredient);
}
