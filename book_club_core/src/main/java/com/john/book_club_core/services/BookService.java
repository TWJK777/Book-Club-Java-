package com.john.book_club_core.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.book_club_core.models.Book;
import com.john.book_club_core.repo.BookRepository;


@Service
public class BookService {

	
	// adding the book repository as a dependency
	@Autowired
	private BookRepository bookRepository;
	
	
	
	
	// READ ALL
	public List<Book> allBooks() {
		return bookRepository.findAll();
	}
	
	
	//CREATE
	public Book createBook(Book b) {
		return bookRepository.save(b);
	}
	
	
	//READ ONE
	public Book findBook(Long id) {
		Optional<Book> optionalBook = bookRepository.findById(id);
		if(optionalBook.isPresent()) {
			return optionalBook.get();
		} else {
			return null;
		}
		
	}
	
	
	//UPDATE
	public Book updateBook(Book b) {
		return bookRepository.save(b);
	}
	
	
	// Delete
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}
}

