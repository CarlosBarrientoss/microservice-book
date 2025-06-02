package com.relatos_de_papel.book.controller;

import com.relatos_de_papel.book.controller.model.BookDto;
import com.relatos_de_papel.book.controller.model.CreateBookRequest;
import com.relatos_de_papel.book.data.model.Book;
import com.relatos_de_papel.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * REST Controller para exponer operaciones CRUD de Book.
 */
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean status) {

        log.info("Buscando libros con filtros: title={}, language={}, categoryId={}, status={}",
                title, language, categoryId, status);

        List<Book> result = bookService.getBooks(title, language, categoryId, status);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") String id) {
        log.info("Buscando libro con ID {}", id);
        Book b = bookService.getBook(id);
        return (b != null) ? ResponseEntity.ok(b) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        Boolean removed = bookService.removeBook(id);
        return (Boolean.TRUE.equals(removed)) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody @Valid CreateBookRequest request) {
        Book created = bookService.createBook(request);
        return (created != null)
                ? ResponseEntity.status(201).body(created)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> patch(@PathVariable("id") String id,
                                      @RequestBody String patchBody) {
        Book patched = bookService.updateBook(id, patchBody);
        return (patched != null)
                ? ResponseEntity.ok(patched)
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable("id") String id,
                                       @RequestBody BookDto dto) {
        Book updated = bookService.updateBook(id, dto);
        return (updated != null)
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }
}
