package model;

public class Pelicula {
    private String nombre;
    private int minDuracion;
    private String director;


    public Pelicula(String nombre, int minDuracion, String director) {
        this.nombre = nombre;
        this.minDuracion = minDuracion;
        this.director = director;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMinDuracion() {
        return minDuracion;
    }

    public void setMinDuracion(int minDuracion) {
        this.minDuracion = minDuracion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

}
