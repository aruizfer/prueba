package com.crowd.coffeeshop;

import static org.mockito.ArgumentMatchers.anyDouble;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.crowd.coffeeshop.model.Coffee;
import com.crowd.coffeeshop.model.Purchase;
import com.crowd.coffeeshop.model.PurchaseItem;
import com.crowd.coffeeshop.repository.CoffeeRepository;
import com.crowd.coffeeshop.repository.PurchaseRepository;

 
 
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class StandaloneRepositoryTests 
{
	@Autowired
    CoffeeRepository coffeeRepository;
	
	@Autowired
    PurchaseRepository purchaseRepository;
    
    @Test
    public void testCreateReadDelete()
    {
    	coffeeRepository.deleteAll();
        Assertions.assertThat(coffeeRepository.findAll()).isEmpty();
    	
		Coffee coffee1 = new Coffee("Capuchino", anyDouble());

		coffeeRepository.save(coffee1);
		
        //test
        Iterable<Coffee> coffees = coffeeRepository.findAll();
        System.out.println(coffees.toString());
        Assertions.assertThat(coffees).extracting(Coffee::getName).containsOnly("Capuchino");

        purchaseRepository.deleteAll();
        
        Purchase purchase1 = new Purchase();
        purchase1.setCardnumber("1234567890");
        purchase1.setCardexpirydate("05/29");
        purchase1.setCost(50);
		Coffee coffee2 = new Coffee("Capuchino", anyDouble());
        Set<PurchaseItem> ps = new HashSet<PurchaseItem>();
        Coffee coffee3 = coffees.iterator().next();
        System.out.println(coffee3.toString());
        ps.add(new PurchaseItem(purchase1, coffee3,2));
        purchase1.setItems(ps);
        purchaseRepository.save(purchase1);
        
        //coffeeRepository.deleteAll();
        //Assertions.assertThat(coffeeRepository.findAll()).isEmpty();
    }

    
}