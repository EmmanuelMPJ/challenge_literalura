package com.alura.challenge_literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer anioNacimiento;
    private Integer anioMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@Transient
    private List<Libro> libros;

    public Autor() {
    }
    public Autor(DatosAutor autor) {
        this.nombre = autor.name();
        this.anioNacimiento = autor.birth_year();
        this.anioMuerte = autor.death_year();
    }

    @Override
    public String toString() {
        List<String> tituloLibros = libros.stream()
                .map(Libro::getTitulo)
                .toList();
        return "nombre: " + nombre + '\n' +
                "Fecha de nacimiento: " + anioNacimiento + '\n' +
                "Fecha de defunción: " + anioMuerte + '\n' +
                "Libros: " + tituloLibros + '\n';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioMuerte() {
        return anioMuerte;
    }

    public void setAnioMuerte(Integer anioMuerte) {
        this.anioMuerte = anioMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
