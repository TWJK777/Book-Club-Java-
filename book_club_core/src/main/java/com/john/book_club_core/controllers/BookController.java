package com.john.book_club_core.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.john.book_club_core.services.BookService;



@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userServ;

	
	@RequestMapping("/books")
	public String showbook(@ModelAttribute("book") Book book, Model model, HttpSession s) {
		List<Book> allBooks = bookService.allBooks();
		model.addAttribute("allBooks", allBooks);
		//Route guard - check if user is in session
		Long userId = (Long) s.getAttribute("user_id");
		//check if userID is null
		if (userId == null) {
			return "redirect:/";
		} else {
		User thisLoggedInUser = userServ.findOne(userId);
		model.addAttribute("thisLoggedInUser", thisLoggedInUser);
		return "book.jsp";
		}
	}

	
	//Create BOOK JSP
	@GetMapping("/new")
	public String newBook(@ModelAttribute("book") Book book, Model model, HttpSession s) {
		//Route guard - check if user is in session
		Long userId = (Long) s.getAttribute("user_id");
		//check if userID is null
		if (userId == null) {
			return "redirect:/";
		} else {
		User thisLoggedInUser = userServ.findOne(userId);
		model.addAttribute("thisLoggedInUser", thisLoggedInUser);
		
		
		List<Book> allBooks = bookService.allBooks();
		model.addAttribute("allBooks", allBooks);
		}
	}


	
	// Create Method
	@PostMapping("/books")
	    public String create(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model, HttpSession s) {
	        if (result.hasErrors()) {
	        	
	        	Long userId = (Long) s.getAttribute("user_id");
	        	User thisLoggedInUser = userServ.findOne(userId);
	    		model.addAttribute("thisLoggedInUser", thisLoggedInUser);
	            return "/new.jsp";
	        } else {
	            bookService.createBook(book);
	            return "redirect:/books";
	        }
	}
	
	//Show One Render
		@GetMapping("/books/{id}")
		public String showOne(@PathVariable("id") Long id, Model model) {
			Book oneBook = bookService.findBook(id);
			model.addAttribute("oneBook", oneBook);
			return "/show.jsp";
		}
		
		
		// Edit JSP
		@RequestMapping("/books/{id}/edit")
	    public String edit(@PathVariable("id") Long id, Model model) {
	        Book book = bookService.findBook(id);
	        model.addAttribute("book", book);
	        return "/edit.jsp";
	    }
	    
		
		// EDIT Method
	    @RequestMapping(value="/books/{id}", method=RequestMethod.PUT)
	    public String update(@Valid @ModelAttribute("book") Book book, BindingResult result) {
	        if (result.hasErrors()) {
	            return "/edit.jsp";
	        } else {
	            bookService.updateBook(book);
	            return "redirect:/books";
	        }
	    }
	    
	    
	    //Delete 
	        @RequestMapping(value="/books/{id}/delete", method=RequestMethod.DELETE)
	        public String destroy(@PathVariable("id") Long id) {
	            bookService.deleteBook(id);
	            return "redirect:/books";
	        }
	        
	        
	        ////// conditional render /// show all of the books that the writer create//
	        @GetMapping("/writer")
	        public String writer(Model model, HttpSession s) {
	        	Long userId = (Long) s.getAttribute("user_id");
	        	//check if userID is null
	        	if (userId == null) {
	        		return "redirect:/";
	        	} else {
	        		User thisLoggedInUser = userServ.findOne(userId);
	        		model.addAttribute("thisLoggedInUser", thisLoggedInUser);
	        		return "writer.jsp";
	        	}
	        }
}
