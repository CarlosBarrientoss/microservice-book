package com.relatos_de_papel.book.data.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Cada SearchStatement representa un “predicado” simple:
 *   - key:        nombre de la propiedad Java (ej. "title", "price", "status", etc.)
 *   - value:      valor contra el que compararemos
 *   - operation:  tipo de operación (EQUAL, MATCH, GREATER_THAN, etc.)
 */
@AllArgsConstructor
@Getter
@Setter
public class SearchStatement {
    private String key;
    private Object value;
    private SearchOperation operation;
}
