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

@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class PurchaseSummary {

	private String coffee_id;

	private String coffee_name;

	private String total_quantity;
	
	private String revenue;

}
