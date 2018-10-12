package com.capitole.challenge.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class Order {
	private Customer customer;
	private List<String> listIdsPhones;
	private BigDecimal total;
}
