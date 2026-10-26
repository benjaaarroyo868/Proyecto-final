package Conexion;
import com.sun.tools.javac.Main;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    Connection conn;

    public void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BDHotelConte", "winterfa", "Mecortaronelfrenilloen2024");

            System.out.println("Conexion establecida con exito");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("error al correr"+e);
        } catch (SQLException e) {
            throw new RuntimeException("error al correr sql"+e);
        }
    }
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearTablaUsuario() throws SQLException {

        String sql= """
                    Create table if not exists Usuarios(
                    nombreUsuario varchar(30) not null primary key,
                    contrasenia varchar(30) not null,
                   TipoUsuario varchar(30) 
                    )
                   
                    """;
        try{
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void crearTablaTecnicos() throws SQLException {

        String sql = """
            CREATE TABLE IF NOT EXISTS Tecnicos (
                DNI VARCHAR(15) NOT NULL PRIMARY KEY,
                nombre VARCHAR(50) NOT NULL,
                apellido VARCHAR(50) NOT NULL,
                telefono VARCHAR(20) NOT NULL,
                email VARCHAR(100) NOT NULL,
                nombreUsuario VARCHAR(30) NOT NULL,
                tipo_de_tecnico VARCHAR(30) NOT NULL,
                formacion VARCHAR(100) NOT NULL,
                disponible BOOLEAN NOT NULL,
                FOREIGN KEY (nombreUsuario) REFERENCES Usuarios(nombreUsuario)
            )
            """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearTablaAdministrador() {
        String sql = "CREATE TABLE IF NOT EXISTS Administradores(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100), " +
                "apellido VARCHAR(100), " +
                "dni VARCHAR(20), " +
                "telefono VARCHAR(20), " +
                "email VARCHAR(100), " +
                "nombreUsuario INT, " +
                "legajo VARCHAR(50), " +
                "sueldo DOUBLE, " +
                "FOREIGN KEY (nombreUsuario) REFERENCES usuarios(nombreUsuario))";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void crearTablaConserje() {
        String sql = "CREATE TABLE IF NOT EXISTS Conserjes(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100), " +
                "apellido VARCHAR(100), " +
                "dni VARCHAR(20), " +
                "telefono VARCHAR(20), " +
                "email VARCHAR(100), " +
                "nombreUsuario INT, " +
                "sueldoPorVenta DOUBLE, " +
                "FOREIGN KEY (nombreUsuario) REFERENCES usuarios(id))";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    

}
