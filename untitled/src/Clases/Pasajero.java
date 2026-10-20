package Clases;

public class Pasajero extends Persona{
    private String origen;
    private String destino_origen;

    public Pasajero(String nombre, String apellido, String DNI, int telefono, String email, Usuario usuario, String origen, String destino_origen) {
        super(nombre, apellido, DNI, telefono, email, usuario);
        this.origen = origen;
        this.destino_origen = destino_origen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino_origen() {
        return destino_origen;
    }

    public void setDestino_origen(String destino_origen) {
        this.destino_origen = destino_origen;
    }

    @Override
    public String toString() {
        return "Pasajero{" + super.toString()+
                "origen='" + origen + '\'' +
                ", destino_origen='" + destino_origen + '\'' +
                '}';
    }
}
