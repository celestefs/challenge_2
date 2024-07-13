package com.example.literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private List<String> idioma;
    private Double numeroDeDescargas;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro(){}

    public Libro(Datos datos){
        List<DatosLibro> resultados = datos.resultados();
        if (!resultados.isEmpty()) {
            DatosLibro primerResultado = resultados.get(0);
            this.titulo = primerResultado.titulo();
            this.idioma = primerResultado.idiomas();
            this.numeroDeDescargas = primerResultado.numeroDeDescargas();
            // ver como agregar el autor de objeto autor a lista de datosautor

            List<DatosAutor> autoresDatos = primerResultado.autor();
            if (!autoresDatos.isEmpty()) {
                DatosAutor primerAutor = autoresDatos.get(0);
                this.autor = new Autor(primerAutor); //
                //Autor autor = new Autor(primerAutor); //
                //this.setAutor(autor); //
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idioma;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idioma = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
        if (!autor.getLibros().contains(this)) {
            autor.getLibros().add(this);
        }
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma=" + idioma +
                ", numeroDeDescargas=" + numeroDeDescargas +
                ", autor=" + autor +
                '}';
    }
}
