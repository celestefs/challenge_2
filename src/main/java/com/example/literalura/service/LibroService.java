package com.example.literalura.service;

import com.example.literalura.dto.AutorDTO;
import com.example.literalura.dto.LibroDTO;
import com.example.literalura.model.Autor;
import com.example.literalura.model.Idioma;
import com.example.literalura.model.Libro;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository repository;
    @Autowired
    private AutorRepository repositorio;

    @GetMapping("/books")
    public List<LibroDTO> obtenerTodosLosLibros() {
        return convertirLibroALibroDTO(repository.findAll());
    }

    public LibroDTO findById(Long id) {
        Optional<Libro> libro = repository.findById(id);
        if(libro.isPresent()){
            Libro l = libro.get();
            return new LibroDTO(l.getId(), l.getTitulo(),
                    l.getNumeroDeDescargas(), l.getIdiomas(), l.getAutor());
        }
        return null;
    }

    public List<AutorDTO> obtenerAutoresRegistrados() {
        return convertirAutorAAutorDTO(repositorio.findAllAuthors());
    }

    public List<LibroDTO> findByLanguage(List<String> tipoIdioma) {
        return convertirLibroALibroDTO(repository.findByIdioma(tipoIdioma));
    }

    public List<AutorDTO> buscarAutoresVivosEnTalAnio(Integer anioTope){
        var autores = repositorio.findAllAuthors();
        return autores.stream()
                .filter(a -> Integer.valueOf(a.getFechaDeNacimiento()) <= anioTope && (a.getFechaDeFallecimiento() == null
                        || Integer.valueOf(a.getFechaDeFallecimiento()) > anioTope))
                .map(a -> new AutorDTO(a.getNombre(), a.getFechaDeNacimiento(), a.getFechaDeFallecimiento(), a.getLibros()))
                .collect(Collectors.toList());
    }

    public AutorDTO obtenerAutorPorNombre(String nombre) {
        Optional<Autor> autor = repositorio.findByNombreContainingIgnoreCase(nombre);
        if(autor.isPresent()){
            Autor a = autor.get();
            return new AutorDTO(a.getNombre(), a.getFechaDeNacimiento(), a.getFechaDeFallecimiento(), a.getLibros());
        }
        return null;
    }
/*
    public LibroDTO obtenerLibroPortitulo(String nombreLibro) {
        Optional<Libro> libro = repository.findByTituloContainingIgnoreCase(nombreLibro);
        if(libro.isPresent()){
            Libro l = libro.get();
            return new LibroDTO(l.getId(), l.getTitulo(),
                    l.getNumeroDeDescargas(), l.getIdiomas(), l.getAutor());
        }
        return null;
    }*/

    public List<LibroDTO> convertirLibroALibroDTO(List<Libro> libros){
        return libros.stream()
                .map(l -> new LibroDTO(l.getId(), l.getTitulo(),
                        l.getNumeroDeDescargas(), l.getIdiomas(), l.getAutor()))
                .collect(Collectors.toList());
    }

    public List<AutorDTO> convertirAutorAAutorDTO(List<Autor> autores){
        return autores.stream()
                .map(a -> new AutorDTO(a.getNombre(), a.getFechaDeNacimiento(), a.getFechaDeFallecimiento(), a.getLibros()))
                .collect(Collectors.toList());
    }

}
