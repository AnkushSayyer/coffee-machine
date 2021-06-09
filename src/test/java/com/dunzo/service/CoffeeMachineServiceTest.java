package com.dunzo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dunzo.domain.Beverage;
import com.dunzo.domain.Ingredient;

public class CoffeeMachineServiceTest {
	
	private CoffeeMachineService coffeeMachineService;
	
	private static final int OUTLETS = 3;
	
	@Before
	public void setUp() {
		coffeeMachineService = new CoffeMachineServiceImpl(OUTLETS);
		addIngredientsToMachine();
		addBeverageRecipesToMachine();
	}
	
	@Test
	public void case1() {
		assertEquals("hot_tea is prepared", this.coffeeMachineService.makeBeverage("hot_tea"));
		assertEquals("hot_coffee is prepared", this.coffeeMachineService.makeBeverage("hot_coffee"));
		assertEquals("green_tea cannot be prepared because green_mixture is not available", this.coffeeMachineService.makeBeverage("green_tea"));
		assertEquals("black_tea cannot be prepared because item hot_water is not sufficient", this.coffeeMachineService.makeBeverage("black_tea"));
	}
	
	@Test
	public void case2() {
		assertEquals("hot_tea is prepared", this.coffeeMachineService.makeBeverage("hot_tea"));
		assertEquals("black_tea is prepared", this.coffeeMachineService.makeBeverage("black_tea"));
		assertEquals("green_tea cannot be prepared because green_mixture is not available", this.coffeeMachineService.makeBeverage("green_tea"));
		assertEquals("hot_coffee cannot be prepared because item hot_water is not sufficient", this.coffeeMachineService.makeBeverage("hot_coffee"));
	}
	
	@Test
	public void case3() {
		assertEquals("hot_coffee is prepared", this.coffeeMachineService.makeBeverage("hot_coffee"));
		assertEquals("black_tea is prepared", this.coffeeMachineService.makeBeverage("black_tea"));
		assertEquals("green_tea cannot be prepared because green_mixture is not available", this.coffeeMachineService.makeBeverage("green_tea"));
		assertEquals("hot_tea cannot be prepared because item hot_water is not sufficient", this.coffeeMachineService.makeBeverage("hot_tea"));
	}
	
	@Test
	public void getLowIngredients() {
		// minimum quantity required for each addIngredientsToMachine
		//Hot Milk available - 500 and minQuantity required in machine - 200
		//Hot Milk required for hot_coffee - 400
		
		assertEquals("hot_coffee is prepared", this.coffeeMachineService.makeBeverage("hot_coffee"));
		
		//now available hot milk is 500-400 = 200 and indicator is set at 200
		
		assertTrue(this.coffeeMachineService.getLowIngredients().contains("hot_milk"));
	}
	
	private void addBeverageRecipesToMachine() {
		this.coffeeMachineService.addBeverageRecipe(createHotTeaRecipe());
		this.coffeeMachineService.addBeverageRecipe(createHotCoffeeRecipe());
		this.coffeeMachineService.addBeverageRecipe(createBlacktTeaRecipe());
		this.coffeeMachineService.addBeverageRecipe(createGreenTeaRecipe());
	}

	private Beverage createHotTeaRecipe() {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(createIngredient("hot_water", 200));
		ingredients.add(createIngredient("hot_milk", 100));
		ingredients.add(createIngredient("ginger_syrup", 10));
		ingredients.add(createIngredient("sugar_syrup", 10));
		ingredients.add(createIngredient("tea_leaves_syrup", 30));
		
		return new Beverage("hot_tea", ingredients);
	}
	
	private Beverage createHotCoffeeRecipe() {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(createIngredient("hot_water", 100));
		ingredients.add(createIngredient("hot_milk", 400));
		ingredients.add(createIngredient("ginger_syrup", 30));
		ingredients.add(createIngredient("sugar_syrup", 50));
		ingredients.add(createIngredient("tea_leaves_syrup", 30));
		
		return new Beverage("hot_coffee", ingredients);
	}
	
	private Beverage createBlacktTeaRecipe() {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(createIngredient("hot_water", 300));
		ingredients.add(createIngredient("ginger_syrup", 30));
		ingredients.add(createIngredient("sugar_syrup", 50));
		ingredients.add(createIngredient("tea_leaves_syrup", 30));
		
		return new Beverage("black_tea", ingredients);
	}
	
	private Beverage createGreenTeaRecipe() {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(createIngredient("hot_water", 100));
		ingredients.add(createIngredient("green_mixture", 30));
		ingredients.add(createIngredient("ginger_syrup", 30));
		ingredients.add(createIngredient("sugar_syrup", 50));
		
		return new Beverage("green_tea", ingredients);
	}

	private void addIngredientsToMachine() {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(createIngredient("hot_water", 500, 200)); // (name, quantity, minimumQuantity) Setting the minimum quantity required in machine  
		ingredients.add(createIngredient("hot_milk", 500, 200));
		ingredients.add(createIngredient("ginger_syrup", 100, 50));
		ingredients.add(createIngredient("sugar_syrup", 100, 50));
		ingredients.add(createIngredient("tea_leaves_syrup", 100, 50));
		this.coffeeMachineService.addIngredients(ingredients);
	}
	
	private Ingredient createIngredient(String name, double quantity, double minQuantity) {
		return new Ingredient(name, quantity, minQuantity);
	}
	
	private Ingredient createIngredient(String name, double quantity) {
		return new Ingredient(name, quantity);
	}
	
}
