package Conexion;

import Clases.Conserje;
import Clases.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ConexionConserjes extends ConexionBase {

    private final ConexionUsuarios conexionUsuarios = new ConexionUsuarios();

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS Conserjes(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100), " +
                "apellido VARCHAR(100), " +
                "dni VARCHAR(20) UNIQUE, " +
                "telefono VARCHAR(20), " +
                "email VARCHAR(100), " +
                "nombreUsuario VARCHAR(30), " +
                "sueldoPorVenta DOUBLE, " +
                "legajo VARCHAR(30), " +
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

    public void insertarConserje(Conserje c) {
        String sql = "INSERT INTO Conserjes(nombre, apellido, dni, telefono, email, nombreUsuario, sueldoPorVenta, legajo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Verifica si el usuario ya existe para respetar la clave foranea.
        conexionUsuarios.asegurarUsuario(c.getUsuario());

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getDNI());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getUsuario().getNombreUsuario());
            ps.setDouble(7, c.getSueldoPorVenta());
            ps.setString(8, c.getLegajo());

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no confirmó la inserción en insertarConserje");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public HashMap<String, Conserje> listarConserjes(HashMap<String, Usuario> usuarios) {
        HashMap<String, Conserje> mapa = new HashMap<>();
        String sql = "SELECT nombre, apellido, dni, telefono, email, nombreUsuario, legajo, sueldoPorVenta FROM Conserjes";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Conserje c = new Conserje(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        usuarios.get(rs.getString("nombreUsuario")),
                        rs.getString("legajo"),
                        rs.getDouble("sueldoPorVenta")
                );
                mapa.put(c.getDNI(), c);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
        return mapa;
    }

    public void eliminarConserjePorDni(String dni) {
        eliminarEstricto("DELETE FROM Conserjes WHERE dni=?", dni);
    }
}
