package Pruebas;

import Clases.Habitacion;
import Clases.Hotel;

public class MainPrueba {
    public static void main(String[] args) throws Exception {
        Hotel hotel = new Hotel("Conte", "Mar del Plata", 10);
        Habitacion habitacion = new Habitacion(101, 2, 2);

        // PRUEBA 1: AGREGAR HABITACION
        comprobar("Agregar habitacion", hotel.agregarHabitacion(habitacion));

    }
        private static void comprobar(String prueba, boolean resultado) {
            if (!resultado) throw new AssertionError("FALLO - " + prueba);
            System.out.println("OK - " + prueba);
        }

    }
