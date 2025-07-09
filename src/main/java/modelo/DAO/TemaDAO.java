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
    String sql = "SELECT t.id, t.titulo, t.contenido, t.fecha_publicacion, t.id_usuario, " +
                 "u.usuario AS nombreUsuario, " +
                 "t.vistas AS numVistas, " +
                 "(SELECT COUNT(*) FROM respuestas r WHERE r.id_tema = t.id) AS numRespuestas " +
                 "FROM temas t " +
                 "INNER JOIN usuarios u ON t.id_usuario = u.id " +
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
            tema.setNumVistas(rs.getInt("numVistas"));
            tema.setNumRespuestas(rs.getInt("numRespuestas"));
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
    
    public void actualizarTema(int id, String nuevoTitulo, String nuevoContenido) throws SQLException, ClassNotFoundException {
    String sql = "UPDATE temas SET titulo = ?, contenido = ? WHERE id = ?";
    try (Connection con = Conexionbd.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nuevoTitulo);
        ps.setString(2, nuevoContenido);
        ps.setInt(3, id);

        ps.executeUpdate();
    }
}

    
    public void eliminarTema(int id) throws SQLException, ClassNotFoundException {
    // Primero elimina las respuestas relacionadas
    String eliminarRespuestas = "DELETE FROM respuestas WHERE id_tema = ?";
    String eliminarTema = "DELETE FROM temas WHERE id = ?";

    try (Connection con = Conexionbd.getConnection()) {
        con.setAutoCommit(false); // Transacción manual

        try (PreparedStatement ps1 = con.prepareStatement(eliminarRespuestas);
             PreparedStatement ps2 = con.prepareStatement(eliminarTema)) {

            ps1.setInt(1, id);
            ps1.executeUpdate();

            ps2.setInt(1, id);
            ps2.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        }
    }
}
   
    public int contarTemasPorUsuario(int idUsuario) throws SQLException, ClassNotFoundException {
    int total = 0;
    String sql = "SELECT COUNT(*) FROM temas WHERE id_usuario = ?";
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
    
    public void incrementarVistas(int idTema) throws SQLException, ClassNotFoundException {
    String sql = "UPDATE temas SET vistas = vistas + 1 WHERE id = ?";
    try (Connection con = Conexionbd.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idTema);
        ps.executeUpdate();
    }
}
    
    
    public List<Tema> buscarTemas(String busqueda) throws SQLException, ClassNotFoundException {
    List<Tema> lista = new ArrayList<>();
    String sql = "SELECT t.id, t.titulo, t.contenido, t.fecha_publicacion, t.id_usuario, u.usuario AS nombreUsuario, " +
                 "       (SELECT COUNT(*) FROM respuestas r WHERE r.id_tema = t.id) AS numRespuestas, " +
                 "       t.vistas AS numVistas " +
                 "FROM temas t " +
                 "INNER JOIN usuarios u ON t.id_usuario = u.id " +
                 "WHERE t.titulo LIKE ? OR t.contenido LIKE ? " +
                 "ORDER BY t.fecha_publicacion DESC";

    try (Connection con = Conexionbd.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        String filtro = "%" + busqueda + "%";
        ps.setString(1, filtro);
        ps.setString(2, filtro);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tema tema = new Tema();
                tema.setId(rs.getInt("id"));
                tema.setTitulo(rs.getString("titulo"));
                tema.setContenido(rs.getString("contenido"));
                tema.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
                tema.setIdUsuario(rs.getInt("id_usuario"));
                tema.setNombreUsuario(rs.getString("nombreUsuario"));
                tema.setNumRespuestas(rs.getInt("numRespuestas"));
                tema.setNumVistas(rs.getInt("numVistas"));
                lista.add(tema);
            }
        }
    }

    return lista;
}

    
    
              





}
