package Clases;

public class Conserje extends Persona {

    private String legajo;
    private double sueldoPorVenta;

    public Conserje(String nombre, String apellido, String DNI, int telefono, String email, String legajo, double sueldoPorVenta) {
        super(nombre, apellido, DNI, telefono, email);
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
