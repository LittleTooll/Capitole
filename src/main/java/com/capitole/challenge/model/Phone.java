package com.capitole.challenge.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Phone {

	@Id private String id;
	
	private String image;
	private String name;
	private String description;
	private BigDecimal price;
}
