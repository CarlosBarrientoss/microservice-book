package com.relatos_de_papel.book.service;

import com.relatos_de_papel.book.controller.model.BookDto;
import com.relatos_de_papel.book.controller.model.CreateBookRequest;
import com.relatos_de_papel.book.data.model.Book;

import java.util.List;

/**
 * Define las operaciones de negocio para la entidad Book.
 */

public interface BookService {

    /**
     * Devuelve todos los libros, o aplica filtros si se proporcionan criterios.
     * @param title       Filtrar por título (contenga).
     * @param language    Filtrar por idioma (igual).
     * @param categoryId  Filtrar por ID de categoría.
     * @param status      Filtrar por estado (activo/inactivo).
     * @return Lista de libros que cumplen los filtros (o todos si no hay filtros).
     */
    List<Book> getBooks(String title, String language, Long categoryId, Boolean status);

    /**
     * Busca un libro por su ID (en formato String). Retorna null si no existe o el ID es inválido.
     */
    Book getBook(String bookId);

    /**
     * Elimina un libro por su ID. Retorna true si se borró correctamente, false si no existía.
     */
    Boolean removeBook(String bookId);

    /**
     * Crea un nuevo libro a partir de los datos del request. Retorna el Book recién guardado (con ID asignado),
     * o null si faltan campos obligatorios.
     */
    Book createBook(CreateBookRequest request);

    /**
     * Aplica un parche parcial (JSON Merge Patch, RFC 7386) al libro cuyo ID coincide con bookId.
     * @param bookId        ID del libro en String.
     * @param patchRequest  JSON con los campos a actualizar.
     * @return El Book parchado y guardado, o null si no existe o hubo error.
     */
    Book updateBook(String bookId, String patchRequest);

    /**
     * Actualiza por completo (PUT) un libro existente con los datos del DTO. Solo sobrescribe los campos no nulos.
     * @param bookId         ID del libro en String.
     * @param updateRequest  DTO con los nuevos valores de cada campo.
     * @return El Book actualizado, o null si no existe.
     */
    Book updateBook(String bookId, BookDto updateRequest);
}
