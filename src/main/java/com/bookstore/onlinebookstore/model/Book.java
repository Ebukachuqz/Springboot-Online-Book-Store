package com.bookstore.onlinebookstore.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Title can only contain Numbers and Letters")
    private String title;

    @NotBlank(message = "ISBN cannot be blank")
    @Pattern(regexp = "^[0-9\\-]+$", message = "ISBN must contain only numbers and dashes")
    private String isbn;

    @NotBlank(message = "Author cannot be blank")
    private String author;

    @NotBlank(message = "Please provide year of publication")
    private int publicationYear;

    @Enumerated(EnumType.STRING)
    private Genre genre;


    private double price;
    private int quantity;

}
