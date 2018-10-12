package com.capitole.challenge.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capitole.challenge.model.Phone;
import com.capitole.challenge.repo.PhoneRepository;

@RestController
@RequestMapping("/challenge/catalog")
public class CatalogController{

	@Autowired
	private PhoneRepository phoneRepo;

	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createPhone (@RequestBody(required = true) Phone phone) {

		phoneRepo.save(phone);
		
		return ResponseEntity.ok("Phone created");
    }
	
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Phone>> getPhones( ) {

    	return ResponseEntity.ok(phoneRepo.findAll());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePhones( ) {
    	
    	phoneRepo.deleteAll();
    	
    	return ResponseEntity.ok("Phones deleted");
    }

}