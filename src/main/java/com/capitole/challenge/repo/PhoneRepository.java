package com.capitole.challenge.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.capitole.challenge.model.Phone;

public interface PhoneRepository  extends MongoRepository<Phone, String> {

}
