package com.capitole.challengue;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.capitole.challenge.Application;
import com.capitole.challenge.model.Customer;
import com.capitole.challenge.model.EnumValidation;
import com.capitole.challenge.model.Order;
import com.capitole.challenge.model.Phone;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChallengeTest {

    private static final int NUM_PHONES_CATALOG = 10;
	private static final String URL_CATALOG = "/challenge/catalog";
	private static final String URL_ORDER = "/challenge/order";

	@Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    
    /**
     * Reiniciamos el catálogo de teléfonos (eliminamos los existentes y creamos N nuevos)
     */
    @Before
    public void initCatalog() {

    	// delete
    	restTemplate.delete(URL_CATALOG);
    	
    	// create catalog
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	for (int i = 1; i < NUM_PHONES_CATALOG; i++) {
       		Phone phone = new Phone(Integer.toString(i), "image" + i + ".jpg", "Phone" + i, "Description" + i, new BigDecimal(i));
            	
    		HttpEntity<Phone> entity = new HttpEntity<Phone>(phone, headers);

    		restTemplate.exchange(
    				URL_CATALOG,
    				HttpMethod.POST, entity, String.class);
			
    	}
    }

    /**
     * Comprobamos que el catálogo tiene teléfonos
     */
    @Test
    public void catalogWithPhones() {

    	ResponseEntity<List<Phone>> response = restTemplate.exchange(
    	  URL_CATALOG,
    	  HttpMethod.GET,
    	  null,
    	  new ParameterizedTypeReference<List<Phone>>(){});
    	
    	List<Phone> phones = response.getBody();
    	
    	assertThat(phones, not(IsEmptyCollection.empty()));
    }

    /**
     * Comprobamos que la orden es correcta
     */
    @Test
    public void orderOk() {

		String idsPhones[] = new String[] { "1", "3", "5" };
		Order order = new Order(new Customer("Felipe", "Perea", "felipe.perea.toledo@gmail.com"),
				Arrays.asList(idsPhones), null);

		HttpEntity<Order> entity = new HttpEntity<Order>(order, headers);

		ResponseEntity<String> exchange = restTemplate.exchange(URL_ORDER, HttpMethod.POST, entity, String.class);
		
		assertEquals(exchange.getStatusCodeValue(), HttpStatus.OK.value());
    }

    /**
     * Comprobamos que está bien controlado que no llegue el cliente en la orden
     */
    @Test
    public void orderWithoutCustomer() {

		String idsPhones[] = new String[] { "1", "3", "5" };
		Order order = new Order(null, Arrays.asList(idsPhones), null);

		HttpEntity<Order> entity = new HttpEntity<Order>(order, headers);

		ResponseEntity<String> exchange = restTemplate.exchange(URL_ORDER, HttpMethod.POST, entity, String.class);
		
		assertEquals(exchange.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
		assertEquals(exchange.getBody(), EnumValidation.CUSTOMER_EMPTY.descripcion());
    }

    /**
     * Comprobamos que esté bien controlado que no lleguen teléfonos en la orden
     */
    @Test
    public void orderWithoutPhones() {

		String idsPhones[] = {};
		Order order = new Order(new Customer("Felipe", "Perea", "felipe.perea.toledo@gmail.com"),
				Arrays.asList(idsPhones), null);

		HttpEntity<Order> entity = new HttpEntity<Order>(order, headers);

		ResponseEntity<String> exchange = restTemplate.exchange(URL_ORDER, HttpMethod.POST, entity, String.class);
		
		assertEquals(exchange.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
		assertEquals(exchange.getBody(), EnumValidation.PHONES_EMPTY.descripcion());
    }

    /**
     * Comprobamos que esté bien controlado que llegue algún teléfono incorrecto en la orden
     */
    @Test
    public void orderWithPhonesBad() {

		String idsPhones[] = new String[] { "999"};
		Order order = new Order(new Customer("Felipe", "Perea", "felipe.perea.toledo@gmail.com"),
				Arrays.asList(idsPhones), null);

		HttpEntity<Order> entity = new HttpEntity<Order>(order, headers);

		ResponseEntity<String> exchange = restTemplate.exchange(URL_ORDER, HttpMethod.POST, entity, String.class);
		
		assertEquals(exchange.getStatusCodeValue(), HttpStatus.BAD_REQUEST.value());
		assertEquals(exchange.getBody(), EnumValidation.PHONE_NO_EXIST.descripcion());
    }

}