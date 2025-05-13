package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sala {
    private int id;
    private int filas;
    private int columnas;
    private List<Asiento> asientos;
    private int capacidadTotal;
    private String tecnologiaDeProyeccion;

    public Sala(int id, int filas, int columnas, String tecnologiaDeProyeccion) {
        this.id = id;
        this.filas = filas;
        this.columnas = columnas;
        this.tecnologiaDeProyeccion = tecnologiaDeProyeccion;
        this.capacidadTotal = filas * columnas;
        this.asientos = new ArrayList<>();
        inicializarAsientos(filas, columnas);
    }

    private void inicializarAsientos(int filas, int columnas) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                asientos.add(new Asiento(i + 1, j + 1, null)); // Asientos numerados desde 1
            }
        }
    }

    public Asiento buscarAsiento(String idAsiento) {
        for (Asiento asiento : asientos) {
            if (asiento.getIdAsiento().equals(idAsiento)) {
                return asiento;
            }
        }
        return null; // Retorna null si no se encuentra el asiento
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(ArrayList<Asiento> asientos) {
        this.asientos = asientos;
    }

    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    public String getTecnologiaDeProyeccion() {
        return tecnologiaDeProyeccion;
    }

    public void setTecnologiaDeProyeccion(String tecnologiaDeProyeccion) {
        this.tecnologiaDeProyeccion = tecnologiaDeProyeccion;
    }

    public String mostrarDisponibles(){
        String[] disponibles = new String[asientos.size()];
        int i = 0;
        for (Asiento asiento : asientos) {
            if (asiento.esLibre()) {
                disponibles[i] = asiento.getIdAsiento();
                i++;
            }
        }
        return Arrays.toString(disponibles);
    }
    
}
