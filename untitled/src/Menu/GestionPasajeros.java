package Menu;

import Clases.*;
import Enums.*;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Generico.RegistroGenericoMapas;
import Interfaces.IEmpleado;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Menu.ConsolaHotel.*;
import static Menu.ValidacionesMenu.*;

/**
 * PASAJEROS
 * Extraído del Menu.java original. No se alteraron los cuerpos de los métodos.
 */
public class GestionPasajeros {

    // ============================================================
    // agregarPasajero
    // ============================================================
    public static void agregarPasajero(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              AGREGAR PASAJERO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // DATOS PERSONALES
        String nombre = pedirSoloLetras("Nombre", sc);
        String apellido = pedirSoloLetras("Apellido", sc);

        // VALIDAR QUE EL DNI NO ESTÉ REGISTRADO
        String dni;

        while (true) {

            dni = pedirDni();

            if (hotel.getPasajeros().containsKey(dni)) {

                System.out.println(ROJO +
                        "ERROR: Ya existe un pasajero con ese DNI" + RESET);
                continue;
            }

            break;
        }

        String telefono = pedirTelefono();
        String email = pedirEmail();

        // =========================================
        // CREAR USUARIO DEL PASAJERO
        // =========================================

        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣");
        System.out.println("║             CREAR USUARIO                 ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String nombreUsuario = pedirNombreUsuario(hotel);
        String contrasenia = pedirContrasenia();

        Usuario usuario = new Usuario(
                nombreUsuario,
                contrasenia,
                ETipoUsuario.PASAJERO
        );

        // DATOS DEL VIAJE
        String origen = pedirSoloLetras("Origen", sc);
        String destino = pedirSoloLetras("Destino", sc);

        // CREAR PASAJERO
        Pasajero pasajero = new Pasajero(
                nombre,
                apellido,
                dni,
                telefono,
                email,
                usuario,
                origen,
                destino
        );

        // =========================================
        // GUARDAR PASAJERO Y USUARIO
        // =========================================

        try {

            usuario.validarDatos();
            pasajero.validarDatos();

            hotel.agregarPasajero(pasajero);

            try {

                hotel.agregarUsuario(usuario);

            } catch (AgenciaHotelException e) {

                // SI FALLA EL USUARIO, DESHACER EL REGISTRO
                hotel.eliminarPasajero(dni);
                throw e;
            }

            System.out.println();
            System.out.println(VERDE +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║        PASAJERO AGREGADO CON ÉXITO        ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL +
                    "║  Nombre: " + nombre + " " + apellido);
            System.out.println("║  DNI: " + dni);
            System.out.println("║  Usuario: " + nombreUsuario + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

        } catch (AgenciaHotelException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }
    }


    // ============================================================
    // buscarPasajero
    // ============================================================
    public static void buscarPasajero(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              BUSCAR PASAJERO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String dni = pedirDni();

        try {

            Pasajero p = hotel.buscarPasajero(dni);

            System.out.printf(
                    VERDE + "║ %-41s ║%n" + RESET,
                    "  PASAJERO ENCONTRADO"
            );

            System.out.printf(
                    AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + p.getNombre()
            );

            System.out.printf(
                    AZUL + "║ %-41s ║%n" + RESET,
                    "  Apellido: " + p.getApellido()
            );

            System.out.printf(
                    AZUL + "║ %-41s ║%n" + RESET,
                    "  DNI: " + p.getDNI()
            );

            System.out.printf(
                    AZUL + "║ %-41s ║%n" + RESET,
                    "  Teléfono: " + p.getTelefono()
            );

            System.out.printf(
                    AZUL + "║ %-41s ║%n" + RESET,
                    "  Email: " + p.getEmail()
            );

            System.out.printf(
                    AZUL + "║ %-41s ║%n" + RESET,
                    "  Origen: " + p.getOrigen()
            );

            System.out.printf(
                    AZUL + "║ %-41s ║%n" + RESET,
                    "  Destino: " + p.getDestino_origen()
            );

            System.out.printf(
                    VERDE + "║ %-41s ║%n" + RESET,
                    "  Usuario: " + p.getUsuario().getNombreUsuario()
            );

        } catch (DatoInvalidoException e) {

            System.out.println(ROJO +
                    "║  Pasajero no encontrado" + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    // ============================================================
    // eliminarPasajero
    // ============================================================
    public static void eliminarPasajero(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             ELIMINAR PASAJERO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String dni = pedirDni();

        try {

            // BUSCAR PASAJERO
            Pasajero p = hotel.buscarPasajero(dni);

            System.out.println(AZUL +
                    "║  Nombre: " + p.getNombre());

            System.out.println(
                    "║  Apellido: " + p.getApellido());

            System.out.println(
                    "║  DNI: " + p.getDNI() + RESET);

            // CONFIRMAR ELIMINACIÓN
            if (confirmarEliminacion()) {

                System.out.println(VERDE +
                        hotel.eliminarPasajero(dni) + RESET);

            } else {

                System.out.println(AMARILLO +
                        "Eliminación cancelada" + RESET);
            }

        } catch (DatoInvalidoException e) {

            System.out.println(ROJO +
                    "Pasajero no encontrado" + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    // ============================================================
    // mostrarPasajeros
    // ============================================================
    public static void mostrarPasajeros(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             MOSTRAR PASAJEROS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // VERIFICAR SI HAY PASAJEROS REGISTRADOS
        if (hotel.mostrarPasajeros().isEmpty()) {

            System.out.println(ROJO +
                    "║  No hay pasajeros registrados" + RESET);

        } else {

            // RECORRER TODOS LOS PASAJEROS
            for (Pasajero p : hotel.mostrarPasajeros()) {

                System.out.printf(
                        AZUL + "║ %-41s ║%n" + RESET,
                        "  Nombre: " + p.getNombre()
                );

                System.out.printf(
                        AZUL + "║ %-41s ║%n" + RESET,
                        "  Apellido: " + p.getApellido()
                );

                System.out.printf(
                        AZUL + "║ %-41s ║%n" + RESET,
                        "  DNI: " + p.getDNI()
                );

                System.out.printf(
                        AZUL + "║ %-41s ║%n" + RESET,
                        "  Teléfono: " + p.getTelefono()
                );

                System.out.printf(
                        AZUL + "║ %-41s ║%n" + RESET,
                        "  Email: " + p.getEmail()
                );

                System.out.printf(
                        AZUL + "║ %-41s ║%n" + RESET,
                        "  Origen: " + p.getOrigen()
                );

                System.out.printf(
                        AZUL + "║ %-41s ║%n" + RESET,
                        "  Destino: " + p.getDestino_origen()
                );

                System.out.printf(
                        VERDE + "║ %-41s ║%n" + RESET,
                        "  Usuario: " +
                                p.getUsuario().getNombreUsuario()
                );

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣" + RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


}
