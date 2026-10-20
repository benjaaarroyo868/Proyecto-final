package Clases;

import java.util.Objects;

public class Reserva {
    private int idReserva;
 private Pasajero pasajero;
 private static int contador=1;

    public Reserva( Pasajero pasajero) {
        this.idReserva = contador++;
        this.pasajero = pasajero;
    }

    public Reserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
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
