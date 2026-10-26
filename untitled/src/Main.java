import Clases.*;
import Enums.*;
import Exepciones.AgenciaHotelException;
import Generico.RegistroGenericoMapas;
import Menu.*;

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
             "45678912",
             "2235678901",
             "juan@gmail.com",
             usuarioPasajero,
             "Mar del Plata",
             "Buenos Aires"
     );

    Pasajero pasajero1 = new Pasajero(
            "Sebastian",
            "Perez",
            "12345678",
            "2235678901",
            "juan@gmail.com",
            usuarioPasajero,
            "Mar del Plata",
            "Buenos Aires"
    );


    RegistroGenericoMapas<String, Pasajero> pasa = new RegistroGenericoMapas<>();


    try {
         h1.agregarUsuario(usuarioPasajero);
         h1.agregarUsuario(usuarioAdmin);

     } catch (AgenciaHotelException e) {
         throw new RuntimeException(e);
     }


    m.menuPrincipal(h1);
}
