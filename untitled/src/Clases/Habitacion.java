package Clases;

import Enums.EEstadoMantenimiento;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Interfaces.IValidarDatos;

public class Habitacion implements IValidarDatos {
    private int numeroHabitacion;
    private int cantidadCamas;
    private int cantidadMaximaPersonas;
    private boolean disponible;
    private EEstadoMantenimiento estado;


    public Habitacion(int numeroHabitacion, int cantidadCamas, int cantidadMaximaPersonas) {
        this.numeroHabitacion = numeroHabitacion;
        this.cantidadCamas = cantidadCamas;
        this.cantidadMaximaPersonas = cantidadMaximaPersonas;
        this.disponible = false;
        this.estado = EEstadoMantenimiento.NORMAL;
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


    public void setEstado(EEstadoMantenimiento estado) {
        this.estado = estado;
    }

    public EEstadoMantenimiento getEstadoMantenimiento() {
        return estado;
    }

    public void setEstadoMantenimiento(
            EEstadoMantenimiento estadoMantenimiento) {

        if (estadoMantenimiento == null) {
            throw new IllegalArgumentException(
                    "El estado de mantenimiento no puede ser nulo");
        }

        this.estado = estadoMantenimiento;
    }

    public boolean necesitaReparacion() {
        return estado ==
                EEstadoMantenimiento.EN_REPARACION;
    }


    @Override
    public void validarDatos() throws AgenciaHotelException {
        if(cantidadCamas <= 0){
            throw new DatoInvalidoException("La cantidad de camas no puede ser 0");
        }
        if(numeroHabitacion <= 0){
            throw new DatoInvalidoException("El numero de la habitacion no puede ser 0");
        }
        if(cantidadMaximaPersonas <= 0){
            throw new DatoInvalidoException("La cantidad maxima de personas no puede ser 0");
        }
    }
}
