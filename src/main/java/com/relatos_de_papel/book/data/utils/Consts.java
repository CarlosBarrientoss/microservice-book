package com.relatos_de_papel.book.data.utils;

/**
 * Clase de constantes para nombres de columnas (usados en Specifications y en @Column(name=...)).
 */
public final class Consts {

    private Consts() {
        // No permitir instanciación
    }

    public static final String TITLE            = "title";
    public static final String DESCRIPTION      = "description";
    public static final String PUBLICATION_DATE = "publication_date";
    public static final String LANGUAGE         = "language";
    public static final String ISBN             = "isbn";
    public static final String NUMBER_PAGES     = "numberPages";   // ojo: en Specification usamos la propiedad Java, no la columna SQL
    public static final String PRICE            = "price";
    public static final String FK_ID_CATEGORY   = "categoryId";    // coincide con nombre del campo en entidad
    public static final String FK_ID_AUTHOR     = "authorId";
    public static final String IMAGE            = "image";
    public static final String REVIEW_SCORE     = "reviewScore";
    public static final String STATUS           = "status";
}
