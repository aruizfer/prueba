package com.crowd.coffeeshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.crowd.coffeeshop.model.Coffee;

/**
* Interface for the service component
* @version 1.0
* @author Andres Ruiz
*/


public interface ICoffeeService {

		public ResponseEntity<List<Coffee>> getAllCoffees();

		public ResponseEntity<Coffee> addCoffee(Coffee coffee);

		public ResponseEntity<Coffee> updateCoffee(Coffee coffee, Long id);

		public ResponseEntity<String> deleteCoffee(Long id);
		
		public Optional<Coffee> findCoffeeById(Long id);

}