import Clases.*;
import Enums.*;
import Exepciones.AgenciaHotelException;
import Generico.RegistroGenericoMapas;
import Menu.*;
import Interfaces.*;

void main() {

     Menu m = new Menu();

     Hotel h1 = new Hotel("Conte", "mar del plata", 20);


     Usuario usuarioAdmin = new Usuario(
             "adminBenja",
             "Admin1234",
             ETipoUsuario.ADMINISTRADOR
     );

     Administrador administrador = new Administrador(
             "Benjamin",
             "Arroyo",
             "12345678",
             "2234567890",
             "benjamin@gmail.com",
             usuarioAdmin,
             "ADM001",
             1500000.0
     );

     Usuario usuarioPasajero = new Usuario(
             "juanPerez",
             "Juan1234",
             ETipoUsuario.PASAJERO
     );

     Pasajero pasajero = new Pasajero(
             "Juan",
             "Perez",
             "48033021",
             "2235678901",
             "juan@gmail.com",
             usuarioPasajero,
             "Mar del Plata",
             "Buenos Aires"
     );


    Usuario usuarioConserje = new Usuario(
            "MartinZalez1234",
            "Martin1234@",
            ETipoUsuario.CONSERJE);

    Conserje conserje = new Conserje(
            "Martin",
            "Gonzalez",
            "35123456",
            "2234567890",
            "martin.gonzalez@gmail.com",
            usuarioConserje,
            "CON001",
            15000.0
    );

    Usuario usuarioTecnico = new Usuario(
            "carlos123",
            "Hotel1234",
            ETipoUsuario.TECNICO
    );

    Tecnico tecnico = new Tecnico(
            "Carlos",
            "Rodriguez",
            "32145678",
            "2234567890",
            "carlos.rodriguez@gmail.com",
            usuarioTecnico,
            ETipoTecnico.TECNICO_ELECTRONICO,
            "Técnico en instalaciones eléctricas",
            true
    );

    Habitacion habitacion1 = new Habitacion(101, 2, 2);
    Habitacion habitacion2 = new Habitacion(102, 3, 4);


    try {
        h1.agregarTecnico(tecnico);
        h1.agregarUsuario(usuarioAdmin);
        h1.agregarUsuario(usuarioPasajero);
        h1.agregarUsuario(usuarioConserje);
        h1.agregarUsuario(usuarioTecnico);
        h1.agregarEmpleado(administrador);
        h1.agregarEmpleado(conserje);
        h1.agregarPasajero(pasajero);
        h1.agregarTecnico(tecnico);
        h1.agregarHabitacion(habitacion1);
        h1.agregarHabitacion(habitacion2);
    } catch (AgenciaHotelException e) {
        throw new RuntimeException(e);
    }


    m.menuPrincipal(h1);
}
