package Conexion;

import Clases.Administrador;
import Clases.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ConexionAdministradores extends ConexionBase {

    private final ConexionUsuarios conexionUsuarios = new ConexionUsuarios();

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS Administradores(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100), " +
                "apellido VARCHAR(100), " +
                "dni VARCHAR(20) UNIQUE, " +
                "telefono VARCHAR(20), " +
                "email VARCHAR(100), " +
                "nombreUsuario VARCHAR(30), " +
                "legajo VARCHAR(50), " +
                "sueldo DOUBLE, " +
                "FOREIGN KEY (nombreUsuario) REFERENCES Usuarios(nombreUsuario))";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error en MySQL: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public void insertarAdministrador(Administrador administrador) {
        String sql = "INSERT INTO Administradores(nombre, apellido, dni, telefono, email, nombreUsuario, legajo, sueldo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Verifica si el usuario ya existe para respetar la clave foranea.
        conexionUsuarios.asegurarUsuario(administrador.getUsuario());

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, administrador.getNombre());
            ps.setString(2, administrador.getApellido());
            ps.setString(3, administrador.getDNI());
            ps.setString(4, administrador.getTelefono());
            ps.setString(5, administrador.getEmail());
            ps.setString(6, administrador.getUsuario().getNombreUsuario());
            ps.setString(7, administrador.getLegajo());
            ps.setDouble(8, administrador.getSueldo());

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no confirmó la inserción en insertarAdministrador");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error en MySQL: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public HashMap<String, Administrador> listarAdministradores(HashMap<String, Usuario> usuarios) {
        HashMap<String, Administrador> mapa = new HashMap<>();
        String sql = "SELECT nombre, apellido, dni, telefono, email, nombreUsuario, legajo, sueldo FROM Administradores";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Administrador a = new Administrador(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        usuarios.get(rs.getString("nombreUsuario")),
                        rs.getString("legajo"),
                        rs.getDouble("sueldo")
                );
                mapa.put(a.getDNI(), a);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
        return mapa;
    }

    public void eliminarAdministradorPorDni(String dni) {
        eliminarEstricto("DELETE FROM Administradores WHERE dni=?", dni);
    }
}
