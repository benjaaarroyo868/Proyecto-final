package Conexion;

import Clases.Habitacion;
import Clases.Pasajero;
import Clases.Reserva;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class ConexionReservas extends ConexionBase {

    public void crearTabla() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Reserva(
                idReserva VARCHAR(50) NOT NULL PRIMARY KEY,
                idPasajero VARCHAR(15) NOT NULL,
                idHabitacion INT NOT NULL,
                cantidadPasajeros INT NOT NULL,
                fechaIngreso DATE NOT NULL,
                fechaSalida DATE NOT NULL,
                FOREIGN KEY (idPasajero) REFERENCES Pasajeros(DNI),
                FOREIGN KEY (idHabitacion) REFERENCES Habitaciones(numero_habitacion)
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

    public void insertarReserva(Reserva r) {
        String sql = "INSERT INTO Reserva(idReserva, idPasajero, idHabitacion, cantidadPasajeros, fechaIngreso, fechaSalida) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getIdReserva());
            ps.setString(2, r.getPasajero().getDNI());                // el DNI es la clave de Pasajeros
            ps.setInt(3, r.getHabitacion().getNumeroHabitacion());    // el numero es la clave de Habitaciones
            ps.setInt(4, r.getCantidadPasajeros());
            ps.setDate(5, Date.valueOf(r.getFechaIngreso()));         // LocalDate -> java.sql.Date
            ps.setDate(6, Date.valueOf(r.getFechaSalida()));

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no confirmó la inserción en insertarReserva");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    // MODIFICAR FECHA DE INGRESO
    public void modificarFechaIngreso(String idReserva, LocalDate nuevaFechaIngreso) {
        String sql = "UPDATE Reserva SET fechaIngreso = ? WHERE idReserva = ?";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(nuevaFechaIngreso));
            ps.setString(2, idReserva);

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no encontró el registro para actualizar: " + "modificarFechaIngreso");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    // MODIFICAR FECHA DE SALIDA
    public void modificarFechaSalida(String idReserva, LocalDate nuevaFechaSalida) {
        String sql = "UPDATE Reserva SET fechaSalida = ? WHERE idReserva = ?";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(nuevaFechaSalida));
            ps.setString(2, idReserva);

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no encontró el registro para actualizar: " + "modificarFechaSalida");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    // MODIFICAR AMBAS FECHAS
    public void modificarFechasReserva(String idReserva, LocalDate nuevaFechaIngreso, LocalDate nuevaFechaSalida) {
        String sql = "UPDATE Reserva SET fechaIngreso = ?, fechaSalida = ? WHERE idReserva = ?";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(nuevaFechaIngreso));
            ps.setDate(2, Date.valueOf(nuevaFechaSalida));
            ps.setString(3, idReserva);

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no encontró el registro para actualizar: " + "modificarFechasReserva");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    // MODIFICAR NUMERO DE HABITACION
    public void modificarNumeroHabitacion(String idReserva, int nuevoNumeroHabitacion) {
        String sql = "UPDATE Reserva SET idHabitacion = ? WHERE idReserva = ?";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nuevoNumeroHabitacion);
            ps.setString(2, idReserva);

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no encontró el registro para actualizar: " + "modificarNumeroHabitacion");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    // MODIFICAR CANTIDAD DE PERSONAS
    public void modificarCantidadPersonas(String idReserva, int nuevaCantidadPersonas) {
        String sql = "UPDATE Reserva SET cantidadPasajeros = ? WHERE idReserva = ?";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nuevaCantidadPersonas);
            ps.setString(2, idReserva);

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no encontró el registro para actualizar: " + "modificarCantidadPersonas");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public HashMap<String, Reserva> listarReservas(HashMap<String, Pasajero> pasajeros,
                                                   HashMap<String, Habitacion> habitaciones) {
        HashMap<String, Reserva> mapa = new HashMap<>();
        String sql = "SELECT idReserva, idPasajero, idHabitacion, cantidadPasajeros, fechaIngreso, fechaSalida FROM Reserva";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pasajero p = pasajeros.get(rs.getString("idPasajero"));
                Habitacion h = habitaciones.get(String.valueOf(rs.getInt("idHabitacion")));

                if (p == null || h == null) {
                    continue; // reserva sin pasajero o habitacion cargados: se omite
                }

                Reserva r = new Reserva(
                        p,
                        h,
                        rs.getInt("cantidadPasajeros"),
                        rs.getDate("fechaIngreso").toLocalDate(),  // java.sql.Date -> LocalDate
                        rs.getDate("fechaSalida").toLocalDate()
                );
                r.setIdReserva(rs.getString("idReserva")); // el constructor genera un id nuevo, hay que pisarlo
                mapa.put(r.getIdReserva(), r);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
        return mapa;
    }

    public void eliminarReservaPorId(String id) {
        eliminarEstricto("DELETE FROM Reserva WHERE idReserva=?", id);
    }
}
