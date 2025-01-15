# literatura
Este proyecto es un catálogo de libros que permite registrar y gestionar información en una base de datos utilizando tecnologías modernas como Java, Spring, y PostgreSQL
Proyecto Literatura
Este proyecto es un catálogo de libros que permite registrar y gestionar información en una base de datos utilizando tecnologías modernas como Java, Spring, y PostgreSQL. También cuenta con una configuración personalizada de logging para optimizar la depuración y el monitoreo.

Características Principales:

-Registro y consulta de libros y autores.

-Persistencia de datos en una base de datos PostgreSQL.

-Consumo de una API para datos iniciales de libros.

-Configuración personalizada de logging para componentes clave como HikariCP y Hibernate.

-Configuración de Logging

El proyecto incluye una configuración avanzada de logging a través de Logback para mejorar la visibilidad de eventos críticos en el sistema y minimizar la verbosidad innecesaria.

Detalles de la configuración:

HikariCP:

Nivel de logging configurado en INFO para mostrar mensajes informativos relacionados con el pool de conexiones.

Hibernate:
Consultas SQL (org.hibernate.SQL): nivel INFO.

Parámetros bindeados (org.hibernate.type.descriptor.sql.BasicBinder): nivel WARN para evitar registros excesivos.

Generación de esquemas (org.hibernate.tool.hbm2ddl): 
nivel INFO.

Transacciones (org.hibernate.engine.transaction.internal.TransactionImpl): 
nivel INFO.

Conexiones JDBC (org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl): 
nivel INFO.

Propósito de esta configuración:

Reducción de ruido en los logs: Mantener el foco en eventos importantes y relevantes.

Visibilidad de consultas y transacciones: Facilitar la depuración y monitoreo de la interacción con la base de datos.

Optimización del rendimiento: Minimizar los gastos de procesamiento asociados con un logging excesivo.

Tecnologías utilizadas:

Java 17

Spring Framework

Hibernate

PostgreSQL

Logback

Manejo de Datos con DTOs

El proyecto utiliza Data Transfer Objects (DTO) para estructurar y manejar los datos intercambiados entre el cliente y el servidor, facilitando la interacción con APIs externas.

Clase AutorDTO

Esta clase representa a un autor y es utilizada para procesar datos obtenidos de una API externa o internamente en el sistema.

Propiedades:

nombre: Nombre del autor (mapeado de name en la API).

anoNacimiento: Año de nacimiento del autor (mapeado de birth_year en la API).

anoFallecimiento: Año de fallecimiento del autor (mapeado de death_year en la API).

Anotaciones:

@JsonIgnoreProperties(ignoreUnknown = true): Ignora propiedades desconocidas durante la deserialización para evitar errores si la API externa envía más datos de los esperados.

@JsonProperty: Vincula las propiedades del JSON a las variables de la clase, permitiendo un mapeo claro y sencillo de los datos de la API.

Ejemplo de JSON manejado:

json

{
  "name": "Gabriel García Márquez",
  "birth_year": 1927,
  "death_year": 2014
}

Beneficios:

Estandarización: 
Facilita el manejo de datos al estructurarlos de forma clara y consistente.

Integración de APIs: Simplifica la transformación de datos al comunicarse con servicios externos.

Mantenimiento: Permite encapsular la lógica de datos, facilitando cambios futuros en la estructura.

Aplicación de Literatura
Esta aplicación permite interactuar con una base de datos de libros y autores. Los usuarios pueden realizar varias acciones, como buscar libros por título, ver libros y autores registrados, filtrar libros por idioma, listar autores vivos en un año específico y más. La información de los libros y autores se obtiene de una API externa y se almacena en una base de datos.

Funcionalidades del Menú
1. Buscar libro por título
Permite al usuario buscar un libro en una API externa utilizando el título proporcionado. Si el libro está disponible, se registra en la base de datos si aún no existe. Además, se muestra el detalle del libro, como el título, el autor, el idioma y el número de descargas.

2. Listar libros registrados
Muestra una lista de todos los libros registrados en la base de datos con su título, autor, idioma y número de descargas.

3. Listar autores registrados
Muestra una lista de todos los autores registrados, incluyendo su nombre, fecha de nacimiento, fecha de fallecimiento (si está disponible) y los libros que han escrito.

4. Listar autores vivos en un año específico
Permite al usuario ingresar un año y obtener una lista de autores vivos en ese año. Se muestra el nombre del autor, fecha de nacimiento, fecha de fallecimiento (si está disponible) y el número de libros que ha escrito.

5. Listar libros por idioma
El usuario puede consultar los libros registrados en la base de datos filtrados por idioma. Los idiomas disponibles son español, inglés, francés y portugués. El menú mostrará los libros correspondientes al idioma seleccionado, junto con su título, autor, idioma y número de descargas.

0. Salir
Permite salir de la aplicación con un mensaje de despedida.

Estructura del Proyecto

Clases Principales:

Principal: Esta clase contiene el método mostrarMenu(), que se encarga de mostrar el menú y procesar las opciones seleccionadas por el usuario.

LibroService: Maneja las operaciones relacionadas con los libros, como registrarlos y listarlos.

AutorService: Gestiona las operaciones relacionadas con los autores.

ConsumoAPI: Se encarga de consumir la API externa para obtener los datos de libros.

ConvierteDatos: Convierte los datos JSON de la API a objetos DTO correspondientes.

Componentes:

DTOs (Data Transfer Objects): Clases como LibroDTO, AutorDTO y RespuestaLibrosDTO son utilizadas para transferir datos entre las distintas capas de la aplicación.

Modelos: Clases como Libro y Autor representan las entidades principales en la base de datos.

Instrucciones de Uso

Ejecuta la aplicación.

Selecciona una opción del menú:

Ingresa el título de un libro para buscarlo y registrarlo.

Consulta los libros y autores registrados.

Filtra libros por idioma o lista autores vivos en un año específico.

Sigue las instrucciones para cada opción.

Para salir, selecciona la opción 0.

Dependencias

La aplicación utiliza las siguientes dependencias:

-Spring Boot: Para gestionar la estructura de la aplicación.
-Scanner: Para la entrada de datos por consola.
-URLEncoder: Para codificar correctamente las entradas del usuario al consultar la API.
