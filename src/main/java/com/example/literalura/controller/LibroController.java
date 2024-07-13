package com.example.literalura.controller;

import com.example.literalura.dto.AutorDTO;
import com.example.literalura.dto.LibroDTO;
import com.example.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class LibroController {

    @Autowired
    private LibroService servicio;

    // para buscar todos los libros
    @GetMapping()
    public List<LibroDTO> obtenerTodosLosLibros() {
        return servicio.obtenerTodosLosLibros();
    }

    // para buscar libros por su id: /books/{id}
    @GetMapping("/{id}")
    public LibroDTO obtenerLibroPorId(@PathVariable Long id){
        return servicio.findById(id);
    }

    // para buscar libros por su idioma: /books?languages={idioma}
    @GetMapping("/?languages={tipoIdioma}")
    public List<LibroDTO> obtenerLibrosPorIdioma(@PathVariable List<String> tipoIdioma){
        return servicio.findByLanguage(tipoIdioma);
    }

    // para buscar todos los autores registrados:
    @GetMapping("/authors")
    public List<AutorDTO> obtenerAutoresRegistrados(){
        return servicio.obtenerAutoresRegistrados();
    }

    // para buscar libros segun fecha en que autor estaba vivo: /?author_year_start={anioTope}
    @GetMapping("/?author_year_start={anioTope}")
    public List<AutorDTO> obtenerAutoresVivosEnTalAnio(@PathVariable Integer anioTope){
        return servicio.buscarAutoresVivosEnTalAnio(anioTope);
    }

    // para buscar libro/autor por nombre:
   /* @GetMapping("/?search={nombre}")
    public AutorDTO obtenerPorNombre(@PathVariable String nombre){
        return servicio.obtenerAutorPorNombre(nombre);
    }

    // para buscar libro por nombre:
    @GetMapping("/?search={titulo}")
    public LibroDTO obtenerPorTitulo(@PathVariable String nombreLibro){
        return servicio.obtenerLibroPortitulo(nombreLibro);
    }*/
}
