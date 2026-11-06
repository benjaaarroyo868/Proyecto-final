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



public class ConsolaHotel {

    // Misma instancia de Scanner compartida por todas las pantallas.
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static final String VERDE = "\u001B[32m";
    public static final String ROJO = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String AMARILLO = "\u001B[33m";

    public static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd-MM-uuuu");

    public static final Scanner sc = new Scanner(System.in);

    // ============================================================
    // esperar
    // ============================================================
    public static void esperar(int segundos) {

        try {
            Thread.sleep(segundos * 1000L);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}
