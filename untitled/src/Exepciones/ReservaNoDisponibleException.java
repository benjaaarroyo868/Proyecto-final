package Exepciones;

public class ReservaNoDisponibleException extends AgenciaHotelException {
    public ReservaNoDisponibleException(String message) {
        super(message);
    }
}
