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
    // Dentro de la clase Sala en model/Sala.java
public String getEstadoCompletoAsientos() {
    StringBuilder sb = new StringBuilder();
    List<String> disponibles = new ArrayList<>();
    List<String> ocupados = new ArrayList<>();

    for (Asiento asiento : asientos) {
        if (asiento.esLibre()) {
            disponibles.add(asiento.getIdAsiento());
        } else {
            String detalleOcupado = asiento.getIdAsiento();
            if (asiento.getEspectador() != null) {
                detalleOcupado += " (Ocupado por: " + asiento.getEspectador().getNombre() + ")";
            } else {
                detalleOcupado += " (Ocupado)";
            }
            ocupados.add(detalleOcupado);
        }
    }

    sb.append("Asientos Disponibles:\n");
    if (disponibles.isEmpty()) {
        sb.append("  Ninguno\n");
    } else {
        sb.append("  ").append(String.join(", ", disponibles)).append("\n");
    }

    sb.append("\nAsientos Ocupados:\n");
    if (ocupados.isEmpty()) {
        sb.append("  Ninguno\n");
    } else {
        sb.append("  ").append(String.join(", ", ocupados)).append("\n");
    }

    return sb.toString();
}

    // Dentro de la clase Sala en model/Sala.java
public String mostrarDisponibles() {
    List<String> disponiblesList = new ArrayList<>();
    for (Asiento asiento : asientos) {
        if (asiento.esLibre()) {
            disponiblesList.add(asiento.getIdAsiento());
        }
    }
    if (disponiblesList.isEmpty()) {
        return "No hay asientos disponibles.";
    }
    // Puedes ajustar el formato como prefieras, ej:
    // return String.join(", ", disponiblesList);
    return "Asientos disponibles: " + disponiblesList.toString();
}
    
}
