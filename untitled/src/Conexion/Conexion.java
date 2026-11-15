
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexion {

    private Connection conn;

    // ESTABLECER CONEXION
    public void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/",
                    "root",
                    System.getenv("Scrfiat868877")
            );

            System.out.println("Conexion establecida con exito");

        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException(
                    "Error al conectar con MySQL: "
                            + e.getMessage(), e);
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
