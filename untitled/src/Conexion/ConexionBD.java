
package Conexion;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {

    // Conexion compartida durante una transaccion.
    private static Connection connTransaccion = null;

    // Reutiliza la conexion de la clase Conexion.
    public static Connection createConnection() {
        Conexion conexion = new Conexion();
        conexion.createConnection();
        return conexion.getConnection();
    }

    // Abre una conexion o reutiliza la transaccion activa.
    public static Connection abrir() {
        if (connTransaccion != null) {
            return connTransaccion;
        }

        return createConnection();
    }


    public static void cerrar(Connection conn) {
        if (conn != null && conn != connTransaccion) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new IllegalStateException(
                        "Error al cerrar la conexion", e);
            }
        }
    }


    // Ejecuta operaciones en una sola transaccion.
    public static void ejecutarTransaccion(Runnable operacion) {

        if (connTransaccion != null) {
            throw new IllegalStateException(
                    "Ya hay una transaccion en curso");
        }

        Connection conn = abrir();

        try {
            conn.setAutoCommit(false);
            connTransaccion = conn;

            operacion.run();

            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollback) {
                e.addSuppressed(rollback);
            }

            throw new IllegalStateException(
                    "Error en la transaccion", e);

        } catch (RuntimeException | Error e) {
            try {
                conn.rollback();
            } catch (SQLException rollback) {
                e.addSuppressed(rollback);
            }

            throw e;

        } finally {
            connTransaccion = null;
            cerrar(conn);
        }
    }

    public static void closeConnection(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al cerrar la conexion", e);
        }
    }
}
