package com.relatos_de_papel.book.data.repository;

import com.relatos_de_papel.book.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookJpaRepository
        extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {

    /** Buscar libros por título exacto */
    List<Book> findByTitle(String title);

    /** Buscar libros por idioma exacto */
    List<Book> findByLanguage(String language);

    /** Buscar libros que pertenezcan a una categoría (id) */
    List<Book> findByCategoryId(Long categoryId);

    /** Buscar libros escritos por un autor (id) */
    List<Book> findByAuthorId(Long authorId);

    /** Buscar libros cuya puntuación sea exactamente reviewScore */
    List<Book> findByReviewScore(int reviewScore);

    /** Buscar libros cuyo status (activo/inactivo) coincida */
    List<Book> findByStatus(Boolean status);

    /** Buscar libros cuyo número de páginas sea exactamente numberPages */
    List<Book> findByNumberPages(int numberPages);

    /** Buscar libros cuyo precio sea exactamente price */
    List<Book> findByPrice(java.math.BigDecimal price);

    /** Buscar libros que contengan una subcadena en la descripción (SQL LIKE) */
    List<Book> findByDescriptionContaining(String fragment);

    /** Ejemplo combinado: buscar por título _y_ idioma */
    List<Book> findByTitleAndLanguage(String title, String language);

    /** Ejemplo combinado: buscar por categoría _y_ status */
    List<Book> findByCategoryIdAndStatus(Long categoryId, Boolean status);

    /** Ejemplo combinado: buscar por autor e idioma */
    List<Book> findByAuthorIdAndLanguage(Long authorId, String language);
}
