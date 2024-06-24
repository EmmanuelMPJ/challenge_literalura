# Conversor de moneda

El proyecto LiterAlura es una aplicación de consola basada en Spring Boot que permite acceder a una vasta colección de datos sobre libros a través de la API de Gutendex.com. Con esta aplicación, los usuarios pueden extraer información de libros desde la API y almacenarla en una base de datos para su posterior uso. Además, los usuarios pueden realizar consultas sobre sus libros guardados por título, género, idioma y autor. La aplicación también cuenta con una función que permite buscar autores que vivieron más allá de una fecha determinada ingresada por el usuario.

Agradecemos especialmente a Gutendex.com por proporcionar los datos de los libros y a la comunidad de Spring Boot por ofrecer un marco de trabajo tan robusto.

# Tecnologías utilizadas
- Java: The core programming language used for developing the application.

- API Integration: Utilized an external API to retrieve real-time exchange rates.

- Spring Boot 3.2

- Gutendex.com API

- Database (PostgreSQL)

# Cómo usar
1. Clona el repositorio localmente.
2. Abre el proyecto en tu IDE de Java o editor de texto.
3. Ejecuta la aplicación ChallengeLiteraluraApplication.
5. Sigue las indicaciones que aparecerán en la terminal.

# :hammer:Funcionalidades del proyecto
- `Menú`: Contiene un menú que se muestra en consola, en el que se pide al usuario ingresar un numero entre 0 y 5 para elegir la acción que quiere realizar.
- `: Se crearon los metodos y clases necesarias para la solicitud de la información a la API y el correcto tratamiento de esta información para poder ser usada.
- `Consumo de la API y Persistencia de los datos de los libros consultados (Opción 2)`: Se crearon los metodos y clases necesarias para la solicitud de la información a la API y el correcto tratamiento de esta información para poder ser usada. Al hacer la busqueda de un libro en gutendex, este se guarda en la base de datos que tengas en PostgreSQL.
- `Listar libros registrados (Opción 2)`: Se muestran en consola los libros que se hayan guardado en la base de datos.
- `Listar autores registrados (Opción 3)`: Se muestran en consola los autores que se hayan guardado en la base de datos dependiendo de los libros que se hayan registrado.
- `Listar autores vivos en un determinado año`: Se le pide al usuario ingresar un año y se muestran en consola los autores que hayan estado vivos en dicho año (si los hay).
- `Buscar libros por idioma`: Se le pide al usuario que ingrese uno de los codigos de idioma (es, en, fr, pt) y se muestra en consola la cantidad de libros y los libros que están guardados en dicho idioma.
- `Acción de salir`: Cuando el usuario haya culminado con el uso del programa se agregó una opción para terminar este mismo (opción 0).


# Autor
[<img src="https://avatars.githubusercontent.com/u/168949963?v=4" width=115><br><sub>Emmanuel Paternina</sub>](https://github.com/EmmanuelMPJ)