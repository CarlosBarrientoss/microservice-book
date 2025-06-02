package com.relatos_de_papel.book.data.utils;

/**
 * Tipo de operación que podemos aplicar en cada criterio de búsqueda:
 * - GREATER_THAN: >
 * - LESS_THAN: <
 * - GREATER_THAN_EQUAL: >=
 * - LESS_THAN_EQUAL: <=
 * - NOT_EQUAL: !=
 * - EQUAL: ==
 * - MATCH:  LIKE %valor% (para texto)
 * - MATCH_END: LIKE valor% (para texto)
 */
public enum SearchOperation {
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN_EQUAL,
    NOT_EQUAL,
    EQUAL,
    MATCH,
    MATCH_END;
}
