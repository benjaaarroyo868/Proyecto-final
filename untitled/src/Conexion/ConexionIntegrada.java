
package Conexion;

import Clases.*;
import Enums.EEstadoMantenimiento;
import Interfaces.IEmpleado;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Puente entre GestionBaseDatos y las clases JDBC separadas del proyecto.
 * No cambia los metodos originales del menu ni los de ConexionX.
 */
public final class ConexionIntegrada {
    private final ConexionUsuarios usuarios = new ConexionUsuarios();
    private final ConexionPasajeros pasajeros = new ConexionPasajeros();
    private final ConexionConserjes conserjes = new ConexionConserjes();
    private final ConexionAdministradores administradores = new ConexionAdministradores();
    private final ConexionTecnicos tecnicos = new ConexionTecnicos();
    private final ConexionHabitaciones habitaciones = new ConexionHabitaciones();
    private final ConexionHotel hoteles = new ConexionHotel();
    private final ConexionReservas reservas = new ConexionReservas();
    private final ConexionReparaciones reparaciones = new ConexionReparaciones();
    private final ConexionTablas tablas = new ConexionTablas();

    public void createConnection() {
        ConexionBD inicial = new ConexionBD();
        try {
            inicial.createConnection();
            inicial.crearBaseDatos("HotelConte");
        } finally {
            inicial.closeConnection();
        }
    }

    public void closeConnection() {
        // ConexionBD cierra cada conexion al finalizar su operacion.
    }

    public void crearBaseDatos(String nombre) {
        if (!"HotelConte".equals(nombre))
            throw new IllegalArgumentException("Base de datos no configurada: " + nombre);
    }

    public void usarBaseDatos(String nombre) {
        if (!"HotelConte".equals(nombre))
            throw new IllegalArgumentException("Base de datos no configurada: " + nombre);
    }

    public void crearTablas() {
        tablas.crearTablas();
    }

    public boolean tieneRegistrosPersistidos() {
        return tablas.tieneRegistrosPersistidos();
    }

    public void ejecutarTransaccion(Runnable accion) {
        ConexionBD.ejecutarTransaccion(accion);
    }

    public void insertarUsuario(Usuario u) { usuarios.insertarUsuario(u); }
    public void insertarPasajero(Pasajero p) { pasajeros.insertarPasajero(p); }
    public void insertarConserje(Conserje c) { conserjes.insertarConserje(c); }
    public void insertarAdministrador(Administrador a) { administradores.insertarAdministrador(a); }
    public void insertarTecnico(Tecnico t) { tecnicos.insertarTecnico(t); }
    public void insertarHabitacion(Habitacion h) { habitaciones.insertarHabitacion(h); }
    public void insertarHotel(Hotel h) { hoteles.insertarHotel(h); }
    public void insertarReserva(Reserva r) { reservas.insertarReserva(r); }

    public void insertarReparacion(Habitacion h, Tecnico t) {
        reparaciones.insertarReparacion(h, t);
    }

    public void asegurarHabitacion(Habitacion h) {
        habitaciones.asegurarHabitacion(h);
    }

    public void asegurarPasajero(Pasajero p) {
        pasajeros.asegurarPasajero(p);
    }

    public void actualizarTecnico(Tecnico t) {
        tecnicos.actualizarTecnico(t);
    }

    public void modificarEstadoHabitacion(int numero, boolean disponible, EEstadoMantenimiento estado) {
        habitaciones.modificarEstadoHabitacion(numero, disponible, estado);
    }

    public void modificarFechaIngreso(String id, LocalDate fecha) {
        reservas.modificarFechaIngreso(id, fecha);
    }

    public void modificarFechaSalida(String id, LocalDate fecha) {
        reservas.modificarFechaSalida(id, fecha);
    }

    public void modificarFechasReserva(String id, LocalDate ingreso, LocalDate salida) {
        reservas.modificarFechasReserva(id, ingreso, salida);
    }

    public void modificarNumeroHabitacion(String id, int numero) {
        reservas.modificarNumeroHabitacion(id, numero);
    }

    public void modificarCantidadPersonas(String id, int cantidad) {
        reservas.modificarCantidadPersonas(id, cantidad);
    }

    public void modificarUsuario(String actual, String nuevo, String contrasenia) {
        usuarios.modificarUsuario(actual, nuevo, contrasenia);
    }

    public void eliminarReparacion(Habitacion h) {
        reparaciones.eliminarReparacion(h);
    }

    public void eliminarUsuarioPorNombre(String nombre) {
        usuarios.eliminarUsuarioPorNombre(nombre);
    }

    public void eliminarPasajeroPorDni(String dni) {
        pasajeros.eliminarPasajeroPorDni(dni);
    }

    public void eliminarReservaPorId(String id) {
        reservas.eliminarReservaPorId(id);
    }

    public void eliminarEmpleadoPorTipo(IEmpleado empleado) {
        if (empleado instanceof Administrador)
            administradores.eliminarAdministradorPorDni(((Administrador) empleado).getDNI());
        else if (empleado instanceof Conserje)
            conserjes.eliminarConserjePorDni(((Conserje) empleado).getDNI());
        else
            throw new IllegalArgumentException("Empleado no soportado");
    }

    public HashMap<String, Usuario> listarUsuarios() {
        return usuarios.listarUsuarios();
    }

    public HashMap<String, Pasajero> listarPasajeros(HashMap<String, Usuario> u) {
        return pasajeros.listarPasajeros(u);
    }

    public HashMap<String, Tecnico> listarTecnicos(HashMap<String, Usuario> u) {
        return tecnicos.listarTecnicos(u);
    }

    public HashMap<String, Conserje> listarConserjes(HashMap<String, Usuario> u) {
        return conserjes.listarConserjes(u);
    }

    public HashMap<String, Administrador> listarAdministradores(HashMap<String, Usuario> u) {
        return administradores.listarAdministradores(u);
    }

    public HashMap<String, Habitacion> listarHabitaciones() {
        return habitaciones.listarHabitaciones();
    }

    public HashMap<String, Reserva> listarReservas(
            HashMap<String, Pasajero> p,
            HashMap<String, Habitacion> h) {
        return reservas.listarReservas(p, h);
    }

    public HashMap<Habitacion, Tecnico> listarReparaciones(
            HashMap<String, Habitacion> h,
            HashMap<String, Tecnico> t) {
        return reparaciones.listarReparaciones(h, t);
    }

    public Hotel listarHotel() {
        return hoteles.listarHotel();
    }
}
