package Clases;

import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Exepciones.ReservaNoDisponibleException;
import Interfaces.IValidarDatos;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Reserva implements IValidarDatos {
    private String idReserva;
    private Pasajero pasajero;
    private Habitacion habitacion;
    private int cantidadPasajeros;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private UUID cont;

    public Reserva(Pasajero pasajero, Habitacion habitacion, int cantidadPasajeros, LocalDate fechaIngreso, LocalDate fechaSalida) {
        this.idReserva = UUID.randomUUID().toString().substring(0,5);
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.cantidadPasajeros = cantidadPasajeros;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
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

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
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

    @Override
    public void validarDatos() throws AgenciaHotelException {

        if(idReserva == null){
            throw new DatoInvalidoException("El id de la reserva no puede ser nulo");
        }
        if(pasajero==null){
            throw new DatoInvalidoException("La reserva debe tener un pasajero registrado");
        }
        if(habitacion==null){
            throw new DatoInvalidoException("La reserva debe tener una habitacion");
        }
        if(habitacion.isDisponible()){
            throw new ReservaNoDisponibleException("La habitacion esta ocupada");
        }
        if(cantidadPasajeros<0){
            throw new DatoInvalidoException("La cantidad de pasajeros debe ser mayor a cero");
        }
        if(cantidadPasajeros>habitacion.getCantidadMaximaPersonas()){
            throw new ReservaNoDisponibleException("La cantidad de pasajeros supera la capacidad de la habitacion");
        }
        if (fechaIngreso == null) {
            throw new DatoInvalidoException("La fecha de ingreso no puede ser null");
        }

        if (fechaSalida == null) {
            throw new DatoInvalidoException("La fecha de salida no puede ser null");
        }

        if (fechaIngreso.isBefore(LocalDate.now())) {
            throw new DatoInvalidoException("La fecha de ingreso no puede ser anterior a hoy");
        }

        if (!fechaSalida.isAfter(fechaIngreso)) {
            throw new DatoInvalidoException("La fecha de salida debe ser posterior a la fecha de ingreso");
        }

    }
}
