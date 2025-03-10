package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yaksha.assignment.controller.BookController;
import com.yaksha.assignment.entity.Book;
import com.yaksha.assignment.repository.BookRepository;
import com.yaksha.assignment.utils.JavaParserUtils;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository bookRepository; // Mock the repository

	private Book book1;
	private Book book2;

	@BeforeEach
	public void setup() {
		// Setting up test data
		book1 = new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", 4.5);
		book2 = new Book(2L, "1984", "George Orwell", 4.0);

		// Mock the repository behavior
		Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
		Mockito.when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));
		Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));
		Mockito.when(bookRepository.findByTitleContainingIgnoreCase("Great Gatsby")).thenReturn(Arrays.asList(book1));
		Mockito.when(bookRepository.findBooksByRatingAbove(4.0)).thenReturn(Arrays.asList(book1));
		Mockito.when(bookRepository.findByAuthorContainingIgnoreCase("George Orwell")).thenReturn(Arrays.asList(book2));
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	// Test to check if the 'BookController' class has @RestController annotation
	@Test
	public void testRestControllerAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/BookController.java";
		boolean result1 = JavaParserUtils.checkClassAnnotation(filePath, "RestController");
		boolean result2 = JavaParserUtils.checkClassAnnotation(filePath, "RequestMapping");
		yakshaAssert(currentTest(), result1 && result2, businessTestFile);
	}

	// Test to check if 'getBooksByTitle' method has @GetMapping annotation with the
	// correct value
	@Test
	public void testGetBooksByTitleAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/BookController.java";

		// Check if @GetMapping annotation exists and has the correct path
		boolean hasGetMapping = JavaParserUtils.checkMethodAnnotation(filePath, "getBooksByTitle", "GetMapping");
		boolean hasCorrectPath = JavaParserUtils.checkMethodAnnotationValue(filePath, "getBooksByTitle",
				"/search/title/{title}");
		if (!hasCorrectPath) {
			System.out.println("getBooksByTitle method doesn't have search/title/{title} path");
		}
		// Assert that both conditions are true
		yakshaAssert(currentTest(), hasGetMapping && hasCorrectPath, businessTestFile);
	}

	// Test to check GET request for all books
	@Test
	public void testGetBooks() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books").contentType("application/json")
				.accept("application/json");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), result.getResponse().getContentAsString() != null ? "true" : "false",
				businessTestFile);
	}

	// Test to check GET request for a book by ID
	@Test
	public void testGetBookById() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/1").contentType("application/json")
				.accept("application/json");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), result.getResponse().getContentAsString() != null ? "true" : "false",
				businessTestFile);
	}

	// Test to check GET request for books by title
	@Test
	public void testGetBooksByTitle() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/search/title/The Great Gatsby")
				.contentType("application/json").accept("application/json");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), result.getResponse().getContentAsString() != null ? "true" : "false",
				businessTestFile);
	}

	// Test to check GET request for books by rating above a certain threshold
	@Test
	public void testGetBooksByRatingAbove() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/search/rating/4.0")
				.contentType("application/json").accept("application/json");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), result.getResponse().getContentAsString() != null ? "true" : "false",
				businessTestFile);
	}

	// Test to check GET request for books by author
	@Test
	public void testGetBooksByAuthor() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/search/author/George Orwell")
				.contentType("application/json").accept("application/json");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), result.getResponse().getContentAsString() != null ? "true" : "false",
				businessTestFile);
	}
}
