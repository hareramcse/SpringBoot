package com.hs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hs.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
