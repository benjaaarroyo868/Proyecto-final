package Clases;

import Enums.EEstadoMantenimiento;

public class Habitacion {
    private int numeroHabitacion;
    private int cantidadCamas;
    private int cantidadMaximaPersonas;
    private boolean disponible;
    private EEstadoMantenimiento estado;


    public Habitacion(int numeroHabitacion, int cantidadCamas, int cantidadMaximaPersonas, boolean disponible, EEstadoMantenimiento estado) {
        this.numeroHabitacion = numeroHabitacion;
        this.cantidadCamas = cantidadCamas;
        this.cantidadMaximaPersonas = cantidadMaximaPersonas;
        this.disponible = disponible;
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

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public EEstadoMantenimiento getEstado() {
        return estado;
    }

    public void setEstado(EEstadoMantenimiento estado) {
        this.estado = estado;
    }


    private EEstadoMantenimiento estadoMantenimiento =
            EEstadoMantenimiento.NORMAL;

    public EEstadoMantenimiento getEstadoMantenimiento() {
        return estadoMantenimiento;
    }

    public void setEstadoMantenimiento(
            EEstadoMantenimiento estadoMantenimiento) {

        if (estadoMantenimiento == null) {
            throw new IllegalArgumentException(
                    "El estado de mantenimiento no puede ser nulo");
        }

        this.estadoMantenimiento = estadoMantenimiento;
    }

    public boolean necesitaReparacion() {
        return estadoMantenimiento ==
                EEstadoMantenimiento.EN_REPARACION;
    }

    public boolean estaEnReparacion() {
        return estadoMantenimiento ==
                EEstadoMantenimiento.EN_REPARACION;
    }



}
