package Menu;

import Clases.*;
import Conexion.Conexion;
import Interfaces.IEmpleado;
import java.util.HashMap;
import java.util.Map;

import static Menu.ConsolaHotel.*;
import static Menu.ValidacionesMenu.*;

/** Integra JDBC con el menú sin trasladar consultas SQL a las pantallas. */
public class GestionBaseDatos {
    private static Conexion conexion;
    private static boolean cargaInicial = false;

    private static boolean conectar() {
        if (conexion != null) return true;
        try {
            Conexion nueva = new Conexion();
            nueva.createConnection();
            nueva.crearBaseDatos("HotelConte");
            nueva.usarBaseDatos("HotelConte");
            nueva.crearTablas();
            conexion = nueva;
            return true;
        } catch (RuntimeException e) {
            throw new IllegalStateException("Base de datos no disponible: " + e.getMessage(), e);
        }
    }

    /** Se ejecuta antes de mostrar el primer menú; no reemplaza los datos ya cargados. */
    public static void iniciar(Hotel hotel) {
        if (cargaInicial) return;
        conectar();
        // Sembrar Main solamente si la base SQL está completamente vacía.
        if (!conexion.tieneRegistrosPersistidos()) {
            insertarTodos(hotel);
        } else if (conexion.listarHotel() == null) {
            // La BD ya contiene objetos: NO reinsertar ni sobrescribirlos.
            // Solo completa los datos generales del Hotel si todavía no existen.
            conexion.insertarHotel(hotel);
        }
        // MySQL es la fuente de verdad: sus instancias reemplazan las
        // precargadas con la misma clave y se recuperan las reparaciones.
        cargarDesdeBaseDatos(hotel);
        cargaInicial = true;
    }

    public static void cerrar() {
        if (conexion != null) {
            conexion.closeConnection();
            conexion = null;
        }
        cargaInicial = false;
    }

    // Guardado automático después de que el objeto pasa las validaciones originales.
    public static void guardarUsuario(Usuario u) {
        conectar();
        conexion.insertarUsuarioNuevo(u);
    }

    public static void guardarPasajero(Pasajero p) {
        conectar();
        conexion.ejecutarTransaccion(() -> conexion.insertarPasajeroNuevo(p));
    }

    public static void guardarEmpleado(IEmpleado e) {
        conectar();
        conexion.ejecutarTransaccion(() -> {
            conexion.insertarEmpleadoNuevo(e);
        });
    }

    /** Para habitaciones nuevas agregadas por Java fuera del menú. */
    public static void guardarHabitacion(Habitacion habitacion) {
        conectar();
        conexion.insertarHabitacion(habitacion);
    }

    /** Para técnicos nuevos agregados por Java fuera del menú. */
    public static void guardarTecnico(Tecnico tecnico) {
        conectar();
        conexion.ejecutarTransaccion(() -> conexion.insertarTecnico(tecnico));
    }

    public static void guardarReserva(Reserva r) {
        conectar();
        conexion.ejecutarTransaccion(() -> {
            // Las claves foráneas deben existir; no volver a insertar las ya guardadas.
            conexion.asegurarHabitacion(r.getHabitacion());
            conexion.asegurarPasajero(r.getPasajero());
            conexion.insertarReservaNueva(r);
        });
    }

    // Las cinco modificaciones de reservas usan los métodos de la última Conexion.java.
    // En el menú, si SQL falla, se restaura el valor anterior del objeto Java.
    public static void modificarFechaIngreso(String id, java.time.LocalDate fecha) {
        conectar();
        conexion.modificarFechaIngreso(id, fecha);
    }

    public static void modificarFechaSalida(String id, java.time.LocalDate fecha) {
        conectar();
        conexion.modificarFechaSalida(id, fecha);
    }

    public static void modificarFechasReserva(String id,
            java.time.LocalDate ingreso, java.time.LocalDate salida) {
        conectar();
        conexion.modificarFechasReserva(id, ingreso, salida);
    }

    public static void modificarCantidadPersonas(String id, int cantidad) {
        conectar();
        conexion.modificarCantidadPersonas(id, cantidad);
    }

    public static void modificarNumeroHabitacion(String id, int numeroHabitacion) {
        conectar();
        conexion.modificarNumeroHabitacion(id, numeroHabitacion);
    }

    public static void eliminarUsuario(String nombre) {
        conectar();
        conexion.eliminarUsuarioPorNombre(nombre);
    }

    public static void eliminarPasajero(String dni) {
        conectar();
        conexion.eliminarPasajeroPorDni(dni);
    }

    public static void eliminarEmpleado(IEmpleado e) {
        conectar();
        conexion.eliminarEmpleadoPorTipo(e);
    }

    public static void eliminarReserva(String id) {
        conectar();
        conexion.eliminarReservaPorId(id);
    }

    public static void guardarReparacion(Habitacion h, Tecnico t) {
        conectar();
        conexion.ejecutarTransaccion(() -> {
            conexion.modificarEstadoHabitacion(h.getNumeroHabitacion(),
                    h.isDisponible(), h.getEstadoMantenimiento());
            conexion.actualizarTecnico(t);
            conexion.insertarReparacion(h, t);
        });
    }

    public static void guardarEstadoHabitacion(Habitacion h) {
        conectar();
        conexion.modificarEstadoHabitacion(h.getNumeroHabitacion(),
                h.isDisponible(), h.getEstadoMantenimiento());
    }

    public static void finalizarReparacion(Habitacion h, Tecnico t) {
        conectar();
        conexion.ejecutarTransaccion(() -> {
            conexion.eliminarReparacion(h);
            conexion.modificarEstadoHabitacion(h.getNumeroHabitacion(),
                    h.isDisponible(), h.getEstadoMantenimiento());
            if (t != null) conexion.actualizarTecnico(t);
        });
    }

    /** Inserta los registros actuales, en orden compatible con las claves foráneas. */
    public static void insertarTodos(Hotel hotel) {
        conectar();
        conexion.ejecutarTransaccion(() -> {
            for (Usuario u : hotel.mostrarUsuarios()) conexion.insertarUsuarioNuevo(u);
            for (Habitacion h : hotel.getHabitaciones().obtenerTodos()) conexion.insertarHabitacion(h);
            for (Tecnico t : hotel.verTecnicos()) conexion.insertarTecnico(t);
            for (IEmpleado e : hotel.mostrarEmpleados()) {
                if (e instanceof Administrador) conexion.insertarAdministrador((Administrador) e);
                else if (e instanceof Conserje) conexion.insertarConserje((Conserje) e);
            }
            for (Pasajero p : hotel.mostrarPasajeros()) conexion.insertarPasajero(p);
            for (Reserva r : hotel.mostrarReservas()) conexion.insertarReserva(r);
            for (Habitacion h : hotel.verHabitacionesEnReparacion()) {
                Tecnico t = hotel.obtenerTecnicoAsignado(h);
                if (t != null) conexion.insertarReparacion(h, t);
            }
            conexion.insertarHotel(hotel);
        });
    }

    /**
     * Reconstruye los mapas con los objetos guardados en MySQL.
     * El orden respeta las relaciones entre tablas (usuarios antes de pasajeros,
     * habitaciones antes de reservas). No llama a los métodos de alta del menú,
     * ya que esos ejecutarían otra vez INSERT SQL y sus validaciones de nuevo alta.
     */
    public static void cargarDesdeBaseDatos(Hotel hotel) {
        conectar();
        if (hotel == null) throw new IllegalArgumentException("El hotel no puede ser nulo");

        HashMap<String, Usuario> usuarios = conexion.listarUsuarios();
        HashMap<String, Habitacion> habitaciones = conexion.listarHabitaciones();
        HashMap<String, Pasajero> pasajeros = conexion.listarPasajeros(usuarios);
        HashMap<String, Tecnico> tecnicos = conexion.listarTecnicos(usuarios);
        HashMap<String, Administrador> admins = conexion.listarAdministradores(usuarios);
        HashMap<String, Conserje> conserjes = conexion.listarConserjes(usuarios);
        HashMap<String, Reserva> reservas = conexion.listarReservas(pasajeros, habitaciones);
        HashMap<Habitacion, Tecnico> reparaciones =
                conexion.listarReparaciones(habitaciones, tecnicos);
        Hotel hotelGuardado = conexion.listarHotel();

        // Evitar que se mezclen objetos temporales de Main con filas de MySQL.
        // Los mapas se reconstruyen únicamente después de completar los SELECT.
        hotel.limpiarRegistrosParaRecarga();
        for (Usuario u : usuarios.values()) {
            hotel.getUsuarios().put(u.getNombreUsuario(), u);
        }
        for (Habitacion h : habitaciones.values()) {
            hotel.getHabitaciones().put(String.valueOf(h.getNumeroHabitacion()), h);
        }
        for (Pasajero p : pasajeros.values()) {
            hotel.getPasajeros().put(p.getDNI(), p);
        }
        for (Tecnico t : tecnicos.values()) {
            hotel.getTecnicos().put(t.getDNI(), t);
        }
        for (Administrador a : admins.values()) {
            hotel.getEmpleados().put(a.getLegajo(), a);
        }
        for (Conserje c : conserjes.values()) {
            hotel.getEmpleados().put(c.getLegajo(), c);
        }
        for (Reserva r : reservas.values()) {
            hotel.getReservas().put(r.getIdReserva(), r);
        }

        // Recupera también la relación Habitación -> Técnico, usando las
        // instancias canónicas de los mapas del Hotel.
        hotel.restaurarReparaciones(reparaciones);

        if (hotelGuardado != null) {
            hotel.setNombre(hotelGuardado.getNombre());
            hotel.setDireccion(hotelGuardado.getDireccion());
            hotel.setCantidadHabitaciones(hotelGuardado.getCantidadHabitaciones());
            hotel.setCantidadOcupadas(hotelGuardado.getCantidadOcupadas());
        }

        System.out.println(VERDE + "Se recuperaron de MySQL: "
                + usuarios.size() + " usuarios, "
                + pasajeros.size() + " pasajeros, "
                + (admins.size() + conserjes.size()) + " empleados, "
                + tecnicos.size() + " técnicos, "
                + habitaciones.size() + " habitaciones, "
                + reservas.size() + " reservas y "
                + reparaciones.size() + " reparaciones." + RESET);
    }

    /** Lee realmente las tablas SQL; no se limita a mostrar las colecciones en memoria. */
    public static void listarDesdeBaseDatos() {
        if (!conectar()) return;
        HashMap<String, Usuario> usuarios = conexion.listarUsuarios();
        HashMap<String, Habitacion> habitaciones = conexion.listarHabitaciones();
        HashMap<String, Pasajero> pasajeros = conexion.listarPasajeros(usuarios);
        HashMap<String, Tecnico> tecnicos = conexion.listarTecnicos(usuarios);
        HashMap<String, Administrador> admins = conexion.listarAdministradores(usuarios);
        HashMap<String, Conserje> conserjes = conexion.listarConserjes(usuarios);
        HashMap<String, Reserva> reservas = conexion.listarReservas(pasajeros, habitaciones);
        HashMap<Habitacion, Tecnico> reparaciones = conexion.listarReparaciones(habitaciones, tecnicos);
        Hotel hotelBD = conexion.listarHotel();

        System.out.println(CYAN + "\n════════════ DATOS EN MYSQL ════════════" + RESET);
        System.out.println(AZUL + "USUARIOS (" + usuarios.size() + ")" + RESET);
        for (Usuario u : usuarios.values())
            System.out.println("  " + u.getNombreUsuario() + " | " + u.getTipoDeUsuario());

        System.out.println(AZUL + "PASAJEROS (" + pasajeros.size() + ")" + RESET);
        for (Pasajero p : pasajeros.values())
            System.out.println("  " + p.getDNI() + " | " + p.getNombre() + " " + p.getApellido());

        System.out.println(AZUL + "EMPLEADOS (" + (admins.size() + conserjes.size()) + ")" + RESET);
        for (Administrador a : admins.values()) System.out.println("  Admin: " + a.getLegajo() + " | " + a.getNombre());
        for (Conserje c : conserjes.values()) System.out.println("  Conserje: " + c.getLegajo() + " | " + c.getNombre());

        System.out.println(AZUL + "TÉCNICOS (" + tecnicos.size() + ")" + RESET);
        for (Tecnico t : tecnicos.values()) System.out.println("  " + t.getDNI() + " | " + t.getNombre());

        System.out.println(AZUL + "HABITACIONES (" + habitaciones.size() + ")" + RESET);
        for (Habitacion h : habitaciones.values())
            System.out.println("  " + h.getNumeroHabitacion() + " | " + h.getEstadoMantenimiento());

        System.out.println(AZUL + "RESERVAS (" + reservas.size() + ")" + RESET);
        for (Reserva r : reservas.values()) System.out.println("  " + r.getIdReserva()
                + " | Hab " + r.getHabitacion().getNumeroHabitacion()
                + " | Pasajeros: " + r.getCantidadPasajeros()
                + " | " + r.getFechaIngreso() + " a " + r.getFechaSalida());

        System.out.println(AZUL + "REPARACIONES (" + reparaciones.size() + ")" + RESET);
        for (Map.Entry<Habitacion, Tecnico> e : reparaciones.entrySet())
            System.out.println("  Hab " + e.getKey().getNumeroHabitacion() + " | Técnico " + e.getValue().getNombre());

        if (hotelBD != null) System.out.println(AZUL + "HOTEL: " + hotelBD.getNombre() + " | " + hotelBD.getDireccion() + RESET);
        System.out.println(CYAN + "══════════════════════════════════════════" + RESET);
    }


}
