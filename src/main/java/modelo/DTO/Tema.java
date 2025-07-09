package modelo.DTO;

import java.sql.Timestamp;

public class Tema {
    private int id;
    private String titulo;
    private String contenido;
    private Timestamp fechaPublicacion;
    private int idUsuario;
    private String nombreUsuario; 
    private Integer numVistas;
    private Integer numRespuestas;


    // Getters y setters
    
    public Integer getNumRespuestas() {
        return numRespuestas;
    }

    public void setNumRespuestas(Integer numRespuestas) {
        this.numRespuestas = numRespuestas;
    }

    public Integer getNumVistas() {
        return numVistas;
    }

    public void setNumVistas(Integer numVistas) {
        this.numVistas = numVistas;
    }
    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Timestamp getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Timestamp fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
