package model;

public class Funcion {
    private String codigoFuncion;
    private String horario;
    private Sala sala;
    private Pelicula pelicula;

    public Funcion(String codigoFuncion, String horario, Sala sala, Pelicula pelicula) {
        this.codigoFuncion = codigoFuncion;
        this.horario = horario;
        this.sala = sala;
        this.pelicula = pelicula;
    }

    public boolean liberarAsiento(Asiento asiento) {
        if (!asiento.esLibre()) {
            asiento.liberar();
            return true;
        }
        System.out.println("Error: El asiento " + asiento.getIdAsiento() + " ya está libre.");
        return false;
    }

    public boolean reservarAsiento(Asiento asiento, Espectador espectador) {
        if (asiento.esLibre()) {
            System.out.println("Error: El asiento " + asiento.getIdAsiento() + " ya está reservado.");
            return false;
        }
        asiento.reservar(espectador);
        return true;
    }

    public String getCodigoFuncion() {
        return codigoFuncion;
    }
    
    public void setCodigoFuncion(String codigoFuncion) {
        this.codigoFuncion = codigoFuncion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }  

}