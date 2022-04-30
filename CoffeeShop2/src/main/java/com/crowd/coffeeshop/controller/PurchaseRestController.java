package com.crowd.coffeeshop.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.crowd.coffeeshop.model.Purchase;
import com.crowd.coffeeshop.model.PurchaseItem;
import com.crowd.coffeeshop.model.PurchaseSummary;
import com.crowd.coffeeshop.repository.PurchaseRepository;
import com.crowd.coffeeshop.service.ICoffeeService;
import com.crowd.coffeeshop.service.IPurchaseService;

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
public class PurchaseRestController {

	@Autowired
	private IPurchaseService service;
  
	
	@Operation(summary = "Returns a summary with the sales made")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "OK", 
			    content = { @Content(mediaType = "application/json",
			      array = @ArraySchema(schema = @Schema(implementation = PurchaseSummary.class))) }), 
	  @ApiResponse(responseCode = "204", description = "No content", 
	    content = @Content) })
    @GetMapping("/purchases/summary") 
    public ResponseEntity<List<PurchaseSummary>> getAllPurchases() {
		return service.getSummary();
	}

	@Operation(summary = "Add a new purchase")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "OK", 
			    content = { @Content(mediaType = "application/json",
			      schema = @Schema(implementation = Coffee.class)) })
	  })
	@PostMapping("/purchases")
	public ResponseEntity<Purchase> addPurchase(@RequestBody Purchase newPurchase) {
		return service.addPurchase(newPurchase);
	}
	
}