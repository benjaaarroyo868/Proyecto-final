package Clases;

import Enums.ETipoHabitacion;
import Enums.ETipoTecnico;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Exepciones.ElementoDuplicadoException;
import Exepciones.ReservaNoDisponibleException;
import Generico.RegistroGenericoMapas;
import Interfaces.IEmpleado;
import Interfaces.IValidarDatos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hotel implements IValidarDatos {
    private String nombre;
    private String direccion;
    private int cantidadHabitaciones;
    private int cantidadOcupadas;

    RegistroGenericoMapas<ETipoTecnico, ArrayList <Tecnico>> tecnicos;
    RegistroGenericoMapas<ETipoHabitacion, ArrayList<Habitacion>> habitaciones;
    RegistroGenericoMapas<String, Pasajero> reservas;

    public Hotel(String nombre, String direccion, int cantidadHabitaciones) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadOcupadas = 0;
        this.tecnicos = new RegistroGenericoMapas<>();
        this.habitaciones = new RegistroGenericoMapas<>();
        this.reservas = new RegistroGenericoMapas<>();

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public void setCantidadHabitaciones(int cantidadHabitaciones) {
        this.cantidadHabitaciones = cantidadHabitaciones;
    }

    public int getCantidadOcupadas() {
        return cantidadOcupadas;
    }

    public void setCantidadOcupadas(int cantidadOcupadas) {
        this.cantidadOcupadas = cantidadOcupadas;
    }



    public void agregarReserva(Reserva reserva)throws AgenciaHotelException{
        if(reserva==null){
            throw new DatoInvalidoException("La reserva no puede ser nula");
        }

        if(reservas.containsKey(reserva.getIdReserva())){
            throw new ElementoDuplicadoException("La reserva ya existe");

        }
        if(reserva.getHabitacion().isEstado()){
            throw new ReservaNoDisponibleException("La habitacion esta ocupada");
        }
        reserva.validarDatos();
        reservas.put(reserva.getIdReserva(), reserva.getPasajero());
        cantidadOcupadas++;
    }


    @Override
    public void validarDatos() throws DatoInvalidoException {
        if (nombre == null || nombre.isBlank() ||
                direccion == null || direccion.isBlank()) {

            throw new DatoInvalidoException(
                    "El nombre y la dirección del hotel no pueden estar vacíos");
        }

        if (cantidadHabitaciones <= 0) {
            throw new DatoInvalidoException(
                    "La cantidad de habitaciones debe ser mayor a cero");
        }

        if (cantidadOcupadas < 0 ||
                cantidadOcupadas > cantidadHabitaciones) {

            throw new DatoInvalidoException(
                    "La cantidad de habitaciones ocupadas es inválida");
        }
    }

}
