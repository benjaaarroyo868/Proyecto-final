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


public class ValidacionesMenu {

    // ============================================================
    // pedirSoloLetras
    // ============================================================
    public static String pedirSoloLetras(String campo, Scanner sc) {

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ " + campo + RESET);

            System.out.print(VERDE +
                    "║  ➜ " + RESET);

            String dato = sc.nextLine().trim();

            if (dato.matches(
                    "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]{2,}( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$")) {

                return dato;
            }

            System.out.println(ROJO +
                    "║  ERROR: Ingresá al menos 2 letras" + RESET);
        }
    }


    // ============================================================
    // pedirDni
    // ============================================================
    public static String pedirDni() {

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ DNI" + RESET);

            System.out.print(VERDE +
                    "║  ➜ " + RESET);

            String dni = sc.nextLine().trim();

            if (dni.matches("[0-9]{7,8}")) {

                return dni;
            }

            System.out.println(ROJO +
                    "║  ERROR: DNI de 7 u 8 números" + RESET);
        }
    }


    // ============================================================
    // pedirTelefono
    // ============================================================
    public static String pedirTelefono() {

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ Teléfono" + RESET);

            System.out.print(VERDE +
                    "║  ➜ " + RESET);

            String telefono = sc.nextLine().trim();

            if (telefono.matches("[0-9]{8,15}")) {

                return telefono;
            }

            System.out.println(ROJO +
                    "║  ERROR: Teléfono de 8 a 15 números" + RESET);
        }
    }


    // ============================================================
    // pedirEmail
    // ============================================================
    public static String pedirEmail() {

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ Email" + RESET);

            System.out.print(VERDE +
                    "║  ➜ " + RESET);

            String email = sc.nextLine().trim();

            if (email.matches(
                    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

                return email;
            }

            System.out.println(ROJO +
                    "║  ERROR: Email inválido" + RESET);
        }
    }


    // ============================================================
    // pedirNombreUsuario
    // ============================================================
    public static String pedirNombreUsuario(Hotel hotel) {

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ Nombre de usuario" + RESET);

            System.out.print(VERDE +
                    "║  ➜ " + RESET);

            String nombre = sc.nextLine().trim();

            // VALIDAR FORMATO
            if (!nombre.matches(
                    "^(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$")) {

                System.out.println(ROJO +
                        "ERROR: Mínimo 8 caracteres, mayúscula y número"
                        + RESET);
                continue;
            }

            // VERIFICAR SI YA EXISTE
            if (hotel.getUsuarios().containsKey(nombre)) {

                System.out.println(ROJO +
                        "ERROR: El nombre de usuario ya existe"
                        + RESET);
                continue;
            }

            return nombre;
        }
    }


    // ============================================================
    // pedirContrasenia
    // ============================================================
    public static String pedirContrasenia() {

        while (true) {

            System.out.println(AZUL +
                    "║  ◈ Contraseña" + RESET);

            System.out.print(VERDE +
                    "║  ➜ " + RESET);

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


    // ============================================================
    // pedirOpcion
    // ============================================================
    public static int pedirOpcion(int minimo, int maximo) {

        while (true) {

            System.out.print(VERDE +
                    "\n➜ Elegí una opción: " + RESET);

            String entrada = sc.nextLine().trim();

            try {

                int opcion = Integer.parseInt(entrada);

                if (opcion >= minimo && opcion <= maximo) {

                    return opcion;
                }

            } catch (NumberFormatException e) {

                // SI INGRESA LETRAS, VUELVE A PEDIR.
            }

            System.out.println(ROJO +
                    "ERROR: Elegí una opción entre " +
                    minimo + " y " + maximo + RESET);
        }
    }


    // ============================================================
    // confirmarEliminacion
    // ============================================================
    public static boolean confirmarEliminacion() {

        while (true) {

            System.out.println(AMARILLO +
                    "║  ¿Desea eliminarlo? (Si/No)" + RESET);

            System.out.print(VERDE +
                    "║  ➜ " + RESET);

            String respuesta = sc.nextLine().trim();

            // CONFIRMAR
            if (respuesta.equalsIgnoreCase("Si") ||
                    respuesta.equalsIgnoreCase("S")) {

                return true;
            }

            // CANCELAR
            if (respuesta.equalsIgnoreCase("No") ||
                    respuesta.equalsIgnoreCase("N")) {

                return false;
            }

            System.out.println(ROJO +
                    "║  ERROR: Ingresá Si o No" + RESET);
        }
    }


}
