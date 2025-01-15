# literatura
Este proyecto es un catálogo de libros que permite registrar y gestionar información en una base de datos utilizando tecnologías modernas como Java, Spring, y PostgreSQL
Proyecto Literatura
Este proyecto es un catálogo de libros que permite registrar y gestionar información en una base de datos utilizando tecnologías modernas como Java, Spring, y PostgreSQL. También cuenta con una configuración personalizada de logging para optimizar la depuración y el monitoreo.

Características Principales
Registro y consulta de libros y autores.
Persistencia de datos en una base de datos PostgreSQL.
Consumo de una API para datos iniciales de libros.
Configuración personalizada de logging para componentes clave como HikariCP y Hibernate.
Configuración de Logging
El proyecto incluye una configuración avanzada de logging a través de Logback para mejorar la visibilidad de eventos críticos en el sistema y minimizar la verbosidad innecesaria.

Detalles de la configuración:
HikariCP:
Nivel de logging configurado en INFO para mostrar mensajes informativos relacionados con el pool de conexiones.
Hibernate:
Consultas SQL (org.hibernate.SQL): nivel INFO.
Parámetros bindeados (org.hibernate.type.descriptor.sql.BasicBinder): nivel WARN para evitar registros excesivos.
Generación de esquemas (org.hibernate.tool.hbm2ddl): nivel INFO.
Transacciones (org.hibernate.engine.transaction.internal.TransactionImpl): nivel INFO.
Conexiones JDBC (org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl): nivel INFO.
Propósito de esta configuración:
Reducción de ruido en los logs: Mantener el foco en eventos importantes y relevantes.
Visibilidad de consultas y transacciones: Facilitar la depuración y monitoreo de la interacción con la base de datos.
Optimización del rendimiento: Minimizar los gastos de procesamiento asociados con un logging excesivo.
Tecnologías utilizadas
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
Estandarización: Facilita el manejo de datos al estructurarlos de forma clara y consistente.
Integración de APIs: Simplifica la transformación de datos al comunicarse con servicios externos.
Mantenimiento: Permite encapsular la lógica de datos, facilitando cambios futuros en la estructura.
