
import Clases.*;
import Enums.*;
import Exepciones.AgenciaHotelException;
import Menu.Menu;

import static Menu.ConsolaHotel.CYAN;
import static Menu.ConsolaHotel.RESET;

public class Main {

    public static void main(String[] args) {

        // =========================================
        // CREAR HOTEL
        // =========================================

        Hotel h1 = new Hotel("Conte", "Mar del Plata", 20);

        // =========================================
        // USUARIOS
        // =========================================

        Usuario usuarioAdmin = new Usuario(
                "adminBenja",
                "Admin1234",
                ETipoUsuario.ADMINISTRADOR
        );

        Usuario usuarioPasajero = new Usuario(
                "juanPerez",
                "Juan1234",
                ETipoUsuario.PASAJERO
        );

        Usuario usuarioConserje = new Usuario(
                "MartinZalez1234",
                "Martin1234@",
                ETipoUsuario.CONSERJE
        );

        Usuario usuarioTecnico = new Usuario(
                "carlos123",
                "Hotel1234",
                ETipoUsuario.TECNICO
        );

        // =========================================
        // ADMINISTRADOR
        // =========================================

        Administrador administrador = new Administrador(
                "Benjamin",
                "Arroyo",
                "12345678",
                "2234567890",
                "benjamin@gmail.com",
                usuarioAdmin,
                "ADM001",
                1500000.0
        );

        // =========================================
        // PASAJERO
        // =========================================

        Pasajero pasajero = new Pasajero(
                "Juan",
                "Perez",
                "48033021",
                "2235678901",
                "juan@gmail.com",
                usuarioPasajero,
                "Mar del Plata",
                "Buenos Aires"
        );

        // =========================================
        // CONSERJE
        // =========================================

        Conserje conserje = new Conserje(
                "Martin",
                "Gonzalez",
                "35123456",
                "2234567890",
                "martin.gonzalez@gmail.com",
                usuarioConserje,
                "CON001",
                15000.0
        );

        // =========================================
        // TÉCNICO
        // =========================================

        Tecnico tecnico = new Tecnico(
                "Carlos",
                "Rodriguez",
                "32145678",
                "2234567890",
                "carlos.rodriguez@gmail.com",
                usuarioTecnico,
                ETipoTecnico.TECNICO_ELECTRONICO,
                "Técnico en instalaciones eléctricas",
                true
        );

        // =========================================
        // HABITACIONES
        // =========================================

        Habitacion habitacion1 = new Habitacion(101, 2, 2);
        Habitacion habitacion2 = new Habitacion(102, 3, 4);

        Habitacion habitacion3 = new Habitacion(201, 4, 6);
        Habitacion habitacion4 = new Habitacion(202, 3, 4);

        Habitacion habitacion5 = new Habitacion(301, 5, 6);
        Habitacion habitacion6 = new Habitacion(302, 6, 8);

        // =========================================
        // CARGAR DATOS INICIALES EN JAVA
        // =========================================

        try {

            // USUARIOS
            h1.agregarUsuario(usuarioAdmin);
            h1.agregarUsuario(usuarioPasajero);
            h1.agregarUsuario(usuarioConserje);
            h1.agregarUsuario(usuarioTecnico);

            // EMPLEADOS
            h1.agregarEmpleado(administrador);
            h1.agregarEmpleado(conserje);

            // TÉCNICO
            h1.agregarTecnico(tecnico);

            // PASAJERO
            h1.agregarPasajero(pasajero);

            // HABITACIONES
            h1.agregarHabitacion(habitacion1);
            h1.agregarHabitacion(habitacion2);
            h1.agregarHabitacion(habitacion3);
            h1.agregarHabitacion(habitacion4);
            h1.agregarHabitacion(habitacion5);
            h1.agregarHabitacion(habitacion6);

        } catch (AgenciaHotelException e) {

            System.out.println(
                    "Error al cargar datos iniciales: " + e.getMessage()
            );

            return;
        }

        // =========================================
        // INICIAR MENÚ CON BASE DE DATOS
        // =========================================

        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║                  USUARIOS                 ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║   ADMINISTRADOR                           ║");
        System.out.println("║   Nombre Usuario -> adminBenja            ║");
        System.out.println("║   Contraseña -> Admin1234                 ║");
        System.out.println("║                                           ║");
        System.out.println("║   CONSERJE                                ║");
        System.out.println("║   Nombre Usuario -> MartinZalez1234       ║");
        System.out.println("║   Contraseña -> Martin1234@               ║");
        System.out.println("║                                           ║");
        System.out.println("║   PASAJERO                                ║");
        System.out.println("║   Nombre Usuario -> juanPerez             ║");
        System.out.println("║   Contraseña -> Juan1234                  ║");
        System.out.println("║                                           ║");
        System.out.println("╠═══════════════════════════════════════════╣"
                + RESET);
        Menu.menuPrincipal(h1);
    }
}
