package com.relatos_de_papel.book.data.model;

import com.relatos_de_papel.book.controller.model.BookDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name= "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Título obligatorio, max 150 caracteres ---
    @NotBlank
    @Size(max = 150)
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    // --- Descripción opcional, max 300 caracteres ---
    @Size(max = 300)
    @Column(name = "description", length = 300)
    private String description;

    // --- Fecha de publicación, tipado como LocalDate ---
    @Column(name = "publication_date")
    private LocalDate publicationDate;

    // --- Idioma obligatorio, max 100 caracteres ---
    @NotBlank
    @Size(max = 100)
    @Column(name = "language", nullable = false, length = 100)
    private String language;

    // --- ISBN opcional, único en la base de datos, max 150 caracteres ---
    @Size(max = 150)
    @Column(name = "isbn", length = 150, unique = true)
    private String isbn;

    // --- Nº de páginas, al menos 1 ---
    @Min(1)
    @Column(name = "number_pages")
    private int numberPages;

    // --- Precio obligatorio, DECIMAL(10,2), al menos 0.0 ---
    @NotNull
    @DecimalMin("0.0")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // --- Clave foránea a Category (solo almacena el ID) ---
    @NotNull
    @Column(name = "fk_id_category", nullable = false)
    private Long categoryId;

    // --- Clave foránea a Author (solo almacena el ID) ---
    @NotNull
    @Column(name = "fk_id_author", nullable = false)
    private Long authorId;

    // --- URL/Path de la imagen, obligatorio, max 300 caracteres ---
    @NotBlank
    @Size(max = 300)
    @Column(name = "image", nullable = false, length = 300)
    private String image;

    // --- Puntuación (0–5) ---
    @Min(0)
    @Max(5)
    @Column(name = "review_score")
    private int reviewScore;

    // --- Estado activo/inactivo del libro ---
    @NotNull
    @Column(name = "status", nullable = false)
    private boolean status;

    /**
     * Actualiza todos los campos de este Book a partir de un DTO.
    */
    public void update(BookDto bookDto) {
        this.title           = bookDto.getTitle();
        this.description     = bookDto.getDescription();
        this.publicationDate = bookDto.getPublicationDate();
        this.language        = bookDto.getLanguage();
        this.isbn            = bookDto.getIsbn();
        this.numberPages     = bookDto.getNumberPages();
        this.price           = bookDto.getPrice();
        this.categoryId      = bookDto.getCategoryId();
        this.authorId        = bookDto.getAuthorId();
        this.image           = bookDto.getImage();
        this.reviewScore     = bookDto.getReviewScore();
        this.status          = bookDto.getStatus();
    }

}
