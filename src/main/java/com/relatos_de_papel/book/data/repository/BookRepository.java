package com.relatos_de_papel.book.data.repository;

import com.relatos_de_papel.book.data.model.Book;
import com.relatos_de_papel.book.data.utils.Consts;
import com.relatos_de_papel.book.data.utils.SearchCriteria;
import com.relatos_de_papel.book.data.utils.SearchOperation;
import com.relatos_de_papel.book.data.utils.SearchStatement;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;    // Usamos StringUtils de Spring, no la de Micrometer

import lombok.RequiredArgsConstructor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final BookJpaRepository repository;  // tu JPA repo que extiende JpaRepository<Book, Long> + JpaSpecificationExecutor

    /**
     * Devuelve todos los libros.
     */
    public List<Book> getBooks() {
        return repository.findAll();
    }

    /**
     * Devuelve un libro por su ID (o null si no existe).
     */
    public Book getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Guarda o actualiza un libro.
     */
    public Book save(Book book) {
        return repository.save(book);
    }

    /**
     * Elimina un libro.
     */
    public void delete(Book book) {
        repository.delete(book);
    }

    /**
     * Búsqueda dinámica sobre los campos:
     *   - title (contiene, MATCH)
     *   - language (igual, EQUAL)
     *   - categoryId (igual, EQUAL)
     *   - status (igual, EQUAL)
     *   - stockMin (mayor que, GREATER_THAN)
     *
     * Si alguno de los parámetros es null/ vacío, se omite en la consulta.
     */
    public List<Book> search(String title,
                             String language,
                             Long categoryId,
                             Boolean status,
                             Integer stockMin) {

        SearchCriteria<Book> spec = new SearchCriteria<>();

        if (StringUtils.hasLength(title)) {
            spec.add(new SearchStatement(Consts.TITLE, title, SearchOperation.MATCH));
        }
        if (StringUtils.hasLength(language)) {
            spec.add(new SearchStatement(Consts.LANGUAGE, language, SearchOperation.EQUAL));
        }
        if (categoryId != null) {
            spec.add(new SearchStatement(Consts.FK_ID_CATEGORY, categoryId, SearchOperation.EQUAL));
        }
        if (status != null) {
            spec.add(new SearchStatement(Consts.STATUS, status, SearchOperation.EQUAL));
        }
        if (stockMin != null) {
            spec.add(new SearchStatement("stock", stockMin, SearchOperation.GREATER_THAN));
        }

        return repository.findAll(spec);
    }
}
