package com.crowd.coffeeshop.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Setter
@Getter
@NoArgsConstructor
@Table(name = "PURCHASE")
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@JsonIgnore
	private long id;	

	@Column(name = "CARD_NUMBER", nullable = false)
	@Size(min = 1, max = 20)
	@Schema(example= "9293929218281732", description = "Card number")
	private String cardnumber;

	@Column(name = "CARD_EXPIRY", nullable = false)
	@Size(min = 1, max = 5)
	@Schema(example= "09/25", description = "Card expiry date")
	private String cardexpirydate;

	@Column(name = "COST", nullable = false, precision = 7, scale = 2)
	@PositiveOrZero
	private double cost;

    @OneToMany(mappedBy = "purchase",  cascade = CascadeType.ALL)
    Set<PurchaseItem> items;

	public void addCost(double d) {
		cost += d;
	}
}
