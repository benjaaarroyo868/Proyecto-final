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
import static Menu.ValidacionesMenu.*;


public class GestionHabitaciones {

    // ============================================================
    // mostrarHabitacionesDisponibles
    // ============================================================
    public static void mostrarHabitacionesDisponibles(Hotel hotel) {

        List<Habitacion> disponibles = new ArrayList<>();

        Collection<Habitacion> ocupadas =
                hotel.verHabitacionesOcupadas();

        // BUSCAR HABITACIONES DISPONIBLES
        for (Habitacion h : hotel.getHabitaciones().obtenerTodos()) {

            if (h.getEstadoMantenimiento() == EEstadoMantenimiento.NORMAL
                    && !ocupadas.contains(h)) {

                disponibles.add(h);
            }
        }

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          HABITACIONES DISPONIBLES         ║");
        System.out.println("╚═══════════════════════════════════════════╝"
                + RESET);

        // MOSTRAR HABITACIONES AGRUPADAS POR PISO
        mostrarHabitacionesPorPiso(disponibles);
    }


    // ============================================================
    // mostrarHabitacionesOcupadas
    // ============================================================
    public static void mostrarHabitacionesOcupadas(Hotel hotel) {

        // OBTENER HABITACIONES OCUPADAS
        List<Habitacion> ocupadas = new ArrayList<>(
                hotel.verHabitacionesOcupadas()
        );

        // INCLUIR HABITACIONES EN MANTENIMIENTO
        for (Habitacion h : hotel.getHabitaciones().obtenerTodos()) {

            if (h.getEstadoMantenimiento()
                    == EEstadoMantenimiento.EN_REPARACION
                    || h.getEstadoMantenimiento()
                    == EEstadoMantenimiento.NECESITA_REPARACION) {

                // EVITAR HABITACIONES REPETIDAS
                if (!ocupadas.contains(h)) {
                    ocupadas.add(h);
                }
            }
        }

        // ENCABEZADO
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║       HABITACIONES NO DISPONIBLES         ║");
        System.out.println("╚═══════════════════════════════════════════╝"
                + RESET);

        // MOSTRAR HABITACIONES AGRUPADAS POR PISO
        mostrarHabitacionesPorPiso(ocupadas);
    }


    // ============================================================
    // ponerHabitacionEnReparacion
    // ============================================================
    public static void ponerHabitacionEnReparacion(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          GESTIÓN DE HABITACIONES          ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // OBTENER HABITACIONES

        java.util.Collection<Habitacion> habitaciones =
                hotel.getHabitaciones().obtenerTodos();

        if (habitaciones.isEmpty()) {

            System.out.println(ROJO +
                    "║  ERROR: No hay habitaciones registradas" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);
            return;
        }

        // MOSTRAR TODAS LAS HABITACIONES AGRUPADAS POR PISO
        // CON EL ESTADO DE MANTENIMIENTO DE CADA UNA.
        System.out.println(AMARILLO +
                "║  HABITACIONES REGISTRADAS" + RESET);

        mostrarHabitacionesPorPiso(habitaciones);

        System.out.println(AMARILLO +
                "║  0. Volver al menú" + RESET);

        System.out.println(CYAN +
                "╠═══════════════════════════════════════════╣" + RESET);

        // =========================================
        // SELECCIONAR HABITACIÓN
        // =========================================

        Habitacion habitacion = null;

        while (habitacion == null) {

            System.out.print(AZUL +
                    "║  Número de habitación: " + RESET);

            String numeroIngresado = sc.nextLine().trim();

            if (numeroIngresado.equals("0")) {

                System.out.println(AMARILLO +
                        "║  Operación cancelada" + RESET);
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
                        "║  ERROR: La habitación no existe" + RESET);

                System.out.println(AMARILLO +
                        "║  Intentá nuevamente o ingresá 0" + RESET);
            }
        }

        // =========================================
        // SELECCIONAR ESTADO
        // =========================================

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║              CAMBIAR ESTADO               ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  Habitación: " + habitacion.getNumeroHabitacion());

        System.out.println(
                "║  Estado actual: " +
                        habitacion.getEstadoMantenimiento() + RESET);

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        System.out.println(VERDE +
                "║  1. NORMAL                                ║" + RESET);

        System.out.println(AMARILLO +
                "║  2. NECESITA_REPARACION                   ║" + RESET);

        System.out.println(ROJO +
                "║  3. EN_REPARACION                         ║" + RESET);

        System.out.println(AMARILLO +
                "║  0. Volver al menú                        ║" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        int opcionEstado = pedirOpcion(0, 3);

        if (opcionEstado == 0) {

            System.out.println(AMARILLO +
                    "Operación cancelada" + RESET);
            return;
        }

        EEstadoMantenimiento estadoActual =
                habitacion.getEstadoMantenimiento();

        // =========================================
        // OPCIÓN 1: NORMAL
        // =========================================

        if (opcionEstado == 1) {

            if (estadoActual == EEstadoMantenimiento.NORMAL) {

                System.out.println(AMARILLO +
                        "La habitación ya está en estado NORMAL" + RESET);
                return;
            }

            try {

                if (estadoActual ==
                        EEstadoMantenimiento.EN_REPARACION) {

                    // FINALIZAR REPARACIÓN Y LIBERAR TÉCNICO
                    hotel.finalizarReparacion(habitacion);

                } else {

                    habitacion.setEstadoMantenimiento(
                            EEstadoMantenimiento.NORMAL);
                }

                System.out.println();
                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║       ESTADO CAMBIADO CORRECTAMENTE       ║");
                System.out.println("╠═══════════════════════════════════════════╣");

                System.out.println("║  Habitación: " +
                        habitacion.getNumeroHabitacion());

                System.out.println("║  Nuevo estado: " +
                        habitacion.getEstadoMantenimiento());

                System.out.println(
                        "╚═══════════════════════════════════════════╝"
                                + RESET);

            } catch (IllegalStateException e) {

                System.out.println(ROJO +
                        "ERROR: " + e.getMessage() + RESET);
            }

            return;
        }

        // =========================================
        // OPCIÓN 2: NECESITA REPARACIÓN
        // =========================================

        if (opcionEstado == 2) {

            if (estadoActual ==
                    EEstadoMantenimiento.NECESITA_REPARACION) {

                System.out.println(AMARILLO +
                        "La habitación ya necesita reparación" + RESET);
                return;
            }

            if (estadoActual ==
                    EEstadoMantenimiento.EN_REPARACION) {

                System.out.println(ROJO +
                        "ERROR: La habitación está en reparación" + RESET);

                System.out.println(AMARILLO +
                        "Primero debés finalizar la reparación" + RESET);
                return;
            }

            try {

                hotel.registrarNecesidadReparacion(habitacion);

                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║       ESTADO CAMBIADO CORRECTAMENTE       ║");
                System.out.println("╠═══════════════════════════════════════════╣");

                System.out.println("║  Habitación: " +
                        habitacion.getNumeroHabitacion());

                System.out.println("║  Nuevo estado: " +
                        habitacion.getEstadoMantenimiento());

                System.out.println(
                        "╚═══════════════════════════════════════════╝"
                                + RESET);

            } catch (IllegalArgumentException |
                     IllegalStateException e) {

                System.out.println(ROJO +
                        "ERROR: " + e.getMessage() + RESET);
            }

            return;
        }

        // =========================================
        // OPCIÓN 3: EN REPARACIÓN
        // =========================================

        if (opcionEstado == 3) {

            if (estadoActual ==
                    EEstadoMantenimiento.EN_REPARACION) {

                System.out.println(AMARILLO +
                        "La habitación ya está en reparación" + RESET);

                Tecnico asignado =
                        hotel.obtenerTecnicoAsignado(habitacion);

                if (asignado != null) {

                    System.out.println(AZUL +
                            "Técnico: " + asignado.getNombre());

                    System.out.println(
                            "Tipo: " + asignado.getTipo_de_tecnico());

                    System.out.println(
                            "Teléfono: " + asignado.getTelefono() + RESET);
                }

                return;
            }

            // OBTENER TÉCNICOS DISPONIBLES

            java.util.List<Tecnico> tecnicos =
                    new java.util.ArrayList<>();

            for (Tecnico t : hotel.verTecnicos()) {

                if (t.isDisponible()) {
                    tecnicos.add(t);
                }
            }

            if (tecnicos.isEmpty()) {

                System.out.println(ROJO +
                        "ERROR: No hay técnicos disponibles" + RESET);
                return;
            }

            // MOSTRAR TÉCNICOS

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║              ASIGNAR TÉCNICO              ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL +
                    "║  Habitación: " +
                    habitacion.getNumeroHabitacion() + RESET);

            for (int i = 0; i < tecnicos.size(); i++) {

                Tecnico t = tecnicos.get(i);

                System.out.println(CYAN +
                        "║                                           ║" + RESET);

                System.out.println(AZUL +
                        "║  " + (i + 1) + ". Nombre: " + t.getNombre());

                System.out.println(
                        "║     Tipo: " + t.getTipo_de_tecnico());

                System.out.println(
                        "║     Teléfono: " + t.getTelefono() + RESET);
            }

            System.out.println(AMARILLO +
                    "║  0. Cancelar" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

            int opcionTecnico = pedirOpcion(0, tecnicos.size());

            if (opcionTecnico == 0) {
                System.out.println(AMARILLO +
                        "Asignación cancelada" + RESET);
                return;
            }

            Tecnico seleccionado =
                    tecnicos.get(opcionTecnico - 1);

            // CONFIRMAR ASIGNACIÓN

            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║           CONFIRMAR ASIGNACIÓN            ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(AZUL +
                    "║  Habitación: " +
                    habitacion.getNumeroHabitacion());

            System.out.println(
                    "║  Técnico: " + seleccionado.getNombre());

            System.out.println(
                    "║  Tipo: " + seleccionado.getTipo_de_tecnico());

            System.out.println(
                    "║  Teléfono: " + seleccionado.getTelefono() + RESET);

            System.out.println(AMARILLO +
                    "║  ¿Confirmar asignación? (Si/No)" + RESET);

            String respuesta;

            while (true) {

                System.out.print(VERDE + "║  ➜ " + RESET);
                respuesta = sc.nextLine().trim();

                if (respuesta.equalsIgnoreCase("Si") ||
                        respuesta.equalsIgnoreCase("S") ||
                        respuesta.equalsIgnoreCase("No") ||
                        respuesta.equalsIgnoreCase("N") ||
                        respuesta.equals("0")) {
                    break;
                }

                System.out.println(ROJO +
                        "║  ERROR: Ingresá Si, No o 0" + RESET);
            }

            if (respuesta.equalsIgnoreCase("No") ||
                    respuesta.equalsIgnoreCase("N") ||
                    respuesta.equals("0")) {

                System.out.println(AMARILLO +
                        "Asignación cancelada" + RESET);
                return;
            }

            // GUARDAR ASIGNACIÓN

            try {

                if (!seleccionado.isDisponible()) {

                    throw new IllegalStateException(
                            "El técnico no está disponible");
                }

                hotel.asignarTecnico(habitacion, seleccionado);

                System.out.println();
                System.out.println(VERDE +
                        "╔═══════════════════════════════════════════╗");
                System.out.println("║       TÉCNICO ASIGNADO CORRECTAMENTE      ║");
                System.out.println("╠═══════════════════════════════════════════╣");

                System.out.println("║  Habitación: " +
                        habitacion.getNumeroHabitacion());

                System.out.println("║  Técnico: " +
                        seleccionado.getNombre());

                System.out.println("║  Tipo: " +
                        seleccionado.getTipo_de_tecnico());

                System.out.println("║  Teléfono: " +
                        seleccionado.getTelefono());

                System.out.println("║  Nuevo estado: " +
                        habitacion.getEstadoMantenimiento());

                System.out.println(
                        "╚═══════════════════════════════════════════╝"
                                + RESET);

            } catch (IllegalArgumentException |
                     IllegalStateException e) {

                System.out.println(ROJO +
                        "║  ERROR: " + e.getMessage() + RESET);
            }
        }
    }


    // ============================================================
    // ponerHabitacionReparada
    // ============================================================
    public static void ponerHabitacionReparada(Hotel hotel) {

        // OBTENER HABITACIONES EN REPARACIÓN
        java.util.List<Habitacion> habitaciones =
                hotel.verHabitacionesEnReparacion();

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║          FINALIZAR REPARACIÓN             ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // VERIFICAR SI HAY HABITACIONES EN REPARACIÓN
        if (habitaciones.isEmpty()) {

            System.out.println(AMARILLO +
                    "║  No hay habitaciones en reparación       ║" + RESET);

            System.out.println(CYAN +
                    "╚═══════════════════════════════════════════╝" + RESET);

            return;
        }

        // MOSTRAR SOLO LAS QUE ESTÁN EN REPARACIÓN,
        // AGRUPADAS POR PISO Y CON EL ESTADO ACTUAL.
        mostrarHabitacionesPorPiso(habitaciones);

        Habitacion seleccionada = null;

        // SELECCIONAR HABITACIÓN
        while (seleccionada == null) {

            System.out.println();
            System.out.println(AMARILLO +
                    "Ingresá el número de habitación o 0 para volver"
                    + RESET);

            System.out.print(VERDE +
                    "➜ " + RESET);

            String numero = sc.nextLine().trim();

            // VOLVER AL MENÚ
            if (numero.equals("0")) {

                System.out.println(AMARILLO +
                        "Operación cancelada" + RESET);

                return;
            }

            // BUSCAR HABITACIÓN
            for (Habitacion h : habitaciones) {

                if (String.valueOf(h.getNumeroHabitacion())
                        .equals(numero)) {

                    seleccionada = h;
                    break;
                }
            }

            // NÚMERO INVÁLIDO
            if (seleccionada == null) {

                System.out.println(ROJO +
                        "ERROR: La habitación no existe o no está en reparación"
                        + RESET);
            }
        }

        // CONFIRMAR FINALIZACIÓN
        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║           CONFIRMAR REPARACIÓN            ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        System.out.println(AZUL +
                "║  Habitación: " +
                seleccionada.getNumeroHabitacion() + RESET);

        System.out.println(AMARILLO +
                "║  Estado: " +
                seleccionada.getEstadoMantenimiento() + RESET);

        System.out.println(CYAN +
                "║                                           ║" + RESET);

        System.out.println(AMARILLO +
                "║  ¿Finalizó la reparación? (Si/No)         ║" + RESET);

        System.out.println(CYAN +
                "╚═══════════════════════════════════════════╝" + RESET);

        String respuesta;

        // VALIDAR RESPUESTA
        while (true) {

            System.out.print(VERDE + "➜ " + RESET);

            respuesta = sc.nextLine().trim();

            if (respuesta.equalsIgnoreCase("Si") ||
                    respuesta.equalsIgnoreCase("S") ||
                    respuesta.equalsIgnoreCase("No") ||
                    respuesta.equalsIgnoreCase("N")) {

                break;
            }

            System.out.println(ROJO +
                    "ERROR: Ingresá Si o No" + RESET);
        }

        // CANCELAR
        if (respuesta.equalsIgnoreCase("No") ||
                respuesta.equalsIgnoreCase("N")) {

            System.out.println(AMARILLO +
                    "La reparación no fue finalizada" + RESET);

            return;
        }

        try {

            // FINALIZAR REPARACIÓN
            // LIBERA AL TÉCNICO Y CAMBIA A REPARADA
            hotel.finalizarReparacion(seleccionada);

            // MOSTRAR ESTADO REPARADA
            System.out.println();
            System.out.println(VERDE +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║          HABITACIÓN REPARADA              ║");
            System.out.println("╠═══════════════════════════════════════════╣");

            System.out.println("║  Habitación: " +
                    seleccionada.getNumeroHabitacion());

            System.out.println("║  Estado: " +
                    seleccionada.getEstadoMantenimiento());

            System.out.println("║  Técnico liberado correctamente");

            System.out.println("╚═══════════════════════════════════════════╝"
                    + RESET);

            // MANTENER REPARADA DURANTE 2 SEGUNDOS
            esperar(2);

            // CAMBIAR AUTOMÁTICAMENTE A NORMAL
            seleccionada.setEstadoMantenimiento(
                    EEstadoMantenimiento.NORMAL);

            // MOSTRAR ESTADO FINAL
            System.out.println();
            System.out.println(CYAN +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║         HABITACIÓN HABILITADA             ║");
            System.out.println("╠═══════════════════════════════════════════╣" + RESET);

            System.out.println(VERDE +
                    "║  Habitación: " +
                    seleccionada.getNumeroHabitacion());

            System.out.println("║  Estado actual: " +
                    seleccionada.getEstadoMantenimiento());

            System.out.println("║  Habitación habilitada correctamente");

            System.out.println("╚═══════════════════════════════════════════╝"
                    + RESET);

        } catch (IllegalStateException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }
    }


    // ============================================================
    // mostrarHabitacionesPorPiso
    // ============================================================
    public static void mostrarHabitacionesPorPiso(
            Collection<Habitacion> habitaciones) {

        Map<Integer, List<Habitacion>> pisos = new TreeMap<>();

        // AGRUPAR HABITACIONES POR PISO
        for (Habitacion h : habitaciones) {

            int piso = h.getNumeroHabitacion() / 100;

            if (!pisos.containsKey(piso)) {
                pisos.put(piso, new ArrayList<>());
            }

            pisos.get(piso).add(h);
        }

        if (pisos.isEmpty()) {

            System.out.println(AMARILLO +
                    "No hay habitaciones para mostrar" + RESET);
            return;
        }

        // RECORRER LOS PISOS
        for (Map.Entry<Integer, List<Habitacion>> entrada
                : pisos.entrySet()) {

            int piso = entrada.getKey();
            List<Habitacion> lista = entrada.getValue();

            // ORDENAR HABITACIONES POR NÚMERO
            lista.sort(Comparator.comparingInt(
                    Habitacion::getNumeroHabitacion));

            System.out.println();
            System.out.println(CYAN +
                    "══════════════════════════════════════════════════════");
            System.out.println("  PISO " + piso + " | " +
                    lista.size() + " habitaciones");
            System.out.println(
                    "══════════════════════════════════════════════════════"
                            + RESET);

            int contador = 0;

            // MOSTRAR HABITACIONES Y SUS ESTADOS
            for (Habitacion h : lista) {

                System.out.printf(
                        VERDE + "[ HAB. %d ] " + RESET +
                                AMARILLO + "%-19s" + RESET + "  ",
                        h.getNumeroHabitacion(),
                        h.getEstadoMantenimiento()
                );

                contador++;

                // CADA 2 HABITACIONES BAJAR DE RENGLÓN
                if (contador % 2 == 0) {
                    System.out.println();
                }
            }

            if (contador % 2 != 0) {
                System.out.println();
            }
        }

        System.out.println();
    }


    // ============================================================
    // llamarTecnico
    // ============================================================
    public static void llamarTecnico(Hotel hotel) {

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║               LLAMAR TÉCNICO              ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        // MOSTRAR HABITACIONES EN REPARACIÓN

        System.out.println(AMARILLO +
                "HABITACIONES ACTUALMENTE EN REPARACIÓN:" + RESET);

        Collection<Habitacion> habitacionesEnReparacion =
                hotel.verHabitacionesEnReparacion();

        if (habitacionesEnReparacion.isEmpty()) {

            System.out.println(VERDE +
                    "No hay habitaciones en reparación" + RESET);

        } else {

            mostrarHabitacionesPorPiso(habitacionesEnReparacion);

            // MOSTRAR TAMBIÉN QUÉ TÉCNICO ESTÁ ASIGNADO
            for (Habitacion h : habitacionesEnReparacion) {
                Tecnico tecnico = hotel.obtenerTecnicoAsignado(h);
                if (tecnico != null) {
                    System.out.println(AZUL +
                            "Técnico de habitación " + h.getNumeroHabitacion()
                            + ": " + tecnico.getNombre() + RESET);
                }
            }
        }

        // OBTENER HABITACIONES PENDIENTES

        java.util.List<Habitacion> habitaciones =
                hotel.verHabitacionesParaReparar();

        System.out.println(AMARILLO +
                "\nHABITACIONES QUE NECESITAN REPARACIÓN:" + RESET);

        if (habitaciones.isEmpty()) {

            System.out.println(VERDE +
                    "No hay habitaciones pendientes de reparación"
                    + RESET);
            return;
        }

        // MOSTRAR HABITACIONES POR PISO Y CON SU ESTADO
        mostrarHabitacionesPorPiso(habitaciones);

        // SELECCIONAR POR NÚMERO REAL (101, 102, 201...), NO ÍNDICE
        Habitacion seleccionada = null;

        while (seleccionada == null) {
            System.out.print(VERDE +
                    "Número de habitación (0 para volver): " + RESET);

            String numeroIngresado = sc.nextLine().trim();

            if (numeroIngresado.equals("0")) {
                System.out.println(AMARILLO +
                        "Asignación cancelada" + RESET);
                return;
            }

            for (Habitacion h : habitaciones) {
                if (String.valueOf(h.getNumeroHabitacion())
                        .equals(numeroIngresado)) {
                    seleccionada = h;
                    break;
                }
            }

            if (seleccionada == null) {
                System.out.println(ROJO +
                        "ERROR: Elegí una habitación de las mostradas" + RESET);
            }
        }

        // OBTENER TÉCNICOS DISPONIBLES

        java.util.List<Tecnico> tecnicos =
                new java.util.ArrayList<>();

        for (Tecnico t : hotel.verTecnicos()) {

            if (t.isDisponible()) {
                tecnicos.add(t);
            }
        }

        System.out.println();
        System.out.println(CYAN +
                "╔═══════════════════════════════════════════╗");
        System.out.println("║             TÉCNICOS DISPONIBLES          ║");
        System.out.println("╠═══════════════════════════════════════════╣" + RESET);

        if (tecnicos.isEmpty()) {

            System.out.println(ROJO +
                    "No hay técnicos disponibles" + RESET);
            return;
        }

        for (int i = 0; i < tecnicos.size(); i++) {

            Tecnico t = tecnicos.get(i);

            System.out.println(AZUL +
                    (i + 1) + ". " + t.getNombre());

            System.out.println(
                    "Tipo: " + t.getTipo_de_tecnico());

            System.out.println(
                    "Teléfono: " + t.getTelefono() + RESET);
        }

        // SELECCIONAR TÉCNICO

        System.out.println(AMARILLO +
                "\nSeleccioná el técnico:" + RESET);

        int opcionTecnico =
                pedirOpcion(1, tecnicos.size());

        Tecnico tecnicoSeleccionado =
                tecnicos.get(opcionTecnico - 1);

        // ASIGNAR TÉCNICO

        try {

            hotel.asignarTecnico(
                    seleccionada,
                    tecnicoSeleccionado
            );

            System.out.println(VERDE +
                    "╔═══════════════════════════════════════════╗");
            System.out.println("║        TÉCNICO ASIGNADO CON ÉXITO         ║");
            System.out.println("╠═══════════════════════════════════════════╣");

            System.out.println("║  Habitación: " +
                    seleccionada.getNumeroHabitacion());

            System.out.println("║  Técnico: " +
                    tecnicoSeleccionado.getNombre());

            System.out.println("║  Estado: " +
                    seleccionada.getEstadoMantenimiento());

            System.out.println(
                    "╚═══════════════════════════════════════════╝"
                            + RESET);

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            System.out.println(ROJO +
                    "ERROR: " + e.getMessage() + RESET);
        }
    }


}
