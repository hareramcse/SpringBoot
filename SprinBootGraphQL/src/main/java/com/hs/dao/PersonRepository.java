package com.hs.dao;

import org.springframework.data.repository.CrudRepository;

import com.hs.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{

	Person findByEmail(String email);

}
