package com.yaksha.assignment.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yaksha.assignment.entity.Book;

public class MasterData {

	// Create a sample Book instance
	public static Book getBook() {
		Book book = new Book();
		book.setId(1L); // Setting the ID for the book
		book.setTitle("The Great Gatsby");
		book.setAuthor("F. Scott Fitzgerald");
		return book;
	}

	// Create a list of books
	public static List<Book> getBookList() {
		List<Book> bookList = new ArrayList<>();
		bookList.add(getBook()); // Adding a sample book
		return bookList;
	}

	// Create a page of books
	public static Page<Book> getBookPage(int page, int size) {
		List<Book> bookList = getBookList(); // Get list of books
		PageRequest pageRequest = PageRequest.of(page, size); // Set pagination
		return new PageImpl<>(bookList, pageRequest, bookList.size()); // Return page
	}

	// Convert an object to a JSON string
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Disable writing dates as timestamps
			final String jsonContent = mapper.writeValueAsString(obj); // Convert object to JSON string
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e); // Handle exceptions during JSON conversion
		}
	}

	// Generate a random string of the given size (not used directly in this class)
	public static String randomStringWithSize(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append("A");
		}
		return s.toString(); // Return the generated string
	}
}
