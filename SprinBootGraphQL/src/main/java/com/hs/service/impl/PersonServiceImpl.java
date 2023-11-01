package com.hs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hs.dao.PersonRepository;
import com.hs.entity.Person;
import com.hs.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository repository;

	@Override
	public List<Person> findAll() {
		return (List<Person>) repository.findAll();
	}

	@Override
	public Person findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public void saveAll(List<Person> persons) {
		repository.saveAll(persons);
	}
}