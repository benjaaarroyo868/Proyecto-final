package Conexion;

import Clases.Tecnico;
import Clases.Usuario;
import Enums.ETipoTecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ConexionTecnicos extends ConexionBase {

    private final ConexionUsuarios conexionUsuarios = new ConexionUsuarios();

    public void crearTabla() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Tecnicos (
                DNI VARCHAR(15) NOT NULL PRIMARY KEY,
                nombre VARCHAR(50) NOT NULL,
                apellido VARCHAR(50) NOT NULL,
                telefono VARCHAR(20) NOT NULL,
                email VARCHAR(100) NOT NULL,
                nombreUsuario VARCHAR(30) NOT NULL,
                tipo_de_tecnico VARCHAR(30) NOT NULL,
                formacion VARCHAR(100) NOT NULL,
                disponible BOOLEAN NOT NULL,
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

    public void insertarTecnico(Tecnico t) {
        String sql = "INSERT INTO Tecnicos(DNI, nombre, apellido, telefono, email, nombreUsuario, tipo_de_tecnico, formacion, disponible) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Verifica si el usuario ya existe para respetar la clave foranea.
        conexionUsuarios.asegurarUsuario(t.getUsuario());

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getDNI());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getApellido());
            ps.setString(4, t.getTelefono());
            ps.setString(5, t.getEmail());
            ps.setString(6, t.getUsuario().getNombreUsuario());
            ps.setString(7, t.getTipo_de_tecnico().name()); // enum guardado como texto
            ps.setString(8, t.getFormacion());
            ps.setBoolean(9, t.isDisponible());

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no confirmó la inserción en insertarTecnico");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public void actualizarTecnico(Tecnico t) {
        String sql = "UPDATE Tecnicos SET disponible=? WHERE DNI=?";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, t.isDisponible());
            ps.setString(2, t.getDNI());
            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("El técnico no se pudo actualizar en MySQL");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo actualizar el técnico", e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public HashMap<String, Tecnico> listarTecnicos(HashMap<String, Usuario> usuarios) {
        HashMap<String, Tecnico> mapa = new HashMap<>();
        String sql = "SELECT DNI, nombre, apellido, telefono, email, nombreUsuario, tipo_de_tecnico, formacion, disponible FROM Tecnicos";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tecnico t = new Tecnico(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        usuarios.get(rs.getString("nombreUsuario")),
                        ETipoTecnico.valueOf(rs.getString("tipo_de_tecnico")), // varchar -> enum
                        rs.getString("formacion"),
                        rs.getBoolean("disponible")
                );
                mapa.put(t.getDNI(), t);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
        return mapa;
    }
}
