package com.dunzo.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoffeeMachine {
	private int outlets;
	private Map<String, Ingredient> availableIngredients;
	private Map<String, Beverage> beverageRecipe;
	
	public CoffeeMachine(int outlets) {
		this.availableIngredients = new HashMap<>();
		this.beverageRecipe = new HashMap<>();
	}

	public int getOutlets() {
		return outlets;
	}

	public void setOutlets(int outlets) {
		this.outlets = outlets;
	}

	public Map<String, Ingredient> getAvailableIngredients() {
		return availableIngredients;
	}
	
	public List<String> getBeverages() {
		return new ArrayList<>(beverageRecipe.keySet());
	}

	public Map<String, Beverage> getBeverageRecipe() {
		return beverageRecipe;
	}

	public void setBeverageRecipe(Map<String, Beverage> beverageRecipe) {
		this.beverageRecipe = beverageRecipe;
	}

	public void setAvailableIngredients(Map<String, Ingredient> availableIngredients) {
		this.availableIngredients = availableIngredients;
	}
}
