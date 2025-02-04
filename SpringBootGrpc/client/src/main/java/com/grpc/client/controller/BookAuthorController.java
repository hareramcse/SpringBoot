package com.grpc.client.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.Descriptors;
import com.grpc.client.service.BookAuthorClientService;

@RestController
public class BookAuthorController {

	@Autowired
	private BookAuthorClientService bookAuthorClientService;

	@GetMapping("/author/{id}")
	public Map<Descriptors.FieldDescriptor, Object> getAuthor(@PathVariable String id) {
		return bookAuthorClientService.getAuthor(Integer.parseInt(id));
	}

	@GetMapping("/book/{author_id}")
	public List<Map<Descriptors.FieldDescriptor, Object>> getBookByAuthor(@PathVariable Integer author_id)
			throws InterruptedException {
		return bookAuthorClientService.getBooksByAuthor(author_id);
	}

	@GetMapping("/bookexpensive")
	public Map<String, Map<Descriptors.FieldDescriptor, Object>> getExpensiveBook() throws InterruptedException {
		return bookAuthorClientService.getExpensiveBook();
	}

	@GetMapping("/book/author/{gender}")
	public List<Map<Descriptors.FieldDescriptor, Object>> getBookByGender(@PathVariable String gender)
			throws InterruptedException {
		return bookAuthorClientService.getBooksByGender(gender);
	}
}
