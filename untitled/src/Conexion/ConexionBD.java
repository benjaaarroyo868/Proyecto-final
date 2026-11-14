package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Unico lugar que sabe conectarse a MySQL y manejar transacciones.
 * Todas las clases ConexionX usan abrir() / cerrar().
 */
public class ConexionBD {



    private static final String URL =
            "jdbc:mysql://localhost:3306/";
    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "Scrfiat868877";

    // Conexion compartida por todas las clases mientras dura una transaccion.
    private static Connection connTransaccion = null;

    /** Abre y devuelve una conexion NUEVA cada vez que se la llama. */
    public static Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, e);
            throw new IllegalStateException("No se encontró el driver de MySQL", e);
        } catch (SQLException e) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, e);
            throw new IllegalStateException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }

    /** Cierra la conexion que recibe (si no es null y sigue abierta). */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        }
    }

    /** Usar al empezar cada metodo: dentro de una transaccion devuelve la conexion de la transaccion. */
    public static Connection abrir() {
        if (connTransaccion != null) {
            return connTransaccion;
        }
        return createConnection();
    }

    /** Usar en el finally de cada metodo: dentro de una transaccion NO cierra la conexion. */
    public static void cerrar(Connection c) {
        if (c != connTransaccion) {
            closeConnection(c);
        }
    }

    /** Ejecuta varias operaciones (de cualquier clase ConexionX) como una sola operación en MySQL. */
    public static void ejecutarTransaccion(Runnable operacion) {
        if (connTransaccion != null) {
            throw new IllegalStateException("Ya hay una transacción en curso");
        }

        Connection conn = createConnection();

        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            closeConnection(conn);
            throw new IllegalStateException("No se pudo iniciar la transacción", e);
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
            try { conn.rollback(); } catch (SQLException rollback) { e.addSuppressed(rollback); }
            throw new IllegalStateException("No se pudo confirmar la transacción", e);
        } finally {
            connTransaccion = null;
            closeConnection(conn);
        }
    }
}