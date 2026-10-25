package Clases;
import Enums.ETipoTecnico;

public class Tecnico extends Persona {
    private ETipoTecnico tipo_de_tecnico;
    private String formacion;
    private boolean disponible;

    public Tecnico(String nombre, String apellido, String DNI, String telefono, String email, Usuario usuario, ETipoTecnico tipo_de_tecnico, String formacion, boolean disponible) {
        super(nombre, apellido, DNI, telefono, email, usuario);
        this.tipo_de_tecnico = tipo_de_tecnico;
        this.formacion = formacion;
        this.disponible = disponible;
    }

    public ETipoTecnico getTipo_de_tecnico() {
        return tipo_de_tecnico;
    }

    public void setTipo_de_tecnico(ETipoTecnico tipo_de_tecnico) {
        this.tipo_de_tecnico = tipo_de_tecnico;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Tecnico{ " +super.toString()+
                "tipo_de_tecnico=" + tipo_de_tecnico +
                ", formacion='" + formacion + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}

