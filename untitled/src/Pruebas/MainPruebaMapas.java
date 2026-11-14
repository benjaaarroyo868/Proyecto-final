package Pruebas;

import Exepciones.DatoInvalidoException;
import Generico.RegistroGenericoMapas;

public class MainPruebaMapas {




        public static void main(String[] args) throws Exception {
            RegistroGenericoMapas<String, String> mapa = new RegistroGenericoMapas<>();
            mapa.cargar("101", "Habitacion 101");
            comprobar("Agregar", mapa.containsKey("101"));
            comprobar("Buscar", mapa.buscar("101").equals("Habitacion 101"));
            mapa.modificar("101", "Habitacion modificada");
            comprobar("Modificar", mapa.buscar("101").equals("Habitacion modificada"));
            try {
                mapa.cargar("101", "Otra habitacion");
                throw new AssertionError("Permitio una clave duplicada");
            } catch (DatoInvalidoException e) {
                System.out.println("OK - Rechaza claves duplicadas");
            }
            mapa.eliminar("101");
            comprobar("Eliminar", !mapa.containsKey("101"));
            try {
                mapa.buscar("999");
                throw new AssertionError("Encontro una clave inexistente");
            } catch (DatoInvalidoException e) {
                System.out.println("OK - Rechaza claves inexistentes");
            }
        }
        private static void comprobar(String prueba, boolean resultado) {
            if (!resultado) throw new AssertionError("FALLO - " + prueba);
            System.out.println("OK - " + prueba);
        }
    }

