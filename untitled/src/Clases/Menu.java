package Clases;

import java.time.LocalDateTime;
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

        public static void IniciarMenu() {

            int opcion;

            do {
                System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
                System.out.println("║              GESTIÓN HOTEL                ║");
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

                switch (opcion){

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
    }

