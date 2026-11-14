package Conexion;

import Clases.Habitacion;
import Enums.EEstadoMantenimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ConexionHabitaciones extends ConexionBase {

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS Habitaciones(" +
                "numero_habitacion INT PRIMARY KEY, " +
                "cantidad_camas INT, " +
                "cantidad_maxima_personas INT, " +
                "estado BOOLEAN, " +
                "estadoMantenimiento VARCHAR(30))";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error en MySQL: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public void insertarHabitacion(Habitacion h) {
        String sql = "INSERT INTO Habitaciones(numero_habitacion, cantidad_camas, cantidad_maxima_personas, estado, estadoMantenimiento) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, h.getNumeroHabitacion());
            ps.setInt(2, h.getCantidadCamas());
            ps.setInt(3, h.getCantidadMaximaPersonas());
            ps.setBoolean(4, h.isDisponible());
            ps.setString(5, h.getEstadoMantenimiento().name()); // enum guardado como texto

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no confirmó la inserción en insertarHabitacion");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public void asegurarHabitacion(Habitacion habitacion) {
        if (!existe("SELECT 1 FROM Habitaciones WHERE numero_habitacion=?",
                habitacion.getNumeroHabitacion())) {
            insertarHabitacion(habitacion);
        }
    }

    // MODIFICAR ESTADO DE HABITACION
    public void modificarEstadoHabitacion(int numeroHabitacion, boolean disponible, EEstadoMantenimiento estadoMantenimiento) {
        String sql = "UPDATE Habitaciones SET estado = ?, estadoMantenimiento = ? WHERE numero_habitacion = ?";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, disponible);
            ps.setString(2, estadoMantenimiento.name());
            ps.setInt(3, numeroHabitacion);

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no encontró el registro para actualizar: " + "modificarEstadoHabitacion");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public HashMap<String, Habitacion> listarHabitaciones() {
        HashMap<String, Habitacion> mapa = new HashMap<>();
        String sql = "SELECT numero_habitacion, cantidad_camas, cantidad_maxima_personas, estado, estadoMantenimiento FROM Habitaciones";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Habitacion h = new Habitacion(
                        rs.getInt("numero_habitacion"),
                        rs.getInt("cantidad_camas"),
                        rs.getInt("cantidad_maxima_personas")
                );
                h.setDisponible(rs.getBoolean("estado"));
                h.setEstadoMantenimiento(EEstadoMantenimiento.valueOf(rs.getString("estadoMantenimiento"))); // varchar -> enum
                mapa.put(String.valueOf(h.getNumeroHabitacion()), h);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
        return mapa;
    }
}
