package modelo.DAO;

import modelo.conexion.Conexionbd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import modelo.DTO.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {
    
    // Registrar usuario con contraseña cifrada
    public boolean registrarUsuario(Usuario u) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO usuarios (nombre, apellido, usuario, correo, clave) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getUsuario());
            ps.setString(4, u.getCorreo());

            // Cifrar la contraseña antes de guardar
            String hashed = BCrypt.hashpw(u.getClave(), BCrypt.gensalt());
            ps.setString(5, hashed);

            return ps.executeUpdate() > 0;
        }
    }
    
    // Validar usuario comparando correo y contraseña con BCrypt
    public Usuario validarUsuario(String correo, String clave) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, correo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String claveHashed = rs.getString("clave");
                    if (BCrypt.checkpw(clave, claveHashed)) {
                        Usuario u = new Usuario();
                        u.setId(rs.getInt("id"));
                        u.setNombre(rs.getString("nombre"));
                        u.setApellido(rs.getString("apellido"));
                        u.setUsuario(rs.getString("usuario"));
                        u.setCorreo(rs.getString("correo"));
                        u.setClave(claveHashed); // Almacenar el hash en el objeto
                        return u;
                    }
                }
                return null; // No coincide usuario o contraseña
            }
        }
    }
    
    // Verificar si el correo ya existe
    public boolean correoExiste(String correo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM usuarios WHERE correo = ?";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Verificar si el usuario ya existe
    public boolean usuarioExiste(String usuario) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM usuarios WHERE usuario = ?";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    // Obtener fecha de registro de usuario
    public Date obtenerFechaRegistro(int idUsuario) throws SQLException, ClassNotFoundException {
        Date fecha = null;
        String sql = "SELECT fecha_registro FROM usuarios WHERE id = ?";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fecha = rs.getTimestamp("fecha_registro");
                }
            }
        }
        return fecha;
    }
}
