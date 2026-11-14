package Conexion;

import Clases.Pasajero;
import Clases.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ConexionPasajeros extends ConexionBase {

    private final ConexionUsuarios conexionUsuarios = new ConexionUsuarios();

    public void crearTabla() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Pasajeros (
                DNI VARCHAR(15) NOT NULL PRIMARY KEY,
                nombre VARCHAR(50) NOT NULL,
                apellido VARCHAR(50) NOT NULL,
                telefono VARCHAR(20) NOT NULL,
                email VARCHAR(100) NOT NULL,
                nombreUsuario VARCHAR(30) NOT NULL,
                origen VARCHAR(100) NOT NULL,
                destino_origen VARCHAR(100) NOT NULL,
                FOREIGN KEY (nombreUsuario) REFERENCES Usuarios(nombreUsuario)
            )
            """;

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public void insertarPasajero(Pasajero p) {
        String sql = "INSERT INTO Pasajeros(DNI, nombre, apellido, telefono, email, nombreUsuario, origen, destino_origen) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Verifica si el usuario ya existe para respetar la clave foranea.
        conexionUsuarios.asegurarUsuario(p.getUsuario());

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getDNI());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getEmail());
            ps.setString(6, p.getUsuario().getNombreUsuario());
            ps.setString(7, p.getOrigen());
            ps.setString(8, p.getDestino_origen());

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no confirmó la inserción en insertarPasajero");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public void asegurarPasajero(Pasajero pasajero) {
        conexionUsuarios.asegurarUsuario(pasajero.getUsuario());
        if (!existe("SELECT 1 FROM Pasajeros WHERE DNI=?", pasajero.getDNI())) {
            insertarPasajero(pasajero);
        }
    }

    public HashMap<String, Pasajero> listarPasajeros(HashMap<String, Usuario> usuarios) {
        HashMap<String, Pasajero> mapa = new HashMap<>();
        String sql = "SELECT DNI, nombre, apellido, telefono, email, nombreUsuario, origen, destino_origen FROM Pasajeros";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pasajero p = new Pasajero(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        usuarios.get(rs.getString("nombreUsuario")),
                        rs.getString("origen"),
                        rs.getString("destino_origen")
                );
                mapa.put(p.getDNI(), p);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
        return mapa;
    }

    public void eliminarPasajeroPorDni(String dni) {
        eliminarEstricto("DELETE FROM Pasajeros WHERE DNI=?", dni);
    }
}
