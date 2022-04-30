package com.crowd.coffeeshop.exception;

public class CoffeeNotFoundException extends RuntimeException {

	public CoffeeNotFoundException(Long id) {
		super("Could not found coffee " + id);
	}
}
