package Clases;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Interfaces.IEmpleado;
import Interfaces.IValidarDatos;

public class Administrador  extends Persona implements IEmpleado, IValidarDatos {

    private String legajo;
    private double sueldo;

    public Administrador(String nombre, String apellido, String DNI, String telefono, String email, Usuario usuario, String legajo, double sueldo) {
        super(nombre, apellido, DNI, telefono, email, usuario);
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

    @Override
    public void validarDatos() throws AgenciaHotelException {
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
        if(getTelefono().matches("[0-9]{11}")){
            throw new DatoInvalidoException("El numero de telefono no valido tiene que tener solo numeros y 8 numeros");
        }
        if(getEmail() == null || getEmail().isBlank()){
            throw new DatoInvalidoException("El email no puede ser vacio");
        }
        if(!getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new DatoInvalidoException("El email es invalido");
        }
        if(getUsuario() == null){
            throw new DatoInvalidoException("El usuario no puede ser vacio");
        }
        if(legajo == null || legajo.isBlank()){
            throw new DatoInvalidoException("El legajo no puede ser vacio ");
        }
        if(sueldo <= 0){
            throw new DatoInvalidoException("Sueldo no puede ser menor o igual  que 0");
        }
    }

}
