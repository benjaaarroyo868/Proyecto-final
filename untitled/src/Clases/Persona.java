package Clases;

import java.util.Objects;

public abstract class Persona {

    private String nombre;
    private String apellido;
    private String DNI;
    private int telefono;
    private String email;
    private Usuario usuario;

    public Persona(String nombre, String apellido, String DNI, int telefono, String email, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.telefono = telefono;
        this.email = email;
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(DNI, persona.DNI);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(DNI);
    }

    @Override
    public String toString() {
        return
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI='" + DNI + '\'' +
                ", telefono=" + telefono +
                ", email='" + email + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
