package modelo.DAO;

import modelo.DTO.Tema;
import modelo.conexion.Conexionbd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TemaDAO {

    public boolean guardarTema(Tema tema) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO temas (titulo, contenido, fecha_publicacion, id_usuario) VALUES (?, ?, NOW(), ?)";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tema.getTitulo());
            ps.setString(2, tema.getContenido());
            ps.setInt(3, tema.getIdUsuario());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Tema> listarTemas() throws SQLException, ClassNotFoundException {
        List<Tema> lista = new ArrayList<>();
        String sql = "SELECT t.id, t.titulo, t.contenido, t.fecha_publicacion, t.id_usuario, u.usuario AS nombreUsuario " +
                     "FROM temas t INNER JOIN usuarios u ON t.id_usuario = u.id " +
                     "ORDER BY t.fecha_publicacion DESC";

        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tema tema = new Tema();
                tema.setId(rs.getInt("id"));
                tema.setTitulo(rs.getString("titulo"));
                tema.setContenido(rs.getString("contenido"));
                tema.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
                tema.setIdUsuario(rs.getInt("id_usuario"));
                tema.setNombreUsuario(rs.getString("nombreUsuario"));
                lista.add(tema);
            }
        }

        return lista;
    }
    
    public Tema obtenerTemaPorId(int id) throws SQLException, ClassNotFoundException {
    String sql = "SELECT t.id, t.titulo, t.contenido, t.fecha_publicacion, t.id_usuario, u.usuario AS nombreUsuario " +
                 "FROM temas t INNER JOIN usuarios u ON t.id_usuario = u.id " +
                 "WHERE t.id = ?";

    try (Connection con = Conexionbd.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Tema tema = new Tema();
                tema.setId(rs.getInt("id"));
                tema.setTitulo(rs.getString("titulo"));
                tema.setContenido(rs.getString("contenido"));
                tema.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
                tema.setIdUsuario(rs.getInt("id_usuario"));
                tema.setNombreUsuario(rs.getString("nombreUsuario"));
                return tema;
            }
        }
    }
    return null;
}


}
