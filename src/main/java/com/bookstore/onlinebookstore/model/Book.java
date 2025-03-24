package com.bookstore.onlinebookstore.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Title can only contain Numbers and Letters")
    private String title;

    @NotBlank(message = "ISBN cannot be blank")
    @Pattern(regexp = "^[0-9\\-]+$", message = "ISBN must contain only numbers and dashes")
    private String isbn;

    @NotBlank(message = "Author cannot be blank")
    private String author;

    @NotNull(message = "Please provide year of publication")
    private int publicationYear;

    @Enumerated(EnumType.STRING)
    private Genre genre;


    private double price;
    private int quantity;

}
