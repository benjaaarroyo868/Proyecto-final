package Clases;

import Enums.ETipoHabitacion;
import Enums.ETipoTecnico;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Exepciones.ElementoDuplicadoException;
import Exepciones.ReservaNoDisponibleException;
import Interfaces.IEmpleado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hotel {
    private String nombre;
    private String direccion;
    private int cantidadHabitaciones;
    private int cantidadOcupadas;
    Map<String, ArrayList<IEmpleado>> empleados;
    Map<ETipoTecnico, ArrayList <Tecnico>> tecnicos;
    Map<ETipoHabitacion, ArrayList<Habitacion>> habitaciones;
    Map<String, Pasajero> pasajeros;
    Map<String, Pasajero> reservas;
    Map<String, Usuario> usuario;

    public Hotel(String nombre, String direccion, int cantidadHabitaciones) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadOcupadas = 0;
        this.empleados = new HashMap<>();
        this.tecnicos = new HashMap<>();
        this.habitaciones = new HashMap<>();
        this.pasajeros = new HashMap<>();
        this.reservas = new HashMap<>();
        this.usuario = new HashMap<>();
    }

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

    public void agregarPasajero(Pasajero pasajero)throws AgenciaHotelException {
        if(pasajero==null){
            throw new DatoInvalidoException("El pasajero no puede ser nulo");
        }

        if (pasajeros.containsKey(pasajero.getDNI())) {
            throw new ElementoDuplicadoException("Ya existe un pasajero con ese DNI");
        }

        pasajero.validarDatos();
        pasajeros.put(pasajero.getDNI(), pasajero);


    }

    public void agregarReserva(Reserva reserva)throws AgenciaHotelException{
        if(reserva==null){
            throw new DatoInvalidoException("La reserva no puede ser nula");
        }

        if(reservas.containsKey(reserva.getIdReserva())){
            throw new ElementoDuplicadoException("La reserva ya existe");

        }
        if(reserva.getHabitacion().isEstado()){
            throw new ReservaNoDisponibleException("La habitacion esta ocupada");
        }
        reserva.validarDatos();
        reservas.put(reserva.getIdReserva(), reserva.getPasajero());
        cantidadOcupadas++;
    }

    public String agregarSectorEmpleado(String s){
        if(empleados.containsKey(s)){
            return "Sector -> "+s+" ya existe";
        }

        empleados.put(s, new ArrayList<>());
        return "Sector -> "+s+" agregado correctamente";
    }

    public String agregarEmpleado (String s, IEmpleado a) throws AgenciaHotelException{
        if(!empleados.containsKey(s)){
            return "Sector -> "+s+" no existe";
        }
        if(empleados.get(s).contains(a)){
            return "Empleado ya existe en este sector -> "+s;
        }
        if(a instanceof Administrador){
            Administrador admin = (Administrador) a;

            admin.validarDatos();
        }
        else if(a instanceof Conserje){
            Conserje c = (Conserje) a;

            c.validarDatos();
        }
        empleados.get(s).add(a);
        return "Empleado guardado correctamente";
    }


    public String agregarUsuario(Usuario u) throws AgenciaHotelException {

        if (usuario == null) {
            throw new DatoInvalidoException(
                    "El usuario no puede ser nulo");
        }

        u.validarDatos();

        // Comprobamos si ya existe
        if (usuario.containsKey(u.getNombreUsuario())) {
            throw new DatoInvalidoException(
                    "El nombre de usuario ya existe");
        }

        usuario.put(u.getNombreUsuario(), u);

        return "Usuario agregado correctamente";
    }


    public Usuario iniciarSesion(String nombre, String contrasenia) {

        Usuario u = usuario.get(nombre);

        if (u != null &&
                u.getContrasenia().equals(contrasenia)) {
            return u;
        }

        return null;
    }

}
