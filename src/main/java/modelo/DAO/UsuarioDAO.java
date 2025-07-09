package modelo.DAO;

import modelo.conexion.Conexionbd;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import modelo.DTO.Usuario;

public class UsuarioDAO {
    
    public boolean registrarUsuario(Usuario u) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO usuarios (nombre, apellido, usuario, correo, clave) VALUES (?, ?, ?, ?, ?)";
    try (Connection con = Conexionbd.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, u.getNombre());
        ps.setString(2, u.getApellido());
        ps.setString(3, u.getUsuario());
        ps.setString(4, u.getCorreo());
        ps.setString(5, u.getClave());

        return ps.executeUpdate() > 0;
    }
}
    
    public Usuario validarUsuario(String correo, String clave) throws SQLException, ClassNotFoundException {
    String sql = "SELECT * FROM usuarios WHERE correo = ? AND clave = ?";
    try (Connection con = Conexionbd.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
         
        ps.setString(1, correo);
        ps.setString(2, clave);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setUsuario(rs.getString("usuario"));
                u.setCorreo(rs.getString("correo"));
                u.setClave(rs.getString("clave"));
                return u;
            } else {
                return null;
            }
        }
    }
}
    public boolean correoExiste(String correo) throws SQLException, ClassNotFoundException {
    String sql = "SELECT id FROM usuarios WHERE correo = ?";
    try (Connection con = Conexionbd.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, correo);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // Si hay resultados, el correo ya existe
    }
}

    public boolean usuarioExiste(String usuario) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM usuarios WHERE usuario = ?";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Si hay resultados, el nombre de usuario ya existe
        }
}
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
