package Clases;

public class Habitacion {
    private int numeroHabitacion;
    private int cantidadCamas;
    private int cantidadMaximaPersonas;
    private boolean estado;


    public Habitacion(int numeroHabitacion, int cantidadCamas, int cantidadMaximaPersonas, boolean estado) {
        this.numeroHabitacion = numeroHabitacion;
        this.cantidadCamas = cantidadCamas;
        this.cantidadMaximaPersonas = cantidadMaximaPersonas;
        this.estado = estado;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public int getCantidadCamas() {
        return cantidadCamas;
    }

    public void setCantidadCamas(int cantidadCamas) {
        this.cantidadCamas = cantidadCamas;
    }

    public int getCantidadMaximaPersonas() {
        return cantidadMaximaPersonas;
    }

    public void setCantidadMaximaPersonas(int cantidadMaximaPersonas) {
        this.cantidadMaximaPersonas = cantidadMaximaPersonas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
