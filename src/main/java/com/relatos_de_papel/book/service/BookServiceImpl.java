package com.relatos_de_papel.book.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.relatos_de_papel.book.controller.model.BookDto;
import com.relatos_de_papel.book.controller.model.CreateBookRequest;
import com.relatos_de_papel.book.data.model.Book;
import com.relatos_de_papel.book.data.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Implementación del servicio de negocio para Book.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public List<Book> getBooks(String title, String language, Long categoryId, Boolean status) {
        // Si se proporcionan criterios de búsqueda, delegamos a search(...)
        if (StringUtils.hasLength(title) ||
                StringUtils.hasLength(language) ||
                categoryId != null ||
                status != null) {

            return repository.search(title, language, categoryId, status);
        }

        // Si no hay filtros, devolvemos todos los libros
        List<Book> all = repository.getBooks();
        return all.isEmpty() ? null : all;
    }

    @Override
    public Book getBook(String bookId) {
        if (!StringUtils.hasLength(bookId)) {
            return null;
        }
        try {
            Long id = Long.valueOf(bookId);
            return repository.getById(id);
        } catch (NumberFormatException e) {
            log.error("ID de libro inválido: {}", bookId, e);
            return null;
        }
    }

    @Override
    public Boolean removeBook(String bookId) {
        Book existing = getBook(bookId);
        if (existing != null) {
            repository.delete(existing);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Book createBook(CreateBookRequest request) {
        // Validación mínima (puede ampliarse con @Valid en el controlador)
        if (request == null ||
                !StringUtils.hasLength(request.getTitle()) ||
                !StringUtils.hasLength(request.getLanguage()) ||
                request.getPrice() == null ||
                request.getCategoryId() == null ||
                request.getAuthorId() == null ||
                !StringUtils.hasLength(request.getImage())) {
            return null;
        }

        // Construcción del nuevo Book
        Book book = Book.builder()
                .title(request.getTitle().trim())
                .description(request.getDescription() == null ? null : request.getDescription().trim())
                .publicationDate(request.getPublicationDate())
                .language(request.getLanguage().trim())
                .isbn(request.getIsbn() == null ? null : request.getIsbn().trim())
                .numberPages(request.getNumberPages())
                .price(request.getPrice())
                .categoryId(request.getCategoryId())
                .authorId(request.getAuthorId())
                .image(request.getImage().trim())
                .reviewScore(request.getReviewScore())
                .status(request.getStatus())       // ← Usamos getStatus() en lugar de isStatus()
                .build();

        return repository.save(book);
    }

    @Override
    public Book updateBook(String bookId, String patchRequest) {
        // PATCH usando JsonMergePatch (RFC 7386)
        Book existing = getBook(bookId);
        if (existing == null) {
            return null;
        }

        try {
            // 1) Convertir el JSON (String) en JsonMergePatch
            JsonMergePatch mergePatch = JsonMergePatch.fromJson(objectMapper.readTree(patchRequest));

            // 2) Convertir el objeto Java “existing” a JsonNode
            JsonNode existingNode = objectMapper.valueToTree(existing);

            // 3) Aplicar el patch (merge) sobre el JsonNode existente
            JsonNode patchedNode = mergePatch.apply(existingNode);

            // 4) Convertir el nodo parchado de vuelta a Book
            Book patchedBook = objectMapper.treeToValue(patchedNode, Book.class);

            // 5) Guardar y retornar
            return repository.save(patchedBook);

        } catch (JsonProcessingException | JsonPatchException e) {
            log.error("Error parchando el libro con ID {}: {}", bookId, e.getMessage());
            return null;
        }
    }

    @Override
    public Book updateBook(String bookId, BookDto updateRequest) {
        Book existing = getBook(bookId);
        if (existing == null || updateRequest == null) {
            return null;
        }

        // Copiar cada campo no nulo desde el DTO al objeto existente
        if (StringUtils.hasLength(updateRequest.getTitle())) {
            existing.setTitle(updateRequest.getTitle().trim());
        }
        if (updateRequest.getDescription() != null) {
            existing.setDescription(updateRequest.getDescription().trim());
        }
        if (updateRequest.getPublicationDate() != null) {
            existing.setPublicationDate(updateRequest.getPublicationDate());
        }
        if (StringUtils.hasLength(updateRequest.getLanguage())) {
            existing.setLanguage(updateRequest.getLanguage().trim());
        }
        if (updateRequest.getIsbn() != null) {
            existing.setIsbn(updateRequest.getIsbn().trim());
        }
        if (updateRequest.getNumberPages() != null) {
            existing.setNumberPages(updateRequest.getNumberPages());
        }
        if (updateRequest.getPrice() != null) {
            existing.setPrice(updateRequest.getPrice());
        }
        if (updateRequest.getCategoryId() != null) {
            existing.setCategoryId(updateRequest.getCategoryId());
        }
        if (updateRequest.getAuthorId() != null) {
            existing.setAuthorId(updateRequest.getAuthorId());
        }
        if (StringUtils.hasLength(updateRequest.getImage())) {
            existing.setImage(updateRequest.getImage().trim());
        }
        if (updateRequest.getReviewScore() != null) {
            existing.setReviewScore(updateRequest.getReviewScore());
        }
        if (updateRequest.getStatus() != null) {
            existing.setStatus(updateRequest.getStatus());
        }

        return repository.save(existing);
    }
}
