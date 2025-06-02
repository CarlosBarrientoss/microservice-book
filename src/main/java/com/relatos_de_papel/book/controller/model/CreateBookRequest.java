package com.relatos_de_papel.book.controller.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Request que llega desde el cliente para crear un Book.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {

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
