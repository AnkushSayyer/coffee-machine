package com.dunzo.domain;

import java.util.List;

public class Beverage extends AbstractBeverage {
	private String name;
	private List<Ingredient> requiredIngredients;
	
	public Beverage(String name, List<Ingredient> requiredIngredients) {
		this.name = name;
		this.requiredIngredients = requiredIngredients;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Ingredient> getRequiredIngredients() {
		return requiredIngredients;
	}
	public void setRequiredIngredients(List<Ingredient> requiredIngredients) {
		this.requiredIngredients = requiredIngredients;
	}

	@Override
	public void addIngredient(Ingredient ingredient) {
		requiredIngredients.add(ingredient);
	}
}
