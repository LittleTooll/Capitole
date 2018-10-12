package com.capitole.challenge.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	private Customer customer;
	private List<String> listIdsPhones;
	private BigDecimal total;
}
