package Clases;

import Enums.TipoHabitacion;
import Enums.TipoTecnico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hotel {
    private String nombre;
    private String direccion;
    private int cantidadHabitaciones;
    private int cantidadOcupadas;
    Map<String, ArrayList<Empleado>> empleados;
    Map<TipoTecnico, ArrayLisT<Tecnico>> tecnicos;
    Map<TipoHabitacion, ArrayList<Habitacion>> habitaciones;
    Map<Integer, ArrayList<Pasajero>> pasajeros;
    Map<String, Pasajero> reserva;

    public Hotel(String nombre, String direccion, int cantidadHabitaciones, int cantidadOcupadas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadOcupadas = cantidadOcupadas;
        this.empleados = new HashMap<>();
        this.tecnicos = new HashMap<>();
        this.habitaciones = new HashMap<>();
        this.pasajeros = new HashMap<>();
        this.reserva = new HashMap<>();
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

    public Map<String, ArrayList<Empleado>> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Map<String, ArrayList<Empleado>> empleados) {
        this.empleados = empleados;
    }

    public Map<TipoTecnico, ArrayLisT<Tecnico>> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(Map<TipoTecnico, ArrayLisT<Tecnico>> tecnicos) {
        this.tecnicos = tecnicos;
    }

    public Map<TipoHabitacion, ArrayList<Habitacion>> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Map<TipoHabitacion, ArrayList<Habitacion>> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Map<Integer, ArrayList<Pasajero>> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(Map<Integer, ArrayList<Pasajero>> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Map<String, Pasajero> getReserva() {
        return reserva;
    }

    public void setReserva(Map<String, Pasajero> reserva) {
        this.reserva = reserva;
    }
}
