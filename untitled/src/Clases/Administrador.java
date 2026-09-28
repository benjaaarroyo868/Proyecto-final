package Clases;

public class Administrador  extends Persona{

    private String legajo;
    private double sueldo;

    public Administrador(String nombre, String apellido, String DNI, int telefono, String email, String legajo, double sueldo) {
        super(nombre, apellido, DNI, telefono, email);
        this.legajo = legajo;
        this.sueldo = sueldo;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                super.toString() +
                "legajo='" + legajo + '\'' +
                ", sueldo=" + sueldo +
                '}';
    }
}
