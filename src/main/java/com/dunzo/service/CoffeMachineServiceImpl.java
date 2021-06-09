package com.dunzo.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import com.dunzo.domain.Beverage;
import com.dunzo.domain.CoffeeMachine;
import com.dunzo.domain.Ingredient;
import com.dunzo.exceptions.GenericException;
import com.dunzo.exceptions.ItemNotAvailableException;

public class CoffeMachineServiceImpl implements CoffeeMachineService {
	private ReentrantLock lock = new ReentrantLock();
	
	private Semaphore semaphore;
	private CoffeeMachine coffeMachine;
	
	public CoffeMachineServiceImpl(int outlets) {
		this.coffeMachine = new CoffeeMachine(outlets);
		semaphore = new Semaphore(outlets);
	}
	
	@Override
	public String makeBeverage(String beverageName) {
		try {
			semaphore.acquireUninterruptibly();
			Beverage beverageRecipe = getBeverageRecipe(beverageName);
			if (beverageRecipe == null) {
				System.out.println("Cant ");
			}
			collectIngredients(beverageRecipe, this.coffeMachine.getAvailableIngredients());
			return dispatchBeverage(beverageName);
		} catch (GenericException exception) {
			return exception.getMessage();
		} finally {
			semaphore.release();
		}
	}

	private String dispatchBeverage(String beverageName) {
		return beverageName + " is prepared";
	}

	private void collectIngredients(Beverage beverage, Map<String, Ingredient> availableIngredients) throws GenericException {
		collectIngredientsHelper(beverage, availableIngredients, 0);
	}

	// Core logic of getting ingredients
	// Recursively get ingredients from list and if we are not able to secure all ingredients start
	// putting the taken ingredients back
	private void collectIngredientsHelper(Beverage beverage, Map<String, Ingredient> availableIngredients, int start) throws GenericException {
		List<Ingredient> requiredIngredients = beverage.getRequiredIngredients();
		if (start == requiredIngredients.size())
			return;
		
		double requiredQuantity = 0;
		double availableQuantity = 0;
		String ingredientName = requiredIngredients.get(start).getName();
		try {
			lock.lock();
			if (availableIngredients.get(ingredientName) == null) {
				String message = beverage.getName() + " cannot be prepared because " + ingredientName + " is not available";
				throw new ItemNotAvailableException(message);
			}

			requiredQuantity = requiredIngredients.get(start).getQuantity();
			availableQuantity = availableIngredients.get(ingredientName).getQuantity();
			if (requiredQuantity > availableQuantity) {
				String message = beverage.getName() + " cannot be prepared because item " + ingredientName + " is not sufficient";
				throw new GenericException(message);
			}
			availableQuantity -= requiredQuantity;
			availableIngredients.get(ingredientName).setQuantity(availableQuantity);
			
			collectIngredientsHelper(beverage, availableIngredients, start + 1);
		}
		catch(ItemNotAvailableException exception) {
			//Item not found and convert it to GenericException so we put the taken ingredients back
			throw (GenericException)exception;
		}
		catch (GenericException exception) {
			//if there is any exception put the taken ingredients back
			availableQuantity += requiredQuantity;
			availableIngredients.get(ingredientName).setQuantity(availableQuantity);
			throw exception;
		}
		finally {
			lock.unlock();
		}
	}
	
	private Beverage getBeverageRecipe(String beverageName) throws GenericException {
		Beverage beverageRecipe = this.coffeMachine.getBeverageRecipe().get(beverageName);
		if (beverageRecipe == null) {
			throw new GenericException(beverageName + " can't be served");
		}
		return beverageRecipe;
	}
	
	@Override
	public List<String> getLowIngredients(){
		return this.coffeMachine.getAvailableIngredients().values().stream()
				.filter(ingredient -> ingredient.getQuantity()<=ingredient.getMinQuantity())
				.map(ingredient -> ingredient.getName())
				.collect(Collectors.toList());
	}
	
	@Override
	public void addIngredientQuantity(String name, Double quantity) {
		Ingredient ingredient = this.coffeMachine.getAvailableIngredients().get(name);
		ingredient.setQuantity(ingredient.getQuantity()+quantity);
	}
	
	@Override
	public void addBeverageRecipe(Beverage beverage) {
		this.coffeMachine.getBeverageRecipe().put(beverage.getName(), beverage);
	}
	
	@Override
	public void addIngredients(List<Ingredient> ingredients) {
		for(Ingredient ingredient : ingredients) {
			this.coffeMachine.getAvailableIngredients().put(ingredient.getName(), ingredient);
		}
	}
	
	@Override
	public void addIngredients(Ingredient ingredient) {
		this.coffeMachine.getAvailableIngredients().put(ingredient.getName(), ingredient);
	}
}
