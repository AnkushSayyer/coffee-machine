package com.dunzo.domain;

import java.util.Map;

public abstract class AbstractCoffeeMachine {
	
	private int outlets;
	
	public AbstractCoffeeMachine(int outlets) {
		this.outlets = outlets;
	}
	
	public int getOutlets() {
		return outlets;
	}
	public void setOutlets(int outlets) {
		this.outlets = outlets;
	}

	public abstract void addIngredient(Ingredient ingredient);
	
	public abstract Map<String, Ingredient> getAvailableIngredients();
	
	public abstract void addBeverageRecipe(Beverage beverage);
}
