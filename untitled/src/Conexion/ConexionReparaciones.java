package Conexion;

import Clases.Habitacion;
import Clases.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ConexionReparaciones extends ConexionBase {

    public void crearTabla() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Reparaciones(
                numero_habitacion INT NOT NULL PRIMARY KEY,
                dniTecnico VARCHAR(15) NOT NULL,
                FOREIGN KEY (numero_habitacion) REFERENCES Habitaciones(numero_habitacion),
                FOREIGN KEY (dniTecnico) REFERENCES Tecnicos(DNI)
            )
            """;

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error en MySQL: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public void insertarReparacion(Habitacion h, Tecnico t) {
        String sql = "INSERT INTO Reparaciones(numero_habitacion, dniTecnico) VALUES (?, ?)";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, h.getNumeroHabitacion());
            ps.setString(2, t.getDNI());

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no confirmó la inserción en insertarReparacion");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    // Cuando se termina una reparacion hay que sacarla de la tabla
    public void eliminarReparacion(Habitacion h) {
        String sql = "DELETE FROM Reparaciones WHERE numero_habitacion = ?";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, h.getNumeroHabitacion());

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no encontró el registro para eliminar: " + "eliminarReparacion");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public HashMap<Habitacion, Tecnico> listarReparaciones(HashMap<String, Habitacion> habitaciones,
                                                           HashMap<String, Tecnico> tecnicos) {
        HashMap<Habitacion, Tecnico> mapa = new HashMap<>();
        String sql = "SELECT numero_habitacion, dniTecnico FROM Reparaciones";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Habitacion h = habitaciones.get(String.valueOf(rs.getInt("numero_habitacion")));
                Tecnico t = tecnicos.get(rs.getString("dniTecnico"));

                if (h != null && t != null) {
                    mapa.put(h, t);
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
        return mapa;
    }
}
