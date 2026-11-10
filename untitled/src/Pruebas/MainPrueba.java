package Pruebas;

import Clases.Habitacion;
import Clases.Hotel;
import Clases.Pasajero;
import Clases.Usuario;
import Enums.ETipoUsuario;

import java.time.LocalDate;

public class MainPrueba {
    public static void main(String[] args) throws Exception {
        Hotel hotel = new Hotel("Conte", "Mar del Plata", 10);
        Habitacion habitacion = new Habitacion(101, 2, 2);

        // PRUEBA 1: AGREGAR HABITACION
        comprobar("Agregar habitacion", hotel.agregarHabitacion(habitacion));

        // PRUEBA 2: NO REPETIR NUMERO DE HABITACION
        comprobar("Evitar habitacion duplicada",
                !hotel.agregarHabitacion(new Habitacion(101, 2, 2)));

        // PRUEBA 3: NO PERMITIR CERO CAMAS
        comprobar("Rechazar habitacion sin camas",
                !hotel.agregarHabitacion(new Habitacion(102, 0, 2)));

        Usuario usuario = new Usuario("anaTest", "clave123", ETipoUsuario.PASAJERO);
        Pasajero pasajero = new Pasajero(
                "Ana", "Perez", "12345678", "2234567890", "ana@test.com",
                usuario, "Mar del Plata", "Buenos Aires");
        hotel.agregarPasajero(pasajero);

        LocalDate ingreso = LocalDate.now().plusDays(10);
        LocalDate salida = ingreso.plusDays(3);

    }
        private static void comprobar(String prueba, boolean resultado) {
            if (!resultado) throw new AssertionError("FALLO - " + prueba);
            System.out.println("OK - " + prueba);
        }

    }
