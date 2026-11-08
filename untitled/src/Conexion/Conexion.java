package Conexion;

import Clases.*;
import Enums.EEstadoMantenimiento;
import Enums.ETipoTecnico;
import Enums.ETipoUsuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    Connection conn;

    public void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "winterfa", "Mecortaronelfrenilloen2024");

            System.out.println("Conexion establecida con exito");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("error al correr" + e);
        } catch (SQLException e) {
            throw new RuntimeException("error al correr sql" + e);
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearBaseDatos (String nombre){
        String sql = "CREATE DATABASE IF NOT EXISTS "+nombre;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la base de datos" +e);
        }
    }

    public void usarBaseDatos (String nombre){
        String sql = "USE "+nombre;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al usar la base de datos"+e);
        }
    }


    //CREACION TABLAS

    // Llamar una sola vez despues de createConnection().
    // El orden importa: las tablas con clave foranea van despues de las que referencian.
    public void crearTablas() {
        crearTablaUsuario();
        crearTablaTecnicos();
        crearTablaConserje();
        crearTablaAdministrador();
        crearTablaHabitacion();
        crearTablaHotel();
        crearTablaPasajeros();
        crearTablaReserva();
        crearTablaReparaciones();
    }

    public void crearTablaUsuario() {

        String sql = """
                    CREATE TABLE IF NOT EXISTS Usuarios(
                        nombreUsuario VARCHAR(30) NOT NULL PRIMARY KEY,
                        contrasenia VARCHAR(30) NOT NULL,
                        TipoUsuario VARCHAR(30)
                    )
                    """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearTablaTecnicos() {

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
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearTablaConserje() {
        String sql = "CREATE TABLE IF NOT EXISTS Conserjes(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100), " +
                "apellido VARCHAR(100), " +
                "dni VARCHAR(20) UNIQUE, " +
                "telefono VARCHAR(20), " +
                "email VARCHAR(100), " +
                "nombreUsuario VARCHAR(30), " +
                "sueldoPorVenta DOUBLE, " +
                "legajo VARCHAR(30), " +
                "FOREIGN KEY (nombreUsuario) REFERENCES Usuarios(nombreUsuario))";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void crearTablaHabitacion() {
        String sql = "CREATE TABLE IF NOT EXISTS Habitaciones(" +
                "numero_habitacion INT PRIMARY KEY, " +
                "cantidad_camas INT, " +
                "cantidad_maxima_personas INT, " +
                "estado BOOLEAN, " +
                "estadoMantenimiento VARCHAR(30))";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void crearTablaAdministrador() {
        String sql = "CREATE TABLE IF NOT EXISTS Administradores(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100), " +
                "apellido VARCHAR(100), " +
                "dni VARCHAR(20) UNIQUE, " +
                "telefono VARCHAR(20), " +
                "email VARCHAR(100), " +
                "nombreUsuario VARCHAR(30), " +
                "legajo VARCHAR(50), " +
                "sueldo DOUBLE, " +
                "FOREIGN KEY (nombreUsuario) REFERENCES Usuarios(nombreUsuario))";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void crearTablaHotel() {
        String sql = "CREATE TABLE IF NOT EXISTS Hotel(" +
                "id INT AUTO_INCREMENT PRIMARY KEY not null, " +
                "nombre VARCHAR(100) not null, " +
                "direccion VARCHAR(200)not null, " +
                "cantidad_habitaciones INT not null,"+
                "habitaciones_ocupadas INT not null";


        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void crearTablaPasajeros() {

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
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
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

    public void insertarUsuario(Usuario u) {
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
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarConserje(Conserje c) {
        String sql = "INSERT INTO Conserjes(nombre, apellido, dni, telefono, email, nombreUsuario, sueldoPorVenta, legajo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Si el usuario ya lo insertas por separado, borra esta linea
            insertarUsuario(c.getUsuario());

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getDNI());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getUsuario().getNombreUsuario());
            ps.setDouble(7, c.getSueldoPorVenta());
            ps.setString(8, c.getLegajo());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarAdministrador(Administrador administrador) {
        String sql = "INSERT INTO Administradores(nombre, apellido, dni, telefono, email, nombreUsuario, legajo, sueldo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Si el usuario ya lo insertas por separado, borra esta linea
            insertarUsuario(administrador.getUsuario());

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
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void insertarHabitacion(Habitacion h) {
        String sql = "INSERT INTO Habitaciones(numero_habitacion, cantidad_camas, cantidad_maxima_personas, estado, estadoMantenimiento) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, h.getNumeroHabitacion());
            ps.setInt(2, h.getCantidadCamas());
            ps.setInt(3, h.getCantidadMaximaPersonas());
            ps.setBoolean(4, h.isDisponible());
            ps.setString(5, h.getEstadoMantenimiento().name()); // enum guardado como texto

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarHotel(Hotel h) {
        String sql = "INSERT INTO Hotel(nombre, direccion, cantidad_habitaciones,CantidadOcupadas) " +
                "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, h.getNombre());
            ps.setString(2, h.getDireccion());
            ps.setInt(3, h.getCantidadHabitaciones());
            ps.setInt(4,h.getCantidadOcupadas());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarPasajero(Pasajero p) {
        String sql = "INSERT INTO Pasajeros(DNI, nombre, apellido, telefono, email, nombreUsuario, origen, destino_origen) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Si el usuario ya lo insertas por separado, borra esta linea
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
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarTecnico(Tecnico t) {
        String sql = "INSERT INTO Tecnicos(DNI, nombre, apellido, telefono, email, nombreUsuario, tipo_de_tecnico, formacion, disponible) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Si el usuario ya lo insertas por separado, borra esta linea
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
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarReserva(Reserva r) {
        String sql = "INSERT INTO Reserva(idReserva, idPasajero, idHabitacion, cantidadPasajeros, fechaIngreso, fechaSalida) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, r.getIdReserva());
            ps.setString(2, r.getPasajero().getDNI());                // el DNI es la clave de Pasajeros
            ps.setInt(3, r.getHabitacion().getNumeroHabitacion());    // el numero es la clave de Habitaciones
            ps.setInt(4, r.getCantidadPasajeros());
            ps.setDate(5, Date.valueOf(r.getFechaIngreso()));         // LocalDate -> java.sql.Date
            ps.setDate(6, Date.valueOf(r.getFechaSalida()));

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarReparacion(Habitacion h, Tecnico t) {
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


    // MODIFICAR ESTADO DE HABITACION
    public void modificarEstadoHabitacion(int numeroHabitacion, boolean disponible, EEstadoMantenimiento estadoMantenimiento) {
        String sql = "UPDATE Habitaciones SET estado = ?, estadoMantenimiento = ? WHERE numero_habitacion = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setBoolean(1, disponible);
            ps.setString(2, estadoMantenimiento.name());
            ps.setInt(3, numeroHabitacion);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MODIFICAR USUARIO
    public void modificarUsuario(String nombreUsuario, String nuevoNombreUsuario, String nuevaContrasenia) {
        String sql = "UPDATE Usuarios SET nombreUsuario = ?, contrasenia = ? WHERE nombreUsuario = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nuevoNombreUsuario);
            ps.setString(2, nuevaContrasenia);
            ps.setString(3, nombreUsuario);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MODIFICAR FECHA DE INGRESO
    public void modificarFechaIngreso(String idReserva, java.time.LocalDate nuevaFechaIngreso) {
        String sql = "UPDATE Reserva SET fechaIngreso = ? WHERE idReserva = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(nuevaFechaIngreso));
            ps.setString(2, idReserva);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MODIFICAR FECHA DE SALIDA
    public void modificarFechaSalida(String idReserva, java.time.LocalDate nuevaFechaSalida) {
        String sql = "UPDATE Reserva SET fechaSalida = ? WHERE idReserva = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(nuevaFechaSalida));
            ps.setString(2, idReserva);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MODIFICAR AMBAS FECHAS
    public void modificarFechasReserva(String idReserva, java.time.LocalDate nuevaFechaIngreso, java.time.LocalDate nuevaFechaSalida) {
        String sql = "UPDATE Reserva SET fechaIngreso = ?, fechaSalida = ? WHERE idReserva = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(nuevaFechaIngreso));
            ps.setDate(2, Date.valueOf(nuevaFechaSalida));
            ps.setString(3, idReserva);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MODIFICAR NUMERO DE HABITACION
    public void modificarNumeroHabitacion(String idReserva, int nuevoNumeroHabitacion) {
        String sql = "UPDATE Reserva SET idHabitacion = ? WHERE idReserva = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, nuevoNumeroHabitacion);
            ps.setString(2, idReserva);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MODIFICAR CANTIDAD DE PERSONAS
    public void modificarCantidadPersonas(String idReserva, int nuevaCantidadPersonas) {
        String sql = "UPDATE Reserva SET cantidadPasajeros = ? WHERE idReserva = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, nuevaCantidadPersonas);
            ps.setString(2, idReserva);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ///ELIMINAR

    // Cuando se termina una reparacion hay que sacarla de la tabla
    public void eliminarReparacion(Habitacion h) {
        String sql = "DELETE FROM Reparaciones WHERE numero_habitacion = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, h.getNumeroHabitacion());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    ///LISTAR

    // Orden para cargar (unos dependen de otros):
    //   HashMap<String, Usuario> usuarios = listarUsuarios();
    //   HashMap<String, Pasajero> pasajeros = listarPasajeros(usuarios);
    //   HashMap<String, Tecnico> tecnicos = listarTecnicos(usuarios);
    //   HashMap<String, Conserje> conserjes = listarConserjes(usuarios);
    //   HashMap<String, Administrador> administradores = listarAdministradores(usuarios);
    //   HashMap<String, Habitacion> habitaciones = listarHabitaciones();
    //   HashMap<String, Reserva> reservas = listarReservas(pasajeros, habitaciones);
    //   HashMap<Habitacion, Tecnico> reparaciones = listarReparaciones(habitaciones, tecnicos);
    //   Hotel hotel = listarHotel();

    public HashMap<String, Usuario> listarUsuarios() {
        HashMap<String, Usuario> mapa = new HashMap<>();
        String sql = "SELECT nombreUsuario, contrasenia, TipoUsuario FROM Usuarios";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("nombreUsuario"),
                        rs.getString("contrasenia"),
                        ETipoUsuario.valueOf(rs.getString("TipoUsuario")) // varchar -> enum
                );
                mapa.put(u.getNombreUsuario(), u);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapa;
    }

    public HashMap<String, Pasajero> listarPasajeros(HashMap<String, Usuario> usuarios) {
        HashMap<String, Pasajero> mapa = new HashMap<>();
        String sql = "SELECT DNI, nombre, apellido, telefono, email, nombreUsuario, origen, destino_origen FROM Pasajeros";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pasajero p = new Pasajero(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        usuarios.get(rs.getString("nombreUsuario")),
                        rs.getString("origen"),
                        rs.getString("destino_origen")
                );
                mapa.put(p.getDNI(), p);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapa;
    }

    public HashMap<String, Tecnico> listarTecnicos(HashMap<String, Usuario> usuarios) {
        HashMap<String, Tecnico> mapa = new HashMap<>();
        String sql = "SELECT DNI, nombre, apellido, telefono, email, nombreUsuario, tipo_de_tecnico, formacion, disponible FROM Tecnicos";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tecnico t = new Tecnico(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        usuarios.get(rs.getString("nombreUsuario")),
                        ETipoTecnico.valueOf(rs.getString("tipo_de_tecnico")), // varchar -> enum
                        rs.getString("formacion"),
                        rs.getBoolean("disponible")
                );
                mapa.put(t.getDNI(), t);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapa;
    }

    public HashMap<String, Conserje> listarConserjes(HashMap<String, Usuario> usuarios) {
        HashMap<String, Conserje> mapa = new HashMap<>();
        String sql = "SELECT nombre, apellido, dni, telefono, email, nombreUsuario, legajo, sueldoPorVenta FROM Conserjes";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Conserje c = new Conserje(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        usuarios.get(rs.getString("nombreUsuario")),
                        rs.getString("legajo"),
                        rs.getDouble("sueldoPorVenta")
                );
                mapa.put(c.getDNI(), c);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapa;
    }

    public HashMap<String, Administrador> listarAdministradores(HashMap<String, Usuario> usuarios) {
        HashMap<String, Administrador> mapa = new HashMap<>();
        String sql = "SELECT nombre, apellido, dni, telefono, email, nombreUsuario, legajo, sueldo FROM Administradores";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Administrador a = new Administrador(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        usuarios.get(rs.getString("nombreUsuario")),
                        rs.getString("legajo"),
                        rs.getDouble("sueldo")
                );
                mapa.put(a.getDNI(), a);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapa;
    }

    public HashMap<String, Habitacion> listarHabitaciones() {
        HashMap<String, Habitacion> mapa = new HashMap<>();
        String sql = "SELECT numero_habitacion, cantidad_camas, cantidad_maxima_personas, estado, estadoMantenimiento FROM Habitaciones";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Habitacion h = new Habitacion(
                        rs.getInt("numero_habitacion"),
                        rs.getInt("cantidad_camas"),
                        rs.getInt("cantidad_maxima_personas")
                );
                h.setDisponible(rs.getBoolean("estado"));
                h.setEstadoMantenimiento(EEstadoMantenimiento.valueOf(rs.getString("estadoMantenimiento"))); // varchar -> enum
                mapa.put(String.valueOf(h.getNumeroHabitacion()), h);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapa;
    }

    public HashMap<String, Reserva> listarReservas(HashMap<String, Pasajero> pasajeros,
                                                   HashMap<String, Habitacion> habitaciones) {
        HashMap<String, Reserva> mapa = new HashMap<>();
        String sql = "SELECT idReserva, idPasajero, idHabitacion, cantidadPasajeros, fechaIngreso, fechaSalida FROM Reserva";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pasajero p = pasajeros.get(rs.getString("idPasajero"));
                Habitacion h = habitaciones.get(String.valueOf(rs.getInt("idHabitacion")));

                if (p == null || h == null) {
                    continue; // reserva sin pasajero o habitacion cargados: se omite
                }

                Reserva r = new Reserva(
                        p,
                        h,
                        rs.getInt("cantidadPasajeros"),
                        rs.getDate("fechaIngreso").toLocalDate(),  // java.sql.Date -> LocalDate
                        rs.getDate("fechaSalida").toLocalDate()
                );
                r.setIdReserva(rs.getString("idReserva")); // el constructor genera un id nuevo, hay que pisarlo
                mapa.put(r.getIdReserva(), r);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapa;
    }

    public HashMap<Habitacion, Tecnico> listarReparaciones(HashMap<String, Habitacion> habitaciones,
                                                           HashMap<String, Tecnico> tecnicos) {
        HashMap<Habitacion, Tecnico> mapa = new HashMap<>();
        String sql = "SELECT numero_habitacion, dniTecnico FROM Reparaciones";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Habitacion h = habitaciones.get(String.valueOf(rs.getInt("numero_habitacion")));
                Tecnico t = tecnicos.get(rs.getString("dniTecnico"));

                if (h != null && t != null) {
                    mapa.put(h, t);
                }
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapa;
    }

    public Hotel listarHotel() {
        Hotel hotel = null;
        String sql = "SELECT nombre, direccion, cantidad_habitaciones FROM Hotel LIMIT 1";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                hotel = new Hotel(
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getInt("cantidad_habitaciones")

                );
                hotel.setCantidadOcupadas(rs.getInt(4));
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hotel;
    }

}