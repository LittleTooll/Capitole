package com.capitole.challenge.control;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capitole.challenge.model.EnumValidation;
import com.capitole.challenge.model.Order;
import com.capitole.challenge.model.Phone;
import com.capitole.challenge.model.Validation;

@RestController
@RequestMapping("/challenge/order")
public class OrderController{

	@Autowired
	private CatalogController catalogController;
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createOrder (@RequestBody(required = true) Order order) {
    	
		Validation val = validate(order);
		
		// Superada la validación
		if (val.getEValidation() == null) {
			order.setTotal(val.getTotal());
			
			// Mostramos por consola la orden
			System.out.println(order);
			
			return ResponseEntity.ok(EnumValidation.OK.descripcion());
		// No superada la validación
		} else {
			return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body(val.getEValidation().descripcion());
		}
		
    }
	
	/**
	 * Comprueba que la orden supere las validaciones identificados y calcula su total
	 * @param order
	 * @return
	 */
    private Validation validate(Order order) {
    	
    	BigDecimal bTotal = BigDecimal.ZERO;
    	
    	// Validamos que el cliente venga relleno
    	if (order.getCustomer() == null)
    		return new Validation (null, EnumValidation.CUSTOMER_EMPTY);
    	
    	// TODO: Acceder por HTTP
    	// Recuperamos el catálogo de teléfonos
    	List<Phone> phones = catalogController.getPhones();

    	// Lo pasamos a un map para que los accesos sean más rápidos
    	Map<String, Phone> map = 
    		    phones.stream().collect(Collectors.toMap(Phone::getId, item -> item));
    	
    	// Validamos que hayamos recibido algún teléfono en la lista
    	List<String> listPhones = order.getListIdsPhones();
    	if (listPhones == null || listPhones.isEmpty()) {
    		return new Validation (null, EnumValidation.PHONES_EMPTY);
    	}
    	
    	// Comprobamos que los teléfonos recibidos existen en el catálogo
    	for (String idPhone : listPhones) {
			if (idPhone == null || !map.containsKey(idPhone)) {
				return new Validation (null, EnumValidation.PHONE_NO_EXIST);				
			} else {
				bTotal = bTotal.add(map.get(idPhone).getPrice());
			}
		}
    	
    	return new Validation(bTotal, null);
    }

}