
package Menu;

import Clases.*;
import Enums.*;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Generico.RegistroGenericoMapas;
import Interfaces.IEmpleado;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Menu {

    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static final String VERDE = "\u001B[32m";
    public static final String ROJO = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String AMARILLO = "\u001B[33m";

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd-MM-uuuu");

    static Scanner sc = new Scanner(System.in);

    // =========================================
    // VALIDACIONES
    // =========================================

    public static String pedirSoloLetras(String campo, Scanner sc) {
        while (true) {
            System.out.println(AZUL + "║  ◈ " + campo + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            String dato = sc.nextLine().trim();

            if (dato.matches(
                    "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]{2,}( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$")) {
                return dato;
            }

            System.out.println(ROJO +
                    "║  ERROR: Ingresá al menos 2 letras" + RESET);
        }
    }

    public static String pedirDni() {
        while (true) {
            System.out.println(AZUL + "║  ◈ DNI" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            String dni = sc.nextLine().trim();

            if (dni.matches("[0-9]{7,8}")) {
                return dni;
            }

            System.out.println(ROJO +
                    "║  ERROR: DNI de 7 u 8 números" + RESET);
        }
    }

    public static String pedirTelefono() {
        while (true) {
            System.out.println(AZUL + "║  ◈ Teléfono" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            String telefono = sc.nextLine().trim();

            if (telefono.matches("[0-9]{8,15}")) {
                return telefono;
            }

            System.out.println(ROJO +
                    "║  ERROR: Teléfono de 8 a 15 números" + RESET);
        }
    }

    public static String pedirEmail() {
        while (true) {
            System.out.println(AZUL + "║  ◈ Email" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            String email = sc.nextLine().trim();

            if (email.matches(
                    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                return email;
            }

            System.out.println(ROJO +
                    "║  ERROR: Email inválido" + RESET);
        }
    }

    public static String pedirNombreUsuario(Hotel hotel) {
        while (true) {
            System.out.println(AZUL + "║  ◈ Nombre de usuario" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            String nombre = sc.nextLine().trim();

            if (!nombre.matches(
                    "^(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")) {

                System.out.println(ROJO +
                        "ERROR: Mínimo 8 caracteres, mayúscula y número" + RESET);
                continue;
            }

            if (hotel.getUsuarios().containsKey(nombre)) {
                System.out.println(ROJO +
                        "ERROR: El nombre de usuario ya existe" + RESET);
                continue;
            }

            return nombre;
        }
    }

    public static String pedirContrasenia() {
        while (true) {
            System.out.println(AZUL + "║  ◈ Contraseña" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            String contrasenia = sc.nextLine();

            if (contrasenia.matches(
                    "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s])\\S{8,}$")) {
                return contrasenia;
            }

            System.out.println(ROJO +
                    "ERROR: Mínimo 8 caracteres, mayúscula, número y símbolo"
                    + RESET);
        }
    }

    public static int pedirOpcion(int minimo, int maximo) {
        while (true) {
            System.out.print(VERDE + "\n➜ Elegí una opción: " + RESET);
            String entrada = sc.nextLine().trim();

            try {
                int opcion = Integer.parseInt(entrada);

                if (opcion >= minimo && opcion <= maximo) {
                    return opcion;
                }

            } catch (NumberFormatException e) {
                // Volver a pedir.
            }

            System.out.println(ROJO +
                    "ERROR: Elegí una opción entre " +
                    minimo + " y " + maximo + RESET);
        }
    }

    public static boolean confirmarEliminacion() {
        while (true) {
            System.out.println(AMARILLO +
                    "║  ¿Desea eliminarlo? (Si/No)" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            String respuesta = sc.nextLine().trim();

            if (respuesta.equalsIgnoreCase("Si") ||
                    respuesta.equalsIgnoreCase("S")) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("No") ||
                    respuesta.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println(ROJO +
                    "║  ERROR: Ingresá Si o No" + RESET);
        }
    }

    public static void mostrarCierre() {
        String fecha = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              SESIÓN CERRADA               ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(VERDE +
                "║  Sesión cerrada correctamente" + RESET);

        System.out.println(AMARILLO +
                "║  Fecha: " + fecha + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // =========================================
    // USUARIOS
    // =========================================

    public static void registrarUsuario(Hotel hotel) {
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             REGISTRAR USUARIO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String nombre = pedirNombreUsuario(hotel);
        String contrasenia = pedirContrasenia();

        Usuario nuevo = new Usuario(
                nombre,
                contrasenia,
                ETipoUsuario.PASAJERO
        );

        try {
            System.out.println(VERDE +
                    hotel.agregarUsuario(nuevo) + RESET);

        } catch (AgenciaHotelException e) {
            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    public static void buscarUsuario(Hotel hotel) {
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║               BUSCAR USUARIO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.print(AZUL + "Nombre de usuario: " + RESET);
        String nombre = sc.nextLine().trim();

        try {
            Usuario usuario = hotel.buscarUsuario(nombre);

            System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                    "  USUARIO ENCONTRADO");

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + usuario.getNombreUsuario());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Tipo: " + usuario.getTipoDeUsuario());

        } catch (DatoInvalidoException e) {
            System.out.println(ROJO +
                    "║  Usuario no encontrado" + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    public static void eliminarUsuario(Hotel hotel) {
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              ELIMINAR USUARIO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.print(AZUL + "Nombre de usuario: " + RESET);
        String nombre = sc.nextLine().trim();

        try {
            Usuario usuario = hotel.buscarUsuario(nombre);

            System.out.println(AZUL +
                    "║  Usuario: " + usuario.getNombreUsuario());

            System.out.println("║  Tipo: " +
                    usuario.getTipoDeUsuario() + RESET);

            if (confirmarEliminacion()) {
                System.out.println(VERDE +
                        hotel.eliminarUsuario(nombre) + RESET);
            } else {
                System.out.println(AMARILLO +
                        "Eliminación cancelada" + RESET);
            }

        } catch (DatoInvalidoException e) {
            System.out.println(ROJO +
                    "Usuario no encontrado" + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    public static void mostrarUsuarios(Hotel hotel) {
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              MOSTRAR USUARIOS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (hotel.mostrarUsuarios().isEmpty()) {
            System.out.println(ROJO +
                    "║  No hay usuarios registrados" + RESET);

        } else {
            for (Usuario usuario : hotel.mostrarUsuarios()) {

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Nombre: " + usuario.getNombreUsuario());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Tipo: " + usuario.getTipoDeUsuario());

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣" + RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // =========================================
    // PASAJEROS
    // =========================================

    public static void agregarPasajero(Hotel hotel) {
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              AGREGAR PASAJERO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String nombre = pedirSoloLetras("Nombre", sc);
        String apellido = pedirSoloLetras("Apellido", sc);

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

        String origen = pedirSoloLetras("Origen", sc);
        String destino = pedirSoloLetras("Destino", sc);

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

        try {
            usuario.validarDatos();
            pasajero.validarDatos();

            hotel.agregarPasajero(pasajero);

            try {
                hotel.agregarUsuario(usuario);

            } catch (AgenciaHotelException e) {
                hotel.eliminarPasajero(dni);
                throw e;
            }

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

    public static void buscarPasajero(Hotel hotel) {
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              BUSCAR PASAJERO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String dni = pedirDni();

        try {
            Pasajero p = hotel.buscarPasajero(dni);

            System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                    "  PASAJERO ENCONTRADO");

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + p.getNombre());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Apellido: " + p.getApellido());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  DNI: " + p.getDNI());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Teléfono: " + p.getTelefono());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Email: " + p.getEmail());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Origen: " + p.getOrigen());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Destino: " + p.getDestino_origen());

            System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                    "  Usuario: " + p.getUsuario().getNombreUsuario());

        } catch (DatoInvalidoException e) {
            System.out.println(ROJO +
                    "║  Pasajero no encontrado" + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    public static void eliminarPasajero(Hotel hotel) {
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             ELIMINAR PASAJERO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String dni = pedirDni();

        try {
            Pasajero p = hotel.buscarPasajero(dni);

            System.out.println(AZUL +
                    "║  Nombre: " + p.getNombre());
            System.out.println("║  Apellido: " + p.getApellido());
            System.out.println("║  DNI: " + p.getDNI() + RESET);

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

    public static void mostrarPasajeros(Hotel hotel) {
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             MOSTRAR PASAJEROS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (hotel.mostrarPasajeros().isEmpty()) {
            System.out.println(ROJO +
                    "║  No hay pasajeros registrados" + RESET);

        } else {
            for (Pasajero p : hotel.mostrarPasajeros()) {

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Nombre: " + p.getNombre());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Apellido: " + p.getApellido());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  DNI: " + p.getDNI());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Teléfono: " + p.getTelefono());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Email: " + p.getEmail());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Origen: " + p.getOrigen());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Destino: " + p.getDestino_origen());

                System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                        "  Usuario: " + p.getUsuario().getNombreUsuario());

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣" + RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // =========================================
    // EMPLEADOS
    // =========================================


    public static void agregarEmpleado(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              AGREGAR EMPLEADO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║    1. Administrador                       ║");
        System.out.println("║    2. Conserje                            ║" + RESET);

        System.out.println(AMARILLO +
                "║    0. Volver                              ║" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        int opcion = pedirOpcion(0, 2);

        if (opcion == 0) {
            return;
        }

        // DATOS PERSONALES
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

        // CREAR USUARIO
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

        // LEGAJO
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

        // SUELDO
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
                // Volver a solicitar sueldo
            }

            System.out.println(ROJO +
                    "ERROR: Ingresá un sueldo válido" + RESET);
        }

        // CREAR EMPLEADO
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

        // GUARDAR EMPLEADO Y USUARIO
        try {

            nuevoUsuario.validarDatos();

            // AGREGAR SOLO EL OBJETO
            hotel.agregarEmpleado(nuevoEmpleado);

            try {

                hotel.agregarUsuario(nuevoUsuario);

            } catch (AgenciaHotelException e) {

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
                    ? "ADMINISTRADOR" : "CONSERJE";

            System.out.println(VERDE +
                    "║  EMPLEADO ENCONTRADO" + RESET);

            System.out.println(AZUL +
                    "║  Legajo: " + empleado.getLegajo());

            System.out.println(
                    "║  DNI: " + empleado.getDni());

            System.out.println(
                    "║  Tipo: " + tipo + RESET);

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


    public static void mostrarEmpleados(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             MOSTRAR EMPLEADOS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (hotel.mostrarEmpleados().isEmpty()) {

            System.out.println(ROJO +
                    "║  No hay empleados registrados" + RESET);

        } else {

            int contador = 1;

            for (IEmpleado empleado : hotel.mostrarEmpleados()) {

                String tipo = empleado instanceof Administrador
                        ? "ADMINISTRADOR" : "CONSERJE";

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
                        "╠═══════════════════════════════════════════╣" + RESET);

                contador++;
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    // =========================================
    //               RESERVAS
    // =========================================

    // VERIFICAR PERMISOS DE LA RESERVA

    private static boolean puedeGestionarReserva(
            Reserva reserva,
            Usuario usuario,
            boolean esEmpleado) {

        if (reserva == null) {
            return false;
        }

        // Administradores y conserjes pueden gestionar
        // las reservas de cualquier pasajero.
        if (esEmpleado) {
            return true;
        }

        // Un pasajero solo puede gestionar sus reservas.
        return usuario != null
                && reserva.getPasajero() != null
                && reserva.getPasajero().getUsuario() != null
                && usuario.getNombreUsuario().equals(
                reserva.getPasajero()
                        .getUsuario()
                        .getNombreUsuario());
    }


    private static void imprimirReserva(Reserva reserva) {

        // CALCULAR DÍAS Y NOCHES
        long cantidadNoches =
                java.time.temporal.ChronoUnit.DAYS.between(
                        reserva.getFechaIngreso(),
                        reserva.getFechaSalida());

        long cantidadDias = cantidadNoches + 1;

        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ID: " + reserva.getIdReserva());

        System.out.println(
                "║  Habitación: " +
                        reserva.getHabitacion().getNumeroHabitacion());

        System.out.println(
                "║  Fecha de ingreso: " +
                        reserva.getFechaIngreso().format(FORMATO_FECHA));

        System.out.println(
                "║  Fecha de salida: " +
                        reserva.getFechaSalida().format(FORMATO_FECHA));

        System.out.println(
                "║  Total de días: " + cantidadDias);

        System.out.println(
                "║  Total de noches: " + cantidadNoches);

        if (reserva.getPasajero() != null) {

            System.out.println(
                    "║  Pasajero: " +
                            reserva.getPasajero().getNombre() + " " +
                            reserva.getPasajero().getApellido());

            System.out.println(
                    "║  DNI: " +
                            reserva.getPasajero().getDNI());
        }

        System.out.println(RESET);
    }




    private static Pasajero seleccionarPasajeroReserva(
            Hotel hotel, Usuario usuario) {

        if (usuario == null) {
            System.out.println(ROJO +
                    "ERROR: No hay sesión iniciada" + RESET);
            return null;
        }

        // =========================================
        // 1. BUSCAR SI YA TIENE UN PASAJERO ASOCIADO
        // =========================================

        Pasajero pasajeroActual = null;

        for (Pasajero p : hotel.mostrarPasajeros()) {

            if (p.getUsuario() != null &&
                    p.getUsuario().getNombreUsuario()
                            .equals(usuario.getNombreUsuario())) {

                pasajeroActual = p;
                break;
            }
        }

        // =========================================
        // 2. SI ES PASAJERO COMÚN
        // =========================================

        if (usuario.getTipoDeUsuario() == ETipoUsuario.PASAJERO) {

            if (pasajeroActual != null) {
                return pasajeroActual;
            }

            System.out.println(ROJO +
                    "ERROR: No hay pasajero asociado a tu cuenta"
                    + RESET);

            return null;
        }

        // =========================================
        // 3. BUSCAR ADMINISTRADOR O CONSERJE
        // =========================================

        Persona empleadoActual = null;

        for (Interfaces.IEmpleado empleado : hotel.mostrarEmpleados()) {

            if (empleado instanceof Administrador) {

                Administrador admin = (Administrador) empleado;

                if (admin.getUsuario() != null &&
                        admin.getUsuario().getNombreUsuario()
                                .equals(usuario.getNombreUsuario())) {

                    empleadoActual = admin;
                    break;
                }

            } else if (empleado instanceof Conserje) {

                Conserje conserje = (Conserje) empleado;

                if (conserje.getUsuario() != null &&
                        conserje.getUsuario().getNombreUsuario()
                                .equals(usuario.getNombreUsuario())) {

                    empleadoActual = conserje;
                    break;
                }
            }
        }

        // =========================================
        // 4. ELEGIR PARA QUIÉN ES LA RESERVA
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          ¿PARA QUIÉN ES LA RESERVA?       ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  1. Para mí                               ║");
        System.out.println("║  2. Para otro pasajero                    ║" + RESET);

        System.out.println(AMARILLO +
                "║  0. Volver al menú                        ║" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        int opcion = pedirOpcion(0, 2);

        if (opcion == 0) {

            System.out.println(AMARILLO +
                    "Operación cancelada" + RESET);
            return null;
        }

        // =========================================
        // 5. OPCIÓN 1: RESERVA PARA MÍ
        // =========================================

        if (opcion == 1) {

            // SI YA ESTÁ REGISTRADO COMO PASAJERO,
            // UTILIZAR SUS DATOS
            if (pasajeroActual != null) {

                System.out.println(VERDE +
                        "Se utilizarán tus datos registrados" + RESET);

                return pasajeroActual;
            }

            // BUSCAR LOS DATOS DEL EMPLEADO
            if (empleadoActual == null) {

                System.out.println();
                System.out.println(CYAN +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║            EMPLEADO NO ENCONTRADO         ║");
                System.out.println("╠═══════════════════════════════════════════╣" + RESET);

                System.out.println(ROJO +
                        "║  No se encontraron tus datos de empleado"
                        + RESET);

                System.out.println(AMARILLO +
                        "║  Verificá tu registro en el hotel"
                        + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝" + RESET);

                return null;
            }

            // =========================================
            // 6. OBTENER DATOS DEL EMPLEADO
            // =========================================

            String nombre = empleadoActual.getNombre();
            String apellido = empleadoActual.getApellido();
            String dni = empleadoActual.getDNI();
            String telefono = empleadoActual.getTelefono();
            String email = empleadoActual.getEmail();

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║              MIS DATOS                    ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL +
                    "║  Nombre: " + nombre);

            System.out.println(
                    "║  Apellido: " + apellido);

            System.out.println(
                    "║  DNI: " + dni);

            System.out.println(
                    "║  Teléfono: " + telefono);

            System.out.println(
                    "║  Email: " + email + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

            // VERIFICAR SI YA EXISTE COMO PASAJERO
            for (Pasajero p : hotel.mostrarPasajeros()) {

                if (p.getDNI().equals(dni)) {

                    // MISMO DNI Y MISMO USUARIO
                    if (p.getUsuario() != null &&
                            p.getUsuario().getNombreUsuario()
                                    .equals(usuario.getNombreUsuario())) {

                        return p;
                    }

                    System.out.println(ROJO +
                            "ERROR: Ese DNI ya está asociado a otro usuario"
                            + RESET);
                    return null;
                }
            }

            // DATOS PROPIOS DEL PASAJERO
            String origen = pedirSoloLetras("Origen", sc);
            String destino = pedirSoloLetras("Destino", sc);

            // CREAR PASAJERO CON DATOS DEL EMPLEADO
            Pasajero nuevoPasajero = new Pasajero(
                    nombre,
                    apellido,
                    dni,
                    telefono,
                    email,
                    usuario,
                    origen,
                    destino
            );

            try {

                nuevoPasajero.validarDatos();

                hotel.agregarPasajero(nuevoPasajero);

                System.out.println();
                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║       DATOS ASOCIADOS CORRECTAMENTE       ║");
                System.out.println("╠═══════════════════════════════════════════╣");

                System.out.println("║  Nombre: " +
                        nombre + " " + apellido);

                System.out.println("║  DNI: " + dni);

                System.out.println("║  Usuario: " +
                        usuario.getNombreUsuario());

                System.out.println("╚═══════════════════════════════════════════╝"
                        + RESET);

                return nuevoPasajero;

            } catch (AgenciaHotelException e) {

                System.out.println(ROJO +
                        "ERROR: " + e.getMessage() + RESET);

                return null;
            }
        }

        // =========================================
        // 7. OPCIÓN 2: PARA OTRO PASAJERO
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             BUSCAR PASAJERO               ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AMARILLO +
                "║  Ingresá 0 para cancelar" + RESET);

        String dni;

        while (true) {

            System.out.print(AZUL +
                    "║  DNI del pasajero: " + RESET);

            dni = sc.nextLine().trim();

            if (dni.equals("0")) {
                return null;
            }

            if (dni.matches("[0-9]{7,8}")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: DNI de 7 u 8 números" + RESET);
        }

        // =========================================
        // 8. BUSCAR SI YA ESTÁ REGISTRADO
        // =========================================

        for (Pasajero p : hotel.mostrarPasajeros()) {

            if (p.getDNI().equals(dni)) {

                System.out.println(VERDE +
                        "║  PASAJERO ENCONTRADO" + RESET);

                System.out.println(AZUL +
                        "║  Nombre: " + p.getNombre());

                System.out.println(
                        "║  Apellido: " + p.getApellido());

                System.out.println(
                        "║  DNI: " + p.getDNI() + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝" + RESET);

                return p;
            }
        }

        // =========================================
        // 9. REGISTRAR PASAJERO NUEVO
        // =========================================

        System.out.println(AMARILLO +
                "║  El pasajero no está registrado" + RESET);

        System.out.println(VERDE +
                "║  Vamos a cargar sus datos" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        String nombre = pedirSoloLetras("Nombre", sc);
        String apellido = pedirSoloLetras("Apellido", sc);
        String telefono = pedirTelefono();
        String email = pedirEmail();

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║         CREAR USUARIO PASAJERO            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String nombreUsuario = pedirNombreUsuario(hotel);
        String contrasenia = pedirContrasenia();

        Usuario usuarioPasajero = new Usuario(
                nombreUsuario,
                contrasenia,
                ETipoUsuario.PASAJERO
        );

        String origen = pedirSoloLetras("Origen", sc);
        String destino = pedirSoloLetras("Destino", sc);

        Pasajero nuevoPasajero = new Pasajero(
                nombre,
                apellido,
                dni,
                telefono,
                email,
                usuarioPasajero,
                origen,
                destino
        );

        // =========================================
        // 10. GUARDAR PASAJERO Y USUARIO
        // =========================================

        try {

            usuarioPasajero.validarDatos();
            nuevoPasajero.validarDatos();

            hotel.agregarPasajero(nuevoPasajero);

            try {

                hotel.agregarUsuario(usuarioPasajero);

            } catch (AgenciaHotelException e) {

                hotel.eliminarPasajero(dni);
                throw e;
            }

            System.out.println();
            System.out.println(VERDE +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║       PASAJERO REGISTRADO CON ÉXITO       ║");
            System.out.println("╠═══════════════════════════════════════════╣");

            System.out.println("║  Nombre: " +
                    nombre + " " + apellido);

            System.out.println("║  DNI: " + dni);

            System.out.println("║  Usuario: " + nombreUsuario);

            System.out.println("╚═══════════════════════════════════════════╝"
                    + RESET);

            return nuevoPasajero;

        } catch (AgenciaHotelException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);

            return null;
        }
    }


    // =========================================
    //              AGREGAR RESERVA
    // =========================================



    public static void realizarReserva(Hotel hotel, Usuario usuario) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              REALIZAR RESERVA             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // =========================================
        // 1. SELECCIONAR PASAJERO
        // =========================================

        if (usuario == null) {

            System.out.println(ROJO +
                    "║  ERROR: No hay sesión iniciada" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // ESTE MÉTODO PERMITE QUE EL ADMINISTRADOR
        // O CONSERJE RESERVE PARA SÍ MISMO O PARA OTRO
        Pasajero pasajero =
                seleccionarPasajeroReserva(hotel, usuario);

        if (pasajero == null) {

            System.out.println(AMARILLO +
                    "Reserva cancelada. Volviendo al menú..." + RESET);
            return;
        }

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          PASAJERO SELECCIONADO            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(VERDE +
                "║  Pasajero seleccionado correctamente" + RESET);

        System.out.println(AZUL +
                "║  Nombre: " +
                pasajero.getNombre() + " " +
                pasajero.getApellido());

        System.out.println(
                "║  DNI: " + pasajero.getDNI() + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        // =========================================
        // 2. OBTENER HABITACIONES EN ESTADO NORMAL
        // =========================================

        java.util.List<Habitacion> habitaciones =
                new java.util.ArrayList<>();

        for (Habitacion h : hotel.getHabitaciones().obtenerTodos()) {

            if (h.getEstadoMantenimiento()
                    == EEstadoMantenimiento.NORMAL) {

                habitaciones.add(h);
            }
        }

        if (habitaciones.isEmpty()) {

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║           HABITACIONES DISPONIBLES        ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(ROJO +
                    "║  ERROR: No hay habitaciones habilitadas" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 3. MOSTRAR HABITACIONES
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          HABITACIONES DISPONIBLES         ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        for (int i = 0; i < habitaciones.size(); i++) {

            Habitacion h = habitaciones.get(i);

            System.out.println(AZUL +
                    "║  " + (i + 1) + ". Habitación " +
                    h.getNumeroHabitacion());

            System.out.println(
                    "║     Camas: " + h.getCantidadCamas() +
                            " | Capacidad: " +
                            h.getCantidadMaximaPersonas() + RESET);

            System.out.println(CYAN +
                    "║                                           ║" + RESET);
        }

        System.out.println(AMARILLO +
                "║  0. Volver al menú" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        int opcion = pedirOpcion(0, habitaciones.size());

        if (opcion == 0) {

            System.out.println(AMARILLO +
                    "Reserva cancelada. Volviendo al menú..." + RESET);
            return;
        }

        Habitacion habitacion = habitaciones.get(opcion - 1);

        // =========================================
        // 4. CANTIDAD DE PERSONAS
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║           CANTIDAD DE PERSONAS            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  Habitación: " +
                habitacion.getNumeroHabitacion());

        System.out.println(
                "║  Capacidad máxima: " +
                        habitacion.getCantidadMaximaPersonas() + RESET);

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        System.out.println(AMARILLO +
                "║  0. Volver al menú" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        int cantidadPasajeros =
                pedirOpcion(0, habitacion.getCantidadMaximaPersonas());

        if (cantidadPasajeros == 0) {

            System.out.println(AMARILLO +
                    "Reserva cancelada. Volviendo al menú..." + RESET);
            return;
        }

        // =========================================
        // 5. FECHAS DE RESERVA
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             FECHAS DE RESERVA             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AMARILLO +
                "║  Escribí 0 para volver al menú" + RESET);

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        // FECHA DE INGRESO
        java.time.LocalDate fechaIngreso;

        while (true) {

            System.out.print(AZUL +
                    "║  Fecha de ingreso (DD-MM-AAAA): " + RESET);

            String entrada = sc.nextLine().trim();

            if (entrada.equals("0")) {

                System.out.println(AMARILLO +
                        "Reserva cancelada. Volviendo al menú..." + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝" + RESET);
                return;
            }

            try {

                fechaIngreso = java.time.LocalDate.parse(
                        entrada, FORMATO_FECHA);

                if (fechaIngreso.isBefore(
                        java.time.LocalDate.now())) {

                    System.out.println(ROJO +
                            "║  ERROR: No puede ser anterior a hoy" + RESET);
                    continue;
                }

                break;

            } catch (java.time.format.DateTimeParseException e) {

                System.out.println(ROJO +
                        "║  ERROR: Fecha inválida. Ejemplo: 20-11-2026"
                        + RESET);
            }
        }

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        // FECHA DE SALIDA
        java.time.LocalDate fechaSalida;

        while (true) {

            System.out.print(AZUL +
                    "║  Fecha de salida (DD-MM-AAAA): " + RESET);

            String entrada = sc.nextLine().trim();

            if (entrada.equals("0")) {

                System.out.println(AMARILLO +
                        "Reserva cancelada. Volviendo al menú..." + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝" + RESET);
                return;
            }

            try {

                fechaSalida = java.time.LocalDate.parse(
                        entrada, FORMATO_FECHA);

                if (!fechaSalida.isAfter(fechaIngreso)) {

                    System.out.println(ROJO +
                            "║  ERROR: La salida debe ser posterior al ingreso"
                            + RESET);
                    continue;
                }

                break;

            } catch (java.time.format.DateTimeParseException e) {

                System.out.println(ROJO +
                        "║  ERROR: Fecha inválida. Ejemplo: 25-11-2026"
                        + RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        // =========================================
        // 6. VALIDAR ESTADO DE LA HABITACIÓN
        // =========================================

        if (habitacion.getEstadoMantenimiento()
                != EEstadoMantenimiento.NORMAL) {

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║          DISPONIBILIDAD HABITACIÓN        ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(ROJO +
                    "║  ERROR: Habitación no habilitada" + RESET);

            System.out.println(AMARILLO +
                    "║  Estado: " +
                    habitacion.getEstadoMantenimiento() + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 7. VALIDAR DISPONIBILIDAD POR FECHAS
        // =========================================

        if (!hotel.habitacionDisponible(
                habitacion, fechaIngreso, fechaSalida)) {

            java.time.LocalDate proximaFecha =
                    hotel.proximaFechaDisponible(
                            habitacion, fechaIngreso, fechaSalida);

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║          DISPONIBILIDAD HABITACIÓN        ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(ROJO +
                    "║  ERROR: Habitación ocupada en esas fechas"
                    + RESET);

            if (proximaFecha != null) {

                System.out.println(CYAN +
                        "║                                           ║" + RESET);

                System.out.println(VERDE +
                        "║  Próxima fecha disponible: " +
                        proximaFecha.format(FORMATO_FECHA) + RESET);
            }

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 8. CALCULAR DÍAS Y NOCHES
        // =========================================

        long cantidadNoches =
                java.time.temporal.ChronoUnit.DAYS.between(
                        fechaIngreso, fechaSalida);

        long cantidadDias = cantidadNoches + 1;

        // =========================================
        // 9. CREAR RESERVA
        // =========================================

        Reserva reserva = new Reserva(
                pasajero,
                habitacion,
                cantidadPasajeros,
                fechaIngreso,
                fechaSalida
        );

        // =========================================
        // 10. MOSTRAR RESUMEN
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             RESUMEN DE RESERVA            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ID: " + reserva.getIdReserva());

        System.out.println(
                "║  Pasajero: " +
                        pasajero.getNombre() + " " +
                        pasajero.getApellido());

        System.out.println(
                "║  DNI: " + pasajero.getDNI());

        System.out.println(
                "║  Habitación: " +
                        habitacion.getNumeroHabitacion());

        System.out.println(
                "║  Cantidad de personas: " + cantidadPasajeros);

        System.out.println(
                "║  Ingreso: " +
                        fechaIngreso.format(FORMATO_FECHA));

        System.out.println(
                "║  Salida: " +
                        fechaSalida.format(FORMATO_FECHA));

        System.out.println(
                "║  Total de días: " + cantidadDias);

        System.out.println(
                "║  Total de noches: " +
                        cantidadNoches + RESET);

        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣" + RESET);

        // =========================================
        // 11. CONFIRMAR RESERVA
        // =========================================

        System.out.println(AMARILLO +
                "║  ¿Confirmar reserva? (Si/No)" + RESET);

        System.out.println(AMARILLO +
                "║  0. Volver al menú" + RESET);

        String respuesta;

        while (true) {

            System.out.print(VERDE + "║  ➜ " + RESET);

            respuesta = sc.nextLine().trim();

            if (respuesta.equals("0") ||
                    respuesta.equalsIgnoreCase("Si") ||
                    respuesta.equalsIgnoreCase("S") ||
                    respuesta.equalsIgnoreCase("No") ||
                    respuesta.equalsIgnoreCase("N")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: Ingresá Si, No o 0" + RESET);
        }

        if (respuesta.equals("0") ||
                respuesta.equalsIgnoreCase("No") ||
                respuesta.equalsIgnoreCase("N")) {

            System.out.println(AMARILLO +
                    "║  Reserva cancelada" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 12. GUARDAR RESERVA EN HOTEL
        // =========================================

        if (hotel.agregarReserva(reserva)) {

            System.out.println(VERDE +
                    "║  Reserva realizada correctamente" + RESET);

            System.out.println(VERDE +
                    "║  Guardá tu ID: " +
                    reserva.getIdReserva() + RESET);

        } else {

            System.out.println(ROJO +
                    "║  ERROR: No se pudo realizar la reserva" + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }




    // =========================================
    //              BUSCAR RESERVA
    // =========================================

    public static void verReserva(
            Hotel hotel,
            Usuario usuario,
            boolean esEmpleado) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║               BUSCAR RESERVA              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ◈ Código de reserva" + RESET);

        System.out.print(VERDE + "║  ➜ " + RESET);

        String id = sc.nextLine().trim();

        Reserva reserva = hotel.buscarReserva(id);

        if (reserva == null) {

            System.out.println(ROJO +
                    "║  ERROR: Reserva no encontrada" + RESET);

        } else if (!puedeGestionarReserva(
                reserva, usuario, esEmpleado)) {

            System.out.println(ROJO +
                    "║  No tenés permiso para ver esta reserva" +
                    RESET);

        } else {

            System.out.println(VERDE +
                    "║  RESERVA ENCONTRADA" + RESET);

            imprimirReserva(reserva);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // =========================================
    //             MODIFICAR RESERVA
    // =========================================

    public static void modificarReserva(
            Hotel hotel,
            Usuario usuario,
            boolean esEmpleado) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             MODIFICAR RESERVA             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.print(AZUL +
                "║  Código de reserva: " + RESET);

        String id = sc.nextLine().trim();

        Reserva reserva = hotel.buscarReserva(id);

        if (reserva == null) {

            System.out.println(ROJO +
                    "║  Reserva no encontrada" + RESET);

        } else if (!puedeGestionarReserva(
                reserva, usuario, esEmpleado)) {

            System.out.println(ROJO +
                    "║  No podés modificar esta reserva" + RESET);

        } else {

            imprimirReserva(reserva);

            System.out.println(AMARILLO +
                    "║  Falta implementar la modificación " +
                    "y validar las nuevas fechas." + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // =========================================
    //              ELIMINAR RESERVA
    // =========================================

    public static void eliminarReserva(
            Hotel hotel,
            Usuario usuario,
            boolean esEmpleado) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              ELIMINAR RESERVA             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ◈ Código de reserva" + RESET);

        System.out.print(VERDE + "║  ➜ " + RESET);

        String id = sc.nextLine().trim();

        Reserva reserva = hotel.buscarReserva(id);

        if (reserva == null) {

            System.out.println(ROJO +
                    "║  ERROR: Reserva no encontrada" + RESET);

            return;
        }

        if (!puedeGestionarReserva(
                reserva, usuario, esEmpleado)) {

            System.out.println(ROJO +
                    "║  No podés eliminar reservas de otros pasajeros" +
                    RESET);

            return;
        }

        imprimirReserva(reserva);

        if (confirmarEliminacion()) {

            if (hotel.eliminarReserva(id)) {

                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println(
                        "║        RESERVA ELIMINADA CON ÉXITO        ║");
                System.out.println(
                        "╚═══════════════════════════════════════════╝" +
                                RESET);

            } else {

                System.out.println(ROJO +
                        "║  ERROR: No se pudo eliminar la reserva" +
                        RESET);
            }

        } else {

            System.out.println(AMARILLO +
                    "║  Eliminación cancelada" + RESET);
        }
    }

    // =========================================
    //              MOSTRAR RESERVAS
    // =========================================

    public static void mostrarReservas(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              MOSTRAR RESERVAS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (hotel.mostrarReservas().isEmpty()) {

            System.out.println(ROJO +
                    "║  No hay reservas registradas" + RESET);

        } else {

            for (Reserva reserva : hotel.mostrarReservas()) {

                imprimirReserva(reserva);

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣" +
                        RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // =========================================
    // HABITACIONES
    // =========================================


    public static void mostrarHabitacionesDisponibles(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║           HABITACIONES REGISTRADAS        ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        java.util.List<Habitacion> habitaciones =
                new java.util.ArrayList<>(
                        hotel.getHabitaciones().obtenerTodos());

        if (habitaciones.isEmpty()) {

            System.out.println(ROJO +
                    "║  No hay habitaciones registradas" + RESET);

        } else {

            System.out.println(CYAN +
                    "║                                           ║" + RESET);

            for (int i = 0; i < habitaciones.size(); i++) {

                Habitacion h = habitaciones.get(i);

                System.out.println(AZUL +
                        "║  " + (i + 1) + ". Habitación " +
                        h.getNumeroHabitacion() + RESET);

                System.out.println(AZUL +
                        "║     Camas: " + h.getCantidadCamas() +
                        " | Capacidad: " +
                        h.getCantidadMaximaPersonas() + RESET);

                System.out.println(CYAN +
                        "║                                           ║" + RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    public static void mostrarHabitacionesOcupadas(Hotel hotel) {

        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║           HABITACIONES OCUPADAS           ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (hotel.verHabitacionesOcupadas().isEmpty()) {

            System.out.println(VERDE +
                    "No hay habitaciones ocupadas" + RESET);

        } else {

            for (Habitacion h : hotel.verHabitacionesOcupadas()) {

                System.out.println(ROJO +
                        "HABITACIÓN OCUPADA" + RESET);

                System.out.println(AZUL + h + RESET);

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣" + RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // =========================================
    // MANDAR HABIATCION A REPARAR
    // =========================================


    public static void ponerHabitacionEnReparacion(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          GESTIÓN DE HABITACIONES          ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // =========================================
        // 1. MOSTRAR TODAS LAS HABITACIONES
        // =========================================

        java.util.Collection<Habitacion> habitaciones =
                hotel.getHabitaciones().obtenerTodos();

        if (habitaciones.isEmpty()) {

            System.out.println(ROJO +
                    "║  ERROR: No hay habitaciones registradas" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        System.out.println(AMARILLO +
                "║  HABITACIONES REGISTRADAS" + RESET);

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        for (Habitacion h : habitaciones) {

            System.out.println(AZUL +
                    "║  Habitación: " + h.getNumeroHabitacion());

            System.out.println(
                    "║  Capacidad: " +
                            h.getCantidadMaximaPersonas());

            System.out.println(
                    "║  Estado: " +
                            h.getEstadoMantenimiento() + RESET);

            System.out.println(CYAN +
                    "║                                           ║" + RESET);
        }

        System.out.println(AMARILLO +
                "║  0. Volver al menú" + RESET);

        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣" + RESET);

        // =========================================
        // 2. SELECCIONAR HABITACIÓN POR NÚMERO
        // =========================================

        System.out.print(AZUL +
                "║  Número de habitación: " + RESET);

        String numeroIngresado = sc.nextLine().trim();

        if (numeroIngresado.equals("0")) {

            System.out.println(AMARILLO +
                    "║  Operación cancelada" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        Habitacion habitacion = null;

        for (Habitacion h : habitaciones) {

            if (String.valueOf(h.getNumeroHabitacion())
                    .equals(numeroIngresado)) {

                habitacion = h;
                break;
            }
        }

        if (habitacion == null) {

            System.out.println(ROJO +
                    "║  ERROR: La habitación no existe" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 3. ELEGIR ESTADO DE LA HABITACIÓN
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              CAMBIAR ESTADO               ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  Habitación: " +
                habitacion.getNumeroHabitacion());

        System.out.println(
                "║  Estado actual: " +
                        habitacion.getEstadoMantenimiento() + RESET);

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        System.out.println(VERDE +
                "║  1. NORMAL                                ║" + RESET);

        System.out.println(AMARILLO +
                "║  2. NECESITA_REPARACION                   ║" + RESET);

        System.out.println(ROJO +
                "║  3. EN_REPARACION                         ║" + RESET);

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        System.out.println(AMARILLO +
                "║  0. Volver al menú" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        int opcionEstado = pedirOpcion(0, 3);

        if (opcionEstado == 0) {

            System.out.println(AMARILLO +
                    "Operación cancelada" + RESET);
            return;
        }

        EEstadoMantenimiento estadoActual =
                habitacion.getEstadoMantenimiento();

        // =========================================
        // 4. OPCIÓN 1: NORMAL
        // =========================================

        if (opcionEstado == 1) {

            if (estadoActual == EEstadoMantenimiento.NORMAL) {

                System.out.println(AMARILLO +
                        "La habitación ya está en estado NORMAL" + RESET);
                return;
            }

            try {

                // SI ESTABA EN REPARACIÓN, FINALIZARLA
                if (estadoActual ==
                        EEstadoMantenimiento.EN_REPARACION) {

                    hotel.finalizarReparacion(habitacion);

                } else {

                    // SI SOLO NECESITABA REPARACIÓN
                    habitacion.setEstadoMantenimiento(
                            EEstadoMantenimiento.NORMAL);
                }

                System.out.println();
                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║       ESTADO CAMBIADO CORRECTAMENTE       ║");
                System.out.println("╠═══════════════════════════════════════════╣");
                System.out.println("║  Habitación: " +
                        habitacion.getNumeroHabitacion());
                System.out.println("║  Nuevo estado: " +
                        habitacion.getEstadoMantenimiento());
                System.out.println("╚═══════════════════════════════════════════╝"
                        + RESET);

            } catch (IllegalStateException e) {

                System.out.println(ROJO +
                        "ERROR: " + e.getMessage() + RESET);
            }

            return;
        }

        // =========================================
        // 5. OPCIÓN 2: NECESITA_REPARACION
        // =========================================

        if (opcionEstado == 2) {

            if (estadoActual ==
                    EEstadoMantenimiento.NECESITA_REPARACION) {

                System.out.println(AMARILLO +
                        "La habitación ya está en ese estado" + RESET);
                return;
            }

            if (estadoActual ==
                    EEstadoMantenimiento.EN_REPARACION) {

                System.out.println(ROJO +
                        "ERROR: La habitación está en reparación" + RESET);

                System.out.println(AMARILLO +
                        "Primero debe finalizar la reparación" + RESET);
                return;
            }

            try {

                // CAMBIAR SOLO A NECESITA_REPARACION
                hotel.registrarNecesidadReparacion(habitacion);

                System.out.println();
                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║       ESTADO CAMBIADO CORRECTAMENTE       ║");
                System.out.println("╠═══════════════════════════════════════════╣");
                System.out.println("║  Habitación: " +
                        habitacion.getNumeroHabitacion());
                System.out.println("║  Nuevo estado: " +
                        habitacion.getEstadoMantenimiento());
                System.out.println("╚═══════════════════════════════════════════╝"
                        + RESET);

            } catch (IllegalArgumentException |
                     IllegalStateException e) {

                System.out.println(ROJO +
                        "ERROR: " + e.getMessage() + RESET);
            }

            return;
        }

        // =========================================
        // 6. OPCIÓN 3: EN_REPARACION
        // =========================================

        if (opcionEstado == 3) {

            if (estadoActual ==
                    EEstadoMantenimiento.EN_REPARACION) {

                System.out.println(AMARILLO +
                        "La habitación ya está en reparación" + RESET);

                Tecnico asignado =
                        hotel.obtenerTecnicoAsignado(habitacion);

                if (asignado != null) {

                    System.out.println(AZUL +
                            "Técnico: " + asignado.getNombre());

                    System.out.println(
                            "Tipo: " + asignado.getTipo_de_tecnico());

                    System.out.println(
                            "Teléfono: " +
                                    asignado.getTelefono() + RESET);
                }

                return;
            }

            // =========================================
            // 7. OBTENER TÉCNICOS DISPONIBLES
            // =========================================

            java.util.List<Tecnico> tecnicos =
                    new java.util.ArrayList<>();

            for (Tecnico t : hotel.verTecnicos()) {

                if (t.isDisponible()) {
                    tecnicos.add(t);
                }
            }

            if (tecnicos.isEmpty()) {

                System.out.println();
                System.out.println(CYAN +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║              ASIGNAR TÉCNICO              ║");
                System.out.println("╠═══════════════════════════════════════════╣" + RESET);

                System.out.println(ROJO +
                        "║  ERROR: No hay técnicos disponibles" + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝" + RESET);
                return;
            }

            // =========================================
            // 8. MOSTRAR TÉCNICOS DISPONIBLES
            // =========================================

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║              ASIGNAR TÉCNICO              ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL +
                    "║  Habitación: " +
                    habitacion.getNumeroHabitacion() + RESET);

            System.out.println(CYAN +
                    "║                                           ║" + RESET);

            System.out.println(AMARILLO +
                    "║  TÉCNICOS DISPONIBLES" + RESET);

            System.out.println(CYAN +
                    "║                                           ║" + RESET);

            for (int i = 0; i < tecnicos.size(); i++) {

                Tecnico tecnico = tecnicos.get(i);

                System.out.println(AZUL +
                        "║  " + (i + 1) + ". Nombre: " +
                        tecnico.getNombre());

                System.out.println(
                        "║     Tipo: " +
                                tecnico.getTipo_de_tecnico());

                System.out.println(
                        "║     Teléfono: " +
                                tecnico.getTelefono() + RESET);

                System.out.println(CYAN +
                        "║                                           ║" + RESET);
            }

            System.out.println(AMARILLO +
                    "║  0. Cancelar" + RESET);

            System.out.println(CYAN +
                    "╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(VERDE +
                    "║  Seleccioná un técnico:" + RESET);

            int opcionTecnico = pedirOpcion(0, tecnicos.size());

            if (opcionTecnico == 0) {

                System.out.println(AMARILLO +
                        "Asignación cancelada" + RESET);
                return;
            }

            Tecnico tecnicoSeleccionado =
                    tecnicos.get(opcionTecnico - 1);

            // =========================================
            // 9. CONFIRMAR ASIGNACIÓN
            // =========================================

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║           CONFIRMAR ASIGNACIÓN            ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL +
                    "║  Habitación: " +
                    habitacion.getNumeroHabitacion());

            System.out.println(
                    "║  Técnico: " +
                            tecnicoSeleccionado.getNombre());

            System.out.println(
                    "║  Tipo: " +
                            tecnicoSeleccionado.getTipo_de_tecnico());

            System.out.println(
                    "║  Teléfono: " +
                            tecnicoSeleccionado.getTelefono() + RESET);

            System.out.println(CYAN +
                    "║                                           ║" + RESET);

            System.out.println(AMARILLO +
                    "║  ¿Confirmar asignación? (Si/No)" + RESET);

            String respuesta;

            while (true) {

                System.out.print(VERDE +
                        "║  ➜ " + RESET);

                respuesta = sc.nextLine().trim();

                if (respuesta.equalsIgnoreCase("Si") ||
                        respuesta.equalsIgnoreCase("S") ||
                        respuesta.equalsIgnoreCase("No") ||
                        respuesta.equalsIgnoreCase("N") ||
                        respuesta.equals("0")) {
                    break;
                }

                System.out.println(ROJO +
                        "║  ERROR: Ingresá Si, No o 0" + RESET);
            }

            if (respuesta.equalsIgnoreCase("No") ||
                    respuesta.equalsIgnoreCase("N") ||
                    respuesta.equals("0")) {

                System.out.println(AMARILLO +
                        "║  Asignación cancelada" + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝" + RESET);
                return;
            }

            // =========================================
            // 10. ASIGNAR TÉCNICO Y CAMBIAR ESTADO
            // =========================================

            try {

                if (!tecnicoSeleccionado.isDisponible()) {

                    throw new IllegalStateException(
                            "El técnico seleccionado no está disponible");
                }

                // COMPROBAR QUE LA HABITACIÓN ESTÉ LIBRE
                if (habitacion.isDisponible()) {

                    throw new IllegalStateException(
                            "La habitación está ocupada. " +
                                    "Debe liberarse primero");
                }

                // SI ESTÁ NORMAL, REGISTRAR QUE NECESITA REPARACIÓN
                if (estadoActual ==
                        EEstadoMantenimiento.NORMAL) {

                    hotel.registrarNecesidadReparacion(habitacion);
                }

                // ASIGNAR TÉCNICO:
                // ESTE MÉTODO PONE LA HABITACIÓN EN EN_REPARACION
                hotel.asignarTecnico(
                        habitacion, tecnicoSeleccionado);

                // MARCAR AL TÉCNICO COMO OCUPADO
                tecnicoSeleccionado.setDisponible(false);

                System.out.println();
                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║       TÉCNICO ASIGNADO CORRECTAMENTE      ║");
                System.out.println("╠═══════════════════════════════════════════╣");

                System.out.println("║  Habitación: " +
                        habitacion.getNumeroHabitacion());

                System.out.println("║  Técnico: " +
                        tecnicoSeleccionado.getNombre());

                System.out.println("║  Tipo: " +
                        tecnicoSeleccionado.getTipo_de_tecnico());

                System.out.println("║  Teléfono: " +
                        tecnicoSeleccionado.getTelefono());

                System.out.println("║  Nuevo estado: " +
                        habitacion.getEstadoMantenimiento());

                System.out.println("╚═══════════════════════════════════════════╝"
                        + RESET);

            } catch (IllegalArgumentException |
                     IllegalStateException e) {

                System.out.println(ROJO +
                        "║  ERROR: " + e.getMessage() + RESET);
            }
        }
    }



    // =========================================
    // MENÚ PRINCIPAL
    // =========================================

    public static void menuPrincipal(Hotel hotel) {
        int opcion;

        do {
            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║                                           ║");
            System.out.println("║               MENÚ PRINCIPAL              ║");
            System.out.println("║                                           ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN+
                    "║                                           ║");
            System.out.println(AZUL +
                    "║    1. Iniciar Sesión                      ║");
            System.out.println(CYAN+
                    "║                                           ║");
            System.out.println(AZUL+
                    "║    2. Registrarse                         ║" );
            System.out.println(CYAN+
                    "║                                           ║");
            System.out.println(ROJO +
                    "║    0. Salir                               ║"+RESET);
            System.out.println(CYAN +
                    "║                                           ║");
            System.out.println(
                    "╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(0, 2);

            switch (opcion) {
                case 1:
                    iniciarSesion(hotel);
                    break;

                case 2:
                    registrarUsuario(hotel);
                    break;

                case 0:
                    mostrarCierre();
                    break;
            }

        } while (opcion != 0);
    }

    // =========================================
    // INICIAR SESIÓN
    // =========================================


    public static void iniciarSesion(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║                                           ║");
        System.out.println("║              INICIO DE SESIÓN             ║");
        System.out.println("║                                           ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║                                           ║");
        System.out.println("║       Accedé a tu cuenta del hotel        ║");
        System.out.println("║                                           ║" + RESET);

        System.out.println(CYAN +
                "║  ◈ Nombre de usuario" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);

        String nombre = sc.nextLine().trim();

        System.out.println(CYAN +
                "║                                           ║");
        System.out.println("║  ◈ Contraseña" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);

        String contrasenia = sc.nextLine();

        System.out.println(CYAN +
                "║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝" + RESET);

        Usuario usuario = hotel.iniciarSesion(nombre, contrasenia);

        if (usuario == null) {

            System.out.println(ROJO +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║     USUARIO O CONTRASEÑA INCORRECTOS      ║");
            System.out.println("╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        System.out.println(VERDE +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          INICIO DE SESIÓN EXITOSO         ║");
        System.out.println("╚═══════════════════════════════════════════╝" + RESET);

        switch (usuario.getTipoDeUsuario()) {

            case ADMINISTRADOR:
                IniciarMenu(hotel, usuario);
                break;

            case CONSERJE:
                menuConserje(hotel, usuario);
                break;

            case PASAJERO:
                menuPasajero(hotel, usuario);
                break;
        }
    }

    // =========================================
    // INFORMACION HOTEL
    // =========================================

    public static void mostrarInformacionHotel(Hotel hotel) {

        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║           INFORMACIÓN DEL HOTEL           ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "Nombre: " + hotel.getNombre());

        System.out.println("Dirección: " + hotel.getDireccion());

        System.out.println("Total de habitaciones: " +
                hotel.getCantidadHabitaciones());

        System.out.println(VERDE +
                "Habitaciones disponibles registradas: " +
                hotel.verHabitacionesDisponibles().size());

        System.out.println(ROJO +
                "Habitaciones ocupadas registradas: " +
                hotel.verHabitacionesOcupadas().size() + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // =========================================
    // LLAMAR TECNICO
    // =========================================

    public static void llamarTecnico(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║               LLAMAR TÉCNICO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // MOSTRAR HABITACIONES EN REPARACIÓN

        System.out.println(AMARILLO +
                "\nHABITACIONES ACTUALMENTE EN REPARACIÓN:" + RESET);

        if (hotel.verHabitacionesEnReparacion().isEmpty()) {

            System.out.println("No hay habitaciones en reparación.");

        } else {

            for (Habitacion h : hotel.verHabitacionesEnReparacion()) {

                System.out.println(AZUL + "Habitación: " + h);
                System.out.println("Estado: EN REPARACIÓN");
                System.out.println("Técnico asignado: " +
                        hotel.obtenerTecnicoAsignado(h) + RESET);
            }
        }

        // OBTENER HABITACIONES PENDIENTES

        java.util.List<Habitacion> habitaciones =
                hotel.verHabitacionesParaReparar();

        System.out.println(AMARILLO +
                "\nHABITACIONES QUE NECESITAN REPARACIÓN:" + RESET);

        if (habitaciones.isEmpty()) {

            System.out.println(VERDE +
                    "No hay habitaciones pendientes de reparación." + RESET);
            return;
        }

        // MOSTRAR HABITACIONES ENUMERADAS

        for (int i = 0; i < habitaciones.size(); i++) {

            Habitacion h = habitaciones.get(i);

            System.out.println(AZUL +
                    (i + 1) + ". " + h + RESET);
        }

        System.out.println(AMARILLO +
                "\nSeleccione la habitación que necesita reparación." + RESET);

        int opcionHabitacion =
                pedirOpcion(1, habitaciones.size());

        Habitacion seleccionada =
                habitaciones.get(opcionHabitacion - 1);

        // MOSTRAR TODOS LOS TÉCNICOS

        java.util.List<Tecnico> tecnicos =
                new java.util.ArrayList<>(hotel.verTecnicos());

        System.out.println(CYAN +
                "\n╔═══════════════════════════════════════════╗");
        System.out.println("║             TÉCNICOS DISPONIBLES          ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (tecnicos.isEmpty()) {

            System.out.println(ROJO +
                    "No hay técnicos registrados." + RESET);
            return;
        }

        for (int i = 0; i < tecnicos.size(); i++) {

            System.out.println(AZUL +
                    (i + 1) + ". " + tecnicos.get(i) + RESET);
        }

        // SELECCIONAR TÉCNICO

        System.out.println(AMARILLO +
                "\nSeleccione el técnico que atenderá la habitación:" + RESET);

        int opcionTecnico =
                pedirOpcion(1, tecnicos.size());

        Tecnico tecnicoSeleccionado =
                tecnicos.get(opcionTecnico - 1);

        // ASIGNAR TÉCNICO Y CAMBIAR ESTADO

        try {

            hotel.asignarTecnico(
                    seleccionada,
                    tecnicoSeleccionado
            );

            System.out.println(VERDE +
                    "\n╔═══════════════════════════════════════════╗");
            System.out.println("║        TÉCNICO ASIGNADO CON ÉXITO         ║");
            System.out.println("╠═══════════════════════════════════════════╣");

            System.out.println("Habitación: " + seleccionada);
            System.out.println("Técnico: " + tecnicoSeleccionado);
            System.out.println("Estado: EN REPARACIÓN");

            System.out.println("╚═══════════════════════════════════════════╝" + RESET);

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }
    }


    // =========================================
    // MENÚ ADMINISTRADOR
    // =========================================




    public static void IniciarMenu(Hotel hotel, Usuario usuario) {

        int opcion;

        do {
            System.out.println();
            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║                MENÚ ADMIN                 ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            // USUARIOS
            System.out.println(AZUL + "║    1. Buscar Usuario                      ║");
            System.out.println("║    2. Eliminar Usuario                    ║");
            System.out.println("║    3. Mostrar Todos los Usuarios          ║");
            System.out.println("║                                           ║");

            // RESERVAS
            System.out.println("║    4. Agregar Reserva                     ║");
            System.out.println("║    5. Buscar Reserva                      ║");
            System.out.println("║    6. Eliminar Reserva                    ║");
            System.out.println("║    7. Mostrar Reservas                    ║");
            System.out.println("║                                           ║");

            // PASAJEROS
            System.out.println("║    8. Agregar Pasajero                    ║");
            System.out.println("║    9. Buscar Pasajero                     ║");
            System.out.println("║   10. Eliminar Pasajero                   ║");
            System.out.println("║   11. Mostrar Pasajeros                   ║");
            System.out.println("║                                           ║");

            // EMPLEADOS
            System.out.println("║   12. Agregar Empleado                    ║");
            System.out.println("║   13. Buscar Empleado                     ║");
            System.out.println("║   14. Eliminar Empleado                   ║");
            System.out.println("║   15. Mostrar Empleados                   ║");
            System.out.println("║                                           ║");

            // PERMISOS Y HABITACIONES
            System.out.println("║   16. Dar Permiso                         ║");
            System.out.println("║   17. Ver Habitaciones Disponibles        ║");
            System.out.println("║   18. Ver Habitaciones Ocupadas           ║");
            System.out.println("║   19. Ver Información del Hotel           ║" + RESET);

            System.out.println(ROJO + "║    0. Cerrar Sesión                       ║" + RESET);
            System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(0, 19);

            switch (opcion) {

                // =========================================
                // USUARIOS
                // =========================================

                case 1:
                    buscarUsuario(hotel);
                    break;

                case 2:
                    eliminarUsuario(hotel);
                    break;

                case 3:
                    mostrarUsuarios(hotel);
                    break;

                // =========================================
                // RESERVAS
                // =========================================

                case 4:
                    realizarReserva(hotel, usuario);
                    break;

                case 5:
                    verReserva(hotel, usuario, true);
                    break;

                case 6:
                    eliminarReserva(hotel, usuario, true);
                    break;

                case 7:
                    mostrarReservas(hotel);
                    break;

                // =========================================
                // PASAJEROS
                // =========================================

                case 8:
                    agregarPasajero(hotel);
                    break;

                case 9:
                    buscarPasajero(hotel);
                    break;

                case 10:
                    eliminarPasajero(hotel);
                    break;

                case 11:
                    mostrarPasajeros(hotel);
                    break;

                // =========================================
                // EMPLEADOS
                // =========================================

                case 12:
                    agregarEmpleado(hotel);
                    break;

                case 13:
                    buscarEmpleado(hotel);
                    break;

                case 14:
                    eliminarEmpleado(hotel);
                    break;

                case 15:
                    mostrarEmpleados(hotel);
                    break;

                // =========================================
                // PERMISOS
                // =========================================

                case 16:
                    System.out.println(AMARILLO +
                            "Función de permisos pendiente" + RESET);
                    break;

                // =========================================
                // HABITACIONES
                // =========================================

                case 17:
                    mostrarHabitacionesDisponibles(hotel);
                    break;

                case 18:
                    mostrarHabitacionesOcupadas(hotel);
                    break;

                // =========================================
                // INFORMACIÓN DEL HOTEL
                // =========================================

                case 19:
                    mostrarInformacionHotel(hotel);
                    break;

                // =========================================
                // CERRAR SESIÓN
                // =========================================

                case 0:
                    mostrarCierre();
                    break;
            }

        } while (opcion != 0);
    }


    // =========================================
    // MENÚ PASAJERO
    // =========================================


    public static void menuPasajero(Hotel hotel, Usuario usuario) {

        int opcion;

        do {
            System.out.println();

            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║               MENÚ PASAJERO               ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL + "║    1. Realizar Reserva                    ║");
            System.out.println("║    2. Ver Mi Reserva                      ║");
            System.out.println("║    3. Modificar Mi Reserva                ║");
            System.out.println("║    4. Eliminar Mi Reserva                 ║");
            System.out.println("║    5. Ver Habitaciones Disponibles        ║");
            System.out.println("║    6. Ver Información del Hotel           ║" + RESET);

            System.out.println(ROJO + "║    0. Cerrar Sesión                       ║" + RESET);
            System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(0, 6);

            switch (opcion) {

                case 1:
                    realizarReserva(hotel, usuario);
                    break;

                case 2:
                    verReserva(hotel, usuario, false);
                    break;

                case 3:
                    modificarReserva(hotel, usuario, false);
                    break;

                case 4:
                    eliminarReserva(hotel, usuario, false);
                    break;

                case 5:
                    mostrarHabitacionesDisponibles(hotel);
                    break;

                case 6:
                    mostrarInformacionHotel(hotel);
                    break;

                case 0:
                    mostrarCierre();
                    break;
            }

        } while (opcion != 0);
    }


    // =========================================
    // MENÚ CONSERJE
    // =========================================




    public static void menuConserje(Hotel hotel, Usuario usuario) {

        int opcion;

        do {
            System.out.println();

            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║               MENÚ CONSERJE               ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL + "║    1. Agregar Reserva                     ║");
            System.out.println("║    2. Buscar Reserva                      ║");
            System.out.println("║    3. Modificar Reserva                   ║");
            System.out.println("║    4. Eliminar Reserva                    ║");
            System.out.println("║    5. Mostrar Reservas                    ║");
            System.out.println("║                                           ║");
            System.out.println("║    6. Agregar Pasajero                    ║");
            System.out.println("║    7. Buscar Pasajero                     ║");
            System.out.println("║    8. Eliminar Pasajero                   ║");
            System.out.println("║    9. Mostrar Pasajeros                   ║");
            System.out.println("║                                           ║");
            System.out.println("║   11. Poner Habitación en Reparación      ║");
            System.out.println("║                                           ║");
            System.out.println("║   12. Ver Habitaciones Disponibles        ║");
            System.out.println("║                                           ║");
            System.out.println("║   13. Ver Habitaciones Ocupadas           ║");
            System.out.println("║                                           ║");
            System.out.println("║   14. Ver Información del Hotel           ║" );
            System.out.println("║                                           ║"+ RESET);

            System.out.println(ROJO + "║    0. Cerrar Sesión                       ║" + RESET);
            System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(0, 14);

            switch (opcion) {

                // =========================================
                // RESERVAS
                // =========================================

                case 1:
                    realizarReserva(hotel, usuario);
                    break;

                case 2:
                    verReserva(hotel, usuario, true);
                    break;

                case 3:
                    modificarReserva(hotel, usuario, true);
                    break;

                case 4:
                    eliminarReserva(hotel, usuario, true);
                    break;

                case 5:
                    mostrarReservas(hotel);
                    break;

                // =========================================
                // PASAJEROS
                // =========================================

                case 6:
                    agregarPasajero(hotel);
                    break;

                case 7:
                    buscarPasajero(hotel);
                    break;

                case 8:
                    eliminarPasajero(hotel);
                    break;

                case 9:
                    mostrarPasajeros(hotel);
                    break;

                // =========================================
                // TÉCNICOS
                // =========================================

                case 10:
                    llamarTecnico(hotel);
                    break;

                // =========================================
                // REPARACIONES
                // =========================================

                case 11:
                    ponerHabitacionEnReparacion(hotel);
                    break;

                // =========================================
                // HABITACIONES
                // =========================================

                case 12:
                    mostrarHabitacionesDisponibles(hotel);
                    break;

                case 13:
                    mostrarHabitacionesOcupadas(hotel);
                    break;

                // =========================================
                // INFORMACIÓN DEL HOTEL
                // =========================================

                case 14:
                    mostrarInformacionHotel(hotel);
                    break;

                // =========================================
                // CERRAR SESIÓN
                // =========================================

                case 0:
                    mostrarCierre();
                    break;
            }

        } while (opcion != 0);
    }


}
