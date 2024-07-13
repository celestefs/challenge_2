package com.example.literalura.dto;

import com.example.literalura.model.Autor;
import com.example.literalura.model.Idioma;

import java.util.List;

public record LibroDTO(
        Long id,
        String titulo,
        Double numeroDeDescargas,
        List<String> idioma,
        Autor autor) {
}
