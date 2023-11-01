package com.hs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hs.entity.Person;
import com.hs.service.PersonService;

import graphql.ExecutionResult;
import graphql.GraphQL;

@RestController
public class PersonController {

	@Autowired
	private GraphQL graphQL;

	@Autowired
	private PersonService personService;

	// It seems this one is needed if we want to use spring boot annotation like
	// @PostMapping, @RequestBody with graphql

	@PostMapping("/addPerson")
	public String addPerson(@RequestBody List<Person> persons) {
		personService.saveAll(persons);
		return "record inserted " + persons.size();
	}

	@GetMapping("/findAllPerson")
	public List<Person> getPersons() {
		return personService.findAll();
	}

	@PostMapping("/getAll")
	public ResponseEntity<Object> getAll(@RequestBody String query) {
		ExecutionResult result = graphQL.execute(query);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@PostMapping("/getPersonByEmail")
	public ResponseEntity<Object> getPersonByEmail(@RequestBody String query) {
		ExecutionResult result = graphQL.execute(query);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
}