
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
        System.out.println("║    2. Conserje                            ║");
        System.out.println("║    0. Volver                              ║" + RESET);

        int opcion = pedirOpcion(0, 2);

        if (opcion == 0) {
            return;
        }

        String nombre = pedirSoloLetras("Nombre", sc);
        String apellido = pedirSoloLetras("Apellido", sc);

        // DNI COMO DATO PERSONAL
        String dni;

        while (true) {
            dni = pedirDni();

            if (hotel.existeDniEmpleado(dni)) {
                System.out.println(ROJO +
                        "ERROR: Ya existe un empleado con ese DNI" + RESET);
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

        ETipoUsuario tipoUsuario = opcion == 1
                ? ETipoUsuario.ADMINISTRADOR
                : ETipoUsuario.CONSERJE;

        Usuario nuevoUsuario = new Usuario(
                nombreUsuario,
                contrasenia,
                tipoUsuario
        );

        // LEGAJO: CLAVE DEL MAP
        String legajo;

        while (true) {
            System.out.println(AZUL +
                    "║  ◈ Número de legajo" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            legajo = sc.nextLine().trim();

            if (legajo.isBlank()) {
                System.out.println(ROJO +
                        "ERROR: El legajo no puede estar vacío" + RESET);
                continue;
            }

            if (hotel.getEmpleados().containsKey(legajo)) {
                System.out.println(ROJO +
                        "ERROR: El legajo ya existe" + RESET);
                continue;
            }

            break;
        }

        double sueldo;

        while (true) {
            System.out.println(AZUL + "║  ◈ Sueldo" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            try {
                sueldo = Double.parseDouble(
                        sc.nextLine().trim().replace(",", "."));

                if (Double.isFinite(sueldo) && sueldo > 0) {
                    break;
                }

            } catch (NumberFormatException e) {
                // Pedir nuevamente.
            }

            System.out.println(ROJO +
                    "ERROR: Ingresá un sueldo válido" + RESET);
        }

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
            // Se necesita el constructor real de Conserje.
            System.out.println(AMARILLO +
                    "Falta configurar el constructor de Conserje." + RESET);
            return;
        }

        try {
            nuevoUsuario.validarDatos();

            hotel.agregarEmpleado(legajo, nuevoEmpleado);

            try {
                hotel.agregarUsuario(nuevoUsuario);

            } catch (AgenciaHotelException e) {
                hotel.eliminarEmpleado(legajo);
                throw e;
            }

            System.out.println(VERDE +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║        EMPLEADO AGREGADO CON ÉXITO        ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL +
                    "║  Nombre: " + nombre + " " + apellido);
            System.out.println("║  DNI: " + dni);
            System.out.println("║  Legajo: " + legajo);
            System.out.println("║  Usuario: " + nombreUsuario);
            System.out.println("║  Tipo: " + tipoUsuario + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

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

        System.out.print(AZUL + "Número de legajo: " + RESET);
        String legajo = sc.nextLine().trim();

        try {
            IEmpleado empleado = hotel.buscarEmpleado(legajo);

            String tipo = empleado instanceof Administrador
                    ? "ADMINISTRADOR" : "CONSERJE";

            System.out.println(VERDE +
                    "║  EMPLEADO ENCONTRADO" + RESET);

            System.out.println(AZUL +
                    "║  Legajo: " + legajo);

            System.out.println("║  DNI: " +
                    empleado.getDni());

            System.out.println("║  Tipo: " + tipo + RESET);

        } catch (DatoInvalidoException e) {
            System.out.println(ROJO +
                    "Empleado no encontrado" + RESET);
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

        System.out.print(AZUL + "Número de legajo: " + RESET);
        String legajo = sc.nextLine().trim();

        try {
            IEmpleado empleado = hotel.buscarEmpleado(legajo);

            System.out.println(AZUL +
                    "║  Legajo: " + legajo);
            System.out.println("║  DNI: " +
                    empleado.getDni() + RESET);

            if (confirmarEliminacion()) {
                System.out.println(VERDE +
                        hotel.eliminarEmpleado(legajo) + RESET);
            } else {
                System.out.println(AMARILLO +
                        "Eliminación cancelada" + RESET);
            }

        } catch (DatoInvalidoException e) {
            System.out.println(ROJO +
                    "Empleado no encontrado" + RESET);
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
            for (IEmpleado empleado : hotel.mostrarEmpleados()) {

                String tipo = empleado instanceof Administrador
                        ? "ADMINISTRADOR" : "CONSERJE";

                System.out.println(VERDE +
                        "║  Tipo: " + tipo + RESET);

                System.out.println(AZUL +
                        "║  DNI: " + empleado.getDni() + RESET);

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣" + RESET);
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
        // 1. OBTENER PASAJERO
        // =========================================

        if (usuario == null) {
            System.out.println(ROJO +
                    "║  ERROR: No hay sesión iniciada" + RESET);
            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        Pasajero pasajero = null;

        for (Pasajero p : hotel.mostrarPasajeros()) {

            if (p.getUsuario() != null &&
                    p.getUsuario().getNombreUsuario()
                            .equals(usuario.getNombreUsuario())) {

                pasajero = p;
                break;
            }
        }

        if (pasajero == null) {
            System.out.println(ROJO +
                    "║  ERROR: Usuario sin pasajero asociado" + RESET);
            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 2. MOSTRAR HABITACIONES
        // =========================================

        java.util.List<Habitacion> habitaciones =
                new java.util.ArrayList<>(
                        hotel.getHabitaciones().obtenerTodos());

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
        // 3. CANTIDAD DE PERSONAS
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

        int cantidadPasajeros = pedirOpcion(
                0, habitacion.getCantidadMaximaPersonas());

        if (cantidadPasajeros == 0) {
            System.out.println(AMARILLO +
                    "Reserva cancelada. Volviendo al menú..." + RESET);
            return;
        }

        // =========================================
        // 4. FECHAS DE RESERVA
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
        // 5. VALIDAR DISPONIBILIDAD
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
        // 6. CALCULAR DÍAS Y NOCHES
        // =========================================

        long cantidadNoches =
                java.time.temporal.ChronoUnit.DAYS.between(
                        fechaIngreso, fechaSalida);

        // Contamos también el día de salida
        long cantidadDias = cantidadNoches + 1;

        // =========================================
        // 7. CREAR LA RESERVA
        // =========================================

        Reserva reserva = new Reserva(
                pasajero,
                habitacion,
                cantidadPasajeros,
                fechaIngreso,
                fechaSalida
        );

        // =========================================
        // 8. MOSTRAR RESUMEN CON ID
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             RESUMEN DE RESERVA            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ID: " + reserva.getIdReserva());

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

        // NUEVO: TOTAL DE DÍAS Y NOCHES
        System.out.println(
                "║  Total de días: " + cantidadDias);

        System.out.println(
                "║  Total de noches: " + cantidadNoches + RESET);

        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣" + RESET);

        // =========================================
        // 9. CONFIRMAR RESERVA
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
        // 10. GUARDAR RESERVA EN HOTEL
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

            System.out.println(AZUL + "║    1. Agregar Usuario                     ║");
            System.out.println("║    2. Buscar Usuario                      ║");
            System.out.println("║    3. Eliminar Usuario                    ║");
            System.out.println("║    4. Mostrar Usuarios                    ║");
            System.out.println("║                                           ║");
            System.out.println("║    5. Agregar Reserva                     ║");
            System.out.println("║    6. Buscar Reserva                      ║");
            System.out.println("║    7. Eliminar Reserva                    ║");
            System.out.println("║    8. Mostrar Reservas                    ║");
            System.out.println("║                                           ║");
            System.out.println("║    9. Agregar Pasajero                    ║");
            System.out.println("║   10. Buscar Pasajero                     ║");
            System.out.println("║   11. Eliminar Pasajero                   ║");
            System.out.println("║   12. Mostrar Pasajeros                   ║");
            System.out.println("║                                           ║");
            System.out.println("║   13. Agregar Empleado                    ║");
            System.out.println("║   14. Buscar Empleado                     ║");
            System.out.println("║   15. Eliminar Empleado                   ║");
            System.out.println("║   16. Mostrar Empleados                   ║");
            System.out.println("║                                           ║");
            System.out.println("║   17. Dar Permiso                         ║");
            System.out.println("║   18. Ver Habitaciones Disponibles        ║");
            System.out.println("║   19. Ver Habitaciones Ocupadas           ║");
            System.out.println("║   20. Ver Información del Hotel           ║" + RESET);

            System.out.println(ROJO + "║    0. Cerrar Sesión                       ║" + RESET);
            System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(0, 20);

            switch (opcion) {

                // USUARIOS

                case 1:
                    registrarUsuario(hotel);
                    break;

                case 2:
                    buscarUsuario(hotel);
                    break;

                case 3:
                    eliminarUsuario(hotel);
                    break;

                case 4:
                    mostrarUsuarios(hotel);
                    break;

                // RESERVAS

                case 5:
                    realizarReserva(hotel, usuario);
                    break;

                case 6:
                    verReserva(hotel, usuario, true);
                    break;

                case 7:
                    eliminarReserva(hotel, usuario, true);
                    break;

                case 8:
                    mostrarReservas(hotel);
                    break;

                // PASAJEROS

                case 9:
                    agregarPasajero(hotel);
                    break;

                case 10:
                    buscarPasajero(hotel);
                    break;

                case 11:
                    eliminarPasajero(hotel);
                    break;

                case 12:
                    mostrarPasajeros(hotel);
                    break;

                // EMPLEADOS

                case 13:
                    agregarEmpleado(hotel);
                    break;

                case 14:
                    buscarEmpleado(hotel);
                    break;

                case 15:
                    eliminarEmpleado(hotel);
                    break;

                case 16:
                    mostrarEmpleados(hotel);
                    break;

                // PERMISOS

                case 17:
                    System.out.println(AMARILLO +
                            "Función de permisos pendiente" + RESET);
                    break;

                // HABITACIONES

                case 18:
                    mostrarHabitacionesDisponibles(hotel);
                    break;

                case 19:
                    mostrarHabitacionesOcupadas(hotel);
                    break;

                // INFORMACION DEL HOTEL

                case 20:
                    mostrarInformacionHotel(hotel);
                    break;

                // SALIR

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
            System.out.println("║   10. Llamar a Técnico                    ║");
            System.out.println("║   11. Ver Habitaciones Disponibles        ║");
            System.out.println("║   12. Ver Habitaciones Ocupadas           ║");
            System.out.println("║   13. Ver Información del Hotel           ║" + RESET);

            System.out.println(ROJO + "║    0. Cerrar Sesión                       ║" + RESET);
            System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(0, 13);

            switch (opcion) {

                // RESERVAS

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

                // PASAJEROS

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

                // TECNICOS

                case 10:
                    llamarTecnico(hotel);
                    break;

                // HABITACIONES

                case 11:
                    mostrarHabitacionesDisponibles(hotel);
                    break;

                case 12:
                    mostrarHabitacionesOcupadas(hotel);
                    break;

                // HOTEL

                case 13:
                    mostrarInformacionHotel(hotel);
                    break;

                // SALIR

                case 0:
                    mostrarCierre();
                    break;
            }

        } while (opcion != 0);
    }

}
