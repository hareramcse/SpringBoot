package com.hs.service;

import java.util.List;

import com.hs.entity.Person;

public interface PersonService {
	void saveAll(List<Person> persons);

	List<Person> findAll();

	Person findByEmail(String email);
}
