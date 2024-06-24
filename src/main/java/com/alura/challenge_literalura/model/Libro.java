package com.alura.challenge_literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idioma;
    private Long cantidadDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.title();
        this.cantidadDescargas = datosLibro.download_count();
        this.idioma = datosLibro.languages() == null || datosLibro.languages().isEmpty() ? "NA" : datosLibro.languages().get(0);

        if (datosLibro.authors() == null || datosLibro.authors().isEmpty()) {
            this.autor = null;
        } else {
            this.autor = new Autor(datosLibro.authors().get(0));
        }

    }
    @Override
    public String toString() {
        return "-----------------Libro-----------------" +'\n' +
                "titulo: " + titulo + '\n' +
                "authors: " + (autor != null ? autor.getNombre() : "N/A")+ '\n' +
                "idioma: " + idioma + '\n' +
                "Cantidad de descargas: " + cantidadDescargas + '\n' +
                "---------------------------------------" +'\n';

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Long cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }
}
