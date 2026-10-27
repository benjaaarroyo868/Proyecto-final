
package Clases;

import Enums.EEstadoMantenimiento;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Exepciones.ElementoDuplicadoException;
import Exepciones.ReservaNoDisponibleException;
import Generico.RegistroGenericoMapas;
import Interfaces.IEmpleado;
import Interfaces.IValidarDatos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Hotel implements IValidarDatos {

    private String nombre;
    private String direccion;
    private int cantidadHabitaciones;
    private int cantidadOcupadas;

    // REGISTROS DEL HOTEL
    private RegistroGenericoMapas<String, IEmpleado> empleados;
    private RegistroGenericoMapas<String, Tecnico> tecnicos;
    private RegistroGenericoMapas<String, Habitacion> habitaciones;
    private RegistroGenericoMapas<String, Pasajero> pasajeros;
    private RegistroGenericoMapas<String, Pasajero> reservas;
    private RegistroGenericoMapas<String, Usuario> usuario;
    private RegistroGenericoMapas<Habitacion, Tecnico> reparaciones;

    // CONSTRUCTOR
    public Hotel(String nombre, String direccion, int cantidadHabitaciones) {

        this.nombre = nombre;
        this.direccion = direccion;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadOcupadas = 0;

        this.empleados = new RegistroGenericoMapas<>();
        this.tecnicos = new RegistroGenericoMapas<>();
        this.habitaciones = new RegistroGenericoMapas<>();
        this.pasajeros = new RegistroGenericoMapas<>();
        this.reservas = new RegistroGenericoMapas<>();
        this.usuario = new RegistroGenericoMapas<>();
        this.reparaciones = new RegistroGenericoMapas<>();
    }

    // GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public void setCantidadHabitaciones(int cantidadHabitaciones) {
        this.cantidadHabitaciones = cantidadHabitaciones;
    }

    public int getCantidadOcupadas() {
        return cantidadOcupadas;
    }

    public void setCantidadOcupadas(int cantidadOcupadas) {
        this.cantidadOcupadas = cantidadOcupadas;
    }

    // GETTERS DE LOS REGISTROS

    public RegistroGenericoMapas<String, IEmpleado> getEmpleados() {
        return empleados;
    }

    public RegistroGenericoMapas<String, Tecnico> getTecnicos() {
        return tecnicos;
    }

    public RegistroGenericoMapas<String, Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public RegistroGenericoMapas<String, Pasajero> getPasajeros() {
        return pasajeros;
    }

    public RegistroGenericoMapas<String, Pasajero> getReservas() {
        return reservas;
    }

    public RegistroGenericoMapas<String, Usuario> getUsuarios() {
        return usuario;
    }

    // ========================================
    //              PASAJEROS
    // ========================================

    // AGREGAR PASAJERO

    public void agregarPasajero(Pasajero pasajero)
            throws AgenciaHotelException {

        if (pasajero == null) {
            throw new DatoInvalidoException(
                    "El pasajero no puede ser nulo");
        }

        if (pasajero.getDNI() == null ||
                !pasajero.getDNI().matches("[0-9]{7,8}")) {

            throw new DatoInvalidoException(
                    "El DNI debe tener 7 u 8 números");
        }

        if (pasajeros.containsKey(pasajero.getDNI())) {
            throw new ElementoDuplicadoException(
                    "Ya existe un pasajero con ese DNI");
        }

        pasajero.validarDatos();

        pasajeros.put(pasajero.getDNI(), pasajero);
    }

    // BUSCAR PASAJERO POR DNI

    public Pasajero buscarPasajero(String dni)
            throws DatoInvalidoException {

        if (dni == null || !dni.matches("[0-9]{7,8}")) {
            throw new DatoInvalidoException(
                    "El DNI debe tener 7 u 8 números");
        }

        return pasajeros.buscar(dni);
    }

    // ELIMINAR PASAJERO POR DNI

    public String eliminarPasajero(String dni)
            throws DatoInvalidoException {

        if (dni == null || !dni.matches("[0-9]{7,8}")) {
            throw new DatoInvalidoException(
                    "El DNI debe tener 7 u 8 números");
        }

        pasajeros.eliminar(dni);

        return "Pasajero eliminado correctamente";
    }

    // MOSTRAR TODOS LOS PASAJEROS

    public Collection<Pasajero> mostrarPasajeros() {
        return pasajeros.obtenerTodos();
    }

    // ========================================
    //               RESERVAS
    // ========================================

    // AGREGAR RESERVA

    public void agregarReserva(Reserva reserva)
            throws AgenciaHotelException {

        if (reserva == null) {
            throw new DatoInvalidoException(
                    "La reserva no puede ser nula");
        }

        if (reservas.containsKey(reserva.getIdReserva())) {
            throw new ElementoDuplicadoException(
                    "La reserva ya existe");
        }

        if (reserva.getHabitacion().isDisponible()) {
            throw new ReservaNoDisponibleException(
                    "La habitación está ocupada");
        }

        reserva.validarDatos();

        reservas.put(
                reserva.getIdReserva(),
                reserva.getPasajero()
        );

        cantidadOcupadas++;
    }

    // ========================================
    //               EMPLEADOS
    // ========================================

    // COMPROBAR SI EXISTE UN EMPLEADO CON ESE DNI

    public boolean existeDniEmpleado(String dni) {

        if (dni == null) {
            return false;
        }

        for (IEmpleado empleado : empleados.obtenerTodos()) {

            if (dni.equals(empleado.getDni())) {
                return true;
            }
        }

        return false;
    }

    // AGREGAR EMPLEADO POR LEGAJO

    public String agregarEmpleado(String legajo, IEmpleado empleado)
            throws AgenciaHotelException {

        if (empleado == null) {
            throw new DatoInvalidoException(
                    "El empleado no puede ser nulo");
        }

        if (legajo == null || legajo.isBlank()) {
            throw new DatoInvalidoException(
                    "El legajo no puede estar vacío");
        }

        legajo = legajo.trim();

        // COMPROBAR LEGAJO DUPLICADO
        if (empleados.containsKey(legajo)) {
            throw new ElementoDuplicadoException(
                    "Ya existe un empleado con ese legajo");
        }

        // VALIDAR DNI COMO DATO PERSONAL
        String dni = empleado.getDni();

        if (dni == null || !dni.matches("[0-9]{7,8}")) {
            throw new DatoInvalidoException(
                    "El DNI debe tener 7 u 8 números");
        }

        // COMPROBAR DNI DUPLICADO
        if (existeDniEmpleado(dni)) {
            throw new ElementoDuplicadoException(
                    "Ya existe un empleado con ese DNI");
        }

        // VALIDAR LOS DATOS SEGÚN EL TIPO
        if (empleado instanceof Administrador) {

            Administrador admin = (Administrador) empleado;
            admin.validarDatos();

        } else if (empleado instanceof Conserje) {

            Conserje conserje = (Conserje) empleado;
            conserje.validarDatos();

        } else {
            throw new DatoInvalidoException(
                    "Tipo de empleado no válido");
        }

        // GUARDAR POR LEGAJO
        empleados.put(legajo, empleado);

        return "Empleado agregado correctamente";
    }

    // BUSCAR EMPLEADO POR LEGAJO

    public IEmpleado buscarEmpleado(String legajo)
            throws DatoInvalidoException {

        if (legajo == null || legajo.isBlank()) {
            throw new DatoInvalidoException(
                    "El legajo no puede estar vacío");
        }

        return empleados.buscar(legajo.trim());
    }

    // ELIMINAR EMPLEADO POR LEGAJO

    public String eliminarEmpleado(String legajo)
            throws DatoInvalidoException {

        if (legajo == null || legajo.isBlank()) {
            throw new DatoInvalidoException(
                    "El legajo no puede estar vacío");
        }

        empleados.eliminar(legajo.trim());

        return "Empleado eliminado correctamente";
    }

    // MOSTRAR TODOS LOS EMPLEADOS

    public Collection<IEmpleado> mostrarEmpleados() {
        return empleados.obtenerTodos();
    }

    // ========================================
    //                USUARIOS
    // ========================================

    // AGREGAR USUARIO

    public String agregarUsuario(Usuario u)
            throws AgenciaHotelException {

        if (u == null) {
            throw new DatoInvalidoException(
                    "El usuario no puede ser nulo");
        }

        u.validarDatos();

        if (usuario.containsKey(u.getNombreUsuario())) {
            throw new ElementoDuplicadoException(
                    "El nombre de usuario ya existe");
        }

        usuario.put(u.getNombreUsuario(), u);

        return "Usuario agregado correctamente";
    }

    // BUSCAR USUARIO

    public Usuario buscarUsuario(String nombreUsuario)
            throws DatoInvalidoException {

        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new DatoInvalidoException(
                    "El nombre de usuario no puede estar vacío");
        }

        return usuario.buscar(nombreUsuario.trim());
    }

    // ELIMINAR USUARIO

    public String eliminarUsuario(String nombreUsuario)
            throws DatoInvalidoException {

        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new DatoInvalidoException(
                    "El nombre de usuario no puede estar vacío");
        }

        usuario.eliminar(nombreUsuario.trim());

        return "Usuario eliminado correctamente";
    }

    // MOSTRAR USUARIOS

    public Collection<Usuario> mostrarUsuarios() {
        return usuario.obtenerTodos();
    }

    // INICIAR SESIÓN

    public Usuario iniciarSesion(String nombre, String contrasenia) {

        if (nombre == null || contrasenia == null) {
            return null;
        }

        Usuario u = usuario.get(nombre);

        if (u != null &&
                u.getContrasenia().equals(contrasenia)) {

            return u;
        }

        return null;
    }

    // ========================================
    //              VER TECNICOS
    // ========================================

    public Collection<Tecnico> verTecnicos() {
        return tecnicos.obtenerTodos();
    }

    // ========================================
    //       HABITACIONES DISPONIBLES
    // ========================================

    public Collection<Habitacion> verHabitacionesDisponibles() {

        Collection<Habitacion> disponibles = new ArrayList<>();

        for (Habitacion h : habitaciones.obtenerTodos()) {
            if (!h.isDisponible()) {
                disponibles.add(h);
            }
        }

        return disponibles;
    }

    // ========================================
    //         HABITACIONES OCUPADAS
    // ========================================

    public Collection<Habitacion> verHabitacionesOcupadas() {

        Collection<Habitacion> ocupadas = new ArrayList<>();

        for (Habitacion h : habitaciones.obtenerTodos()) {
            if (h.isDisponible()) {
                ocupadas.add(h);
            }
        }

        return ocupadas;
    }


// HABITACIONES QUE NECESITAN REPARACIÓN

    public List<Habitacion> verHabitacionesParaReparar() {

        List<Habitacion> lista = new ArrayList<>();

        for (Habitacion h : habitaciones.obtenerTodos()) {
            if (h.necesitaReparacion()) {
                lista.add(h);
            }
        }

        return lista;
    }

// HABITACIONES QUE YA ESTÁN EN REPARACIÓN

    public List<Habitacion> verHabitacionesEnReparacion() {

        List<Habitacion> lista = new ArrayList<>();

        for (Habitacion h : habitaciones.obtenerTodos()) {
            if (h.estaEnReparacion()) {
                lista.add(h);
            }
        }

        return lista;
    }

// INFORMAR QUE UNA HABITACIÓN NECESITA REPARACIÓN

    public void registrarNecesidadReparacion(Habitacion habitacion) {

        if (habitacion == null ||
                !habitaciones.obtenerTodos().contains(habitacion)) {
            throw new IllegalArgumentException(
                    "La habitación no pertenece al hotel");
        }

        if (habitacion.estaEnReparacion()) {
            throw new IllegalStateException(
                    "La habitación ya está en reparación");
        }

        habitacion.setEstadoMantenimiento(
                EEstadoMantenimiento.NECESITA_REPARACION
        );
    }

// ASIGNAR TÉCNICO A UNA HABITACIÓN

    public void asignarTecnico(
            Habitacion habitacion, Tecnico tecnico) {

        if (habitacion == null || tecnico == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una habitación y un técnico");
        }

        if (!habitaciones.obtenerTodos().contains(habitacion)) {
            throw new IllegalArgumentException(
                    "La habitación no pertenece al hotel");
        }

        if (!tecnicos.obtenerTodos().contains(tecnico)) {
            throw new IllegalArgumentException(
                    "El técnico no pertenece al hotel");
        }

        if (!habitacion.necesitaReparacion()) {
            throw new IllegalStateException(
                    "La habitación no está pendiente de reparación");
        }

        if (habitacion.isDisponible()) {
            throw new IllegalStateException(
                    "La habitación está ocupada. Debe liberarse primero");
        }

        reparaciones.put(habitacion, tecnico);

        habitacion.setEstadoMantenimiento(
                EEstadoMantenimiento.EN_REPARACION
        );
    }

// CONSULTAR TÉCNICO ASIGNADO

    public Tecnico obtenerTecnicoAsignado(Habitacion habitacion) {
        return reparaciones.get(habitacion);
    }

// FINALIZAR REPARACIÓN

    public void finalizarReparacion(Habitacion habitacion) {

        if (habitacion == null || !habitacion.estaEnReparacion()) {
            throw new IllegalStateException(
                    "La habitación no está en reparación");
        }

        try {
            reparaciones.eliminar(habitacion);
        } catch (DatoInvalidoException e) {
            System.out.println(e.getMessage());;
        }

        habitacion.setEstadoMantenimiento(
                EEstadoMantenimiento.NORMAL
        );
    }



    // ========================================
    //           VALIDACIONES DEL HOTEL
    // ========================================

    @Override
    public void validarDatos() throws DatoInvalidoException {

        if (nombre == null || nombre.isBlank() ||
                direccion == null || direccion.isBlank()) {

            throw new DatoInvalidoException(
                    "El nombre y la dirección del hotel no pueden estar vacíos");
        }

        if (cantidadHabitaciones <= 0) {
            throw new DatoInvalidoException(
                    "La cantidad de habitaciones debe ser mayor a cero");
        }

        if (cantidadOcupadas < 0 ||
                cantidadOcupadas > cantidadHabitaciones) {

            throw new DatoInvalidoException(
                    "La cantidad de habitaciones ocupadas es inválida");
        }
    }
}
