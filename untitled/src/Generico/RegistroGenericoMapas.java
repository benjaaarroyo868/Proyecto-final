package Generico;

import Exepciones.DatoInvalidoException;

import java.util.HashMap;
import java.util.Map;

public class RegistroGenericoMapas<K,V>{

    Map<K,V> mapa;

    public RegistroGenericoMapas() {
        mapa=new HashMap<>();
    }


    public void cargar(K clave, V valor) throws DatoInvalidoException {
        if (clave == null || valor == null) {
            throw new DatoInvalidoException("La clave y el valor no pueden ser nulos");
        }

        if (mapa.containsKey(clave)) {
            throw new DatoInvalidoException("Ya existe un elemento con esa clave");
        }

        mapa.put(clave, valor);
    }

    public void eliminar(K clave) throws DatoInvalidoException {
        if (clave == null) {
            throw new DatoInvalidoException("La clave no puede ser nula");
        }

        if (!mapa.containsKey(clave)) {
            throw new DatoInvalidoException("No existe un elemento con esa clave");
        }

        mapa.remove(clave);
    }

    public void mostrar() throws DatoInvalidoException {
        if (mapa.isEmpty()) {
            throw new DatoInvalidoException("No hay elementos para mostrar");
        }

        for (Map.Entry<K, V> entrada : mapa.entrySet()) {
            System.out.println("Clave: " + entrada.getKey());
            System.out.println("Valor: " + entrada.getValue());
            System.out.println("--------------------");
        }
    }

    public void modificar(K clave, V nuevoValor) throws DatoInvalidoException {
        if (clave == null || nuevoValor == null) {
            throw new DatoInvalidoException("La clave y el nuevo valor no pueden ser nulos");
        }

        if (!mapa.containsKey(clave)) {
            throw new DatoInvalidoException("No existe un elemento con esa clave");
        }

        mapa.put(clave, nuevoValor);
    }

    public V buscar(K clave) throws DatoInvalidoException {
        if (clave == null) {
            throw new DatoInvalidoException("La clave no puede ser nula");
        }

        if (!mapa.containsKey(clave)) {
            throw new DatoInvalidoException("No se encontró un elemento con esa clave");
        }

        return mapa.get(clave);
    }

}

}
