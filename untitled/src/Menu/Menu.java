package Menu;
import Clases.*;
import Enums.*;
import Exepciones.AgenciaHotelException;
import Exepciones.AgregadoCorrectamenteExepcion;
import Exepciones.DatoInvalidoException;
import Generico.*;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

import static java.awt.Color.CYAN;


public class Menu {


    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static final String VERDE = "\u001B[32m";
    public static final String ROJO = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String AMARILLO = "\u001B[33m";



    static Scanner sc = new Scanner(System.in);


    public static void menuPrincipal(Hotel hotel, RegistroGenericoMapas<String, Usuario> usu) {

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
                    iniciarSesion(hotel, usu);
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



    public static void iniciarSesion(Hotel hotel, RegistroGenericoMapas<String, Usuario> usu) {

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
                IniciarMenu(usu);
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


    public static void IniciarMenu(RegistroGenericoMapas<String, Usuario> usu) {

        int opcion;

        do {
            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║              GESTIÓN HOTEL                ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);
            System.out.println("║                                           ║");
            System.out.println("║                MENÚ ADMIN                 ║");
            System.out.println("║                                           ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);
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
                    System.out.println();
                    System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
                    System.out.println("║              ELIMINAR USUARIO              ║");
                    System.out.println("╠═══════════════════════════════════════════╣");

                    System.out.println(AZUL + "║                                           ║");
                    System.out.println("║  ◈ Nombre de usuario                      ║" + RESET);
                    System.out.print(VERDE + "║  ➜ " + RESET);
                    String nombre1 = sc.nextLine().trim();

                    try {
                        usu.eliminar(nombre1);
                    } catch (DatoInvalidoException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                case 4:

                    mostrarUsuarios(usu);

                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
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


