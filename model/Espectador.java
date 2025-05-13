package model;

public class Espectador {
    private String idEspectador;
    private String nombre;
    private String identificacion; // Agregado para la identificación

    public Espectador(String idEspectador, String nombre, String identificacion) {
        this.idEspectador = idEspectador;
        this.nombre = nombre;
        this.identificacion = identificacion;
    }

    public String getIdEspectador() {
        return idEspectador;
    }

    public void setIdEspectador(String idEspectador) {
        this.idEspectador = idEspectador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String toString() {
        return "Espectador{" +
                "idEspectador='" + idEspectador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", identificacion='" + identificacion + '\'' +
                '}';
    }
} 
    

