
package Menu;

import Clases.Hotel;
import Clases.Usuario;
import Enums.ETipoUsuario;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Generico.RegistroGenericoMapas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static Menu.Menu.*;

public class FuncionesMenu {

    // REGISTRAR USUARIO
    public static void registrarUsuario(
            RegistroGenericoMapas<String, Usuario> usu,
            Scanner sc) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║             REGISTRAR USUARIO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL + "║  ◈ Nombre de usuario                      ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);
        String nombre = sc.nextLine().trim();

        System.out.println(AZUL + "║  ◈ Contraseña                             ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);
        String contrasenia = sc.nextLine();

        System.out.println(CYAN + "╚═══════════════════════════════════════════╝" + RESET);

        Usuario nuevo = new Usuario(
                nombre,
                contrasenia,
                ETipoUsuario.PASAJERO
        );

        try {
            nuevo.validarDatos();
            usu.cargar(nuevo.getNombreUsuario(), nuevo);

            System.out.println(VERDE +
                    "✔ Usuario registrado correctamente" + RESET);

        } catch (AgenciaHotelException e) {
            System.out.println(ROJO +
                    "✘ ERROR: " + e.getMessage() + RESET);
        }
    }

    // BUSCAR USUARIO
    public static void buscarUsuario(
            RegistroGenericoMapas<String, Usuario> usu,
            Scanner sc) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║               BUSCAR USUARIO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL + "║  ◈ Nombre de usuario                      ║" + RESET);
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
                    "  Tipo: " + encontrado.getTipo_de_usuario());

        } catch (DatoInvalidoException e) {
            System.out.println(ROJO +
                    "║          USUARIO NO ENCONTRADO            ║" + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // ELIMINAR USUARIO
    public static void eliminarUsuario(
            RegistroGenericoMapas<String, Usuario> usu,
            Scanner sc) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║              ELIMINAR USUARIO             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL + "║  ◈ Nombre de usuario                      ║" + RESET);
        System.out.print(VERDE + "║  ➜ " + RESET);
        String nombre = sc.nextLine().trim();

        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣" + RESET);

        try {
            usu.eliminar(nombre);

            System.out.println(VERDE +
                    "║       Usuario eliminado correctamente     ║" + RESET);

        } catch (DatoInvalidoException e) {
            System.out.println(ROJO +
                    "║          ERROR AL ELIMINAR USUARIO        ║" + RESET);

            System.out.println(ROJO + e.getMessage() + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }

    // MOSTRAR USUARIOS
    public static void mostrarUsuarios(
            RegistroGenericoMapas<String, Usuario> usu) {

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.println("║             LISTA DE USUARIOS             ║");
        System.out.println("╚═══════════════════════════════════════════╝" + RESET);

        try {
            usu.mostrar();

        } catch (DatoInvalidoException e) {
            System.out.println(ROJO +
                    "✘ " + e.getMessage() + RESET);
        }
    }

    // INICIAR SESIÓN
    public static void iniciarSesion(
            Hotel hotel,
            RegistroGenericoMapas<String, Usuario> usu,
            Scanner sc) {

        Usuario usuario;

        do {
            System.out.println();
            System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
            System.out.println("║               GESTIÓN HOTEL               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║             INICIO DE SESIÓN              ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL + "║  ◈ Nombre de usuario                      ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);
            String nombre = sc.nextLine().trim();

            System.out.println(AZUL + "║  ◈ Contraseña                             ║" + RESET);
            System.out.print(VERDE + "║  ➜ " + RESET);
            String contrasenia = sc.nextLine();

            System.out.println(CYAN + "╠═══════════════════════════════════════════╣");
            System.out.println("║          VALIDANDO CREDENCIALES           ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            usuario = usu.iniciarSesion(nombre, contrasenia);

            if (usuario == null) {
                System.out.println(ROJO +
                        "║     Usuario o contraseña incorrectos      ║" + RESET);

                System.out.println(AMARILLO +
                        "║            Intentá nuevamente            ║" + RESET);
            } else {
                System.out.println(VERDE +
                        "║       Inicio de sesión exitoso            ║" + RESET);
            }

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

        } while (usuario == null);

        // MOSTRAR MENÚ SEGÚN TIPO DE USUARIO
        switch (usuario.getTipo_de_usuario()) {

            case ADMINISTRADOR:
            case CONSERJE:
                Menu.IniciarMenu(usu);
                break;

            case PASAJERO:
                Menu.menuPasajero();
                break;

            default:
                System.out.println(ROJO +
                        "Tipo de usuario no reconocido" + RESET);
        }
    }

    // MOSTRAR CIERRE DE SESIÓN O PROGRAMA
    public static void mostrarCierre(String titulo) {

        LocalDateTime fecha = LocalDateTime.now();

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════════════╗");
        System.out.printf("║ %-41s ║%n", "          " + titulo);
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.printf(VERDE + "║ %-41s ║%n" + RESET,
                "      Cerrado correctamente");

        System.out.printf(AMARILLO + "║ %-41s ║%n" + RESET,
                "          ¡Que tenga buen día!");

        System.out.printf(CYAN + "║ %-41s ║%n" + RESET,
                "      Fecha: " + fecha.format(formato));

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }
}
