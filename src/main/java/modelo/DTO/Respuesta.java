package modelo.DTO;

import java.sql.Timestamp;

public class Respuesta {
    private int id;
    private int idTema;
    private int idUsuario;
    private String contenido;
    private Timestamp fechaRespuesta;
    private String nombreUsuario; // para mostrar el autor

    // Getters y setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdTema() { return idTema; }
    public void setIdTema(int idTema) { this.idTema = idTema; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public Timestamp getFechaRespuesta() { return fechaRespuesta; }
    public void setFechaRespuesta(Timestamp fechaRespuesta) { this.fechaRespuesta = fechaRespuesta; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
