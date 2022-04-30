package com.crowd.coffeeshop.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crowd.coffeeshop.exception.CardInvalidException;
import com.crowd.coffeeshop.model.Coffee;
import com.crowd.coffeeshop.model.Purchase;
import com.crowd.coffeeshop.model.PurchaseItem;
import com.crowd.coffeeshop.model.PurchaseSummary;
import com.crowd.coffeeshop.repository.PurchaseRepository;

@Service
public class PurchaseServiceImpl implements IPurchaseService {

	@Autowired
	private PurchaseRepository repository;
	
	@Autowired
	private ICoffeeService service;
	
	@Override
	public ResponseEntity<Purchase> addPurchase(Purchase purchase) {
		// validate that the number of card is not empty and the expiry date is forward
		if (purchase.getCardnumber() == null || purchase.getCardnumber().length() == 0)
			throw new IllegalArgumentException("Card number null or empty");
		if (purchase.getItems().size() == 0)
			throw new IllegalArgumentException("Number of items equals 0");
		
		try {
			Date date1=new SimpleDateFormat("dd/MM/yy").parse("01/"+purchase.getCardexpirydate());
			if (date1.before(new Date()))
				throw new CardInvalidException("Card expiry date invalid " + purchase.getCardexpirydate() );
		} catch (ParseException e1) {
			throw new CardInvalidException("Date format invalid " + purchase.getCardexpirydate() );
		}

		// validate that each coffee exists and quantity is >= 0
		// calculate the final cost
		purchase.setCost(0);
		for (PurchaseItem i : purchase.getItems())
		{
			if (i.getCoffee() == null)
				throw new IllegalArgumentException("Coffee invalid or null");
			Optional<Coffee> coffee = service.findCoffeeById(i.getCoffee().getId());
			if (!coffee.isPresent())
				throw new IllegalArgumentException("Coffee Id invalid " + i.getCoffee().getId());
			if (i.getQuantity() < 0)
				throw new IllegalArgumentException("Coffee quantity invalid " + i.getQuantity());
			purchase.addCost(i.getQuantity()*coffee.get().getPrice());
		}
		purchase.getItems().forEach(e-> System.out.println(e.getCoffee().getId()));
		
		purchase.getItems().forEach(e-> e.setPurchase(purchase));
		Purchase newPurchase = repository.save(purchase);
		return new ResponseEntity<>(newPurchase, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<PurchaseSummary>> getSummary() {
		List<PurchaseSummary> list = new ArrayList<PurchaseSummary>();
		List<Object[]> salida = repository.getSummary();
		for (int i = 0; i < salida.size(); i++)
		{
			Object[] arr = salida.get(i);
			PurchaseSummary ps = new PurchaseSummary();
			ps.setCoffee_id(((BigInteger)arr[0]).toString());
			ps.setCoffee_name((String)arr[1]);
			ps.setTotal_quantity(((BigInteger)arr[2]).toString());
			ps.setRevenue(new BigDecimal((Double)arr[3]).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		
			list.add(ps);
		}
        return (list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(list, HttpStatus.OK));
	}

}
