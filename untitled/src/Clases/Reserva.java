package Clases;

import java.util.Objects;
import java.util.UUID;

public class Reserva {
    private String idReserva;
    private Pasajero pasajero;
    private Habitacion habitacion;
    private UUID cont;

    public Reserva(Pasajero pasajero, Habitacion habitacion) {
        this.idReserva = UUID.randomUUID().toString().substring(0,5);
        this.pasajero = pasajero;
        this.habitacion = habitacion;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Reserva reserva = (Reserva) object;
        return idReserva == reserva.idReserva;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idReserva);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", pasajero=" + pasajero.toString() +
                '}';
    }
}
