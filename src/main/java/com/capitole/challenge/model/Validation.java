package com.capitole.challenge.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Validation {
	
	private BigDecimal total;
	private EnumValidation eValidation;
}
