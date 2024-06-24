package com.alura.challenge_literalura.principal;

import com.alura.challenge_literalura.model.Autor;
import com.alura.challenge_literalura.model.DatosLibro;
import com.alura.challenge_literalura.model.Libro;
import com.alura.challenge_literalura.repository.iAutorRepository;
import com.alura.challenge_literalura.repository.iLibroRepository;
import com.alura.challenge_literalura.service.ConsumoAPI;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;
import java.util.regex.Matcher;

public class Principal {
    private Scanner scan = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private static String URL_BASE = "https://gutendex.com/books/?search=";
    private List<Libro> libros;
    private List<Autor> autores;
    Gson gson = new Gson();
    private iLibroRepository libroRepository;
    private iAutorRepository autorRepository;

    public Principal(iLibroRepository libroRepository, iAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        loop: while (true) {
            var menu = """
                    ------------------------------------------------
                    1 - Buscar y agregar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Buscar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            String opcion = scan.nextLine();

            switch (opcion) {
                case "1":
                    guardarLibroEncontrado();
                    break;
                case "2":
                    mostrarLibrosRegistrados();
                    break;
                case "3":
                    mostrarAutoresRegistrados();
                    break;
                case "4":
                    mostrarAutoresVivosCiertoAnio();
                    break;
                case "5":
                    mostrarLibrosPorIdioma();
                    break;
                case "0":
                    System.out.println("Cerrando la aplicación...");
                    break loop;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
    private Optional<DatosLibro> getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreSerie = scan.nextLine();
        var jsonStr = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "%20"));
        JsonObject json = JsonParser.parseString(jsonStr).getAsJsonObject();
        JsonArray resultados = json.get("results").getAsJsonArray();
        if (!resultados.isEmpty()) {
            JsonObject primerLibro = resultados.get(0).getAsJsonObject();
            DatosLibro datosPrimerLibro = gson.fromJson(primerLibro, DatosLibro.class);
            System.out.println(primerLibro);
            return Optional.of(datosPrimerLibro);
        }else {
            return Optional.empty();
        }
    }

    private void guardarLibroEncontrado(){
        Optional<DatosLibro> datos = getDatosLibro();
        if (datos.isPresent()){
            DatosLibro datosLibro = datos.get();
            Libro libro = new Libro(datosLibro);
            Autor autor = libro.getAutor();
            Optional<Autor> existingAutor = autorRepository.findByNombre(autor.getNombre());
            if (existingAutor.isPresent()) {
                autor.setId(existingAutor.get().getId()); // Asignar el ID existente al autor del libro
            } else {
                try {
                    autorRepository.save(autor); // Guardar el autor si no existe
                } catch (DataAccessException e) {
                    System.out.println("Error al guardar el autor: " + e.getMessage());
                }
            }
            try {
                if (libroRepository.existsByTitulo(libro.getTitulo())) {
                    System.out.println("El libro ya se encuentra en la base de datos");
                } else {
                    libroRepository.save(libro);
                    System.out.println(libro);
                }
            } catch (DataAccessException e) {
                System.out.println("No se pudo guardar el libro buscado: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontraron datos del libro");
        }

    }

    private void mostrarLibrosRegistrados() {
        libros = libroRepository.findAll();
        if (!libros.isEmpty()){
            libros.forEach(System.out::println);
        }
        else{
            System.out.println("No se encuentran libros registrados en la base de datos");
        }
    }

    private void mostrarAutoresRegistrados() {
        autores = autorRepository.findAll();
        if (!autores.isEmpty()){
            autores.forEach(System.out::println);
        }
        else{
            System.out.println("No se encuentran autores registrados en la base de datos");
        }
    }

    private void mostrarAutoresVivosCiertoAnio() {
        System.out.println("Ingrese año para el que desea buscar");
        boolean inputValido = false;
        int anio = 0;

        while (!inputValido) {
            try {
                anio = scan.nextInt();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
                scan.next(); // Descarta la entrada inválida
            }
        }
        autores = autorRepository.findAutoresVivosEnAnio(anio);
        if (!autores.isEmpty()){
            autores.forEach(System.out::println);
        }
        else{
            System.out.println("No se encuentra algún autor vivo para el año especificado");
        }

    }

    private void mostrarLibrosPorIdioma() {
        var menuIdiomas = """
                    ------------------------------------------------
                    es- Español
                    en- Inglés
                    fr- Francés
                    pt- Portugués
                    """;
        System.out.println(menuIdiomas);
        var codIdioma = scan.nextLine();
        switch (codIdioma){
            case "es", "en", "fr", "pt" -> {
                libros = libroRepository.findByIdioma(codIdioma);
                int cantidadLibros = libros.size();
                if (!libros.isEmpty()){
                    System.out.println("---------------------------------------" + '\n');
                    System.out.printf("Cantidad de libros encontrados en %s: %d%n", codIdioma, cantidadLibros);
                    libros.forEach(System.out::println);
                }
                else{
                    System.out.println("No se encontraron libros registrados en el idioma especificado");
                }
            }
            default -> {
                System.out.println("Opción no válida");
            }
        }
    }


}
