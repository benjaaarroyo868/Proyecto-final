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
import static Menu.GestionEmpleados.*;
import static Menu.GestionHabitaciones.*;
import static Menu.GestionPasajeros.*;
import static Menu.GestionReservas.*;
import static Menu.GestionUsuarios.*;
import static Menu.ValidacionesMenu.*;

/**
 * MENÚS, INICIO DE SESIÓN Y PANTALLAS GENERALES

 */
public class Menu {

    // ============================================================
    // menuPrincipal
    // ============================================================
    public static void menuPrincipal(Hotel hotel) {

        try {
            GestionBaseDatos.iniciar(hotel);
        } catch (IllegalStateException e) {
            System.out.println(ROJO + "No se puede iniciar el hotel con MySQL: "
                    + e.getMessage() + RESET);
            GestionBaseDatos.cerrar();
            return;
        }

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
            System.out.println("╠═══════════════════════════════════════════╣"
                    + RESET);

            System.out.println(AZUL +
                    "║    1. Iniciar Sesión                      ║");
            System.out.println(
                    "║    2. Registrarse                         ║"
                            + RESET);

            System.out.println(ROJO +
                    "║    0. Salir                               ║"
                    + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝"
                    + RESET);

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
                    GestionBaseDatos.cerrar();
                    break;
            }

            // ESPERAR ANTES DE VOLVER AL MENÚ
            if (opcion != 0) {
                esperar(2);
            }

        } while (opcion != 0);
    }


    // ============================================================
    // IniciarMenu
    // ============================================================
    public static void IniciarMenu(Hotel hotel, Usuario usuario) {

        int opcion;

        do {

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║                MENÚ ADMIN                 ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            // USUARIOS
            System.out.println(AZUL +
                    "║    1. Buscar Usuario                      ║");
            System.out.println(
                    "║    2. Eliminar Usuario                    ║");
            System.out.println(
                    "║    3. Mostrar Todos los Usuarios          ║");
            System.out.println(
                    "║                                           ║");

            // RESERVAS
            System.out.println(
                    "║    4. Agregar Reserva                     ║");
            System.out.println(
                    "║    5. Buscar Reserva                      ║");
            System.out.println(
                    "║    6. Modificar Reserva                   ║");
            System.out.println(
                    "║    7. Eliminar Reserva                    ║");
            System.out.println(
                    "║    8. Mostrar Reservas                    ║");
            System.out.println(
                    "║                                           ║");

            // PASAJEROS
            System.out.println(
                    "║    9. Agregar Pasajero                    ║");
            System.out.println(
                    "║   10. Buscar Pasajero                     ║");
            System.out.println(
                    "║   11. Eliminar Pasajero                   ║");
            System.out.println(
                    "║   12. Mostrar Pasajeros                   ║");
            System.out.println(
                    "║                                           ║");

            // EMPLEADOS
            System.out.println(
                    "║   13. Agregar Empleado                    ║");
            System.out.println(
                    "║   14. Buscar Empleado                     ║");
            System.out.println(
                    "║   15. Eliminar Empleado                   ║");
            System.out.println(
                    "║   16. Mostrar Empleados                   ║");
            System.out.println(
                    "║                                           ║");

            // PERMISOS
            System.out.println(
                    "║   17. Dar Permiso                         ║");
            System.out.println(
                    "║                                           ║");

            // HABITACIONES
            System.out.println(
                    "║   18. Ver Habitaciones Disponibles        ║");
            System.out.println(
                    "║   19. Ver Habitaciones Ocupadas           ║");
            System.out.println(
                    "║   20. Ver Información del Hotel           ║");
            System.out.println(
                    "║                                           ║");

            // REPARACIONES
            System.out.println(
                    "║   21. Gestionar Reparación de Habitación  ║");
            System.out.println(
                    "║   22. Finalizar Reparación                ║");


            System.out.println(CYAN +
                    "║                                           ║" + RESET);

            System.out.println(ROJO +
                    "║    0. Cerrar Sesión                       ║" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(0, 22);

            switch (opcion) {

                // USUARIOS
                case 1:
                    buscarUsuario(hotel);
                    break;

                case 2:
                    eliminarUsuario(hotel);
                    break;

                case 3:
                    mostrarUsuarios(hotel);
                    break;

                // RESERVAS
                case 4:
                    realizarReserva(hotel, usuario);
                    break;

                case 5:
                    verReserva(hotel, usuario, true);
                    break;

                case 6:
                    modificarReserva(hotel, usuario, true);
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

                case 20:
                    mostrarInformacionHotel(hotel);
                    break;

                // REPARACIONES
                case 21:
                    ponerHabitacionEnReparacion(hotel);
                    break;

                case 22:
                    ponerHabitacionReparada(hotel);
                    break;


                // CERRAR SESIÓN
                case 0:
                    mostrarCierre();
                    break;
            }

            // ESPERAR 2 SEGUNDOS ANTES DE MOSTRAR EL MENÚ
            if (opcion != 0) {
                esperar(2);
            }

        } while (opcion != 0);
    }


    // ============================================================
    // menuConserje
    // ============================================================
    public static void menuConserje(Hotel hotel, Usuario usuario) {

        int opcion;

        do {

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║               MENÚ CONSERJE               ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            // RESERVAS
            System.out.println(AZUL +
                    "║    1. Agregar Reserva                     ║");
            System.out.println(
                    "║    2. Buscar Reserva                      ║");
            System.out.println(
                    "║    3. Modificar Reserva                   ║");
            System.out.println(
                    "║    4. Eliminar Reserva                    ║");
            System.out.println(
                    "║    5. Mostrar Reservas                    ║");
            System.out.println(
                    "║                                           ║");

            // PASAJEROS
            System.out.println(
                    "║    6. Agregar Pasajero                    ║");
            System.out.println(
                    "║    7. Buscar Pasajero                     ║");
            System.out.println(
                    "║    8. Eliminar Pasajero                   ║");
            System.out.println(
                    "║    9. Mostrar Pasajeros                   ║");
            System.out.println(
                    "║                                           ║");

            // REPARACIONES
            System.out.println(
                    "║   10. Gestionar Reparación de Habitación  ║");
            System.out.println(
                    "║                                           ║");

            // HABITACIONES
            System.out.println(
                    "║   11. Ver Habitaciones Disponibles        ║");
            System.out.println(
                    "║   12. Ver Habitaciones Ocupadas           ║");
            System.out.println(
                    "║   13. Ver Información del Hotel           ║");
            System.out.println(
                    "║                                           ║");

            // FINALIZAR REPARACIÓN
            System.out.println(
                    "║   14. Finalizar Reparación                ║" + RESET);

            System.out.println(CYAN +
                    "║                                           ║" + RESET);

            System.out.println(ROJO +
                    "║    0. Cerrar Sesión                       ║" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

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
                // REPARACIONES
                // =========================================

                case 10:
                    ponerHabitacionEnReparacion(hotel);
                    break;

                // =========================================
                // HABITACIONES
                // =========================================

                case 11:
                    mostrarHabitacionesDisponibles(hotel);
                    break;

                case 12:
                    mostrarHabitacionesOcupadas(hotel);
                    break;

                case 13:
                    mostrarInformacionHotel(hotel);
                    break;

                // =========================================
                // FINALIZAR REPARACIÓN
                // =========================================

                case 14:
                    ponerHabitacionReparada(hotel);
                    break;

                // =========================================
                // CERRAR SESIÓN
                // =========================================

                case 0:
                    mostrarCierre();
                    break;
            }

            // ESPERAR 2 SEGUNDOS ANTES DE VOLVER AL MENÚ
            if (opcion != 0) {
                esperar(2);
            }

        } while (opcion != 0);
    }


    // ============================================================
    // menuPasajero
    // ============================================================
    public static void menuPasajero(Hotel hotel, Usuario usuario) {

        int opcion;

        do {

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║               MENÚ PASAJERO               ║");
            System.out.println("╠═══════════════════════════════════════════╣"
                    + RESET);

            // RESERVAS
            System.out.println(AZUL +
                    "║    1. Realizar Reserva                    ║");
            System.out.println(
                    "║    2. Ver Mi Reserva                      ║");
            System.out.println(
                    "║    3. Modificar Mi Reserva                ║");
            System.out.println(
                    "║    4. Eliminar Mi Reserva                 ║");
            System.out.println(
                    "║                                           ║");

            // HABITACIONES
            System.out.println(
                    "║    5. Ver Habitaciones Disponibles        ║");
            System.out.println(
                    "║    6. Ver Información del Hotel           ║"
                            + RESET);

            System.out.println(ROJO +
                    "║    0. Cerrar Sesión                       ║"
                    + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝"
                    + RESET);

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

            // ESPERAR 2 SEGUNDOS ANTES DE MOSTRAR EL MENÚ
            if (opcion != 0) {
                esperar(2);
            }

        } while (opcion != 0);
    }


    // ============================================================
    // mostrarInformacionHotel
    // ============================================================
    public static void mostrarInformacionHotel(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║           INFORMACIÓN DEL HOTEL           ║");
        System.out.println("╠═══════════════════════════════════════════╣"
                + RESET);

        System.out.println(AZUL +
                "║  Nombre: " + hotel.getNombre());

        System.out.println(
                "║  Dirección: " + hotel.getDireccion());

        System.out.println(
                "║  Total de habitaciones: " +
                        hotel.getCantidadHabitaciones());

        System.out.println(VERDE +
                "║  Habitaciones disponibles registradas: " +
                hotel.verHabitacionesDisponibles().size());

        System.out.println(ROJO +
                "║  Habitaciones ocupadas registradas: " +
                hotel.verHabitacionesOcupadas().size() + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝"
                + RESET);
    }


    // ============================================================
    // mostrarCierre
    // ============================================================
    public static void mostrarCierre() {

        String fecha = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              SESIÓN CERRADA               ║");
        System.out.println("╠═══════════════════════════════════════════╣"
                + RESET);

        System.out.println(VERDE +
                "║  Sesión cerrada correctamente" + RESET);

        System.out.println(AMARILLO +
                "║  Fecha: " + fecha + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝"
                + RESET);
    }


}
