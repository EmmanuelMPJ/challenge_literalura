package com.alura.challenge_literalura.repository;

import com.alura.challenge_literalura.model.Autor;
import com.alura.challenge_literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface iLibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);
    List<Libro> findByIdioma(String idioma);
}
