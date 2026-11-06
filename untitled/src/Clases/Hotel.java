
package Clases;

import Enums.EEstadoMantenimiento;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Exepciones.ElementoDuplicadoException;
import Exepciones.ReservaNoDisponibleException;
import Generico.RegistroGenericoMapas;
import Interfaces.IEmpleado;
import Interfaces.IValidarDatos;

import java.time.LocalDate;
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
    private RegistroGenericoMapas<String, Reserva> reservas;
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

    public RegistroGenericoMapas<String, Reserva> getReservas() {
        return reservas;
    }

    public RegistroGenericoMapas<String, Usuario> getUsuarios() {
        return usuario;
    }


    // ========================================
    //                TECNICOS
    // ========================================


    public String agregarTecnico( Tecnico tecnico)
            throws AgenciaHotelException {

        if (tecnico == null) {
            throw new DatoInvalidoException(
                    "El técnico no puede ser nulo");
        }

        // VALIDAR LOS DATOS DEL TÉCNICO


        // GUARDAR EN EL REGISTRO DEL HOTEL
        tecnicos.put(tecnico.getDNI(), tecnico);

        return "Técnico agregado correctamente";
    }


    // ========================================
    // BUSCAR TECNICO POR DNI
    // ========================================

    public Tecnico buscarTecnico(String dni)
            throws DatoInvalidoException {

        if (dni == null || !dni.matches("[0-9]{7,8}")) {
            throw new DatoInvalidoException(
                    "El DNI debe tener 7 u 8 números");
        }

        return tecnicos.buscar(dni);
    }

    // ========================================
    // ELIMINAR TECNICO POR DNI
    // ========================================

    public String eliminarTecnico(String dni)
            throws DatoInvalidoException {

        if (dni == null || !dni.matches("[0-9]{7,8}")) {
            throw new DatoInvalidoException(
                    "El DNI debe tener 7 u 8 números");
        }

        if (!tecnicos.containsKey(dni)) {
            throw new DatoInvalidoException(
                    "No existe un técnico con ese DNI");
        }

        // NO ELIMINAR SI TIENE REPARACIONES ASIGNADAS
        Tecnico tecnico = tecnicos.buscar(dni);

        for (Habitacion habitacion : habitaciones.obtenerTodos()) {

            if (tecnico.equals(reparaciones.get(habitacion))) {
                throw new DatoInvalidoException(
                        "El técnico tiene una reparación asignada");
            }
        }

        tecnicos.eliminar(dni);

        return "Técnico eliminado correctamente";
    }

    // ========================================
    // MOSTRAR TODOS LOS TECNICOS
    // ========================================

    public Collection<Tecnico> mostrarTecnicos() {

        return tecnicos.obtenerTodos();
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

    public boolean agregarReserva(Reserva reserva) {

        try {
            if (reserva == null) {
                throw new DatoInvalidoException(
                        "La reserva no puede ser nula");
            }

            reserva.validarDatos();

            if (reservas.containsKey(reserva.getIdReserva())) {
                throw new ElementoDuplicadoException(
                        "Ya existe una reserva con ese ID");
            }

            if (!habitacionDisponible(
                    reserva.getHabitacion(),
                    reserva.getFechaIngreso(),
                    reserva.getFechaSalida())) {

                throw new ReservaNoDisponibleException(
                        "La habitación ya está reservada en esas fechas");
            }

            reservas.put(reserva.getIdReserva(), reserva);
            return true;

        } catch (AgenciaHotelException e) {
            return false;
        }
    }


    // ========================================
    //       PRÓXIMA FECHA DISPONIBLE
    // ========================================

    public LocalDate proximaFechaDisponible(
            Habitacion habitacion,
            LocalDate fechaIngreso,
            LocalDate fechaSalida) {

        // VALIDAR DATOS
        if (habitacion == null ||
                fechaIngreso == null ||
                fechaSalida == null ||
                !fechaIngreso.isBefore(fechaSalida)) {
            return null;
        }

        // CALCULAR CANTIDAD DE NOCHES
        long cantidadNoches =
                java.time.temporal.ChronoUnit.DAYS.between(
                        fechaIngreso, fechaSalida);

        LocalDate proximaFecha = fechaIngreso;

        // BUSCAR UN PERÍODO SIN RESERVAS SUPERPUESTAS
        while (true) {

            LocalDate proximaSalida =
                    proximaFecha.plusDays(cantidadNoches);

            boolean haySuperposicion = false;

            for (Reserva reserva : reservas.obtenerTodos()) {

                // COMPROBAR QUE SEA LA MISMA HABITACIÓN
                if (reserva.getHabitacion()
                        .getNumeroHabitacion() ==
                        habitacion.getNumeroHabitacion()) {

                    LocalDate ingresoReservado =
                            reserva.getFechaIngreso();

                    LocalDate salidaReservada =
                            reserva.getFechaSalida();

                    // COMPROBAR SUPERPOSICIÓN
                    if (proximaFecha.isBefore(salidaReservada) &&
                            proximaSalida.isAfter(ingresoReservado)) {

                        // PROBAR DESDE LA SALIDA DE ESA RESERVA
                        proximaFecha = salidaReservada;

                        haySuperposicion = true;
                        break;
                    }
                }
            }

            // SI NO HAY SUPERPOSICIÓN, DEVOLVER FECHA
            if (!haySuperposicion) {
                return proximaFecha;
            }
        }
    }


    // VALIDAR FECHAS
    public boolean habitacionDisponible(
            Habitacion habitacion,
            LocalDate nuevaEntrada,
            LocalDate nuevaSalida) {

        // Validar fechas
        if (habitacion == null ||
                nuevaEntrada == null ||
                nuevaSalida == null ||
                !nuevaEntrada.isBefore(nuevaSalida)) {

            return false;
        }

        // Recorrer todas las reservas existentes
        for (Reserva reserva : reservas.obtenerTodos()) {

            // Comprobar si es la misma habitación
            if (reserva.getHabitacion().equals(habitacion)) {

                LocalDate entradaExistente =
                        reserva.getFechaIngreso();

                LocalDate salidaExistente =
                        reserva.getFechaSalida();

                // Comprobar si se superponen
                boolean seSuperponen =
                        nuevaEntrada.isBefore(salidaExistente) &&
                                nuevaSalida.isAfter(entradaExistente);

                if (seSuperponen) {
                    return false;
                }
            }
        }

        return true;
    }

    public Reserva buscarReserva(String idReserva) {

        try {
            if (idReserva == null || idReserva.isBlank()) {
                throw new DatoInvalidoException(
                        "El ID de la reserva no puede estar vacío");
            }

            Reserva reserva = reservas.buscar(idReserva);

            return reserva;

        } catch (AgenciaHotelException e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    public boolean eliminarReserva(String idReserva) {

        try {
            if (idReserva == null || idReserva.isBlank()) {
                throw new DatoInvalidoException(
                        "El ID de la reserva no puede estar vacío");
            }

            reservas.eliminar(idReserva);

            return true;

        } catch (AgenciaHotelException e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    public Collection<Reserva> mostrarReservas() {
        return reservas.obtenerTodos();
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



    public String agregarEmpleado(IEmpleado empleado)
            throws AgenciaHotelException {

        // VALIDAR EMPLEADO
        if (empleado == null) {
            throw new DatoInvalidoException(
                    "El empleado no puede ser nulo");
        }

        // OBTENER LEGAJO DIRECTAMENTE DEL OBJETO
        String legajo = empleado.getLegajo();

        if (legajo == null || legajo.isBlank()) {
            throw new DatoInvalidoException(
                    "El legajo no puede estar vacío");
        }

        legajo = legajo.trim();

        // VALIDAR LEGAJO DUPLICADO
        if (empleados.containsKey(legajo)) {
            throw new ElementoDuplicadoException(
                    "Ya existe un empleado con ese legajo");
        }

        // VALIDAR DNI
        String dni = empleado.getDni();

        if (dni == null || !dni.matches("[0-9]{7,8}")) {
            throw new DatoInvalidoException(
                    "El DNI debe tener 7 u 8 números");
        }

        // VALIDAR DNI DUPLICADO
        if (existeDniEmpleado(dni)) {
            throw new ElementoDuplicadoException(
                    "Ya existe un empleado con ese DNI");
        }

        // VALIDAR SEGÚN EL TIPO
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

        // GUARDAR EMPLEADO POR LEGAJO
        empleados.put(legajo, empleado);

        return "Empleado agregado correctamente";
    }


    // ELIMINAR EMPLEADO POR LEGAJO


    public String eliminarEmpleado(String legajo)
            throws DatoInvalidoException {

        if (legajo == null || legajo.isBlank()) {
            throw new DatoInvalidoException(
                    "El legajo no puede estar vacío");
        }

        legajo = legajo.trim();

        if (!empleados.containsKey(legajo)) {
            throw new DatoInvalidoException(
                    "No existe un empleado con ese legajo");
        }

        empleados.eliminar(legajo);

        return "Empleado eliminado correctamente";
    }


    // MOSTRAR TODOS LOS EMPLEADOS


    public Collection<IEmpleado> mostrarEmpleados() {

        return empleados.obtenerTodos();
    }


    // BUSCAR EMPLEADO

    public IEmpleado buscarEmpleado(String legajo)
            throws DatoInvalidoException {

        // VALIDAR LEGAJO
        if (legajo == null || legajo.isBlank()) {
            throw new DatoInvalidoException(
                    "El legajo no puede estar vacío");
        }

        legajo = legajo.trim();

        // VERIFICAR SI EXISTE
        if (!empleados.containsKey(legajo)) {
            throw new DatoInvalidoException(
                    "No existe un empleado con ese legajo");
        }

        // DEVOLVER EMPLEADO
        return empleados.buscar(legajo);
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
            if (h.getEstadoMantenimiento().equals(EEstadoMantenimiento.EN_REPARACION)) {
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

        if (habitacion.getEstadoMantenimiento().equals(EEstadoMantenimiento.EN_REPARACION)) {
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

        // VALIDAR DATOS
        if (habitacion == null || tecnico == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una habitación y un técnico");
        }

        // VALIDAR QUE LA HABITACIÓN EXISTA
        if (!habitaciones.obtenerTodos().contains(habitacion)) {
            throw new IllegalArgumentException(
                    "La habitación no pertenece al hotel");
        }

        // VALIDAR QUE EL TÉCNICO EXISTA
        if (!tecnicos.obtenerTodos().contains(tecnico)) {
            throw new IllegalArgumentException(
                    "El técnico no pertenece al hotel");
        }

        // VALIDAR DISPONIBILIDAD DEL TÉCNICO
        if (!tecnico.isDisponible()) {
            throw new IllegalStateException(
                    "El técnico seleccionado no está disponible");
        }

        // VALIDAR QUE NO ESTÉ YA EN REPARACIÓN
        if (habitacion.getEstadoMantenimiento()
                == EEstadoMantenimiento.EN_REPARACION) {

            throw new IllegalStateException(
                    "La habitación ya está en reparación");
        }

        // LA HABITACIÓN DEBE ESTAR LIBRE
        // Se mantiene la lógica actual de tu clase Hotel
        if (habitacion.isDisponible()) {
            throw new IllegalStateException(
                    "La habitación está ocupada. Debe liberarse primero");
        }

        // REGISTRAR ASIGNACIÓN
        reparaciones.put(habitacion, tecnico);

        // CAMBIAR ESTADO
        habitacion.setEstadoMantenimiento(
                EEstadoMantenimiento.EN_REPARACION);

        // MARCAR TÉCNICO COMO OCUPADO
        tecnico.setDisponible(false);
    }


// CONSULTAR TÉCNICO ASIGNADO

    public Tecnico obtenerTecnicoAsignado(Habitacion habitacion) {
        return reparaciones.get(habitacion);
    }

// FINALIZAR REPARACIÓN


    public void finalizarReparacion(Habitacion habitacion) {

        if (habitacion == null ||
                habitacion.getEstadoMantenimiento() !=
                        EEstadoMantenimiento.EN_REPARACION) {

            throw new IllegalStateException(
                    "La habitación no está en reparación");
        }

        // OBTENER EL TÉCNICO ASIGNADO
        Tecnico tecnico = reparaciones.get(habitacion);

        if (tecnico == null) {
            throw new IllegalStateException(
                    "No hay técnico asignado a esta habitación");
        }

        // ELIMINAR LA ASIGNACIÓN
        try {
            reparaciones.eliminar(habitacion);

        } catch (DatoInvalidoException e) {
            throw new IllegalStateException(
                    "No se pudo finalizar la reparación: " +
                            e.getMessage());
        }

        // LIBERAR TÉCNICO
        tecnico.setDisponible(true);

        // CAMBIAR A REPARADA
        habitacion.setEstadoMantenimiento(
                EEstadoMantenimiento.REPARADA);
    }

    // AGREGAR HABITACIÓN
    public boolean agregarHabitacion(Habitacion habitacion) {

        try {

            if (habitacion == null) {
                throw new DatoInvalidoException(
                        "La habitación no puede ser nula");
            }

            String numero = String.valueOf(
                    habitacion.getNumeroHabitacion());

            if (habitaciones.containsKey(numero)) {
                throw new ElementoDuplicadoException(
                        "Ya existe una habitación con ese número");
            }

            habitacion.validarDatos();

            habitaciones.put(numero, habitacion);

            System.out.println(
                    "Habitación agregada correctamente");

            return true;

        } catch (AgenciaHotelException e) {

            System.out.println(
                    "ERROR: " + e.getMessage());

            return false;
        }
    }

    // BUSCAR HABITACIÓN
    public Habitacion buscarHabitacion(int numeroHabitacion) {

        try {

            String numero = String.valueOf(numeroHabitacion);

            if (!habitaciones.containsKey(numero)) {
                throw new DatoInvalidoException(
                        "No existe una habitación con ese número");
            }

            return habitaciones.get(numero);

        } catch (AgenciaHotelException e) {

            System.out.println(
                    "ERROR: " + e.getMessage());

            return null;
        }
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
