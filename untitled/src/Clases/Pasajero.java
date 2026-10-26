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
        if(getNombre() == null || getNombre().isBlank()){
            throw new DatoInvalidoException("El nombre no puede ser vacio");
        }
        if(getApellido() == null || getApellido().isBlank()){
            throw new DatoInvalidoException("El apellido no puede ser vacio");
        }
        if(getDNI() == null || getDNI().isBlank()){
            throw new DatoInvalidoException("El dni no puede ser vacio");
        }
        if(!getDNI().matches("[0-9]{8}")){
            throw new DatoInvalidoException("El dni no valido tiene que tener solo numeros y 8 numeros");
        }
        if(getTelefono() == null || getTelefono().isBlank()){
            throw new DatoInvalidoException("El numero de telefono no puede ser vacio");
        }
        if(!getTelefono().matches("[0-9]{10}")){
            throw new DatoInvalidoException("El numero de telefono no valido tiene que tener solo numeros y 8 numeros");
        }
        if(getEmail() == null || getEmail().isBlank()){
            throw new DatoInvalidoException("El email no puede ser vacio");
        }
        if(!getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new DatoInvalidoException("El email es invalido");
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
