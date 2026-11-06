package Menu;

import Clases.*;
import Enums.*;
import Exepciones.AgenciaHotelException;
import Exepciones.DatoInvalidoException;
import Generico.RegistroGenericoMapas;
import Interfaces.IEmpleado;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Menu.ConsolaHotel.*;
import static Menu.GestionHabitaciones.*;
import static Menu.ValidacionesMenu.*;


public class GestionReservas {

    // ============================================================
    // puedeGestionarReserva
    // ============================================================
    private static boolean puedeGestionarReserva(
            Reserva reserva,
            Usuario usuario,
            boolean esEmpleado) {

        if (reserva == null) {
            return false;
        }

        // ADMINISTRADORES Y CONSERJES
        if (esEmpleado) {
            return true;
        }

        // PASAJERO: SOLO SUS PROPIAS RESERVAS
        return usuario != null
                && reserva.getPasajero() != null
                && reserva.getPasajero().getUsuario() != null
                && usuario.getNombreUsuario().equals(
                reserva.getPasajero()
                        .getUsuario()
                        .getNombreUsuario());
    }


    // ============================================================
    // imprimirReserva
    // ============================================================
    private static void imprimirReserva(Reserva reserva) {

        // CALCULAR DÍAS Y NOCHES
        long cantidadNoches =
                java.time.temporal.ChronoUnit.DAYS.between(
                        reserva.getFechaIngreso(),
                        reserva.getFechaSalida());

        long cantidadDias = cantidadNoches + 1;

        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ID: " + reserva.getIdReserva());

        System.out.println(
                "║  Habitación: " +
                        reserva.getHabitacion().getNumeroHabitacion());

        System.out.println(
                "║  Fecha de ingreso: " +
                        reserva.getFechaIngreso().format(FORMATO_FECHA));

        System.out.println(
                "║  Fecha de salida: " +
                        reserva.getFechaSalida().format(FORMATO_FECHA));

        System.out.println(
                "║  Total de días: " + cantidadDias);

        System.out.println(
                "║  Total de noches: " + cantidadNoches);

        if (reserva.getPasajero() != null) {

            System.out.println(
                    "║  Pasajero: " +
                            reserva.getPasajero().getNombre() + " " +
                            reserva.getPasajero().getApellido());

            System.out.println(
                    "║  DNI: " +
                            reserva.getPasajero().getDNI());
        }

        System.out.println(RESET);
    }


    // ============================================================
    // seleccionarPasajeroReserva
    // ============================================================
    private static Pasajero seleccionarPasajeroReserva(
            Hotel hotel, Usuario usuario) {

        if (usuario == null) {
            System.out.println(ROJO +
                    "ERROR: No hay sesión iniciada" + RESET);
            return null;
        }

        // =========================================
        // 1. BUSCAR PASAJERO ASOCIADO
        // =========================================

        Pasajero pasajeroActual = null;

        for (Pasajero p : hotel.mostrarPasajeros()) {

            if (p.getUsuario() != null &&
                    p.getUsuario().getNombreUsuario()
                            .equals(usuario.getNombreUsuario())) {

                pasajeroActual = p;
                break;
            }
        }

        // =========================================
        // 2. SI ES PASAJERO COMÚN
        // =========================================

        if (usuario.getTipoDeUsuario()
                == ETipoUsuario.PASAJERO) {

            if (pasajeroActual != null) {
                return pasajeroActual;
            }

            System.out.println(ROJO +
                    "ERROR: No hay pasajero asociado a tu cuenta"
                    + RESET);

            return null;
        }

        // =========================================
        // 3. BUSCAR ADMINISTRADOR O CONSERJE
        // =========================================

        Persona empleadoActual = null;

        for (IEmpleado empleado : hotel.mostrarEmpleados()) {

            if (empleado instanceof Administrador) {

                Administrador admin = (Administrador) empleado;

                if (admin.getUsuario() != null &&
                        admin.getUsuario().getNombreUsuario()
                                .equals(usuario.getNombreUsuario())) {

                    empleadoActual = admin;
                    break;
                }

            } else if (empleado instanceof Conserje) {

                Conserje conserje = (Conserje) empleado;

                if (conserje.getUsuario() != null &&
                        conserje.getUsuario().getNombreUsuario()
                                .equals(usuario.getNombreUsuario())) {

                    empleadoActual = conserje;
                    break;
                }
            }
        }

        // =========================================
        // 4. ELEGIR PARA QUIÉN ES LA RESERVA
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          ¿PARA QUIÉN ES LA RESERVA?       ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  1. Para mí                               ║");
        System.out.println(
                "║  2. Para otro pasajero                    ║" + RESET);

        System.out.println(AMARILLO +
                "║  0. Volver al menú                        ║" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        int opcion = pedirOpcion(0, 2);

        if (opcion == 0) {

            System.out.println(AMARILLO +
                    "Operación cancelada" + RESET);
            return null;
        }

        // =========================================
        // 5. OPCIÓN 1: PARA MÍ
        // =========================================

        if (opcion == 1) {

            // SI YA TIENE PASAJERO ASOCIADO
            if (pasajeroActual != null) {

                System.out.println(VERDE +
                        "Se utilizarán tus datos registrados" + RESET);

                return pasajeroActual;
            }

            // VERIFICAR EMPLEADO
            if (empleadoActual == null) {

                System.out.println();
                System.out.println(CYAN +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║            EMPLEADO NO ENCONTRADO         ║");
                System.out.println("╠═══════════════════════════════════════════╣"
                        + RESET);

                System.out.println(ROJO +
                        "║  No se encontraron tus datos de empleado"
                        + RESET);

                System.out.println(AMARILLO +
                        "║  Verificá tu registro en el hotel"
                        + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝"
                        + RESET);

                return null;
            }

            // =========================================
            // 6. RECUPERAR DATOS DEL EMPLEADO
            // =========================================

            String nombre = empleadoActual.getNombre();
            String apellido = empleadoActual.getApellido();
            String dni = empleadoActual.getDNI();
            String telefono = empleadoActual.getTelefono();
            String email = empleadoActual.getEmail();

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║              MIS DATOS                    ║");
            System.out.println("╠═══════════════════════════════════════════╣"
                    + RESET);

            System.out.println(AZUL +
                    "║  Nombre: " + nombre);

            System.out.println(
                    "║  Apellido: " + apellido);

            System.out.println(
                    "║  DNI: " + dni);

            System.out.println(
                    "║  Teléfono: " + telefono);

            System.out.println(
                    "║  Email: " + email + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝"
                    + RESET);

            // VERIFICAR SI EXISTE COMO PASAJERO
            for (Pasajero p : hotel.mostrarPasajeros()) {

                if (p.getDNI().equals(dni)) {

                    if (p.getUsuario() != null &&
                            p.getUsuario().getNombreUsuario()
                                    .equals(usuario.getNombreUsuario())) {

                        return p;
                    }

                    System.out.println(ROJO +
                            "ERROR: Ese DNI ya está asociado a otro usuario"
                            + RESET);
                    return null;
                }
            }

            // DATOS PROPIOS DE PASAJERO
            String origen = pedirSoloLetras("Origen", sc);
            String destino = pedirSoloLetras("Destino", sc);

            // CREAR PASAJERO CON LOS DATOS DEL EMPLEADO
            Pasajero nuevoPasajero = new Pasajero(
                    nombre,
                    apellido,
                    dni,
                    telefono,
                    email,
                    usuario,
                    origen,
                    destino
            );

            try {

                nuevoPasajero.validarDatos();

                hotel.agregarPasajero(nuevoPasajero);

                System.out.println();
                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║       DATOS ASOCIADOS CORRECTAMENTE       ║");
                System.out.println("╠═══════════════════════════════════════════╣");

                System.out.println("║  Nombre: " +
                        nombre + " " + apellido);

                System.out.println("║  DNI: " + dni);

                System.out.println("║  Usuario: " +
                        usuario.getNombreUsuario());

                System.out.println(
                        "╚═══════════════════════════════════════════╝"
                                + RESET);

                return nuevoPasajero;

            } catch (AgenciaHotelException e) {

                System.out.println(ROJO +
                        "ERROR: " + e.getMessage() + RESET);

                return null;
            }
        }

        // =========================================
        // 7. OPCIÓN 2: PARA OTRO PASAJERO
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             BUSCAR PASAJERO               ║");
        System.out.println("╠═══════════════════════════════════════════╣"
                + RESET);

        System.out.println(AMARILLO +
                "║  Ingresá 0 para cancelar" + RESET);

        String dni;

        while (true) {

            System.out.print(AZUL +
                    "║  DNI del pasajero: " + RESET);

            dni = sc.nextLine().trim();

            if (dni.equals("0")) {
                return null;
            }

            if (dni.matches("[0-9]{7,8}")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: DNI de 7 u 8 números" + RESET);
        }

        // =========================================
        // 8. BUSCAR PASAJERO REGISTRADO
        // =========================================

        for (Pasajero p : hotel.mostrarPasajeros()) {

            if (p.getDNI().equals(dni)) {

                System.out.println(VERDE +
                        "║  PASAJERO ENCONTRADO" + RESET);

                System.out.println(AZUL +
                        "║  Nombre: " + p.getNombre());

                System.out.println(
                        "║  Apellido: " + p.getApellido());

                System.out.println(
                        "║  DNI: " + p.getDNI() + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝"
                        + RESET);

                return p;
            }
        }

        // =========================================
        // 9. REGISTRAR PASAJERO NUEVO
        // =========================================

        System.out.println(AMARILLO +
                "║  El pasajero no está registrado" + RESET);

        System.out.println(VERDE +
                "║  Vamos a cargar sus datos" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝"
                + RESET);

        String nombre = pedirSoloLetras("Nombre", sc);
        String apellido = pedirSoloLetras("Apellido", sc);
        String telefono = pedirTelefono();
        String email = pedirEmail();

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║         CREAR USUARIO PASAJERO            ║");
        System.out.println("╠═══════════════════════════════════════════╣"
                + RESET);

        String nombreUsuario = pedirNombreUsuario(hotel);
        String contrasenia = pedirContrasenia();

        Usuario usuarioPasajero = new Usuario(
                nombreUsuario,
                contrasenia,
                ETipoUsuario.PASAJERO
        );

        String origen = pedirSoloLetras("Origen", sc);
        String destino = pedirSoloLetras("Destino", sc);

        Pasajero nuevoPasajero = new Pasajero(
                nombre,
                apellido,
                dni,
                telefono,
                email,
                usuarioPasajero,
                origen,
                destino
        );

        // =========================================
        // 10. GUARDAR PASAJERO Y USUARIO
        // =========================================

        try {

            usuarioPasajero.validarDatos();
            nuevoPasajero.validarDatos();

            hotel.agregarPasajero(nuevoPasajero);

            try {

                hotel.agregarUsuario(usuarioPasajero);

            } catch (AgenciaHotelException e) {

                hotel.eliminarPasajero(dni);
                throw e;
            }

            System.out.println();
            System.out.println(VERDE +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║       PASAJERO REGISTRADO CON ÉXITO       ║");
            System.out.println("╠═══════════════════════════════════════════╣");

            System.out.println("║  Nombre: " +
                    nombre + " " + apellido);

            System.out.println("║  DNI: " + dni);

            System.out.println("║  Usuario: " + nombreUsuario);

            System.out.println(
                    "╚═══════════════════════════════════════════╝"
                            + RESET);

            return nuevoPasajero;

        } catch (AgenciaHotelException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);

            return null;
        }
    }


    // ============================================================
    // realizarReserva
    // ============================================================
    public static void realizarReserva(Hotel hotel, Usuario usuario) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              REALIZAR RESERVA             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // =========================================
        // 1. SELECCIONAR PASAJERO
        // =========================================

        if (usuario == null) {

            System.out.println(ROJO +
                    "║  ERROR: No hay sesión iniciada" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        Pasajero pasajero =
                seleccionarPasajeroReserva(hotel, usuario);

        if (pasajero == null) {

            System.out.println(AMARILLO +
                    "Reserva cancelada. Volviendo al menú..." + RESET);
            return;
        }

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          PASAJERO SELECCIONADO            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(VERDE +
                "║  Pasajero seleccionado correctamente" + RESET);

        System.out.println(AZUL +
                "║  Nombre: " +
                pasajero.getNombre() + " " +
                pasajero.getApellido());

        System.out.println(
                "║  DNI: " + pasajero.getDNI() + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        // =========================================
        // 2. OBTENER HABITACIONES EN ESTADO NORMAL
        // =========================================

        java.util.List<Habitacion> habitaciones =
                new java.util.ArrayList<>();

        for (Habitacion h : hotel.getHabitaciones().obtenerTodos()) {

            if (h.getEstadoMantenimiento()
                    == EEstadoMantenimiento.NORMAL) {

                habitaciones.add(h);
            }
        }

        if (habitaciones.isEmpty()) {

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║           HABITACIONES DISPONIBLES        ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(ROJO +
                    "║  ERROR: No hay habitaciones habilitadas" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 3. MOSTRAR HABITACIONES
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          HABITACIONES DISPONIBLES         ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // MOSTRAR HABITACIONES POR PISO Y CON SU ESTADO
        mostrarHabitacionesPorPiso(habitaciones);

        System.out.println(AMARILLO +
                "║  0. Volver al menú" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        // AHORA SE SELECCIONA EL NÚMERO REAL: 101, 102, 201...
        // Y NO EL ÍNDICE DE LA LISTA.
        Habitacion habitacion = null;

        while (habitacion == null) {

            System.out.print(VERDE +
                    "║  Número de habitación (0 para volver): " + RESET);

            String numeroIngresado = sc.nextLine().trim();

            if (numeroIngresado.equals("0")) {
                System.out.println(AMARILLO +
                        "Reserva cancelada. Volviendo al menú..." + RESET);
                return;
            }

            for (Habitacion h : habitaciones) {
                if (String.valueOf(h.getNumeroHabitacion())
                        .equals(numeroIngresado)) {
                    habitacion = h;
                    break;
                }
            }

            if (habitacion == null) {
                System.out.println(ROJO +
                        "║  ERROR: Ingresá el número de una habitación mostrada" + RESET);
            }
        }

        // =========================================
        // 4. CANTIDAD DE PERSONAS
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║           CANTIDAD DE PERSONAS            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  Habitación: " +
                habitacion.getNumeroHabitacion());

        System.out.println(
                "║  Capacidad máxima: " +
                        habitacion.getCantidadMaximaPersonas() + RESET);

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        System.out.println(AMARILLO +
                "║  0. Volver al menú" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        int cantidadPasajeros =
                pedirOpcion(0, habitacion.getCantidadMaximaPersonas());

        if (cantidadPasajeros == 0) {

            System.out.println(AMARILLO +
                    "Reserva cancelada. Volviendo al menú..." + RESET);
            return;
        }

        // =========================================
        // 5. FECHAS DE RESERVA
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             FECHAS DE RESERVA             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AMARILLO +
                "║  Escribí 0 para volver al menú" + RESET);

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        // FECHA DE INGRESO
        java.time.LocalDate fechaIngreso;

        while (true) {

            System.out.print(AZUL +
                    "║  Fecha de ingreso (DD-MM-AAAA): " + RESET);

            String entrada = sc.nextLine().trim();

            if (entrada.equals("0")) {

                System.out.println(AMARILLO +
                        "Reserva cancelada. Volviendo al menú..." + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝" + RESET);
                return;
            }

            try {

                fechaIngreso = java.time.LocalDate.parse(
                        entrada, FORMATO_FECHA);

                if (fechaIngreso.isBefore(
                        java.time.LocalDate.now())) {

                    System.out.println(ROJO +
                            "║  ERROR: No puede ser anterior a hoy" + RESET);
                    continue;
                }

                break;

            } catch (java.time.format.DateTimeParseException e) {

                System.out.println(ROJO +
                        "║  ERROR: Fecha inválida. Ejemplo: 20-11-2026"
                        + RESET);
            }
        }

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        // FECHA DE SALIDA
        java.time.LocalDate fechaSalida;

        while (true) {

            System.out.print(AZUL +
                    "║  Fecha de salida (DD-MM-AAAA): " + RESET);

            String entrada = sc.nextLine().trim();

            if (entrada.equals("0")) {

                System.out.println(AMARILLO +
                        "Reserva cancelada. Volviendo al menú..." + RESET);

                System.out.println(CYAN +
                        "╚═══════════════════════════════════════════╝" + RESET);
                return;
            }

            try {

                fechaSalida = java.time.LocalDate.parse(
                        entrada, FORMATO_FECHA);

                if (!fechaSalida.isAfter(fechaIngreso)) {

                    System.out.println(ROJO +
                            "║  ERROR: La salida debe ser posterior al ingreso"
                            + RESET);
                    continue;
                }

                break;

            } catch (java.time.format.DateTimeParseException e) {

                System.out.println(ROJO +
                        "║  ERROR: Fecha inválida. Ejemplo: 25-11-2026"
                        + RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        // =========================================
        // 6. VALIDAR ESTADO DE LA HABITACIÓN
        // =========================================

        if (habitacion.getEstadoMantenimiento()
                != EEstadoMantenimiento.NORMAL) {

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║          DISPONIBILIDAD HABITACIÓN        ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(ROJO +
                    "║  ERROR: Habitación no habilitada" + RESET);

            System.out.println(AMARILLO +
                    "║  Estado: " +
                    habitacion.getEstadoMantenimiento() + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 7. VALIDAR DISPONIBILIDAD POR FECHAS
        // =========================================

        if (!hotel.habitacionDisponible(
                habitacion, fechaIngreso, fechaSalida)) {

            java.time.LocalDate proximaFecha =
                    hotel.proximaFechaDisponible(
                            habitacion, fechaIngreso, fechaSalida);

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║          DISPONIBILIDAD HABITACIÓN        ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(ROJO +
                    "║  ERROR: Habitación ocupada en esas fechas"
                    + RESET);

            if (proximaFecha != null) {

                System.out.println(CYAN +
                        "║                                           ║" + RESET);

                System.out.println(VERDE +
                        "║  Próxima fecha disponible: " +
                        proximaFecha.format(FORMATO_FECHA) + RESET);
            }

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 8. CALCULAR DÍAS Y NOCHES
        // =========================================

        long cantidadNoches =
                java.time.temporal.ChronoUnit.DAYS.between(
                        fechaIngreso, fechaSalida);

        long cantidadDias = cantidadNoches + 1;

        // =========================================
        // 9. CREAR RESERVA
        // =========================================

        Reserva reserva = new Reserva(
                pasajero,
                habitacion,
                cantidadPasajeros,
                fechaIngreso,
                fechaSalida
        );

        // =========================================
        // 10. MOSTRAR RESUMEN
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             RESUMEN DE RESERVA            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ID: " + reserva.getIdReserva());

        System.out.println(
                "║  Pasajero: " +
                        pasajero.getNombre() + " " +
                        pasajero.getApellido());

        System.out.println(
                "║  DNI: " + pasajero.getDNI());

        System.out.println(
                "║  Habitación: " +
                        habitacion.getNumeroHabitacion());

        System.out.println(
                "║  Cantidad de personas: " + cantidadPasajeros);

        System.out.println(
                "║  Ingreso: " +
                        fechaIngreso.format(FORMATO_FECHA));

        System.out.println(
                "║  Salida: " +
                        fechaSalida.format(FORMATO_FECHA));

        System.out.println(
                "║  Total de días: " + cantidadDias);

        System.out.println(
                "║  Total de noches: " +
                        cantidadNoches + RESET);

        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣" + RESET);

        // =========================================
        // 11. CONFIRMAR RESERVA
        // =========================================

        System.out.println(AMARILLO +
                "║  ¿Confirmar reserva? (Si/No)" + RESET);

        System.out.println(AMARILLO +
                "║  0. Volver al menú" + RESET);

        String respuesta;

        while (true) {

            System.out.print(VERDE + "║  ➜ " + RESET);

            respuesta = sc.nextLine().trim();

            if (respuesta.equals("0") ||
                    respuesta.equalsIgnoreCase("Si") ||
                    respuesta.equalsIgnoreCase("S") ||
                    respuesta.equalsIgnoreCase("No") ||
                    respuesta.equalsIgnoreCase("N")) {
                break;
            }

            System.out.println(ROJO +
                    "║  ERROR: Ingresá Si, No o 0" + RESET);
        }

        if (respuesta.equals("0") ||
                respuesta.equalsIgnoreCase("No") ||
                respuesta.equalsIgnoreCase("N")) {

            System.out.println(AMARILLO +
                    "║  Reserva cancelada" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // =========================================
        // 12. GUARDAR RESERVA EN EL HOTEL
        // =========================================

        if (hotel.agregarReserva(reserva)) {

            System.out.println(VERDE +
                    "║  Reserva realizada correctamente" + RESET);

            System.out.println(VERDE +
                    "║  Guardá tu ID: " +
                    reserva.getIdReserva() + RESET);

        } else {

            System.out.println(ROJO +
                    "║  ERROR: No se pudo realizar la reserva" + RESET);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);
    }


    // ============================================================
    // verReserva
    // ============================================================
    public static void verReserva(
            Hotel hotel,
            Usuario usuario,
            boolean esEmpleado) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║               BUSCAR RESERVA              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ◈ Código de reserva" + RESET);

        System.out.print(VERDE + "║  ➜ " + RESET);

        String id = sc.nextLine().trim();

        Reserva reserva = hotel.buscarReserva(id);

        if (reserva == null) {

            System.out.println(ROJO +
                    "║  ERROR: Reserva no encontrada" + RESET);

        } else if (!puedeGestionarReserva(
                reserva, usuario, esEmpleado)) {

            System.out.println(ROJO +
                    "║  No tenés permiso para ver esta reserva"
                    + RESET);

        } else {

            System.out.println(VERDE +
                    "║  RESERVA ENCONTRADA" + RESET);

            imprimirReserva(reserva);
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝"
                + RESET);
    }


    // ============================================================
    // modificarReserva
    // ============================================================
    public static void modificarReserva(Hotel hotel, Usuario usuario, boolean esEmpleado) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             MODIFICAR RESERVA             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.print(VERDE + "║  Código de reserva (0 para volver): " + RESET);
        String codigo = sc.nextLine().trim();

        if (codigo.equals("0")) {
            return;
        }

        Reserva reserva = hotel.buscarReserva(codigo);

        if (reserva == null) {
            System.out.println(ROJO +
                    "║  ERROR: No se encontró la reserva" + RESET);
            return;
        }

        // VERIFICAR PERMISOS
        if (!puedeGestionarReserva(reserva, usuario, esEmpleado)) {
            System.out.println(ROJO +
                    "║  No tenés permiso para modificar esta reserva" + RESET);
            return;
        }

        java.time.format.DateTimeFormatter formato =
                java.time.format.DateTimeFormatter
                        .ofPattern("dd-MM-uuuu")
                        .withResolverStyle(
                                java.time.format.ResolverStyle.STRICT);

        int opcion;

        do {

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║             MODIFICAR RESERVA             ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL +
                    "║  Reserva: " + reserva.getIdReserva());
            System.out.println(
                    "║  Habitación: " +
                            reserva.getHabitacion().getNumeroHabitacion());
            System.out.println(
                    "║  Fecha entrada: " +
                            reserva.getFechaIngreso().format(formato));
            System.out.println(
                    "║  Fecha salida: " +
                            reserva.getFechaSalida().format(formato));
            System.out.println(
                    "║  Cantidad personas: " +
                            reserva.getCantidadPasajeros() + RESET);

            System.out.println(CYAN +
                    "╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL +
                    "║  1. Cambiar fecha de entrada              ║");
            System.out.println(
                    "║  2. Cambiar fecha de salida               ║");
            System.out.println(
                    "║  3. Cambiar ambas fechas                  ║");
            System.out.println(
                    "║  4. Cambiar cantidad de personas          ║");
            System.out.println(
                    "║  5. Cambiar habitación                    ║" + RESET);

            System.out.println(CYAN +
                    "║                                           ║" + RESET);

            System.out.println(ROJO +
                    "║  0. Salir del menú                        ║" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

            opcion = pedirOpcion(0, 5);

            switch (opcion) {

                // ============================================
                // 1 - CAMBIAR FECHA DE ENTRADA
                // ============================================

                case 1: {

                    java.time.LocalDate nuevaEntrada;

                    while (true) {

                        System.out.println(AMARILLO +
                                "║  Fecha actual: " +
                                reserva.getFechaIngreso().format(formato) + RESET);

                        System.out.print(VERDE +
                                "║  Nueva entrada (DD-MM-AAAA / 0 volver): " + RESET);

                        String texto = sc.nextLine().trim();

                        if (texto.equals("0")) {
                            break;
                        }

                        try {

                            nuevaEntrada =
                                    java.time.LocalDate.parse(texto, formato);

                            if (nuevaEntrada.isBefore(
                                    reserva.getFechaIngreso())) {

                                System.out.println(ROJO +
                                        "║  La fecha no puede retroceder" + RESET);
                                continue;
                            }

                            if (nuevaEntrada.isBefore(
                                    java.time.LocalDate.now())) {

                                System.out.println(ROJO +
                                        "║  La fecha no puede ser anterior a hoy"
                                        + RESET);
                                continue;
                            }

                            if (!nuevaEntrada.isBefore(
                                    reserva.getFechaSalida())) {

                                System.out.println(ROJO +
                                        "║  La entrada debe ser anterior a la salida"
                                        + RESET);
                                continue;
                            }

                            if (nuevaEntrada.equals(
                                    reserva.getFechaIngreso())) {

                                System.out.println(AMARILLO +
                                        "║  La fecha ingresada es la misma" + RESET);
                                break;
                            }

                            // VERIFICAR SUPERPOSICIÓN
                            boolean ocupada = false;

                            for (Reserva otra : hotel.mostrarReservas()) {

                                if (otra.getIdReserva().equals(
                                        reserva.getIdReserva())) {
                                    continue;
                                }

                                if (otra.getHabitacion().getNumeroHabitacion()
                                        == reserva.getHabitacion()
                                        .getNumeroHabitacion()) {

                                    if (nuevaEntrada.isBefore(
                                            otra.getFechaSalida())
                                            && reserva.getFechaSalida().isAfter(
                                            otra.getFechaIngreso())) {

                                        ocupada = true;
                                        break;
                                    }
                                }
                            }

                            if (ocupada) {

                                System.out.println(ROJO +
                                        "║  Habitación ocupada en esas fechas"
                                        + RESET);
                                continue;
                            }

                            if (confirmarEliminacion()) {

                                reserva.setFechaIngreso(nuevaEntrada);

                                System.out.println(VERDE +
                                        "║  Fecha de entrada modificada correctamente"
                                        + RESET);
                            } else {

                                System.out.println(AMARILLO +
                                        "║  Modificación cancelada" + RESET);
                            }

                            break;

                        } catch (java.time.format.DateTimeParseException e) {

                            System.out.println(ROJO +
                                    "║  Fecha inválida. Ejemplo: 20-11-2026"
                                    + RESET);
                        }
                    }

                    break;
                }

                // ============================================
                // 2 - CAMBIAR FECHA DE SALIDA
                // ============================================

                case 2: {

                    while (true) {

                        System.out.println(AMARILLO +
                                "║  Fecha actual: " +
                                reserva.getFechaSalida().format(formato) + RESET);

                        System.out.print(VERDE +
                                "║  Nueva salida (DD-MM-AAAA / 0 volver): " + RESET);

                        String texto = sc.nextLine().trim();

                        if (texto.equals("0")) {
                            break;
                        }

                        try {

                            java.time.LocalDate nuevaSalida =
                                    java.time.LocalDate.parse(texto, formato);

                            if (nuevaSalida.isBefore(
                                    reserva.getFechaSalida())) {

                                System.out.println(ROJO +
                                        "║  La fecha no puede retroceder" + RESET);
                                continue;
                            }

                            if (!nuevaSalida.isAfter(
                                    reserva.getFechaIngreso())) {

                                System.out.println(ROJO +
                                        "║  La salida debe ser posterior a la entrada"
                                        + RESET);
                                continue;
                            }

                            if (nuevaSalida.equals(
                                    reserva.getFechaSalida())) {

                                System.out.println(AMARILLO +
                                        "║  La fecha ingresada es la misma" + RESET);
                                break;
                            }

                            boolean ocupada = false;

                            for (Reserva otra : hotel.mostrarReservas()) {

                                if (otra.getIdReserva().equals(
                                        reserva.getIdReserva())) {
                                    continue;
                                }

                                if (otra.getHabitacion().getNumeroHabitacion()
                                        == reserva.getHabitacion()
                                        .getNumeroHabitacion()) {

                                    if (reserva.getFechaIngreso().isBefore(
                                            otra.getFechaSalida())
                                            && nuevaSalida.isAfter(
                                            otra.getFechaIngreso())) {

                                        ocupada = true;
                                        break;
                                    }
                                }
                            }

                            if (ocupada) {

                                System.out.println(ROJO +
                                        "║  Habitación ocupada en esas fechas"
                                        + RESET);
                                continue;
                            }

                            if (confirmarEliminacion()) {

                                reserva.setFechaSalida(nuevaSalida);

                                System.out.println(VERDE +
                                        "║  Fecha de salida modificada correctamente"
                                        + RESET);

                            } else {

                                System.out.println(AMARILLO +
                                        "║  Modificación cancelada" + RESET);
                            }

                            break;

                        } catch (java.time.format.DateTimeParseException e) {

                            System.out.println(ROJO +
                                    "║  Fecha inválida. Ejemplo: 25-11-2026"
                                    + RESET);
                        }
                    }

                    break;
                }

                // ============================================
                // 3 - CAMBIAR AMBAS FECHAS
                // ============================================

                case 3: {

                    java.time.LocalDate nuevaEntrada = null;
                    java.time.LocalDate nuevaSalida = null;

                    while (true) {

                        System.out.print(VERDE +
                                "║  Nueva entrada (DD-MM-AAAA / 0 volver): "
                                + RESET);

                        String texto = sc.nextLine().trim();

                        if (texto.equals("0")) {
                            break;
                        }

                        try {

                            nuevaEntrada =
                                    java.time.LocalDate.parse(texto, formato);

                            if (nuevaEntrada.isBefore(
                                    reserva.getFechaIngreso())
                                    || nuevaEntrada.isBefore(
                                    java.time.LocalDate.now())) {

                                System.out.println(ROJO +
                                        "║  La fecha de entrada no puede retroceder"
                                        + RESET);
                                continue;
                            }

                            break;

                        } catch (java.time.format.DateTimeParseException e) {

                            System.out.println(ROJO +
                                    "║  Fecha inválida" + RESET);
                        }
                    }

                    if (nuevaEntrada == null) {
                        break;
                    }

                    while (true) {

                        System.out.print(VERDE +
                                "║  Nueva salida (DD-MM-AAAA / 0 volver): "
                                + RESET);

                        String texto = sc.nextLine().trim();

                        if (texto.equals("0")) {
                            break;
                        }

                        try {

                            nuevaSalida =
                                    java.time.LocalDate.parse(texto, formato);

                            if (nuevaSalida.isBefore(
                                    reserva.getFechaSalida())) {

                                System.out.println(ROJO +
                                        "║  La fecha de salida no puede retroceder"
                                        + RESET);
                                continue;
                            }

                            if (!nuevaSalida.isAfter(nuevaEntrada)) {

                                System.out.println(ROJO +
                                        "║  La salida debe ser posterior a la entrada"
                                        + RESET);
                                continue;
                            }

                            break;

                        } catch (java.time.format.DateTimeParseException e) {

                            System.out.println(ROJO +
                                    "║  Fecha inválida" + RESET);
                        }
                    }

                    if (nuevaSalida == null) {
                        break;
                    }

                    if (nuevaEntrada.equals(reserva.getFechaIngreso())
                            && nuevaSalida.equals(reserva.getFechaSalida())) {

                        System.out.println(AMARILLO +
                                "║  No hay cambios en las fechas" + RESET);
                        break;
                    }

                    boolean ocupada = false;

                    for (Reserva otra : hotel.mostrarReservas()) {

                        if (otra.getIdReserva().equals(
                                reserva.getIdReserva())) {
                            continue;
                        }

                        if (otra.getHabitacion().getNumeroHabitacion()
                                == reserva.getHabitacion()
                                .getNumeroHabitacion()) {

                            if (nuevaEntrada.isBefore(
                                    otra.getFechaSalida())
                                    && nuevaSalida.isAfter(
                                    otra.getFechaIngreso())) {

                                ocupada = true;
                                break;
                            }
                        }
                    }

                    if (ocupada) {

                        System.out.println(ROJO +
                                "║  Habitación ocupada en esas fechas"
                                + RESET);
                        break;
                    }

                    System.out.println(AZUL +
                            "║  Entrada: " + nuevaEntrada.format(formato));
                    System.out.println(
                            "║  Salida: " + nuevaSalida.format(formato)
                                    + RESET);

                    if (confirmarEliminacion()) {

                        reserva.setFechaIngreso(nuevaEntrada);
                        reserva.setFechaSalida(nuevaSalida);

                        System.out.println(VERDE +
                                "║  Ambas fechas modificadas correctamente"
                                + RESET);

                    } else {

                        System.out.println(AMARILLO +
                                "║  Modificación cancelada" + RESET);
                    }

                    break;
                }

                // ============================================
                // 4 - CAMBIAR CANTIDAD DE PERSONAS
                // ============================================

                case 4: {

                    int capacidadMaxima =
                            reserva.getHabitacion()
                                    .getCantidadMaximaPersonas();

                    System.out.println();
                    System.out.println(CYAN +
                            "╔═══════════════════════════════════════════╗");
                    System.out.println("║        MODIFICAR CANTIDAD PERSONAS        ║");
                    System.out.println("╠═══════════════════════════════════════════╣"
                            + RESET);

                    System.out.println(AZUL +
                            "║  Cantidad actual: " +
                            reserva.getCantidadPasajeros());
                    System.out.println(
                            "║  Capacidad máxima: " + capacidadMaxima
                                    + RESET);

                    while (true) {

                        System.out.print(VERDE +
                                "║  Nueva cantidad (0 para volver): " + RESET);

                        String texto = sc.nextLine().trim();

                        if (texto.equals("0")) {
                            break;
                        }

                        try {

                            int cantidad = Integer.parseInt(texto);

                            if (cantidad < 1 || cantidad > capacidadMaxima) {

                                System.out.println(ROJO +
                                        "║  La cantidad debe estar entre 1 y "
                                        + capacidadMaxima + RESET);
                                continue;
                            }

                            if (cantidad == reserva.getCantidadPasajeros()) {

                                System.out.println(AMARILLO +
                                        "║  La cantidad ingresada es la misma"
                                        + RESET);
                                break;
                            }

                            if (confirmarEliminacion()) {

                                reserva.setCantidadPasajeros(cantidad);

                                System.out.println(VERDE +
                                        "║  Cantidad modificada correctamente"
                                        + RESET);

                            } else {

                                System.out.println(AMARILLO +
                                        "║  Modificación cancelada" + RESET);
                            }

                            break;

                        } catch (NumberFormatException e) {

                            System.out.println(ROJO +
                                    "║  Ingresá un número válido" + RESET);
                        }
                    }

                    break;
                }

                // ============================================
                // 5 - CAMBIAR HABITACIÓN
                // ============================================

                case 5: {

                    System.out.println();
                    System.out.println(CYAN +
                            "╔═══════════════════════════════════════════╗");
                    System.out.println("║             CAMBIAR HABITACIÓN            ║");
                    System.out.println("╠═══════════════════════════════════════════╣"
                            + RESET);

                    System.out.println(AZUL +
                            "║  Habitación actual: " +
                            reserva.getHabitacion().getNumeroHabitacion()
                            + RESET);

                    System.out.println(CYAN +
                            "║                                           ║" + RESET);

                    System.out.println(AZUL +
                            "║  HABITACIONES APTAS PARA LA RESERVA       ║"
                            + RESET);

                    java.util.List<Habitacion> opciones =
                            new java.util.ArrayList<>();

                    for (Habitacion h :
                            hotel.getHabitaciones().obtenerTodos()) {

                        // NO MOSTRAR HABITACIÓN ACTUAL
                        if (h.getNumeroHabitacion()
                                == reserva.getHabitacion()
                                .getNumeroHabitacion()) {
                            continue;
                        }

                        // NO MOSTRAR HABITACIONES EN REPARACIÓN
                        if (h.getEstadoMantenimiento()
                                != EEstadoMantenimiento.NORMAL) {
                            continue;
                        }

                        // VALIDAR CAPACIDAD
                        if (h.getCantidadMaximaPersonas()
                                < reserva.getCantidadPasajeros()) {
                            continue;
                        }

                        // VALIDAR FECHAS
                        if (!hotel.habitacionDisponible(
                                h,
                                reserva.getFechaIngreso(),
                                reserva.getFechaSalida())) {
                            continue;
                        }

                        opciones.add(h);
                    }

                    if (opciones.isEmpty()) {

                        System.out.println(AMARILLO +
                                "║  No hay otras habitaciones disponibles"
                                + RESET);
                        break;
                    }

                    // MOSTRAR AL LADO Y AGRUPADAS POR PISO
                    mostrarHabitacionesPorPiso(opciones);

                    Habitacion seleccionada = null;

                    while (seleccionada == null) {

                        System.out.print(VERDE +
                                "║  Número de habitación (0 para volver): "
                                + RESET);

                        String numero = sc.nextLine().trim();

                        if (numero.equals("0")) {
                            break;
                        }

                        for (Habitacion h : opciones) {

                            if (String.valueOf(h.getNumeroHabitacion())
                                    .equals(numero)) {

                                seleccionada = h;
                                break;
                            }
                        }

                        if (seleccionada == null) {

                            System.out.println(ROJO +
                                    "║  Habitación inválida o no disponible"
                                    + RESET);
                        }
                    }

                    if (seleccionada == null) {
                        break;
                    }

                    System.out.println(AZUL +
                            "║  Nueva habitación: " +
                            seleccionada.getNumeroHabitacion() + RESET);

                    if (confirmarEliminacion()) {

                        reserva.setHabitacion(seleccionada);

                        System.out.println(VERDE +
                                "║  Habitación modificada correctamente"
                                + RESET);

                    } else {

                        System.out.println(AMARILLO +
                                "║  Modificación cancelada" + RESET);
                    }

                    break;
                }

                // ============================================
                // 0 - SALIR DEL SUBMENÚ
                // ============================================

                case 0:

                    System.out.println(AMARILLO +
                            "║  Volviendo al menú anterior..." + RESET);
                    break;
            }

            if (opcion != 0) {
                esperar(2);
            }

        } while (opcion != 0);
    }


    // ============================================================
    // eliminarReserva
    // ============================================================
    public static void eliminarReserva(
            Hotel hotel,
            Usuario usuario,
            boolean esEmpleado) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              ELIMINAR RESERVA             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  ◈ Código de reserva" + RESET);

        System.out.print(VERDE + "║  ➜ " + RESET);

        String id = sc.nextLine().trim();

        Reserva reserva = hotel.buscarReserva(id);

        // VERIFICAR SI EXISTE
        if (reserva == null) {

            System.out.println(ROJO +
                    "║  ERROR: Reserva no encontrada" + RESET);

            return;
        }

        // VERIFICAR PERMISOS
        if (!puedeGestionarReserva(
                reserva, usuario, esEmpleado)) {

            System.out.println(ROJO +
                    "║  No podés eliminar reservas de otros pasajeros"
                    + RESET);

            return;
        }

        // MOSTRAR RESERVA ANTES DE ELIMINAR
        imprimirReserva(reserva);

        // CONFIRMAR ELIMINACIÓN
        if (confirmarEliminacion()) {

            if (hotel.eliminarReserva(id)) {

                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println(
                        "║        RESERVA ELIMINADA CON ÉXITO        ║");
                System.out.println(
                        "╚═══════════════════════════════════════════╝"
                                + RESET);

            } else {

                System.out.println(ROJO +
                        "║  ERROR: No se pudo eliminar la reserva"
                        + RESET);
            }

        } else {

            System.out.println(AMARILLO +
                    "║  Eliminación cancelada" + RESET);
        }
    }


    // ============================================================
    // mostrarReservas
    // ============================================================
    public static void mostrarReservas(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              MOSTRAR RESERVAS             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (hotel.mostrarReservas().isEmpty()) {

            System.out.println(ROJO +
                    "║  No hay reservas registradas" + RESET);

        } else {

            for (Reserva reserva : hotel.mostrarReservas()) {

                imprimirReserva(reserva);

                System.out.println(CYAN +
                        "╠═══════════════════════════════════════════╣"
                        + RESET);
            }
        }

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝"
                + RESET);
    }


}
