package com.yaksha.assignment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Book {

	@Id
	private Long id;

	@NotNull(message = "Title cannot be null")
	@Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
	private String title;

	@NotNull(message = "Author cannot be null")
	@Size(min = 2, max = 100, message = "Author must be between 2 and 100 characters")
	private String author;

	private double rating; // New field for rating

	// Getters and Setters

	public Book() {
	}

	public Book(Long id, String title, String author, double rating) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
}
