package Clases;

import Enums.ETipoUsuario;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Interfaces.IValidarDatos;

import java.util.Objects;

public class Usuario implements IValidarDatos {
    private String nombreUsuario;
    private String contrasenia;
    private ETipoUsuario tipoDeUsuario;

    public Usuario(String nombreUsuario, String contrasenia, ETipoUsuario tipo_de_usuario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.tipoDeUsuario = tipo_de_usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public ETipoUsuario getTipoDeUsuario() {
        return tipoDeUsuario;
    }

    public void setTipoDeUsuario(ETipoUsuario tipoDeUsuario) {
        this.tipoDeUsuario = tipoDeUsuario;
    }

    @Override
    public boolean equals(Object object) {
        if(object==this)return true;
        if (object == null || getClass() != object.getClass()) return false;
        Usuario usuario = (Usuario) object;
        return Objects.equals(nombreUsuario, usuario.nombreUsuario) && Objects.equals(contrasenia, usuario.contrasenia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreUsuario, contrasenia);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", tipo_de_usuario=" + tipoDeUsuario +
                '}';
    }

    @Override
    public void validarDatos() throws AgenciaHotelException {
        if (nombreUsuario == null || nombreUsuario.isBlank() ||
                contrasenia == null || contrasenia.isBlank()) {

            throw new DatoInvalidoException("El nombre de usuario y la contraseña no pueden estar vacíos");
        }

        if (tipoDeUsuario == null) {
            throw new DatoInvalidoException("El tipo de usuario no puede ser nulo");
        }
    }
}
