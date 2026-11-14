package Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Metodos auxiliares compartidos por todas las clases ConexionX. */
public abstract class ConexionBase {

    /** Devuelve true si la consulta (con un parametro) encuentra al menos una fila. */
    protected boolean existe(String sql, Object clave) {
        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, clave);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo comprobar un registro en MySQL", e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    /** Ejecuta un DELETE con un parametro y exige que borre exactamente una fila. */
    protected void eliminarEstricto(String sql, Object clave) {
        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, clave);
            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("No se encontró el registro para eliminar en MySQL");
            }
        } catch (SQLException e) {
            // Si existe una FK (ej. pasajero con reservas), NO se elimina del mapa.
            throw new IllegalStateException("No se pudo eliminar en MySQL. Verificá los datos vinculados: "
                    + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }
}

