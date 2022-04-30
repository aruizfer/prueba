package com.crowd.coffeeshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crowd.coffeeshop.exception.CoffeeNotFoundException;
import com.crowd.coffeeshop.model.Coffee;
import com.crowd.coffeeshop.repository.CoffeeRepository;

@Service
public class CoffeeServiceImpl implements ICoffeeService {

	@Autowired
	private CoffeeRepository repository;

	@Override
	public ResponseEntity<List<Coffee>> getAllCoffees() {
		List<Coffee> coffees = new ArrayList<Coffee>();
		repository.findAll().forEach(coffees::add);
        return (coffees.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(coffees, HttpStatus.OK));
	}

	@Override
	public ResponseEntity<Coffee> addCoffee(Coffee coffee) {
		Coffee coffeeSaved = repository.save(coffee);
		return new ResponseEntity<>(coffee, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Coffee> updateCoffee(Coffee coffee, Long id) {
		Optional<Coffee> coffeeFound = repository.findById(id);
		
		if (!coffeeFound.isPresent()) 
			throw new CoffeeNotFoundException(id);

		coffeeFound.get().setName(coffee.getName());
		coffeeFound.get().setPrice(coffee.getPrice());
		 
		Coffee coffeeUpdate = repository.save(coffeeFound.get()); // updating
		 
		if( coffeeUpdate == null ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		 return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteCoffee(Long id) {
		Optional<Coffee> coffeeFound = repository.findById(id);
		if (!coffeeFound.isPresent()) 
			throw new CoffeeNotFoundException(id);

		repository.deleteById(id);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Override
	public Optional<Coffee> findCoffeeById(Long id) {
		return repository.findById(id);
	}

}
