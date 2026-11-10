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

/**
 * USUARIOS
 * Extraído del Menu.java original. No se alteraron los cuerpos de los métodos.
 */
public class GestionUsuarios {

    // ============================================================
    // registrarUsuario
    // ============================================================
    /**
     * REGISTRARSE: crea la cuenta de usuario y su ficha de pasajero.
     * Si falla una operación, revierte los cambios realizados en los mapas.
     * GestionBaseDatos.guardarPasajero() inserta ambos registros en MySQL
     * dentro de una transacción (Usuario y Pasajero).
     */
    public static void registrarUsuario(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║         REGISTRARSE COMO PASAJERO         ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // 1. DATOS PERSONALES
        String nombre = pedirSoloLetras("Nombre", sc);
        String apellido = pedirSoloLetras("Apellido", sc);

        String dni;
        while (true) {
            dni = pedirDni();

            // La validación de Pasajero exige exactamente ocho dígitos.
            if (!dni.matches("[0-9]{8}")) {
                System.out.println(ROJO +
                        "║  ERROR: El DNI debe tener 8 números" + RESET);
                continue;
            }
            if (hotel.getPasajeros().containsKey(dni)) {
                System.out.println(ROJO +
                        "║  ERROR: Ya existe un pasajero con ese DNI" + RESET);
                continue;
            }
            break;
        }

        String telefono;
        while (true) {
            telefono = pedirTelefono();
            // La clase Pasajero valida que tenga diez dígitos.
            if (telefono.matches("[0-9]{10}")) break;
            System.out.println(ROJO +
                    "║  ERROR: El teléfono debe tener 10 números" + RESET);
        }

        String email = pedirEmail();

        // 2. CUENTA DEL PASAJERO
        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣");
        System.out.println("║            DATOS DE LA CUENTA             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        String nombreUsuario = pedirNombreUsuario(hotel);
        String contrasenia = pedirContrasenia();

        Usuario usuario = new Usuario(
                nombreUsuario, contrasenia, ETipoUsuario.PASAJERO);

        // 3. DATOS NECESARIOS PARA CONSTRUIR EL PASAJERO
        String origen = pedirSoloLetras("Origen", sc);
        String destino = pedirSoloLetras("Destino", sc);

        Pasajero pasajero = new Pasajero(
                nombre, apellido, dni, telefono, email,
                usuario, origen, destino);

        boolean usuarioAgregado = false;
        boolean pasajeroAgregado = false;

        try {
            usuario.validarDatos();
            pasajero.validarDatos();

            // 4. GUARDAR EN LOS MAPAS DE JAVA
            hotel.agregarUsuario(usuario);
            usuarioAgregado = true;
            hotel.agregarPasajero(pasajero);
            pasajeroAgregado = true;

            // 5. GUARDAR AMBOS REGISTROS EN MYSQL EN UNA TRANSACCIÓN
            // No llamar también a guardarUsuario(): guardarPasajero() ya
            // asegura e inserta la cuenta antes de insertar el pasajero.
            GestionBaseDatos.guardarPasajero(pasajero);

            System.out.println(VERDE +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║        REGISTRO REALIZADO CON ÉXITO       ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║  Pasajero: " + nombre + " " + apellido);
            System.out.println("║  DNI: " + dni);
            System.out.println("║  Usuario: " + nombreUsuario);
            System.out.println("║  Guardado en Java y MySQL");
            System.out.println("╚═══════════════════════════════════════════╝" + RESET);

        } catch (AgenciaHotelException | IllegalStateException e) {

            // Si falla la validación o el INSERT SQL, quitar de Java
            // únicamente lo que se agregó durante este registro.
            if (pasajeroAgregado) {
                try {
                    hotel.eliminarPasajero(dni);
                } catch (DatoInvalidoException ex) {
                    System.out.println(ROJO +
                            "║  No se pudo revertir el pasajero: " + ex.getMessage() + RESET);
                }
            }
            if (usuarioAgregado) {
                try {
                    hotel.eliminarUsuario(nombreUsuario);
                } catch (DatoInvalidoException ex) {
                    System.out.println(ROJO +
                            "║  No se pudo revertir el usuario: " + ex.getMessage() + RESET);
                }
            }

            System.out.println(ROJO +
                    "║  Registro cancelado: " + e.getMessage() + RESET);
        }
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
    // modificarUsuario: busca la cuenta y muestra un submenú de cambios
    // ============================================================
    public static void modificarUsuario(Hotel hotel) {
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              MODIFICAR USUARIO            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.print(AZUL + "║  Nombre de usuario actual: " + RESET);
        String nombreActual = sc.nextLine().trim();

        Usuario usuario;
        try {
            usuario = hotel.buscarUsuario(nombreActual);
        } catch (DatoInvalidoException e) {
            System.out.println(ROJO + "║  Usuario no encontrado" + RESET);
            return;
        }

        while (true) {
            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║         ¿QUÉ DESEÁS MODIFICAR?            ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);
            System.out.println(AZUL + "║  Usuario: " + usuario.getNombreUsuario());
            System.out.println("║                                           ║");
            System.out.println("║    1. Solo nombre de usuario              ║");
            System.out.println("║    2. Solo contraseña                     ║");
            System.out.println("║    3. Nombre de usuario y contraseña      ║" + RESET);
            System.out.println(ROJO + "║    0. Volver al menú de administrador     ║" + RESET);
            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

            int opcion = pedirOpcion(0, 3);
            if (opcion == 0) {
                System.out.println(AMARILLO + "║  Volviendo al menú de administrador" + RESET);
                return;
            }

            // Se conservan los datos que la opción elegida no va a modificar.
            String nuevoNombre = nombreActual;
            String nuevaContrasenia = usuario.getContrasenia();

            if (opcion == 1 || opcion == 3) {
                while (true) {
                    System.out.print(AZUL + "║  Nuevo nombre de usuario: " + RESET);
                    nuevoNombre = sc.nextLine().trim();

                    if (nuevoNombre.length() > 15 ||
                            !nuevoNombre.matches("^(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")) {
                        System.out.println(ROJO +
                                "║  Mínimo 8 caracteres, mayúscula y número; máximo 15" + RESET);
                        continue;
                    }
                    if (!nuevoNombre.equals(nombreActual) &&
                            hotel.getUsuarios().containsKey(nuevoNombre)) {
                        System.out.println(ROJO + "║  Ese nombre de usuario ya existe" + RESET);
                        continue;
                    }
                    break;
                }
            }

            if (opcion == 2 || opcion == 3) {
                while (true) {
                    System.out.print(AZUL + "║  Nueva contraseña: " + RESET);
                    nuevaContrasenia = sc.nextLine();

                    if (nuevaContrasenia.length() > 30 ||
                            !nuevaContrasenia.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s])\\S{8,}$")) {
                        System.out.println(ROJO +
                                "║  Mínimo 8 caracteres, mayúscula, número y símbolo; máximo 30" + RESET);
                        continue;
                    }
                    break;
                }
            }

            if (nombreActual.equals(nuevoNombre) &&
                    usuario.getContrasenia().equals(nuevaContrasenia)) {
                System.out.println(AMARILLO + "║  No hay cambios para guardar" + RESET);
                continue;
            }

            System.out.print(AMARILLO + "║  ¿Confirmás el cambio? (Si/No): " + RESET);
            String confirmacion = sc.nextLine().trim();
            if (!confirmacion.equalsIgnoreCase("si") &&
                    !confirmacion.equalsIgnoreCase("sí") &&
                    !confirmacion.equalsIgnoreCase("s")) {
                System.out.println(AMARILLO + "║  Modificación cancelada" + RESET);
                continue;
            }

            try {
                // Primero guardar en MySQL (con transacción para el cambio de nombre).
                GestionBaseDatos.modificarUsuario(nombreActual, nuevoNombre, nuevaContrasenia);

                // Persona comparte la misma referencia al objeto Usuario.
                if (!nombreActual.equals(nuevoNombre)) {
                    hotel.getUsuarios().eliminar(nombreActual);
                    usuario.setNombreUsuario(nuevoNombre);
                    hotel.getUsuarios().put(nuevoNombre, usuario);
                    nombreActual = nuevoNombre; // permite seguir editando la misma cuenta
                }
                usuario.setContrasenia(nuevaContrasenia);

                System.out.println(VERDE +
                        "║  Usuario modificado correctamente en Java y MySQL" + RESET);
            } catch (IllegalStateException e) {
                System.out.println(ROJO + "║  No se modificó el usuario: " + e.getMessage() + RESET);
            } catch (DatoInvalidoException e) {
                System.out.println(ROJO + "║  Error al actualizar el mapa: " + e.getMessage() + RESET);
            }
        }
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

                try {
                    GestionBaseDatos.eliminarUsuario(nombre);
                    System.out.println(VERDE + hotel.eliminarUsuario(nombre) + RESET);
                } catch (IllegalStateException e) {
                    System.out.println(ROJO + "No se eliminó el usuario: " + e.getMessage() + RESET);
                }

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
