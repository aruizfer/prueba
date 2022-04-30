package com.crowd.coffeeshop.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crowd.coffeeshop.model.Coffee;
import com.crowd.coffeeshop.service.ICoffeeService;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
* Controller component that processes each request in the REST service
* @version 1.0
* @author Andres Ruiz
*/

@RestController
@Validated
@RequestMapping(path = "/api/v1")
public class CoffeeRestController {

	@Autowired
	private ICoffeeService service;
	
	@Operation(summary = "Returns all the coffees in the menu")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "OK", 
			    content = { @Content(mediaType = "application/json",
			      array = @ArraySchema(schema = @Schema(implementation = Coffee.class))) }), 
	  @ApiResponse(responseCode = "204", description = "No content", 
	    content = @Content) })
    @GetMapping("/coffees") 
    public ResponseEntity<List<Coffee>> getAllCoffees() {
		return service.getAllCoffees();
	}

	@Operation(summary = "Add a new coffee in the menu")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "OK", 
			    content = { @Content(mediaType = "application/json",
			      schema = @Schema(implementation = Coffee.class)) })
	  })
	@PostMapping("/coffees")
	public ResponseEntity<Coffee> addCoffee(@RequestBody Coffee newCoffee) {
		return service.addCoffee(newCoffee);
	}
	
	@Operation(summary = "Update a coffee in the menu")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "OK", 
			    content = { @Content(mediaType = "application/json",
			      schema = @Schema(implementation = Coffee.class)) })
	  })
	@PutMapping("/coffees/{id}")
	public ResponseEntity<Coffee> updateCoffee(@RequestBody Coffee newCoffee, @PathVariable Long id) {
		System.out.println("Entra");
		
		return service.updateCoffee(newCoffee,id);
	}

	@Operation(summary = "Delete a coffee in the menu")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "OK", 
			    content = { @Content(mediaType = "text/plain",
			      schema = @Schema(implementation = Coffee.class)) })
	  })
	@DeleteMapping("/coffees/{id}")
	public ResponseEntity<String> deleteCoffee(@PathVariable Long id) {
		return service.deleteCoffee(id);
	}

}