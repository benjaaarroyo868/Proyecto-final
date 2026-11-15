
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que administra la conexion con MySQL
 * y las transacciones de las clases ConexionX.
 */
public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/HotelConte";

    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "TU_CONTRASENA_MYSQL";

    // Conexion compartida durante una transaccion.
    private static Connection connTransaccion = null;

    // Crea una nueva conexion con MySQL.
    public static Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    URL, USUARIO, CONTRASENIA);

        } catch (ClassNotFoundException e) {
            Logger.getLogger(ConexionBD.class.getName())
                    .log(Level.SEVERE, null, e);

            throw new IllegalStateException(
                    "No se encontró el driver de MySQL", e);

        } catch (SQLException e) {
            Logger.getLogger(ConexionBD.class.getName())
                    .log(Level.SEVERE, null, e);

            throw new IllegalStateException(
                    "Error al conectar con la base de datos: "
                            + e.getMessage(), e);
        }
    }

    // Cierra una conexion.
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error en MySQL: " + e.getMessage(), e);
        }
    }

    // Abre una conexion o reutiliza la transaccion activa.
    public static Connection abrir() {
        if (connTransaccion != null) {
            return connTransaccion;
        }

        return createConnection();
    }

    // Cierra solamente las conexiones que no pertenecen
    // a una transaccion activa.
    public static void cerrar(Connection c) {
        if (c != connTransaccion) {
            closeConnection(c);
        }
    }

    // Ejecuta varias operaciones en una unica transaccion.
    public static void ejecutarTransaccion(Runnable operacion) {

        if (connTransaccion != null) {
            throw new IllegalStateException(
                    "Ya hay una transacción en curso");
        }

        Connection conn = createConnection();

        try {
            conn.setAutoCommit(false);

        } catch (SQLException e) {
            closeConnection(conn);

            throw new IllegalStateException(
                    "No se pudo iniciar la transacción", e);
        }

        connTransaccion = conn;

        try {
            operacion.run();
            conn.commit();

        } catch (RuntimeException | Error e) {
            try {
                conn.rollback();
            } catch (SQLException rollback) {
                e.addSuppressed(rollback);
            }

            throw e;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollback) {
                e.addSuppressed(rollback);
            }

            throw new IllegalStateException(
                    "No se pudo confirmar la transacción", e);

        } finally {
            connTransaccion = null;
            closeConnection(conn);
        }
    }
}
