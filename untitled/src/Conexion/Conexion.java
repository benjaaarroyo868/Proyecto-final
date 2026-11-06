package Conexion;
import Clases.*;
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
                "dni VARCHAR(20) unique," +
                "telefono VARCHAR(20), " +
                "email VARCHAR(100), " +
                "nombreUsuario varchar(30), " +
                "sueldoPorVenta DOUBLE, " +
                "legajo varchar(30), "+
                "FOREIGN KEY (nombreUsuario) REFERENCES Usuarios(nombreUsuarios))";

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
                "dni VARCHAR(20) unique, " +
                "telefono VARCHAR(20), " +
                "email VARCHAR(100), " +
                "nombreUsuario varchar(30), " +
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
                idPasajero VARCHAR(15) NOT NULL,
                idHabitacion INT NOT NULL,
                cantidadPasajeros INT NOT NULL,
                fechaIngreso DATE NOT NULL,
                fechaSalida DATE NOT NULL,
                FOREIGN KEY (idPasajero) REFERENCES Pasajeros(DNI),
                FOREIGN KEY (idHabitacion) REFERENCES Habitaciones(numero_habitacion)
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
    public void crearTablaReparaciones() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Reparaciones(
                numero_habitacion INT NOT NULL PRIMARY KEY,
                dniTecnico VARCHAR(15) NOT NULL,
                FOREIGN KEY (numero_habitacion) REFERENCES Habitaciones(numero_habitacion),
                FOREIGN KEY (dniTecnico) REFERENCES Tecnicos(DNI)
            )
            """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    ///INSERTAR

    public void insertarConserje(Conserje c) {
        crearTablaConserje();
        String sql = "INSERT INTO conserjes(nombre, apellido, dni, telefono, email, id_usuario, sueldoPorVenta,legajo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getDNI());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getUsuario().getNombreUsuario());
            ps.setDouble(7, c.getSueldoPorVenta());
            ps.setString(8,c.getLegajo());

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

    public void insertarUsuario(Usuario u) {
        crearTablaUsuario();
        String sql = "INSERT INTO Usuarios(nombreUsuario, contrasenia, TipoUsuario) " +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getContrasenia());
            ps.setString(3, u.getTipoDeUsuario().name()); // enum guardado como texto

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarPasajero(Pasajero p) {
        String sql = "INSERT INTO Pasajeros(DNI, nombre, apellido, telefono, email, nombreUsuario, origen, destino_origen) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Si el usuario ya lo insertás por separado, borrá esta línea
            insertarUsuario(p.getUsuario());

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, p.getDNI());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getEmail());
            ps.setString(6, p.getUsuario().getNombreUsuario());
            ps.setString(7, p.getOrigen());
            ps.setString(8, p.getDestino_origen());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarTecnico(Tecnico t) {
        crearTablaTecnicos();
        String sql = "INSERT INTO Tecnicos(DNI, nombre, apellido, telefono, email, nombreUsuario, tipo_de_tecnico, formacion, disponible) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Si el usuario ya lo insertás por separado, borrá esta línea
            insertarUsuario(t.getUsuario());

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, t.getDNI());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getApellido());
            ps.setString(4, t.getTelefono());
            ps.setString(5, t.getEmail());
            ps.setString(6, t.getUsuario().getNombreUsuario());
            ps.setString(7, t.getTipo_de_tecnico().name()); // enum guardado como texto
            ps.setString(8, t.getFormacion());
            ps.setBoolean(9, t.isDisponible());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarReserva(Reserva r) {
        crearTablaReserva();
        String sql = "INSERT INTO Reserva(idReserva, idPasajero, idHabitacion, cantidadPasajeros, fechaIngreso, fechaSalida) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, r.getIdReserva());
            ps.setString(2, r.getPasajero().getDNI());                // el DNI es la clave de Pasajeros
            ps.setInt(3, r.getHabitacion().getNumeroHabitacion());    // el número es la clave de Habitaciones
            ps.setInt(4, r.getCantidadPasajeros());
            ps.setDate(5, Date.valueOf(r.getFechaIngreso()));         // LocalDate -> java.sql.Date
            ps.setDate(6, Date.valueOf(r.getFechaSalida()));

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void insertarReparacion(Habitacion h, Tecnico t) {
        crearTablaReparaciones();
        String sql = "INSERT INTO Reparaciones(numero_habitacion, dniTecnico) VALUES (?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, h.getNumeroHabitacion());
            ps.setString(2, t.getDNI());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


///MODIFICAR


    ///ELIMINAR

///LISTAR


    

}
