package com.yaksha.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yaksha.assignment.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	// Query method to find books by title
	List<Book> findByTitleContainingIgnoreCase(String title);

	// Custom JPQL query to find books by rating above a threshold
	@Query("SELECT b FROM Book b WHERE b.rating > :rating")
	List<Book> findBooksByRatingAbove(@Param("rating") double rating);

	// Custom query method to find books by author
	List<Book> findByAuthorContainingIgnoreCase(String author);
}
