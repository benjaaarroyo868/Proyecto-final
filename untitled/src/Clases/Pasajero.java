package Clases;

import Exepciones.DatoInvalidoException;
import Interfaces.IValidarDatos;

public class Pasajero extends Persona implements IValidarDatos {
    private String origen;
    private String destino_origen;

    public Pasajero(String nombre, String apellido, String DNI, String telefono, String email, Usuario usuario, String origen, String destino_origen) {
        super(nombre, apellido, DNI, telefono, email, usuario);
        this.origen = origen;
        this.destino_origen = destino_origen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino_origen() {
        return destino_origen;
    }

    public void setDestino_origen(String destino_origen) {
        this.destino_origen = destino_origen;
    }

    public void validarDatos() throws DatoInvalidoException {
        if (getNombre() == null || getNombre().isBlank() ||
                getApellido() == null || getApellido().isBlank() ||
                getDNI() == null || getDNI().isBlank() ||
                !getDNI().matches("[0-9]{8}")||
                getEmail() == null || getEmail().isBlank()||
               !getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")||
                getTelefono() == null || getTelefono().isBlank()||
                getTelefono().matches("[0-9]{10}"))
        {

            throw new DatoInvalidoException("Los datos personales son inválidos");
        }

        if (origen == null || origen.isBlank()) {
            throw new DatoInvalidoException("El origen no puede estar vacío");
        }
    }

    @Override
    public String toString() {
        return "Pasajero{" + super.toString()+
                "origen='" + origen + '\'' +
                ", destino_origen='" + destino_origen + '\'' +
                '}';
    }
}
