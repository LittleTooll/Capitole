package com.capitole.challenge.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent=true)
@Getter
public enum EnumValidation {
	PHONE_NO_EXIST ("A phone from the list does not exist"),
	PHONES_EMPTY ("Empty phone list"),
	CUSTOMER_EMPTY ("No customer"),
	OK ("Created orden");
	
	private final String descripcion;
}