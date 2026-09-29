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
    

}
