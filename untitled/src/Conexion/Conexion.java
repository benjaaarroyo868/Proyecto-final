
package Conexion;

import com.sun.tools.javac.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private Connection conn;

    // ESTABLECER CONEXION
    public void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Scrfiat868877");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Error al conectar con la base de datos");
        }
    }

    // OBTENER LA CONEXION
    public Connection getConnection() {
        return conn;
    }


    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al cerrar la conexion: " + e.getMessage(), e);
        }
    }


    // VALIDAR NOMBRE DE LA BASE DE DATOS
    private static void validarNombreBaseDatos(String nombre) {
        if (nombre == null ||
                !nombre.matches("[A-Za-z][A-Za-z0-9_]*")) {
            throw new IllegalArgumentException(
                    "Nombre de base de datos invalido");
        }
    }

    // CREAR BASE DE DATOS
    public void crearBaseDatos(String nombre) {
        validarNombreBaseDatos(nombre);

        String sql = "CREATE DATABASE IF NOT EXISTS `" + nombre + "`";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al crear la base de datos: "
                            + e.getMessage(), e);
        }
    }

    // SELECCIONAR BASE DE DATOS
    public void usarBaseDatos(String nombre) {
        validarNombreBaseDatos(nombre);

        try {
            conn.setCatalog(nombre);

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al seleccionar la base de datos: "
                            + e.getMessage(), e);
        }
    }
}
