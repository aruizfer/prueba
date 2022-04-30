package com.crowd.coffeeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crowd.coffeeshop.model.Coffee;
import com.crowd.coffeeshop.model.Purchase;

/**
* Repository for coffee entities, with the dynamic methods to get specific data 
* @version 1.0
* @author Andres Ruiz
*/

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query(value = "SELECT c.id, c.name, sum(p.quantity),sum(p.quantity*c.price) FROM PURCHASE_ITEM p, COFFEE c WHERE c.id = p.coffee_id group by c.id,c.name order by c.id", nativeQuery=true)
    List<Object[]> getSummary();
    
}
