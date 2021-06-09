package com.dunzo.domain;

public class Ingredient {
	private String name;
	private Double quantity;
	private Double minQuantity;
	
	public Ingredient(String name, Double quantity, Double minQuantity) {
		this.name = name;
		this.quantity = quantity;
		this.minQuantity = minQuantity;
	}
	
	public Ingredient(String name, Double quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(Double minQuantity) {
		this.minQuantity = minQuantity;
	}

	@Override
	public String toString() {
		return "Ingredient [quantity=" + quantity + "]";
	}
}
