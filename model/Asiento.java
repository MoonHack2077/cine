package model;

public class Asiento {
    private String idAsiento; 
    private int fila;
    private int columna;
    private Espectador espectador; // Referencia al espectador que ha reservado el asiento

    public Asiento(int fila, int columna, Espectador espectador) {
        this.fila = fila;
        this.columna = columna;
        this.espectador = espectador;
        this.idAsiento = generarIdAsiento(fila, columna);
    }

    private String generarIdAsiento(int fila, int columna) {
        return "A" + fila + "C" + columna; // Formato: A1C1
    }

    public String getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(String idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public Espectador getEspectador() {
        return espectador;
    }

    public void reservar(Espectador espectador) {
        this.espectador = espectador;
    }

    public void liberar() {
        this.espectador = null; // Liberar el asiento al establecer el espectador a null
    }

    public boolean esLibre() {
        return espectador == null; // Si el espectador no es nulo, el asiento está reservado
    }

}
