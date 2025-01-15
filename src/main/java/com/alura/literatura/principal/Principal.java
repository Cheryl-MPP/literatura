package com.alura.literatura.principal;

import com.alura.literatura.dto.AutorDTO;
import com.alura.literatura.dto.LibroDTO;
import com.alura.literatura.dto.RespuestaLibrosDTO;
import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Libro;
import com.alura.literatura.service.AutorService;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;
import com.alura.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private ConsumoAPI consumoAPI;
    @Autowired
    private ConvierteDatos convierteDatos;
    private static final String BASE_URL = "https://gutendex.com/books/";

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("*****************************************");
            System.out.println("     💖  Bienvenida a Literatura 💖      ");
            System.out.println("*****************************************");
            System.out.println("🌸 Opciones disponibles:");
            System.out.println("   1️⃣  ✨ Buscar libro por título");
            System.out.println("   2️⃣  📚 Listar libros registrados");
            System.out.println("   3️⃣  🖋️ Listar autores registrados");
            System.out.println("   4️⃣  🌟 Listar autores vivos en un año");
            System.out.println("   5️⃣  🗣️ Listar libros por idioma");
            System.out.println("   0️⃣  🚪 Salir");
            System.out.println("*****************************************");
            System.out.print("🌷 Por favor, selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = scanner.nextLine();
                    try {
                        String encodedTitulo = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
                        String json = consumoAPI.obtenerDatos(BASE_URL + "?search=" + encodedTitulo);
                        RespuestaLibrosDTO respuestaLibrosDTO = convierteDatos.obtenerDatos(json, RespuestaLibrosDTO.class);
                        List<LibroDTO> librosDTO = respuestaLibrosDTO.getLibros();
                        if (librosDTO.isEmpty()) {
                            System.out.println("Libro no encontrado en la API");
                        } else {
                            boolean libroRegistrado = false;
                            for (LibroDTO libroDTO : librosDTO) {
                                if (libroDTO.getTitulo().equalsIgnoreCase(titulo)) {
                                    Optional<Libro> libroExistente = libroService.obtenerLibroPorTitulo(titulo);
                                    if (libroExistente.isPresent()) {
                                        System.out.println("Detalle: Clave (titulo)=(" + titulo + ") ya existe");
                                        System.out.println("No se puede registrar el mismo libro más de una vez");
                                        libroRegistrado = true;
                                        break;
                                    } else {
                                        Libro libro = new Libro();
                                        libro.setTitulo(libroDTO.getTitulo());
                                        libro.setIdioma(libroDTO.getIdioma().get(0));
                                        libro.setNumeroDescargas(libroDTO.getNumeroDescargas());

                                        // Buscar o crear el Autor
                                        AutorDTO primerAutorDTO = libroDTO.getAutores().get(0);
                                        Autor autor = autorService.obtenerAutorPorNombre(primerAutorDTO.getNombre())
                                                .orElseGet(() -> {
                                                    Autor nuevoAutor = new Autor();
                                                    nuevoAutor.setNombre(primerAutorDTO.getNombre());
                                                    nuevoAutor.setAnoNacimiento(primerAutorDTO.getAnoNacimiento());
                                                    nuevoAutor.setAnoFallecimiento(primerAutorDTO.getAnoFallecimiento());
                                                    return autorService.crearAutor(nuevoAutor);
                                                });

                                        // Asociar el Autor al Libro
                                        libro.setAutor(autor);

                                        // Guardar el libro en la base de datos
                                        libroService.crearLibro(libro);
                                        System.out.println("Libro registrado: " + libro.getTitulo());
                                        mostrarDetallesLibro(libroDTO);
                                        libroRegistrado = true;
                                        break;
                                    }
                                }
                            }
                            if (!libroRegistrado) {
                                System.out.println("No se encontró un libro exactamente con el título '" + titulo + "' en la API");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error al obtener datos de la API: " + e.getMessage());
                    }
                    break;
                case 2:
                    libroService.listarLibros().forEach(libro -> {
                        System.out.println("*****************************************");
                        System.out.println("        💖 Detalles del Libro 💖");
                        System.out.println("*****************************************");
                        System.out.println("📚 Título: " + libro.getTitulo());
                        System.out.println("👩‍🏫 Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
                        System.out.println("🌍 Idioma: " + libro.getIdioma());
                        System.out.println("📥 Número de descargas: " + libro.getNumeroDescargas());
                        System.out.println("*****************************************");
                    });
                    break;
                case 3:
                    autorService.listarAutores().forEach(autor -> {
                        System.out.println("*****************************************");
                        System.out.println("        💖 Detalles del Autor 💖");
                        System.out.println("*****************************************");
                        System.out.println("👩‍🏫 Autor: " + autor.getNombre());
                        System.out.println("🎂 Fecha de nacimiento: " + autor.getAnoNacimiento());
                        System.out.println("💀 Fecha de fallecimiento: " + (autor.getAnoFallecimiento() != null ? autor.getAnoFallecimiento() : "Desconocido"));
                        String libros = autor.getLibros().stream()
                                .map(Libro::getTitulo)
                                .collect(Collectors.joining(", "));
                        System.out.println("📚 Libros: [ " + libros + " ]");
                        System.out.println("*****************************************");
                    });

                    break;
                case 4:
                    System.out.print("🌸 Ingrese el año en que desea buscar autores vivos: ");
                    int ano = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea
                    List<Autor> autoresVivos = autorService.listaAutoresVivosEnAno(ano);
                    if (autoresVivos.isEmpty()) {
                        System.out.println("😔 No se encontraron autores vivos en el año " + ano);
                    } else {
                        System.out.println("💖 Autores vivos en el año " + ano + " 💖");
                        autoresVivos.forEach(autor -> {
                            System.out.println("*****************************************");
                            System.out.println("👩‍🏫 Autor: " + autor.getNombre());
                            System.out.println("🎂 Fecha de nacimiento: " + autor.getAnoNacimiento());
                            System.out.println("💀 Fecha de fallecimiento: " + (autor.getAnoFallecimiento() != null ? autor.getAnoFallecimiento() : "Desconocido"));
                            System.out.println("📚 Número de libros: " + autor.getLibros().size());
                            System.out.println("*****************************************");
                        });
                    }

                    break;
                case 5:
                    System.out.println("🌸 Ingrese el idioma de los libros que desea consultar:");
                    System.out.println("  📚 es - Español");
                    System.out.println("  🌍 en - Inglés");
                    System.out.println("  🇫🇷 fr - Francés");
                    System.out.println("  🇵🇹 pt - Portugués");
                    String idioma = scanner.nextLine();
                    if ("es".equalsIgnoreCase(idioma) || "en".equalsIgnoreCase(idioma) || "fr".equalsIgnoreCase(idioma) || "pt".equalsIgnoreCase(idioma)) {
                        System.out.println("💖 Mostrando libros en el idioma: " + idioma.toUpperCase() + " 💖");
                        libroService.listarLibrosPorIdioma(idioma).forEach(libro -> {
                            System.out.println("*****************************************");
                            System.out.println("📖 Título: " + libro.getTitulo());
                            System.out.println("👨‍🏫 Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
                            System.out.println("🌍 Idioma: " + libro.getIdioma());
                            System.out.println("📥 Número de descargas: " + libro.getNumeroDescargas());
                            System.out.println("*****************************************");
                        });
                    } else {
                        System.out.println("❌ Idioma no válido. Intente de nuevo. 💔");
                    }

                    break;
                case 0:
                    System.out.println("🌸 Saliendo... 💖");
                    System.out.println("🚪 ¡Adiós! Que tengas un hermoso día 🌟");
                    break;
                default:
                    System.out.println("❌ Opción no válida. Intenta de nuevo, por favor. 🌸");

            }
        } while (opcion != 0);

        scanner.close();
    }

    /**
     * Muestra los detalles de un libro DTO.
     *
     * @param libroDTO El objeto LibroDTO cuyos detalles se van a mostrar.
     */
    private void mostrarDetallesLibro(LibroDTO libroDTO) {
        System.out.println("💖------ LIBRO ------💖");
        System.out.println("📚 Título: " + libroDTO.getTitulo());
        System.out.println("👨‍🏫 Autor: " + (libroDTO.getAutores().isEmpty() ? "Desconocido" : libroDTO.getAutores().get(0).getNombre()));
        System.out.println("🌍 Idioma: " + libroDTO.getIdioma().get(0));
        System.out.println("📥 Número de descargas: " + libroDTO.getNumeroDescargas());
        System.out.println("💖-------------------💖");
        ;
    }
}




