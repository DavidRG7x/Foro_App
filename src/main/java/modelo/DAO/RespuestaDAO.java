package modelo.DAO;

import modelo.DTO.Respuesta;
import modelo.conexion.Conexionbd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RespuestaDAO {

    public List<Respuesta> listarRespuestasPorTema(int idTema) throws SQLException, ClassNotFoundException {
        List<Respuesta> lista = new ArrayList<>();
        String sql = "SELECT r.id, r.id_tema, r.id_usuario, r.contenido, r.fecha_respuesta, u.usuario AS nombreUsuario " +
                     "FROM respuestas r INNER JOIN usuarios u ON r.id_usuario = u.id " +
                     "WHERE r.id_tema = ? ORDER BY r.fecha_respuesta ASC";

        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTema);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Respuesta r = new Respuesta();
                    r.setId(rs.getInt("id"));
                    r.setIdTema(rs.getInt("id_tema"));
                    r.setIdUsuario(rs.getInt("id_usuario"));
                    r.setContenido(rs.getString("contenido"));
                    r.setFechaRespuesta(rs.getTimestamp("fecha_respuesta"));
                    r.setNombreUsuario(rs.getString("nombreUsuario"));
                    lista.add(r);
                }
            }
        }
        return lista;
    }

    public boolean guardarRespuesta(Respuesta respuesta) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO respuestas (id_tema, id_usuario, contenido, fecha_respuesta) VALUES (?, ?, ?, NOW())";

    try (Connection con = Conexionbd.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, respuesta.getIdTema());
        ps.setInt(2, respuesta.getIdUsuario());
        ps.setString(3, respuesta.getContenido());
        return ps.executeUpdate() > 0;
        }
    }
    
    public Respuesta obtenerPorId(int id) throws SQLException, ClassNotFoundException {
    String sql = "SELECT * FROM respuestas WHERE id = ?";
    try (Connection con = Conexionbd.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Respuesta r = new Respuesta();
                r.setId(rs.getInt("id"));
                r.setIdTema(rs.getInt("id_tema"));
                r.setIdUsuario(rs.getInt("id_usuario"));
                r.setContenido(rs.getString("contenido"));
                r.setFechaRespuesta(rs.getTimestamp("fecha_respuesta"));
                return r;
                }
            }
        }
        return null;
    }

    public void actualizarContenido(int id, String nuevoContenido) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE respuestas SET contenido = ? WHERE id = ?";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoContenido);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM respuestas WHERE id = ?";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public int contarRespuestasPorUsuario(int idUsuario) throws SQLException, ClassNotFoundException {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM respuestas WHERE id_usuario = ?";
        try (Connection con = Conexionbd.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        }
        return total;
    }

}
