package model;

import java.util.ArrayList;
import java.util.List;

public class Cine {
    private String nombre;
    private String direccion;
    private int capacidad;
    private List<Sala> salas;
    private List<Pelicula> peliculas;
    private List<Funcion> funciones; 
    private List<Espectador> espectadores; 

    public Cine(String nombre, String direccion, int capacidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.salas = new ArrayList<>();
        this.peliculas = new ArrayList<>();
        this.funciones = new ArrayList<>();
        this.espectadores = new ArrayList<>();
    }

    public Espectador buscarEspectador(String idEspectador) {
        for (Espectador espectador : espectadores) {
            if (espectador.getIdEspectador().equals(idEspectador)) {
                return espectador;
            }
        }
        return null; // Retorna null si no se encuentra el espectador
    }

    public void crearFuncion(String codigoFuncion, Sala sala, Pelicula pelicula, String horario) {
        if (buscarFuncion(codigoFuncion) != null) {
            System.out.println("Error: Ya existe una función con el código " + codigoFuncion);
            return;
        }

        Funcion nuevaFuncion = new Funcion(codigoFuncion, horario, sala, pelicula);
        funciones.add(nuevaFuncion);
        System.out.println("Función creada exitosamente con código " + codigoFuncion);
    }
    
    public Funcion buscarFuncion(String codigoFuncion) {
        for (Funcion funcion : funciones) {
            if (funcion.getCodigoFuncion().equals(codigoFuncion)) {
                return funcion;
            }
        }
        return null; // Retorna null si no se encuentra la función
    }

    public boolean comprarTicket(String idFuncion, String idAsiento, String idEspectador) {
        Funcion funcion = buscarFuncion(idFuncion);
        Espectador espectador = buscarEspectador(idEspectador);

        if (funcion == null) {
            System.out.println("Error: Función con ID " + idFuncion + " no encontrada.");
            return false;
        }

        if (espectador == null) {
            System.out.println("Error: Espectador con ID " + idEspectador + " no encontrado.");
            return false;
        }

        Sala sala = funcion.getSala();
        Asiento asiento = sala.buscarAsiento(idAsiento);

        if (asiento == null) {
            System.out.println("Error: Asiento " + idAsiento + " no existe en la sala.");
            return false;
        }

        return funcion.reservarAsiento(asiento, espectador);
    }


    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(List<Funcion> funciones) {
        this.funciones = funciones;
    }

    public List<Espectador> getEspectadores() {
        return espectadores;
    }

    public void setEspectadores(List<Espectador> espectadores) {
        this.espectadores = espectadores;
    }
}