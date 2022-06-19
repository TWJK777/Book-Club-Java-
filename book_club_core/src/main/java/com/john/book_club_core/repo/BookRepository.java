package com.john.book_club_core.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.john.book_club_core.models.Book;



@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	List<Book> findAll();
}

