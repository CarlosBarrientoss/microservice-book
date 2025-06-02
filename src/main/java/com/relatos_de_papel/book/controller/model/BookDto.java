package com.relatos_de_papel.book.controller.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para actualizar totalmente un Book con PUT.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookDto {

    private String title;
    private String description;
    private LocalDate publicationDate;
    private String language;
    private String isbn;
    private Integer numberPages;
    private BigDecimal price;
    private Long categoryId;
    private Long authorId;
    private String image;
    private Integer reviewScore;
    private Boolean status;
}
