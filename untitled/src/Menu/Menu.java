package Menu;
import Clases.*;
import Enums.*;
import Exepciones.AgenciaHotelException;
import Exepciones.AgregadoCorrectamenteExepcion;
import Exepciones.DatoInvalidoException;
import Generico.*;
import Interfaces.IEmpleado;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;


public class Menu {


    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static final String VERDE = "\u001B[32m";
    public static final String ROJO = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String AMARILLO = "\u001B[33m";



    static Scanner sc = new Scanner(System.in);


    public static void menuPrincipal(Hotel hotel,
                                     RegistroGenericoMapas<String, Usuario> usu,
                                     RegistroGenericoMapas<String, Pasajero> pasa,
                                     RegistroGenericoMapas<String, ArrayList<IEmpleado>> empleado) {

        int opcion;

        do {
            System.out.println();
            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║                                           ║");
            System.out.println("║               MENÚ PRINCIPAL              ║");
            System.out.println("║                                           ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL + "║                                           ║");
            System.out.println("║    1. Iniciar Sesión                      ║");
            System.out.println("║    2. Registrarse                         ║");
            System.out.println("║                                           ║" + RESET);

            System.out.println(ROJO + "║    0. Salir                               ║" + RESET);

            System.out.println(CYAN + "║                                           ║");
            System.out.println("╚═══════════════════════════════════════════╝" + RESET);

            // VALIDAR OPCIÓN
            while (true) {

                System.out.print(VERDE + "\n➜ Elegí una opción: " + RESET);
                String entrada = sc.nextLine().trim();

                if (!entrada.matches("[0-2]")) {
                    System.out.println(ROJO +
                            "ERROR: Ingresá un número del 0 al 2." + RESET);
                    continue;
                }

                opcion = Integer.parseInt(entrada);
                break;
            }

            switch (opcion) {

                case 1:
                    iniciarSesion(hotel, usu, pasa, empleado);
                    break;

                case 2:
                    registrarUsuario(hotel, usu);
                    break;

                case 0:
                    LocalDateTime fechaFinal = LocalDateTime.now();

                    DateTimeFormatter formato =
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                    System.out.println();
                    System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
                    System.out.println("║              SISTEMA CERRADO              ║");
                    System.out.println("╠═══════════════════════════════════════════╣" + RESET);

                    System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                            "      Programa cerrado correctamente");

                    System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                            "          ¡Que tenga buen día!");

                    System.out.printf(CYAN + "║ %-41s ║%n" + RESET,
                            "      Fecha: " + fechaFinal.format(formato));

                    System.out.println(CYAN +
                            "╚═══════════════════════════════════════════╝" + RESET);
                    break;
            }

        } while (opcion != 0);
    }


    public static void buscarUsuario(
            RegistroGenericoMapas<String, Usuario> usu) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║               BUSCAR USUARIO              ║");
        System.out.println("╠═══════════════════════════════════════════╣");

        System.out.println(AZUL + "║                                           ║");
        System.out.println("║  ◈ Ingrese nombre de usuario              ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);

        String nombre = sc.nextLine().trim();

        System.out.println(CYAN + "╠═══════════════════════════════════════════╣" + RESET);

        try {
            Usuario encontrado = usu.buscar(nombre);

            System.out.println(VERDE +
                    "║           USUARIO ENCONTRADO              ║" + RESET);

            System.out.println(CYAN +
                    "╠═══════════════════════════════════════════╣" + RESET);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + encontrado.getNombreUsuario());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Tipo: " + encontrado.getTipoDeUsuario());

        } catch (DatoInvalidoException e) {

            System.out.println(ROJO +
                    "║           USUARIO NO ENCONTRADO           ║" + RESET);

            System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                    "  Verificá el nombre ingresado");
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }



    public static void registrarUsuario(Hotel hotel, RegistroGenericoMapas<String, Usuario> usu) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║               GESTIÓN HOTEL               ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║             REGISTRAR USUARIO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL + "║                                           ║");
        System.out.println("║  ◈ Nombre de usuario                      ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);
        String nombre = sc.nextLine().trim();

        System.out.println(AZUL + "║                                           ║");
        System.out.println("║  ◈ Contraseña                             ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);
        String contrasenia = sc.nextLine();

        System.out.println(CYAN + "║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝" + RESET);

        // Los registros públicos son de tipo PASAJERO
        Usuario nuevo = new Usuario(
                nombre,
                contrasenia,
                ETipoUsuario.PASAJERO
        );

        try {
            nuevo.validarDatos();

            usu.cargar(nuevo.getNombreUsuario(), nuevo);

            System.out.println(VERDE +
                    "Usuario registrado correctamente" + RESET);

        } catch (AgenciaHotelException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }
    }



    public static void iniciarSesion(Hotel hotel, RegistroGenericoMapas<String, Usuario> usu,
                                     RegistroGenericoMapas<String, Pasajero> pasa,
                                     RegistroGenericoMapas<String, ArrayList<IEmpleado>> empleado) {

        Usuario usuario;

        do {
            System.out.println();
            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║                                           ║");
            System.out.println("║             INICIO DE SESIÓN              ║");
            System.out.println("║                                           ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║       Accedé a tu cuenta del hotel        ║");
            System.out.println("║                                           ║");
            System.out.println("║  ◈ Nombre de usuario                      ║" + RESET);

            System.out.print(CYAN + "║  " + VERDE + "➜ " + RESET);
            String nombre = sc.nextLine().trim();

            System.out.println(CYAN + "║                                           ║");
            System.out.println("║  ◈ Contraseña                             ║" + RESET);

            System.out.print(CYAN + "║  " + VERDE + "➜ " + RESET);
            String contrasenia = sc.nextLine();

            System.out.println(CYAN + "║                                           ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║          VALIDANDO CREDENCIALES           ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            usuario = usu.iniciarSesion(nombre, contrasenia);

            if (usuario == null) {

                System.out.println(ROJO +
                        "║     Usuario o contraseña incorrectos      ║" + RESET);

                System.out.println(AMARILLO +
                        "║            Intentá nuevamente            ║" + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝" + RESET);
            }

        } while (usuario == null);

        System.out.println(VERDE +
                "║       Inicio de sesión exitoso            ║" + RESET);

        System.out.println(AMARILLO +
                "║        Bienvenido al sistema              ║" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        switch (usuario.getTipoDeUsuario()) {

            case ADMINISTRADOR:
                IniciarMenu(usu, pasa, empleado);
                break;

            case PASAJERO:
                menuPasajero();
                break;

            case CONSERJE:
                menuConserje(usu);

            default:
                System.out.println(ROJO +
                        "Tipo de usuario no reconocido" + RESET);
        }
    }



    public static void menuPasajero() {

        int opcion;

        do {
            System.out.println();
            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║                                           ║");
            System.out.println("║               MENÚ PASAJERO               ║");
            System.out.println("║                                           ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL + "║                                           ║");
            System.out.println("║    1. Realizar Reserva                    ║");
            System.out.println("║    2. Ver Reserva                         ║");
            System.out.println("║    3. Moficar Reserva                     ║");
            System.out.println("║    4. Eliminar Reserva                    ║");
            System.out.println("║    5. Ver Habitaciones Disponibles        ║");
            System.out.println("║                                           ║" + RESET);

            System.out.println(ROJO + "║    0. Cerrar Sesión                       ║" + RESET);

            System.out.println(CYAN + "║                                           ║");
            System.out.println("╚═══════════════════════════════════════════╝" + RESET);

            // VALIDACIÓN DE LA OPCIÓN
            while (true) {

                System.out.print(VERDE + "\n➜ Elegí una opción: " + RESET);

                String entrada = sc.nextLine().trim();

                if (!entrada.matches("[0-3]")) {
                    System.out.println(ROJO +
                            "ERROR: Solo se permiten números del 0 al 3." + RESET);
                    continue;
                }

                opcion = Integer.parseInt(entrada);
                break;
            }

            // OPCIONES DEL MENÚ
            switch (opcion) {

                case 1:
                    System.out.println();
                    System.out.println(VERDE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("              REALIZAR RESERVA");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);

                    // Acá llamás a tu función para realizar reservas.
                    // Utilizá el usuario que inició sesión.

                    break;

                case 2:
                    System.out.println();
                    System.out.println(AMARILLO + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("              VER RESERVA");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);

                    // Acá llamás a tu función para eliminar reservas.
                    // Verificá que la reserva pertenezca al pasajero.

                    break;

                case 3:
                    System.out.println();
                    System.out.println(CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("         MODIFICAR RESERVA");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);

                    // Acá llamás a la función de Hotel
                    // para consultar las habitaciones disponibles.

                    break;

                case 4:
                    System.out.println();
                    System.out.println(CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("         ELIMINAR RESERVA");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);

                    break;

                case 5:
                    System.out.println();
                    System.out.println(CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("         HABITACIONES DISPONIBLES");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);

                case 0:
                    LocalDateTime fechaCierre = LocalDateTime.now();

                    DateTimeFormatter formato =
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                    System.out.println();
                    System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
                    System.out.println("║              SESIÓN CERRADA               ║");
                    System.out.println("╠═══════════════════════════════════════════╣" + RESET);

                    System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                            "      Sesión cerrada correctamente");

                    System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                            "          ¡Que tenga buen día!");

                    System.out.println(CYAN + "║                                           ║");

                    System.out.printf(CYAN + "║ %-41s ║%n" + RESET,
                            "      Fecha: " + fechaCierre.format(formato));

                    System.out.println(CYAN +
                            "╚═══════════════════════════════════════════╝" + RESET);
                    break;
            }

        } while (opcion != 0);
    }


    public static void mostrarUsuarios(
            RegistroGenericoMapas<String, Usuario> usu) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║              MOSTRAR USUARIOS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (usu.obtenerTodos().isEmpty()) {

            System.out.printf(ROJO + "║ %-41s ║%n" + RESET,
                    "  No hay usuarios registrados");

        } else {

            int contador = 0;
            int total = usu.obtenerTodos().size();

            for (Usuario usuario : usu.obtenerTodos()) {

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Nombre: " + usuario.getNombreUsuario());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Contraseña: " + usuario.getContrasenia());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Tipo: " + usuario.getTipoDeUsuario());

                contador++;

                if (contador < total) {
                    System.out.println(CYAN +
                            "╠═══════════════════════════════════════════╣" + RESET);
                }
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    public static void agregarPasajero(
            RegistroGenericoMapas<String, Pasajero> pasajeros,
            RegistroGenericoMapas<String, Usuario> usuarios
            ) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║              AGREGAR PASAJERO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);


        String nombre = pedirSoloLetras("Nombre", sc);

        String apellido = pedirSoloLetras("Apellido", sc);

// DNI: solo números, entre 7 y 8 dígitos
        String dni;

        while (true) {
            System.out.println(AZUL +
                    "║  ◈ DNI                                     ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            dni = sc.nextLine().trim();

            if (dni.matches("[0-9]{7,8}")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: El DNI debe tener 7 u 8 números." + RESET);
        }

// TELÉFONO: solo números, entre 8 y 15 dígitos
        String telefono;

        while (true) {
            System.out.println(AZUL +
                    "║  Teléfono                                ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            telefono = sc.nextLine().trim();

            if (telefono.matches("[0-9]{8,10}")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: telefono invalido" + RESET);
        }

// EMAIL: validar formato
        String email;

        while (true) {
            System.out.println(AZUL +
                    "║  ◈ Email                                   ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            email = sc.nextLine().trim();

            if (email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: Ingresá un email válido." + RESET);
        }


        // CREAR USUARIO PARA EL PASAJERO
        System.out.println(CYAN + "╠═══════════════════════════════════════════╣");
        System.out.println("║          CREAR CUENTA DE USUARIO          ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);


        String nombreUsuario;

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ Nombre de usuario                       ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            nombreUsuario = sc.nextLine().trim();

            if (nombreUsuario.matches("^(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")) {
                break;
            }

            System.out.println(ROJO +
                    "║   ERROR: Mínimo 8 caracteres,             ║");
            System.out.println(
                    "║    una mayúscula y un número.              ║" + RESET);
        }


// CONTRASEÑA
        String contrasenia;

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ Contraseña                              ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            contrasenia = sc.nextLine();

            if (contrasenia.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s])\\S{8,}$")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: Mínimo 8 caracteres,             ║");
            System.out.println(
                    "║    una mayúscula, un número y un símbolo.  ║" + RESET);
        }

        Usuario nuevoUsuario = new Usuario(
                nombreUsuario,
                contrasenia,
                ETipoUsuario.PASAJERO
        );

        System.out.println(CYAN + "╠═══════════════════════════════════════════╣");
        System.out.println("║          INFORMACIÓN DEL PASAJERO         ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String origen = pedirSoloLetras("Origen", sc);

        String destinoOrigen = pedirSoloLetras("Destino", sc);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        try {
            nuevoUsuario.validarDatos();

            // COMPROBAR DUPLICADOS ANTES DE GUARDAR
            if (usuarios.containsKey(nombreUsuario)) {
                System.out.println(ROJO +
                        "ERROR: El nombre de usuario ya existe." + RESET);
                return;
            }

            if (pasajeros.containsKey(dni)) {
                System.out.println(ROJO +
                        "ERROR: Ya existe un pasajero con ese DNI." + RESET);
                return;
            }

            // CREAR PASAJERO Y ASOCIARLE SU USUARIO
            Pasajero nuevoPasajero = new Pasajero(
                    nombre,
                    apellido,
                    dni,
                    telefono,
                    email,
                    nuevoUsuario,
                    origen,
                    destinoOrigen
            );

            // GUARDAR LOS DOS OBJETOS
            usuarios.cargar(nombreUsuario, nuevoUsuario);
            pasajeros.cargar(dni, nuevoPasajero);

            System.out.println(VERDE +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║         PASAJERO AGREGADO CON ÉXITO       ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + nombre + " " + apellido);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  DNI: " + dni);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Usuario: " + nombreUsuario);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Tipo: " + nuevoUsuario.getTipoDeUsuario());

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

        } catch (AgenciaHotelException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }
    }

    public static void buscarPasajero(
            RegistroGenericoMapas<String, Pasajero> pasa) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║              BUSCAR PASAJERO              ║");
        System.out.println("╠═══════════════════════════════════════════╣");

        System.out.println(AZUL + "║                                           ║");
        System.out.println("║  ◈ Ingrese DNI                    ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);

        String dni = sc.nextLine().trim();

        System.out.println(CYAN + "╠═══════════════════════════════════════════╣" + RESET);

        try {
            Usuario encontrado = pasa.buscar(dni).getUsuario();

            System.out.println(VERDE +
                    "║           USUARIO ENCONTRADO              ║" + RESET);

            System.out.println(CYAN +
                    "╠═══════════════════════════════════════════╣" + RESET);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + encontrado.getNombreUsuario());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Tipo: " + encontrado.getTipoDeUsuario());

        } catch (DatoInvalidoException e) {

            System.out.println(ROJO +
                    "║           USUARIO NO ENCONTRADO           ║" + RESET);

            System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                    "  Verificá el nombre ingresado");
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }



    public static String pedirSoloLetras(String campo, Scanner sc) {

        while (true) {

            System.out.printf(AZUL + "║  ◈ %-41s ║%n" + RESET, campo);
            System.out.print(VERDE + "║  ➜ " + RESET);

            String dato = sc.nextLine().trim();

            if (dato.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]{2,}( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$")) {
                return dato;
            }

            System.out.println(ROJO +
                    "║  ERROR: Mínimo 2 letras.                ║");

        }
    }


    public static void eliminarPasajero(
            RegistroGenericoMapas<String, Pasajero> pasa) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║             ELIMINAR PASAJERO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL + "║  ◈ DNI del pasajero                       ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);

        String dni = sc.nextLine().trim();

        try {

            // BUSCAR EL PASAJERO ANTES DE ELIMINARLO
            Pasajero encontrado = pasa.buscar(dni);

            System.out.println(CYAN +
                    "╠═══════════════════════════════════════════╣" + RESET);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + encontrado.getNombre());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Apellido: " + encontrado.getApellido());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  DNI: " + encontrado.getDNI());

            // CONFIRMAR ELIMINACIÓN
            String confirmar;

            while (true) {

                System.out.println(AMARILLO +
                        "║  ¿Desea eliminarlo? (S/N)                 ║" + RESET);
                System.out.print(VERDE + "║  ➜ " + RESET);

                confirmar = sc.nextLine().trim();

                if (confirmar.equalsIgnoreCase("S") ||
                        confirmar.equalsIgnoreCase("N")) {
                    break;
                }

                System.out.println(ROJO +
                        "║  ERROR: Ingrese S o N                     ║" + RESET);
            }

            if (confirmar.equalsIgnoreCase("S")) {

                pasa.eliminar(dni);

                System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                        "  Pasajero eliminado correctamente");

            } else {

                System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                        "  Eliminación cancelada");
            }

        } catch (DatoInvalidoException e) {

            System.out.printf(ROJO + "║ %-41s ║%n" + RESET,
                    "  ERROR: Pasajero no encontrado");
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    public static void mostrarPasajeros(
            RegistroGenericoMapas<String, Pasajero> pasa) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║             MOSTRAR PASAJEROS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (pasa.obtenerTodos().isEmpty()) {

            System.out.printf(ROJO + "║ %-41s ║%n" + RESET,
                    "  No hay usuarios registrados");

        } else {

            int contador = 0;
            int total = pasa.obtenerTodos().size();


            for (Pasajero pasajero : pasa.obtenerTodos()) {

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Nombre: " + pasajero.getNombre());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Apellido: " + pasajero.getApellido());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  DNI: " + pasajero.getDNI());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Teléfono: " + pasajero.getTelefono());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Email: " + pasajero.getEmail());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Origen: " + pasajero.getOrigen());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Destino: " + pasajero.getDestino_origen());

                System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                        "  Usuario: " + pasajero.getUsuario().getNombreUsuario());

                System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                        "  Tipo: " + pasajero.getUsuario().getTipoDeUsuario());

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣" + RESET);

            contador++;

                if (contador < total) {
                    System.out.println(CYAN +
                            "╠═══════════════════════════════════════════╣" + RESET);
                }
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    public static void agregarEmpleado(
            RegistroGenericoMapas<String, ArrayList<IEmpleado>> empleados,
            RegistroGenericoMapas<String, Usuario> usu) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║              AGREGAR EMPLEADO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);
        System.out.println(AZUL + "║    1. Administrador                       ║");
        System.out.println("║    2. Conserje                            ║");
        System.out.println("║    0. Volver                              ║" + RESET);
        System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

        int opcion;

        while (true) {
            System.out.print(VERDE + "➜ Elegí una opción: " + RESET);
            String entrada = sc.nextLine().trim();

            if (entrada.matches("[0-2]")) {
                opcion = Integer.parseInt(entrada);
                break;
            }

            System.out.println(ROJO +
                    "ERROR: Elegí 0, 1 o 2." + RESET);
        }

        if (opcion == 0) {
            return;
        }

        String tipo = opcion == 1 ? "ADMINISTRADOR" : "CONSERJE";

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║           DATOS DEL EMPLEADO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // DATOS PERSONALES
        String nombre = pedirSoloLetras("Nombre", sc);
        String apellido = pedirSoloLetras("Apellido", sc);

        String dni;
        while (true) {
            System.out.println(AZUL + "║  ◈ DNI                                    ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);
            dni = sc.nextLine().trim();

            if (dni.matches("[0-9]{7,8}")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: DNI de 7 u 8 números              ║" + RESET);
        }

        // VERIFICAR DNI DUPLICADO
        for (ArrayList<IEmpleado> lista : empleados.obtenerTodos()) {
            for (IEmpleado e : lista) {
                if (e.getDni().equals(dni)) {
                    System.out.println(ROJO +
                            "ERROR: Ya existe un empleado con ese DNI." + RESET);
                    return;
                }
            }
        }

        String telefono;
        while (true) {
            System.out.println(AZUL + "║  ◈ Teléfono                               ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);
            telefono = sc.nextLine().trim();

            if (telefono.matches("[0-9]{8,10}")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: Teléfono de 8 a 15 números        ║" + RESET);
        }

        String email;
        while (true) {
            System.out.println(AZUL + "║  ◈ Email                                  ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);
            email = sc.nextLine().trim();

            if (email.matches(
                    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: Email inválido                    ║" + RESET);
        }

        // CREAR CUENTA
        System.out.println(CYAN + "╠═══════════════════════════════════════════╣");
        System.out.println("║             CREAR USUARIO                 ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String nombreUsuario;
        while (true) {
            System.out.println(AZUL +
                    "║  ◈ Nombre de usuario                      ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);
            nombreUsuario = sc.nextLine().trim();

            if (!nombreUsuario.matches(
                    "^(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")) {
                System.out.println(ROJO +
                        "║  Mínimo 8 caracteres, mayúscula y número  ║" + RESET);
                continue;
            }

            if (usu.containsKey(nombreUsuario)) {
                System.out.println(ROJO +
                        "║  ERROR: Ese usuario ya existe             ║" + RESET);
                continue;
            }

            break;
        }

        String contrasenia;
        while (true) {
            System.out.println(AZUL +
                    "║  ◈ Contraseña                             ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);
            contrasenia = sc.nextLine();

            if (contrasenia.matches(
                    "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s])\\S{8,}$")) {
                break;
            }

            System.out.println(ROJO +
                    "║  Mínimo 8 caracteres, mayúscula,          ║");
            System.out.println(
                    "║  número y carácter especial               ║" + RESET);
        }

        ETipoUsuario tipoUsuario = opcion == 1
                ? ETipoUsuario.ADMINISTRADOR
                : ETipoUsuario.CONSERJE;

        Usuario nuevoUsuario = new Usuario(
                nombreUsuario,
                contrasenia,
                tipoUsuario
        );

        // DATOS ESPECÍFICOS DEL EMPLEADO
        System.out.println(CYAN + "╠═══════════════════════════════════════════╣");
        System.out.println("║          DATOS DEL PUESTO                 ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ◈ Legajo                                 ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);
        String legajo = sc.nextLine().trim();

        double sueldo;

        while (true) {
            System.out.println(AZUL +
                    "║  ◈ Sueldo                                 ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);

            try {
                sueldo = Double.parseDouble(
                        sc.nextLine().trim().replace(",", "."));

                if (Double.isFinite(sueldo) && sueldo > 0) {
                    break;
                }
            } catch (NumberFormatException e) {
                // Se muestra el error debajo
            }

            System.out.println(ROJO +
                    "║  ERROR: Ingresá un sueldo válido          ║" + RESET);
        }

        // CREACIÓN DEL EMPLEADO
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

            // Falta adaptar esta parte al constructor
            // real de tu clase Conserje.
            System.out.println(AMARILLO +
                    "Falta configurar el constructor de Conserje."
                    + RESET);
            return;
        }

        try {
            nuevoUsuario.validarDatos();

            ArrayList<IEmpleado> lista = empleados.get(tipo);

            if (lista == null) {
                lista = new ArrayList<>();
            }

            // GUARDAR EL USUARIO Y EL EMPLEADO
            usu.cargar(nombreUsuario, nuevoUsuario);

            lista.add(nuevoEmpleado);

            if (!empleados.containsKey(tipo)) {
                empleados.cargar(tipo, lista);
            }

            System.out.println(VERDE +
                    "╠═══════════════════════════════════════════╣");
            System.out.println("║        EMPLEADO AGREGADO CON ÉXITO        ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + nombre + " " + apellido);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  DNI: " + dni);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Tipo: " + tipo);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Usuario: " + nombreUsuario);

        } catch (AgenciaHotelException e) {
            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    public static void buscarEmpleado(
            RegistroGenericoMapas<String, ArrayList<IEmpleado>> empleados) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║              BUSCAR EMPLEADO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ◈ Ingrese DNI del empleado               ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);

        String dni = sc.nextLine().trim();

        for (ArrayList<IEmpleado> lista : empleados.obtenerTodos()) {

            for (IEmpleado empleado : lista) {

                if (empleado.getDni().equals(dni)) {

                    String tipo = empleado instanceof Administrador
                            ? "ADMINISTRADOR" : "CONSERJE";

                    System.out.println(CYAN +
                            "╠═══════════════════════════════════════════╣" + RESET);

                    System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                            "  EMPLEADO ENCONTRADO");

                    System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                            "  Tipo: " + tipo);

                    System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                            "  DNI: " + empleado.getDni());

                    System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                            "  Información: " + empleado);

                    System.out.println(CYAN +
                            "╚═══════════════════════════════════════════╝" + RESET);
                    return;
                }
            }
        }

        System.out.printf(ROJO + "║ %-41s ║%n" + RESET,
                "  No se encontró el empleado");

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    public static void mostrarEmpleados(
            RegistroGenericoMapas<String, ArrayList<IEmpleado>> empleados) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║             MOSTRAR EMPLEADOS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        int contador = 0;

        for (String tipo : new String[]{"ADMINISTRADOR", "CONSERJE"}) {

            ArrayList<IEmpleado> lista = empleados.get(tipo);

            if (lista == null || lista.isEmpty()) {
                continue;
            }

            for (IEmpleado empleado : lista) {

                if (contador > 0) {
                    System.out.println(CYAN +
                            "╠═══════════════════════════════════════════╣" + RESET);
                }

                System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                        "  " + tipo);

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  DNI: " + empleado.getDni());

                System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                        "  Información: " + empleado);

                contador++;
            }
        }

        if (contador == 0) {
            System.out.printf(ROJO + "║ %-41s ║%n" + RESET,
                    "  No hay empleados registrados");
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    public static void eliminarEmpleado(
            RegistroGenericoMapas<String, ArrayList<IEmpleado>> empleados,
            RegistroGenericoMapas<String, Usuario> usu,
            Scanner sc) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║             ELIMINAR EMPLEADO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ◈ Ingrese DNI del empleado               ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);

        String dni = sc.nextLine().trim();

        if (!dni.matches("[0-9]{7,8}")) {
            System.out.println(ROJO +
                    "║  ERROR: DNI inválido                      ║" + RESET);
            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // RECORRER LOS DOS TIPOS DE EMPLEADOS
        for (String tipo : new String[]{"ADMINISTRADOR", "CONSERJE"}) {

            ArrayList<IEmpleado> lista = empleados.get(tipo);

            if (lista == null) {
                continue;
            }

            for (int i = 0; i < lista.size(); i++) {

                IEmpleado empleado = lista.get(i);

                if (empleado.getDni().equals(dni)) {

                    System.out.println(CYAN +
                            "╠═══════════════════════════════════════════╣" + RESET);

                    System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                            "  EMPLEADO ENCONTRADO");

                    System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                            "  Tipo: " + tipo);

                    System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                            "  DNI: " + empleado.getDni());

                    // CONFIRMAR ELIMINACIÓN
                    String confirmar;

                    while (true) {

                        System.out.println(AMARILLO +
                                "║  ¿Desea eliminarlo? (Si/No)                 ║" + RESET);
                        System.out.print(VERDE + "║  ➜ " + RESET);

                        confirmar = sc.nextLine().trim();

                        if (confirmar.equalsIgnoreCase("Si") ||
                                confirmar.equalsIgnoreCase("No")) {
                            break;
                        }

                        System.out.println(ROJO +
                                "║  ERROR: Ingrese Si o No                     ║" + RESET);
                    }

                    if (confirmar.equalsIgnoreCase("No")) {

                        System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                                "  Eliminación cancelada");

                    } else {

                        // ELIMINAR DE LA LISTA
                        lista.remove(i);

                        System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                                "  Empleado eliminado correctamente");
                    }

                    System.out.println(CYAN +
                            "╚═══════════════════════════════════════════╝" + RESET);

                    return;
                }
            }
        }

        System.out.printf(ROJO + "║ %-41s ║%n" + RESET,
                "  ERROR: Empleado no encontrado");

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    public static void eliminarUsuario(
            RegistroGenericoMapas<String, Usuario> usu) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║              ELIMINAR USUARIO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ◈ Nombre de usuario                      ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);

        String nombre = sc.nextLine().trim();

        try {

            // BUSCAR USUARIO ANTES DE ELIMINARLO
            Usuario encontrado = usu.buscar(nombre);

            System.out.println(CYAN +
                    "╠═══════════════════════════════════════════╣" + RESET);

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + encontrado.getNombreUsuario());

            System.out.printf(AZUL + "║ %-41s ║%n" + RESET,
                    "  Tipo: " + encontrado.getTipoDeUsuario());

            // CONFIRMAR ELIMINACIÓN
            String confirmar;

            while (true) {

                System.out.println(AMARILLO +
                        "║  ¿Desea eliminarlo? (S/N)                 ║" + RESET);

                System.out.print(VERDE + "║  ➜ " + RESET);

                confirmar = sc.nextLine().trim();

                if (confirmar.equalsIgnoreCase("S") ||
                        confirmar.equalsIgnoreCase("N")) {
                    break;
                }

                System.out.println(ROJO +
                        "║  ERROR: Ingrese S o N                     ║" + RESET);
            }

            if (confirmar.equalsIgnoreCase("S")) {

                usu.eliminar(nombre);

                System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                        "  Usuario eliminado correctamente");

            } else {

                System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                        "  Eliminación cancelada");
            }

        } catch (DatoInvalidoException e) {

            System.out.printf(ROJO + "║ %-41s ║%n" + RESET,
                    "  ERROR: " + e.getMessage());
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }








    public static void IniciarMenu(RegistroGenericoMapas<String, Usuario> usu,
                                   RegistroGenericoMapas<String, Pasajero> pasa,
                                   RegistroGenericoMapas<String, ArrayList<IEmpleado>> empleado) {

        int opcion;

        do {
            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println(CYAN +"║              GESTIÓN HOTEL                ║");
            System.out.println(CYAN +"╠═══════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN +"║                                           ║");
            System.out.println(CYAN +"║                MENÚ ADMIN                 ║");
            System.out.println(CYAN +"║                                           ║");
            System.out.println(CYAN +"╠═══════════════════════════════════════════╣" + RESET);
            System.out.println(AZUL + "║    1. Agregar usuario                     ║ " + RESET);
            System.out.println(AZUL + "║    2. Buscar usuario                      ║ " + RESET);
            System.out.println(AZUL + "║    3. Eliminar usuario                    ║ " + RESET);
            System.out.println(AZUL + "║    4. Mostrar usuarios                    ║ " + RESET);
            System.out.println(AZUL + "║                                           ║ " + RESET);
            System.out.println(AZUL + "║    5. Realizar Reserva                    ║ " + RESET);
            System.out.println(AZUL + "║    6. Buscar Reserva                      ║ " + RESET);
            System.out.println(AZUL + "║    7. Eliminar Reserva                    ║ " + RESET);
            System.out.println(AZUL + "║    8. Mostrar Reservas                    ║ " + RESET);
            System.out.println(AZUL + "║                                           ║ " + RESET);
            System.out.println(AZUL + "║    9. Agregar Pasajero                    ║" + RESET);
            System.out.println(AZUL + "║   10. Buscar Pasajero                     ║" + RESET);
            System.out.println(AZUL + "║   11. Eliminar Pasajero                   ║" + RESET);
            System.out.println(AZUL + "║   12. Mostrar Pasajeros                   ║" + RESET);
            System.out.println(AZUL + "║                                           ║" + RESET);
            System.out.println(AZUL + "║   13. Agregar Empleado                    ║" + RESET);
            System.out.println(AZUL + "║   14. Buscar Empleado                     ║" + RESET);
            System.out.println(AZUL + "║   15. Eliminar Empleado                   ║" + RESET);
            System.out.println(AZUL + "║   16. Mostar Empleados                    ║" + RESET);
            System.out.println(AZUL + "║                                           ║" + RESET);
            System.out.println(AZUL + "║   17. Dar Permiso                         ║" + RESET);
            System.out.println(AZUL + "║                                           ║" + RESET);
            System.out.println(AZUL + "║   18. Mostrar Habitaciones                ║" + RESET);
            System.out.println(AZUL + "║   19. Mostrar Habitaciones Ocupadas       ║" + RESET);
            System.out.println(AZUL + "║   20. Mostrar Informacion Hotel           ║" + RESET);
            System.out.println(AZUL + "║                                           ║" + RESET);
            System.out.println(ROJO + "║    0. Salir                               ║" + RESET);
            System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

            String entrada;

            do {
                System.out.print(VERDE + "\n➜ Elegí una opción: " + RESET);
                entrada = sc.nextLine().trim();

                if (!entrada.matches("[0-9]+")) {
                    System.out.println(ROJO +
                            "ERROR: Solo se permiten números enteros." + RESET);
                } else {
                    try {
                        opcion = Integer.parseInt(entrada);

                        if (opcion < 0 || opcion > 20) {
                            System.out.println(ROJO +
                                    "ERROR: Elegí una opción entre 0 y 20." + RESET);
                        } else {
                            break;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println(ROJO +
                                "ERROR: Número demasiado grande." + RESET);
                    }
                }

            } while (true);

            switch (opcion) {

                case 0:
                    LocalDateTime fechaFinal = LocalDateTime.now();

                    DateTimeFormatter formato =
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                    System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
                    System.out.println("║            GESTIÓN HOTEL - FIN            ║");
                    System.out.println("╠═══════════════════════════════════════════╣" + RESET);

                    System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                            "      Programa cerrado correctamente");

                    System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                            "          ¡Que tenga buen día!");

                    System.out.printf(CYAN + "║ %-41s ║%n" + RESET,
                            "      Fecha: " + fechaFinal.format(formato));

                    System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);
                    break;

                case 1:

                    System.out.println("╠═══════════════════════════════════════════╣");
                    System.out.println("║             REGISTRAR USUARIO             ║");
                    System.out.println("╠═══════════════════════════════════════════╣" + RESET);

                    System.out.println(AZUL + "║                                           ║");
                    System.out.println("║  ◈ Nombre de usuario                      ║" + RESET);
                    System.out.print(VERDE + "║  ➜ " + RESET);
                    String nombre = sc.nextLine().trim();

                    System.out.println(AZUL + "║                                           ║");
                    System.out.println("║  ◈ Contraseña                             ║" + RESET);
                    System.out.print(VERDE + "║  ➜ " + RESET);
                    String contrasenia = sc.nextLine();

                    System.out.println(CYAN + "║                                           ║");
                    System.out.println("╚═══════════════════════════════════════════╝" + RESET);

                    // Los registros públicos son de tipo PASAJERO
                    Usuario nuevo = new Usuario(
                            nombre,
                            contrasenia,
                            ETipoUsuario.PASAJERO
                    );

                    try {
                        nuevo.validarDatos();

                        usu.cargar(nuevo.getContrasenia(), nuevo);

                        System.out.println(VERDE +
                                "\n Usuario registrado correctamente" + RESET);

                        System.out.println(AMARILLO +
                                "Ahora podés iniciar sesión." + RESET);

                    }catch (AgregadoCorrectamenteExepcion e) {

                        System.out.println(VERDE +
                                "\nUsuario -> " + e.getMessage() + RESET);
                    }
                    catch (AgenciaHotelException e) {

                        System.out.println(ROJO +
                                "\nERROR: " + e.getMessage() + RESET);
                    }


                case 2:
                    System.out.println();
                    System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
                    System.out.println("║               BUSCAR USUARIO              ║");
                    System.out.println("╠═══════════════════════════════════════════╣" + RESET);

                    buscarUsuario(usu);
                    break;

                case 3:
                    eliminarUsuario(usu);
                    break;

                case 4:

                    mostrarUsuarios(usu);

                    break;
                case 5:
                case 6:
                case 7:
                case 8:

                case 9:
                    agregarPasajero(pasa, usu);
                    break;

                case 10:
                    buscarPasajero(pasa);
                    break;

                case 11:
                    eliminarPasajero(pasa);
                    break;

                case 12:
                    mostrarPasajeros(pasa);
                    break;

                case 13:
                    agregarEmpleado(empleado, usu);
                    break;

                case 14:
                    buscarEmpleado(empleado);
                    break;

                case 15:
                    eliminarEmpleado(empleado, usu, sc);

                case 16:
                    mostrarEmpleados(empleado);
                    break;


                case 17:
                case 18:
                case 19:
                case 20:
            }

        }while (opcion != 0);
    }



    public static void menuConserje (RegistroGenericoMapas<String, Usuario> usu) {

        int opcion;

        do {
            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║              GESTIÓN HOTEL                ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);
            System.out.println("║                                           ║");
            System.out.println("║               MENÚ CONSERJE               ║");
            System.out.println("║                                           ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);
            System.out.println(AZUL + "║                                           ║ " + RESET);
            System.out.println(AZUL + "║    1. Realizar Reserva                    ║ " + RESET);
            System.out.println(AZUL + "║    2. Buscar Reserva                      ║ " + RESET);
            System.out.println(AZUL + "║    3. Eliminar Reserva                    ║ " + RESET);
            System.out.println(AZUL + "║    4. Mostrar Reservas                    ║ " + RESET);
            System.out.println(AZUL + "║                                           ║ " + RESET);
            System.out.println(AZUL + "║    5. Agregar Pasajero                    ║" + RESET);
            System.out.println(AZUL + "║    6. Buscar Pasajero                     ║" + RESET);
            System.out.println(AZUL + "║    7. Eliminar Pasajero                   ║" + RESET);
            System.out.println(AZUL + "║    8. Mostrar Pasajeros                   ║" + RESET);
            System.out.println(AZUL + "║                                           ║" + RESET);
            System.out.println(AZUL + "║    9. Llamar Tecnico                      ║" + RESET);
            System.out.println(AZUL + "║                                           ║" + RESET);
            System.out.println(AZUL + "║   10. Mostrar Habitaciones                ║" + RESET);
            System.out.println(AZUL + "║   11. Mostrar Habitaciones Ocupadas       ║" + RESET);
            System.out.println(AZUL + "║   12. Mostrar Informacion Hotel           ║" + RESET);
            System.out.println(AZUL + "║                                           ║" + RESET);
            System.out.println(ROJO + "║    0. Salir                               ║" + RESET);
            System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

            String entrada;

            do {
                System.out.print(VERDE + "\n➜ Elegí una opción: " + RESET);
                entrada = sc.nextLine().trim();

                if (!entrada.matches("[0-12]+")) {
                    System.out.println(ROJO +
                            "ERROR: Solo se permiten números enteros." + RESET);
                } else {
                    try {
                        opcion = Integer.parseInt(entrada);

                        if (opcion < 0 || opcion > 12) {
                            System.out.println(ROJO +
                                    "ERROR: Elegí una opción entre 0 y 12." + RESET);
                        } else {
                            break;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println(ROJO +
                                "ERROR: Número demasiado grande." + RESET);
                    }
                }

            } while (true);

            switch (opcion) {

                case 0:
                    LocalDateTime fechaFinal = LocalDateTime.now();

                    DateTimeFormatter formato =
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                    System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
                    System.out.println("║            GESTIÓN HOTEL - FIN            ║");
                    System.out.println("╠═══════════════════════════════════════════╣" + RESET);

                    System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                            "      Programa cerrado correctamente");

                    System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                            "          ¡Que tenga buen día!");

                    System.out.printf(CYAN + "║ %-41s ║%n" + RESET,
                            "      Fecha: " + fechaFinal.format(formato));

                    System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);
                    break;

                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:

            }

        }while (opcion != 0);
    }



}


