package com.crowd.coffeeshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.crowd.coffeeshop.model.Coffee;
import com.crowd.coffeeshop.model.Purchase;
import com.crowd.coffeeshop.model.PurchaseSummary;

/**
* Interface for the service component
* @version 1.0
* @author Andres Ruiz
*/


public interface IPurchaseService {

		public ResponseEntity<Purchase> addPurchase(Purchase purchase);

		public ResponseEntity<List<PurchaseSummary>> getSummary();

}