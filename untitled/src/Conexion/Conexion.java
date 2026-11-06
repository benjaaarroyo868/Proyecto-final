package Conexion;
import Clases.Administrador;
import Clases.Conserje;
import Clases.Habitacion;
import Clases.Hotel;
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


//CREACION TABLAS

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

    public void crearTablaHabitacion() {
        String sql = "CREATE TABLE IF NOT EXISTS Habitaciones(" +
                "numero_habitacion INT PRIMARY KEY, " +
                "cantidad_camas INT, " +
                "cantidad_maxima_personas INT, " +
                "estado BOOLEAN)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
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
                "FOREIGN KEY (nombreUsuario) REFERENCES usuarios(id))";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void crearTablaHotel() {
        String sql = "CREATE TABLE IF NOT EXISTS Hotel(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100), " +
                "direccion VARCHAR(200), " +
                "cantidad_habitaciones INT)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void crearTablaReserva() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Reserva(
                idReserva VARCHAR(50) NOT NULL PRIMARY KEY,
                idPasajero INT NOT NULL,
                idHabitacion INT NOT NULL,
                cantidadPasajeros INT NOT NULL,
                fechaIngreso DATE NOT NULL,
                fechaSalida DATE NOT NULL,
                FOREIGN KEY (idPasajero) REFERENCES Pasajero(id),
                FOREIGN KEY (idHabitacion) REFERENCES Habitacion(id)
            )
            """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void crearTablaPasajeros() throws SQLException {

        String sql = """
            CREATE TABLE IF NOT EXISTS Pasajeros (
                DNI VARCHAR(15) NOT NULL PRIMARY KEY,
                nombre VARCHAR(50) NOT NULL,
                apellido VARCHAR(50) NOT NULL,
                telefono VARCHAR(20) NOT NULL,
                email VARCHAR(100) NOT NULL,
                nombreUsuario VARCHAR(30) NOT NULL,
                origen VARCHAR(100) NOT NULL,
                destino_origen VARCHAR(100) NOT NULL,
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


    ///INSERTAR

    public void insertarConserje(Conserje c) {
        crearTablaConserje();
        String sql = "INSERT INTO conserjes(nombre, apellido, dni, telefono, email, id_usuario, sueldoPorVenta) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getDNI());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getUsuario().getNombreUsuario());
            ps.setDouble(7, c.getSueldoPorVenta());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarAdministrador(Administrador administrador) {
        crearTablaAdministrador();
        String sql = "INSERT INTO administradores(nombre, apellido, dni, telefono, email, id_usuario, legajo, sueldo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, administrador.getNombre());
            ps.setString(2, administrador.getApellido());
            ps.setString(3, administrador.getDNI());
            ps.setString(4, administrador.getTelefono());
            ps.setString(5, administrador.getEmail());
            ps.setString(6, administrador.getUsuario().getNombreUsuario());
            ps.setString(7, administrador.getLegajo());
            ps.setDouble(8, administrador.getSueldo());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    public void insertarHabitacion(Habitacion h) {
        crearTablaHabitacion();
        String sql = "INSERT INTO habitaciones(numero_habitacion, cantidad_camas, cantidad_maxima_personas, estado) " +
                "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, h.getNumeroHabitacion());
            ps.setInt(2, h.getCantidadCamas());
            ps.setInt(3, h.getCantidadMaximaPersonas());
            ps.setBoolean(4, h.isDisponible());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarHotel(Hotel h) {
        crearTablaHotel();
        String sql = "INSERT INTO hoteles(nombre, direccion, cantidad_habitaciones) " +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, h.getNombre());
            ps.setString(2, h.getDireccion());
            ps.setInt(3, h.getCantidadHabitaciones());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    

}
