package Conexion;

import Clases.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionHotel extends ConexionBase {

    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS Hotel(" +
                "id INT AUTO_INCREMENT PRIMARY KEY not null, " +
                "nombre VARCHAR(100) not null, " +
                "direccion VARCHAR(200)not null, " +
                "cantidad_habitaciones INT not null," +
                "habitaciones_ocupadas INT not null)";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error en MySQL: " + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public void insertarHotel(Hotel h) {
        String sql = "INSERT INTO Hotel(nombre, direccion, cantidad_habitaciones,habitaciones_ocupadas) " +
                "VALUES (?, ?, ?, ?)";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getDireccion());
            ps.setInt(3, h.getCantidadHabitaciones());
            ps.setInt(4, h.getCantidadOcupadas());

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no confirmó la inserción en insertarHotel");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public Hotel listarHotel() {
        Hotel hotel = null;
        String sql = "SELECT nombre, direccion, cantidad_habitaciones, habitaciones_ocupadas FROM Hotel LIMIT 1";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                hotel = new Hotel(
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getInt("cantidad_habitaciones")
                );
                hotel.setCantidadOcupadas(rs.getInt("habitaciones_ocupadas"));
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
        return hotel;
    }
}
