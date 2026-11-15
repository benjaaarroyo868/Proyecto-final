package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexion {

    private Connection conn;

    public void createConnection() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/",
                    "root",
                    "TU_CONTRASENA_MYSQL"
            );

            System.out.println("Conexion establecida con exito");

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al conectar con MySQL", e);
        }
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al cerrar la conexion", e);
        }
    }

    private static void validarNombreBaseDatos(String nombre) {
        if (nombre == null ||
                !nombre.matches("[A-Za-z][A-Za-z0-9_]*")) {
            throw new IllegalArgumentException(
                    "Nombre de base de datos invalido");
        }
    }

    public void crearBaseDatos(String nombre) {
        validarNombreBaseDatos(nombre);

        String sql = "CREATE DATABASE IF NOT EXISTS `" + nombre + "`";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al crear la base de datos", e);
        }
    }


    public void usarBaseDatos(String nombre) {
        validarNombreBaseDatos(nombre);

        try {
            conn.setCatalog(nombre);
            System.out.println("Base de datos seleccionada: " + nombre);

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al usar la base de datos: " + e.getMessage(), e);
        }
    }

}