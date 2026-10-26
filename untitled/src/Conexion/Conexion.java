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
    
}
