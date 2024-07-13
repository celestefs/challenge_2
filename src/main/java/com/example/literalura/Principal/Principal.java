package com.example.literalura.Principal;

import com.example.literalura.model.*;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import com.example.literalura.service.ConsumoAPI;
import com.example.literalura.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private AutorRepository autorRepository;
    private LibroRepository repositorio;
    private List<Libro> libros;
    private List<Autor> autores;
    private Optional<Libro> libroBuscado;
    public Principal(LibroRepository repository, AutorRepository repositorio){
        this.repositorio = repository;
        this.autorRepository = repositorio;
    }

    public void muestraElMenu(){
        var opcion = -1;
        while(opcion != 0){
            var menu = """
                    Elija la opcion a través de su número:
                    
                    1. Buscar libro por título
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    6. Top 10 libros más descargados

                    
                    0. Salir
                    """;
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    buscarLibrosRegistrados();
                    break;
                case 3:
                    buscarAutoresRegistrados();
                    break;
                case 4:
                    bucarAutoresVivosEnTalAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    topLibrosDescargados();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private Datos getDatosLibro(){
        System.out.println("Escribe el nombre del libro que desees buscar:");
        var nombreLibro = sc.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        //System.out.println(json);
        var datos =  conversor.obtenerDatos(json, Datos.class);
        //System.out.println(datos);
        return datos;
    }

    private void buscarLibroWeb(){
        Datos libroBuscado = getDatosLibro();
        Libro libro = new Libro(libroBuscado);
        repositorio.save(libro);
        System.out.println("*****LIBRO*****\n" +
                "Titulo: " + libro.getTitulo() + "\n" +
                "Autor: " + libro.getAutor().getNombre() + "\n" +
                "Idioma: " + libro.getIdiomas() + "\n" +
                "Numero de descargas: " + libro.getNumeroDeDescargas() + "\n" +
                "***************\n");
    }

    private void buscarLibrosRegistrados(){
        libros = repositorio.findAll();
        libros.stream()
                .forEach(l -> System.out.println("*****LIBRO*****\n" +
                        "Titulo: " + l.getTitulo() + "\n" +
                        "Autor: " + l.getAutor().getNombre() + "\n" +
                        "Idioma: " + l.getIdiomas() + "\n" +
                        "Numero de descargas: " + l.getNumeroDeDescargas() + "\n" +
                        "***************\n"));
    }

    private void buscarAutoresRegistrados(){
       autores = autorRepository.findAllAuthors();
       autores.stream()
               .forEach(a -> System.out.println("*****AUTOR*****\n" +
                       "Autor: " + a.getNombre() +  "\n" +
                       "Fecha de Nacimiento: " + a.getFechaDeNacimiento() + "\n" +
                       "Fecha de Facllecimiento: " + a.getFechaDeFallecimiento() + "\n" +
                       "Libros: " + a.getLibros().stream()
                       .map(l->l.getTitulo())
                       .collect(Collectors.toList()) + "\n" +
                       "***************\n"));
    }

    private void bucarAutoresVivosEnTalAnio(){
        System.out.println("Ingrese el año del cual desee buscar los autores vivos:");
        var opcion = sc.nextInt();
        autores = autorRepository.findAllAuthors();
        autores.stream()
                .filter(a -> {
                    int fechaNacimiento = Integer.parseInt(a.getFechaDeNacimiento());
                    int fechaFallecimiento = (a.getFechaDeFallecimiento() == null) ? Integer.MAX_VALUE : Integer.parseInt(a.getFechaDeFallecimiento());
                    return fechaNacimiento <= opcion && fechaFallecimiento > opcion;
                })
                .forEach(a -> System.out.println("*****AUTOR*****\n" +
                        "Autor: " + a.getNombre() +  "\n" +
                        "Libros: " + a.getLibros().stream()
                        .map(l->l.getTitulo())
                        .collect(Collectors.toList()) + "\n" +
                        "***************\n"));
    }

    private void listarLibrosPorIdioma(){
        System.out.println("Escriba el idioma de los libros que desee buscar: " +
                "es- español\n" +
                "en- inglés\n" +
                "fr- francés\n" +
                "pt- portugués");
        var eleccion = sc.nextLine();
        List<Libro> librosPorIdioma = repositorio.findByIdioma(Arrays.asList(eleccion));
        librosPorIdioma.forEach(l -> System.out.println("*****LIBRO*****\n" +
                "Titulo: " + l.getTitulo() + "\n" +
                "Autor: " + l.getAutor().getNombre() + "\n" +
                "Idioma: " + l.getIdiomas() + "\n" +
                "Numero de descargas: " + l.getNumeroDeDescargas() + "\n" +
                "***************\n"));
    }

    private void topLibrosDescargados() {
        System.out.println("Top 10 libros más descargados:");
        libros = repositorio.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getNumeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.getTitulo().toUpperCase())
                .forEach(System.out::println);
    }
}




//Autor autor = libro.getAutor(); //
//if (autor.getId() == null) {    //
//    autorRepository.save(autor); //
//}


