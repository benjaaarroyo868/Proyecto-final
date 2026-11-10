package Pruebas;

import Clases.*;
import Enums.ETipoUsuario;

import java.time.LocalDate;

public class MainPruebaHotel {
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

        // PRUEBA 4: RESERVA VALIDA
        Reserva primera = new Reserva(pasajero, habitacion, 2, ingreso, salida);
        comprobar("Agregar reserva valida", hotel.agregarReserva(primera));

        // PRUEBA 5: RECHAZAR RESERVA SUPERPUESTA
        Reserva superpuesta = new Reserva(
                pasajero, habitacion, 1, ingreso.plusDays(1), salida.plusDays(1));
        comprobar("Evitar fechas superpuestas", !hotel.agregarReserva(superpuesta));

        // PRUEBA 6: ADMITIR ENTRADA EL DIA QUE SALE OTRO PASAJERO
        Reserva seguida = new Reserva(pasajero, habitacion, 1, salida, salida.plusDays(2));
        comprobar("Permitir reserva seguida", hotel.agregarReserva(seguida));

        // PRUEBA 7: RECHAZAR RESERVA QUE SUPERA LA CAPACIDAD
        Reserva demasiados = new Reserva(
                pasajero, habitacion, 3, salida.plusDays(3), salida.plusDays(4));
        comprobar("Rechazar exceso de pasajeros", !hotel.agregarReserva(demasiados));

        // PRUEBA 8: FECHA DE SALIDA NO POSTERIOR A INGRESO
        Reserva fechaInvalida = new Reserva(
                pasajero, habitacion, 1, salida.plusDays(5), salida.plusDays(5));
        comprobar("Rechazar fechas iguales", !hotel.agregarReserva(fechaInvalida));

    }
        private static void comprobar(String prueba, boolean resultado) {
            if (!resultado) throw new AssertionError("FALLO - " + prueba);
            System.out.println("OK - " + prueba);
        }

    }
