package com.crowd.coffeeshop.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* Main entity that uses in the web service
* @version 1.0
* @author Andres Ruiz
*/


@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "PURCHASE_ITEM")
public class PurchaseItem {

//	@EmbeddedId
//	//@JsonIgnore
//	PurchaseItemKey id;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@MapsId("purchaseId")
	@JoinColumn(name = "purchase_id")
	@JsonIgnore
	//@JsonManagedReference
	Purchase purchase;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@MapsId("coffeeId")
	@JoinColumn(name = "coffee_id")
	Coffee coffee;

	@Column(name = "QUANTITY", nullable = false)
	private int quantity;
	
	public PurchaseItem(Purchase purchase, Coffee coffee, int quantity) {
		this.purchase = purchase;
		this.coffee = coffee;
		this.quantity = quantity;
//		this.id = new PurchaseItemKey(purchase.getId(),coffee.getId());
	}

}
