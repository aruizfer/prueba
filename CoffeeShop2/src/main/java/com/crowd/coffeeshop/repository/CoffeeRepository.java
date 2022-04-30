package com.crowd.coffeeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crowd.coffeeshop.model.Coffee;

/**
* Repository for coffee entities, with the dynamic methods to get specific data 
* @version 1.0
* @author Andres Ruiz
*/

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
	
}
