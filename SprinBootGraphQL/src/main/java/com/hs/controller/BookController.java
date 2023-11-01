package com.hs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.hs.dao.BookRepository;
import com.hs.entity.Book;

import lombok.Getter;
import lombok.Setter;

@Controller
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@MutationMapping("createBook")
	public Book create(@Argument BookInput book) {
		Book b = new Book();
		b.setTitle(book.getTitle());
		b.setDesc(book.getDesc());
		b.setPrice(book.getPrice());
		b.setAuthor(book.getAuthor());
		b.setPages(book.getPages());
		return this.bookRepository.save(b);
	}

	@QueryMapping("allBooks")
	public List<Book> getAll() {
		return this.bookRepository.findAll();
	}

	@QueryMapping("getBook")
	public Book get(@Argument int bookId) {
		return this.bookRepository.findById(bookId).get();
	}

}

@Getter
@Setter
class BookInput {
	private String title;
	private String desc;
	private String author;
	private double price;
	private int pages;
}
