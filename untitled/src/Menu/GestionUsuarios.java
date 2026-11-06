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
import static Menu.Menu.*;
import static Menu.ValidacionesMenu.*;


public class GestionUsuarios {

    // ============================================================
    // registrarUsuario
    // ============================================================
    public static void registrarUsuario(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             REGISTRAR USUARIO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // PEDIR DATOS DEL USUARIO
        String nombre = pedirNombreUsuario(hotel);
        String contrasenia = pedirContrasenia();

        // CREAR USUARIO DE TIPO PASAJERO
        Usuario nuevo = new Usuario(
                nombre,
                contrasenia,
                ETipoUsuario.PASAJERO
        );

        // GUARDAR USUARIO
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


    // ============================================================
    // buscarUsuario
    // ============================================================
    public static void buscarUsuario(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║               BUSCAR USUARIO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.print(AZUL +
                "Nombre de usuario: " + RESET);

        String nombre = sc.nextLine().trim();

        // BUSCAR USUARIO
        try {

            Usuario usuario = hotel.buscarUsuario(nombre);

            System.out.printf(
                    VERDE + "║ %-41s ║%n" + RESET,
                    "  USUARIO ENCONTRADO"
            );

            System.out.printf(
                    AZUL + "║ %-41s ║%n" + RESET,
                    "  Nombre: " + usuario.getNombreUsuario()
            );

            System.out.printf(
                    AZUL + "║ %-41s ║%n" + RESET,
                    "  Tipo: " + usuario.getTipoDeUsuario()
            );

        } catch (DatoInvalidoException e) {

            System.out.println(ROJO +
                    "║  Usuario no encontrado" + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    // ============================================================
    // eliminarUsuario
    // ============================================================
    public static void eliminarUsuario(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              ELIMINAR USUARIO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.print(AZUL +
                "Nombre de usuario: " + RESET);

        String nombre = sc.nextLine().trim();

        // BUSCAR USUARIO
        try {

            Usuario usuario = hotel.buscarUsuario(nombre);

            System.out.println(AZUL +
                    "║  Usuario: " + usuario.getNombreUsuario());

            System.out.println(
                    "║  Tipo: " +
                            usuario.getTipoDeUsuario() + RESET);

            // CONFIRMAR ELIMINACIÓN
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


    // ============================================================
    // mostrarUsuarios
    // ============================================================
    public static void mostrarUsuarios(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              MOSTRAR USUARIOS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // VERIFICAR SI HAY USUARIOS
        if (hotel.mostrarUsuarios().isEmpty()) {

            System.out.println(ROJO +
                    "║  No hay usuarios registrados" + RESET);

        } else {

            // RECORRER TODOS LOS USUARIOS
            for (Usuario usuario : hotel.mostrarUsuarios()) {

                System.out.printf(
                        AZUL + "║ %-41s ║%n" + RESET,
                        "  Nombre: " + usuario.getNombreUsuario()
                );

                System.out.printf(
                        AZUL + "║ %-41s ║%n" + RESET,
                        "  Tipo: " + usuario.getTipoDeUsuario()
                );

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣" + RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    // ============================================================
    // iniciarSesion
    // ============================================================
    public static void iniciarSesion(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║                                           ║");
        System.out.println("║              INICIO DE SESIÓN             ║");
        System.out.println("║                                           ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║       Accedé a tu cuenta del hotel        ║");
        System.out.println("╠═══════════════════════════════════════════╣"
                + RESET);

        // NOMBRE DE USUARIO
        System.out.println(CYAN +
                "║  ◈ Nombre de usuario" + RESET);

        System.out.print(VERDE +
                "║  ➜ " + RESET);

        String nombre = sc.nextLine().trim();

        // RENGLÓN DE SEPARACIÓN
        System.out.println(CYAN +
                "║                                           ║"
                + RESET);

        // CONTRASEÑA
        System.out.println(CYAN +
                "║  ◈ Contraseña" + RESET);

        System.out.print(VERDE +
                "║  ➜ " + RESET);

        String contrasenia = sc.nextLine();

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝"
                + RESET);

        // VALIDAR CREDENCIALES
        Usuario usuario = hotel.iniciarSesion(
                nombre,
                contrasenia
        );

        if (usuario == null) {

            System.out.println();
            System.out.println(ROJO +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║     USUARIO O CONTRASEÑA INCORRECTOS      ║");
            System.out.println("╚═══════════════════════════════════════════╝"
                    + RESET);

            return;
        }

        // INICIO EXITOSO
        System.out.println();
        System.out.println(VERDE +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          INICIO DE SESIÓN EXITOSO         ║");
        System.out.println("╚═══════════════════════════════════════════╝"
                + RESET);

        // ESPERAR ANTES DE ENTRAR AL MENÚ
        esperar(2);

        // REDIRIGIR SEGÚN EL ROL
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


}
