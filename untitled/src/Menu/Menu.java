
package Menu;

import Clases.*;
import Generico.RegistroGenericoMapas;

import java.util.Scanner;

public class Menu {

    // COLORES
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static final String VERDE = "\u001B[32m";
    public static final String ROJO = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String AMARILLO = "\u001B[33m";

    static Scanner sc = new Scanner(System.in);

    // VALIDAR OPCIONES NUMÉRICAS
    public static int pedirOpcion(int maximo) {

        while (true) {

            System.out.print(VERDE + "\n➜ Elegí una opción: " + RESET);
            String entrada = sc.nextLine().trim();

            if (!entrada.matches("[0-9]+")) {
                System.out.println(ROJO +
                        "✘ ERROR: Solo se permiten números enteros." + RESET);
                continue;
            }

            try {
                int opcion = Integer.parseInt(entrada);

                if (opcion >= 0 && opcion <= maximo) {
                    return opcion;
                }

                System.out.println(ROJO +
                        "✘ ERROR: Elegí una opción entre 0 y " +
                        maximo + RESET);

            } catch (NumberFormatException e) {
                System.out.println(ROJO +
                        "✘ ERROR: Número demasiado grande." + RESET);
            }
        }
    }

    // ==========================================
    //             MENÚ PRINCIPAL
    // ==========================================

    public static void menuPrincipal(
            Hotel hotel,
            RegistroGenericoMapas<String, Usuario> usu) {

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

            opcion = pedirOpcion(2);

            switch (opcion) {

                case 1:
                    FuncionesMenu.iniciarSesion(hotel, usu, sc);
                    break;

                case 2:
                    FuncionesMenu.registrarUsuario(usu, sc);
                    break;

                case 0:
                    FuncionesMenu.mostrarCierre("SISTEMA CERRADO");
                    break;
            }

        } while (opcion != 0);
    }

    // ==========================================
    //         MENÚ ADMINISTRADOR / CONSERJE
    // ==========================================

    public static void IniciarMenu(
            RegistroGenericoMapas<String, Usuario> usu) {

        int opcion;

        do {
            System.out.println();
            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL + "║    1. Agregar usuario                     ║");
            System.out.println("║    2. Buscar usuario                      ║");
            System.out.println("║    3. Eliminar usuario                    ║");
            System.out.println("║    4. Mostrar usuarios                    ║");
            System.out.println("║                                           ║");
            System.out.println("║    5. Realizar Reserva                    ║");
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
            System.out.println("║                                           ║");
            System.out.println("║   18. Mostrar Habitaciones                ║");
            System.out.println("║   19. Mostrar Habitaciones Ocupadas       ║");
            System.out.println("║   20. Mostrar Informacion Hotel           ║");
            System.out.println("║                                           ║" + RESET);

            System.out.println(ROJO + "║    0. Cerrar Sesión                       ║" + RESET);
            System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(20);

            switch (opcion) {

                case 1:
                    FuncionesMenu.registrarUsuario(usu, sc);
                    break;

                case 2:
                    FuncionesMenu.buscarUsuario(usu, sc);
                    break;

                case 3:
                    FuncionesMenu.eliminarUsuario(usu, sc);
                    break;

                case 4:
                    FuncionesMenu.mostrarUsuarios(usu);
                    break;

                case 5:
                    System.out.println(VERDE +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("              REALIZAR RESERVA");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
                    // Acá conectás tu función de realizar reserva.
                    break;

                case 6:
                    System.out.println(CYAN + "BUSCAR RESERVA" + RESET);
                    // Acá conectás tu función de buscar reserva.
                    break;

                case 7:
                    System.out.println(AMARILLO + "ELIMINAR RESERVA" + RESET);
                    // Acá conectás tu función de eliminar reserva.
                    break;

                case 8:
                    System.out.println(CYAN + "MOSTRAR RESERVAS" + RESET);
                    // Acá conectás tu función de mostrar reservas.
                    break;

                case 9:
                    System.out.println(VERDE + "AGREGAR PASAJERO" + RESET);
                    break;

                case 10:
                    System.out.println(CYAN + "BUSCAR PASAJERO" + RESET);
                    break;

                case 11:
                    System.out.println(AMARILLO + "ELIMINAR PASAJERO" + RESET);
                    break;

                case 12:
                    System.out.println(CYAN + "MOSTRAR PASAJEROS" + RESET);
                    break;

                case 13:
                    System.out.println(VERDE + "AGREGAR EMPLEADO" + RESET);
                    break;

                case 14:
                    System.out.println(CYAN + "BUSCAR EMPLEADO" + RESET);
                    break;

                case 15:
                    System.out.println(AMARILLO + "ELIMINAR EMPLEADO" + RESET);
                    break;

                case 16:
                    System.out.println(CYAN + "MOSTRAR EMPLEADOS" + RESET);
                    break;

                case 17:
                    System.out.println(AMARILLO + "DAR PERMISO" + RESET);
                    break;

                case 18:
                    System.out.println(CYAN + "MOSTRAR HABITACIONES" + RESET);
                    break;

                case 19:
                    System.out.println(CYAN + "HABITACIONES OCUPADAS" + RESET);
                    break;

                case 20:
                    System.out.println(CYAN + "INFORMACIÓN DEL HOTEL" + RESET);
                    break;

                case 0:
                    FuncionesMenu.mostrarCierre("SESIÓN CERRADA");
                    break;
            }

        } while (opcion != 0);
    }

    // ==========================================
    //              MENÚ PASAJERO
    // ==========================================

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
            System.out.println("║    2. Eliminar Reserva                    ║");
            System.out.println("║    3. Ver Habitaciones Disponibles        ║");
            System.out.println("║                                           ║" + RESET);

            System.out.println(ROJO + "║    0. Cerrar Sesión                       ║" + RESET);

            System.out.println(CYAN + "║                                           ║");
            System.out.println("╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(3);

            switch (opcion) {

                case 1:
                    System.out.println(VERDE +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("              REALIZAR RESERVA");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);

                    // Conectar método para realizar reserva.
                    break;

                case 2:
                    System.out.println(AMARILLO +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("              ELIMINAR RESERVA");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);

                    // Verificar que la reserva pertenezca al pasajero.
                    break;

                case 3:
                    System.out.println(CYAN +
                            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("         HABITACIONES DISPONIBLES");
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);

                    // Consultar disponibilidad según las fechas.
                    break;

                case 0:
                    FuncionesMenu.mostrarCierre("SESIÓN CERRADA");
                    break;
            }

        } while (opcion != 0);
    }
}
