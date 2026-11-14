package Conexion;

import Clases.Usuario;
import Enums.ETipoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ConexionUsuarios extends ConexionBase {

    public void crearTabla() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS Usuarios(
                        nombreUsuario VARCHAR(30) NOT NULL PRIMARY KEY,
                        contrasenia VARCHAR(30) NOT NULL,
                        TipoUsuario VARCHAR(30)
                    )
                    """;
        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    public void insertarUsuario(Usuario u) {
        String sql = "INSERT INTO Usuarios(nombreUsuario, contrasenia, TipoUsuario) VALUES (?, ?, ?)";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getContrasenia());
            ps.setString(3, u.getTipoDeUsuario().name()); // enum guardado como texto

            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("MySQL no confirmó la inserción en insertarUsuario");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    /** Conserva la misma cuenta si ya existe; no intenta insertarla de nuevo. */
    public void asegurarUsuario(Usuario usuario) {
        if (!existe("SELECT 1 FROM Usuarios WHERE nombreUsuario=?", usuario.getNombreUsuario())) {
            insertarUsuario(usuario);
        }
    }

    // MODIFICAR USUARIO
    // Si cambia el nombre de usuario: crea la cuenta nueva, actualiza las referencias
    // y borra la vieja. Conviene ejecutarlo dentro de ConexionBD.ejecutarTransaccion().
    public void modificarUsuario(String nombreUsuario, String nuevoNombreUsuario, String nuevaContrasenia) {
        Connection conn = ConexionBD.abrir();
        try {
            if (nombreUsuario.equals(nuevoNombreUsuario)) {
                // Si solo cambia la contrasenia, basta con un UPDATE.
                try (PreparedStatement ps = conn.prepareStatement(
                        "UPDATE Usuarios SET contrasenia = ? WHERE nombreUsuario = ?")) {
                    ps.setString(1, nuevaContrasenia);
                    ps.setString(2, nombreUsuario);
                    if (ps.executeUpdate() != 1) {
                        throw new IllegalStateException("El usuario no existe en MySQL");
                    }
                }
                return;
            }

            // Recuperar el tipo de usuario de la cuenta original.
            ETipoUsuario tipo;
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT TipoUsuario FROM Usuarios WHERE nombreUsuario = ?")) {
                ps.setString(1, nombreUsuario);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new IllegalStateException("El usuario no existe en MySQL");
                    }
                    tipo = ETipoUsuario.valueOf(rs.getString("TipoUsuario"));
                }
            }

            // nombreUsuario es PK referenciada por otras tablas: primero se crea la cuenta
            // nueva, se actualizan las referencias y despues se elimina la vieja.
            insertarUsuario(new Usuario(nuevoNombreUsuario, nuevaContrasenia, tipo));

            actualizarReferenciaUsuario(conn, "Pasajeros", nombreUsuario, nuevoNombreUsuario);
            actualizarReferenciaUsuario(conn, "Tecnicos", nombreUsuario, nuevoNombreUsuario);
            actualizarReferenciaUsuario(conn, "Administradores", nombreUsuario, nuevoNombreUsuario);
            actualizarReferenciaUsuario(conn, "Conserjes", nombreUsuario, nuevoNombreUsuario);

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Usuarios WHERE nombreUsuario = ?")) {
                ps.setString(1, nombreUsuario);
                if (ps.executeUpdate() != 1) {
                    throw new IllegalStateException("No se pudo eliminar el usuario anterior");
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo modificar el usuario en MySQL: "
                    + e.getMessage(), e);
        } finally {
            ConexionBD.cerrar(conn);
        }
    }

    // Solo se llama con nombres de tabla escritos en este archivo.
    // Un UPDATE puede afectar 0 filas si el usuario no tiene ese tipo de perfil.
    private void actualizarReferenciaUsuario(Connection conn, String tabla, String anterior, String nuevo)
            throws SQLException {
        String sql = "UPDATE " + tabla + " SET nombreUsuario = ? WHERE nombreUsuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevo);
            ps.setString(2, anterior);
            ps.executeUpdate();
        }
    }

    public HashMap<String, Usuario> listarUsuarios() {
        HashMap<String, Usuario> mapa = new HashMap<>();
        String sql = "SELECT nombreUsuario, contrasenia, TipoUsuario FROM Usuarios";

        Connection conn = ConexionBD.abrir();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("nombreUsuario"),
                        rs.getString("contrasenia"),
                        ETipoUsuario.valueOf(rs.getString("TipoUsuario")) // varchar -> enum
                );
                mapa.put(u.getNombreUsuario(), u);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error en MySQL: " + ex.getMessage(), ex);
        } finally {
            ConexionBD.cerrar(conn);
        }
        return mapa;
    }

    public void eliminarUsuarioPorNombre(String nombre) {
        eliminarEstricto("DELETE FROM Usuarios WHERE nombreUsuario=?", nombre);
    }
}
