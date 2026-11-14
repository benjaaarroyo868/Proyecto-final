package Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Crea todas las tablas en el orden correcto y revisa si la BD ya tiene datos. */
public class ConexionTablas {

    // Llamar una sola vez al iniciar el programa.
    // El orden importa: las tablas con clave foranea van despues de las que referencian.
    public void crearTablas() {
        new ConexionUsuarios().crearTabla();
        new ConexionTecnicos().crearTabla();
        new ConexionConserjes().crearTabla();
        new ConexionAdministradores().crearTabla();
        new ConexionHabitaciones().crearTabla();
        new ConexionHotel().crearTabla();
        new ConexionPasajeros().crearTabla();
        new ConexionReservas().crearTabla();
        new ConexionReparaciones().crearTabla();
    }

    /** Evita insertar registros de Main cuando la BD ya tiene datos reales. */
    public boolean tieneRegistrosPersistidos() {
        String sql = "SELECT (SELECT COUNT(*) FROM Usuarios) + "
                + "(SELECT COUNT(*) FROM Pasajeros) + "
                + "(SELECT COUNT(*) FROM Habitaciones) + "
                + "(SELECT COUNT(*) FROM Reserva) + "
                + "(SELECT COUNT(*) FROM Tecnicos) + "
                + "(SELECT COUNT(*) FROM Conserjes) + "
                + "(SELECT COUNT(*) FROM Administradores) + "
                + "(SELECT COUNT(*) FROM Reparaciones) + "
                + "(SELECT COUNT(*) FROM Hotel) AS total";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() && rs.getLong("total") > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo revisar el contenido de MySQL", e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }
}
