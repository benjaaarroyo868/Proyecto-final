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
 * EMPLEADOS
 * Extraído del Menu.java original. No se alteraron los cuerpos de los métodos.
 */
public class GestionEmpleados {

    // ============================================================
    // agregarEmpleado
    // ============================================================
    public static void agregarEmpleado(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              AGREGAR EMPLEADO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  1. Administrador                         ║");
        System.out.println(
                "║  2. Conserje                              ║" + RESET);

        System.out.println(AMARILLO +
                "║  0. Volver al menú                        ║" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        int opcion = pedirOpcion(0, 2);

        if (opcion == 0) {
            return;
        }

        // =========================================
        // DATOS PERSONALES
        // =========================================

        String nombre = pedirSoloLetras("Nombre", sc);
        String apellido = pedirSoloLetras("Apellido", sc);

        String dni;

        while (true) {

            dni = pedirDni();

            if (hotel.existeDniEmpleado(dni)) {

                System.out.println(ROJO +
                        "ERROR: Ya existe un empleado con ese DNI"
                        + RESET);
                continue;
            }

            break;
        }

        String telefono = pedirTelefono();
        String email = pedirEmail();

        // =========================================
        // CREAR USUARIO
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              CREAR USUARIO                ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String nombreUsuario = pedirNombreUsuario(hotel);
        String contrasenia = pedirContrasenia();

        ETipoUsuario tipoUsuario = opcion == 1
                ? ETipoUsuario.ADMINISTRADOR
                : ETipoUsuario.CONSERJE;

        Usuario nuevoUsuario = new Usuario(
                nombreUsuario,
                contrasenia,
                tipoUsuario
        );

        // =========================================
        // VALIDAR LEGAJO
        // =========================================

        String legajo;

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ Número de legajo" + RESET);

            System.out.print(VERDE + "║  ➜ " + RESET);

            legajo = sc.nextLine().trim();

            if (legajo.isBlank()) {

                System.out.println(ROJO +
                        "ERROR: El legajo no puede estar vacío"
                        + RESET);
                continue;
            }

            if (hotel.getEmpleados().containsKey(legajo)) {

                System.out.println(ROJO +
                        "ERROR: El legajo ya existe" + RESET);
                continue;
            }

            break;
        }

        // =========================================
        // VALIDAR SUELDO
        // =========================================

        double sueldo;

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ Sueldo" + RESET);

            System.out.print(VERDE + "║  ➜ " + RESET);

            try {

                sueldo = Double.parseDouble(
                        sc.nextLine().trim().replace(",", "."));

                if (Double.isFinite(sueldo) && sueldo > 0) {
                    break;
                }

            } catch (NumberFormatException e) {
                // Volver a solicitar el sueldo
            }

            System.out.println(ROJO +
                    "ERROR: Ingresá un sueldo válido" + RESET);
        }

        // =========================================
        // CREAR EMPLEADO
        // =========================================

        IEmpleado nuevoEmpleado;

        if (opcion == 1) {

            nuevoEmpleado = new Administrador(
                    nombre,
                    apellido,
                    dni,
                    telefono,
                    email,
                    nuevoUsuario,
                    legajo,
                    sueldo
            );

        } else {

            nuevoEmpleado = new Conserje(
                    nombre,
                    apellido,
                    dni,
                    telefono,
                    email,
                    nuevoUsuario,
                    legajo,
                    sueldo
            );
        }

        // =========================================
        // GUARDAR EMPLEADO Y USUARIO
        // =========================================

        try {

            nuevoUsuario.validarDatos();

            // AGREGAR EMPLEADO
            hotel.agregarEmpleado(nuevoEmpleado);

            // AGREGAR USUARIO
            try {

                hotel.agregarUsuario(nuevoUsuario);

            } catch (AgenciaHotelException e) {

                // DESHACER EL REGISTRO SI FALLA EL USUARIO
                hotel.eliminarEmpleado(legajo);
                throw e;
            }

            System.out.println();
            System.out.println(VERDE +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║        EMPLEADO AGREGADO CON ÉXITO        ║");
            System.out.println("╠═══════════════════════════════════════════╣");

            System.out.println(AZUL +
                    "║  Nombre: " + nombre + " " + apellido);

            System.out.println("║  DNI: " + dni);
            System.out.println("║  Legajo: " + legajo);
            System.out.println("║  Usuario: " + nombreUsuario);
            System.out.println("║  Tipo: " + tipoUsuario + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝"
                    + RESET);

        } catch (AgenciaHotelException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }
    }


    // ============================================================
    // buscarEmpleado
    // ============================================================
    public static void buscarEmpleado(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              BUSCAR EMPLEADO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AMARILLO +
                "║  Ingresá 0 para volver al menú" + RESET);

        System.out.print(AZUL +
                "║  Número de legajo: " + RESET);

        String legajo = sc.nextLine().trim();

        if (legajo.equals("0")) {

            System.out.println(AMARILLO +
                    "Búsqueda cancelada" + RESET);
            return;
        }

        try {

            IEmpleado empleado = hotel.buscarEmpleado(legajo);

            String tipo = empleado instanceof Administrador
                    ? "ADMINISTRADOR"
                    : "CONSERJE";

            System.out.println(VERDE +
                    "║  EMPLEADO ENCONTRADO" + RESET);

            System.out.println(AZUL +
                    "║  Legajo: " + empleado.getLegajo());

            System.out.println(
                    "║  DNI: " + empleado.getDni());

            System.out.println(
                    "║  Tipo: " + tipo + RESET);

            // MOSTRAR DATOS PERSONALES
            if (empleado instanceof Persona) {

                Persona persona = (Persona) empleado;

                System.out.println(AZUL +
                        "║  Nombre: " + persona.getNombre());

                System.out.println(
                        "║  Apellido: " + persona.getApellido());

                System.out.println(
                        "║  Teléfono: " + persona.getTelefono());

                System.out.println(
                        "║  Email: " + persona.getEmail() + RESET);
            }

        } catch (DatoInvalidoException e) {

            System.out.println(ROJO +
                    "║  ERROR: " + e.getMessage() + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    // ============================================================
    // eliminarEmpleado
    // ============================================================
    public static void eliminarEmpleado(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             ELIMINAR EMPLEADO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AMARILLO +
                "║  Ingresá 0 para volver al menú" + RESET);

        System.out.print(AZUL +
                "║  Número de legajo: " + RESET);

        String legajo = sc.nextLine().trim();

        if (legajo.equals("0")) {

            System.out.println(AMARILLO +
                    "Eliminación cancelada" + RESET);
            return;
        }

        try {

            // BUSCAR EMPLEADO
            IEmpleado empleado = hotel.buscarEmpleado(legajo);

            System.out.println(AZUL +
                    "║  Legajo: " + empleado.getLegajo());

            System.out.println(
                    "║  DNI: " + empleado.getDni());

            if (empleado instanceof Persona) {

                Persona persona = (Persona) empleado;

                System.out.println(
                        "║  Nombre: " + persona.getNombre());

                System.out.println(
                        "║  Apellido: " + persona.getApellido());
            }

            System.out.println(RESET);

            // CONFIRMAR ELIMINACIÓN
            if (confirmarEliminacion()) {

                System.out.println(VERDE +
                        hotel.eliminarEmpleado(legajo) + RESET);

            } else {

                System.out.println(AMARILLO +
                        "Eliminación cancelada" + RESET);
            }

        } catch (DatoInvalidoException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    // ============================================================
    // mostrarEmpleados
    // ============================================================
    public static void mostrarEmpleados(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             MOSTRAR EMPLEADOS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // VERIFICAR SI HAY EMPLEADOS
        if (hotel.mostrarEmpleados().isEmpty()) {

            System.out.println(ROJO +
                    "║  No hay empleados registrados" + RESET);

        } else {

            int contador = 1;

            // RECORRER TODOS LOS EMPLEADOS
            for (IEmpleado empleado : hotel.mostrarEmpleados()) {

                String tipo = empleado instanceof Administrador
                        ? "ADMINISTRADOR"
                        : "CONSERJE";

                System.out.println(VERDE +
                        "║  EMPLEADO " + contador + RESET);

                System.out.println(AZUL +
                        "║  Legajo: " + empleado.getLegajo());

                System.out.println(
                        "║  DNI: " + empleado.getDni());

                System.out.println(
                        "║  Tipo: " + tipo);

                if (empleado instanceof Persona) {

                    Persona persona = (Persona) empleado;

                    System.out.println(
                            "║  Nombre: " + persona.getNombre());

                    System.out.println(
                            "║  Apellido: " + persona.getApellido());

                    System.out.println(
                            "║  Teléfono: " + persona.getTelefono());

                    System.out.println(
                            "║  Email: " + persona.getEmail());
                }

                System.out.println(RESET);

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣"
                        + RESET);

                contador++;
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


}
