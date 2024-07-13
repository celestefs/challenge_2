package com.example.literalura.model;

public enum Idioma {
    ESPANOL("es", "Español"),
    INGLES("en", "Inglés"),
    FRANCES("fr", "Francés"),
    PORTUGUES("pt", "Portugués");

    private String categoriaGutendex;
    private String categoriaEspanol;

    Idioma (String categoriaGutendex, String categoriaEspanol){
        this.categoriaGutendex = categoriaGutendex;
        this.categoriaEspanol = categoriaEspanol;
    }

    public static Idioma fromString(String text){
        for (Idioma idioma : Idioma.values()) {
            if (idioma.categoriaGutendex.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + text);
    }

    public static Idioma fromEspanol(String text){
        for (Idioma idioma : Idioma.values()) {
            if (idioma.categoriaEspanol.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + text);
    }
}
