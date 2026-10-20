package Clases;

import Interfaces.IEmpleado;

public class Conserje extends Persona implements IEmpleado {

    private String legajo;
    private double sueldoPorVenta;

    public Conserje(String nombre, String apellido, String DNI, int telefono, String email, Usuario usuario, String legajo, double sueldoPorVenta) {
        super(nombre, apellido, DNI, telefono, email, usuario);
        this.legajo = legajo;
        this.sueldoPorVenta = sueldoPorVenta;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public double getSueldoPorVenta() {
        return sueldoPorVenta;
    }

    public void setSueldoPorVenta(double sueldoPorVenta) {
        this.sueldoPorVenta = sueldoPorVenta;
    }

    @Override
    public String toString() {
        return "Conserje{" +
                super.toString() +
                "legajo='" + legajo + '\'' +
                ", sueldoPorVenta=" + sueldoPorVenta +
                '}';
    }
}
