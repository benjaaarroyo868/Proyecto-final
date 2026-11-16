
package Conexion;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {

    // CONEXION COMPARTIDA DURANTE UNA TRANSACCION
    private static Connection connTransaccion = null;

    // OBTENER CONEXION DESDE CONEXION.JAVA
    public static Connection createConnection() {
        Conexion conexion = new Conexion();

        conexion.createConnection();

        try {
            conexion.usarBaseDatos("HotelConte");
            return conexion.getConnection();

        } catch (RuntimeException e) {
            conexion.closeConnection();
            throw e;
        }
    }

    // ABRIR O REUTILIZAR CONEXION
    public static Connection abrir() {
        if (connTransaccion != null) {
            return connTransaccion;
        }

        return createConnection();
    }

    // CERRAR UTILIZANDO LA CLASE CONEXION
    public static void cerrar(Connection conn) {
        if (conn != null && conn != connTransaccion) {
            Conexion.cerrar(conn);
        }
    }

    // EJECUTAR OPERACIONES EN UNA TRANSACCION
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

            // SI TODO SALE BIEN, GUARDAR
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
}
