package com.example.literalura.dto;

import com.example.literalura.model.Libro;

import java.util.List;

public record AutorDTO(
        String nombre,
        String fechaDeNacimiento,
        String fechaDeFallecimiento,
        List<Libro> libros) {
}
