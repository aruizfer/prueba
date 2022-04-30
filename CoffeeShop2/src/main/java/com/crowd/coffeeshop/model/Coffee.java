package com.crowd.coffeeshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

//@Data
@Entity
@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "COFFEE")
public class Coffee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@JsonIgnore
	private long id;	

	@Column(name = "NAME", nullable = false)
	@Size(min = 1, max = 50)
	private String name;

	@Column(name = "PRICE", nullable = false, precision = 5, scale = 2)
	@PositiveOrZero
	private double price;

	public Coffee(String name, double price) {
		this.name = name;
		this.price = price;
	}

}
