package com.yaksha.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yaksha.assignment.entity.Book;
import com.yaksha.assignment.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	// Endpoint to fetch all books
	@GetMapping
	public ResponseEntity<List<Book>> getBooks() {
		return ResponseEntity.ok(bookRepository.findAll()); // 200 OK
	}

	// Endpoint to fetch a book by its ID
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		return bookRepository.findById(id).map(book -> ResponseEntity.ok(book)) // 200 OK
				.orElseGet(() -> ResponseEntity.notFound().build()); // 404 Not Found
	}

	// Endpoint to fetch books by title
	@GetMapping("/search/title/{title}")
	public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title) {
		List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
		return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books); // 200 OK
	}

	// Endpoint to fetch books with a rating above a certain threshold
	@GetMapping("/search/rating/{rating}")
	public ResponseEntity<List<Book>> getBooksByRatingAbove(@PathVariable double rating) {
		List<Book> books = bookRepository.findBooksByRatingAbove(rating);
		return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books); // 200 OK
	}

	// Endpoint to fetch books by author
	@GetMapping("/search/author/{author}")
	public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
		List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
		return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books); // 200 OK
	}
}
