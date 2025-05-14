package model;

import java.util.ArrayList;
import java.util.List;
// Considera importar Optional si prefieres usarlo en buscarAsientoEnFuncion, aunque un bucle simple está bien.

public class Funcion {
    private String codigoFuncion;
    private String horario;
    private Sala sala; // Referencia a la plantilla de la Sala
    private Pelicula pelicula;
    private List<Asiento> asientosDeLaFuncion; // Lista de asientos específica para ESTA función

    public Funcion(String codigoFuncion, String horario, Sala sala, Pelicula pelicula) {
        this.codigoFuncion = codigoFuncion;
        this.horario = horario;
        this.sala = sala; // Guardamos la referencia a la sala (para su configuración)
        this.pelicula = pelicula;
        this.asientosDeLaFuncion = new ArrayList<>();

        // Crear una copia profunda de los asientos de la sala para esta función
        if (sala != null && sala.getAsientos() != null) {
            for (Asiento asientoPlantilla : sala.getAsientos()) {
                // Creamos un NUEVO objeto Asiento para esta función,
                // basado en la plantilla de la sala.
                // El espectador inicialmente es null (asiento libre).
                this.asientosDeLaFuncion.add(new Asiento(asientoPlantilla.getFila(), asientoPlantilla.getColumna(), null));
            }
        }
    }

    /**
     * Busca un asiento específico DENTRO de esta función por su ID.
     * @param idAsiento El ID del asiento a buscar (ej: "A1C1").
     * @return El objeto Asiento si se encuentra, o null si no.
     */
    public Asiento buscarAsientoEnFuncion(String idAsiento) {
        for (Asiento asiento : this.asientosDeLaFuncion) {
            if (asiento.getIdAsiento().equals(idAsiento)) {
                return asiento;
            }
        }
        return null; // No se encontró el asiento en esta función
    }

    /**
     * Reserva un asiento para un espectador en esta función.
     * @param idAsiento El ID del asiento a reservar.
     * @param espectador El espectador que ocupará el asiento.
     * @return true si la reserva fue exitosa, false si el asiento no existe o ya está ocupado.
     */
    public boolean reservarAsiento(String idAsiento, Espectador espectador) {
        Asiento asientoAReservar = buscarAsientoEnFuncion(idAsiento);
        if (asientoAReservar != null) {
            if (asientoAReservar.esLibre()) {
                asientoAReservar.reservar(espectador); // Llama al método reservar del Asiento específico de esta función
                return true;
            } else {
                // System.out.println("Error: El asiento " + idAsiento + " ya está ocupado en la función " + this.codigoFuncion);
                return false; // Asiento ya ocupado
            }
        }
        // System.out.println("Error: El asiento " + idAsiento + " no existe en la función " + this.codigoFuncion);
        return false; // Asiento no encontrado
    }

    /**
     * Libera un asiento en esta función.
     * @param idAsiento El ID del asiento a liberar.
     * @return true si la liberación fue exitosa, false si el asiento no existe o ya estaba libre.
     */
    public boolean liberarAsiento(String idAsiento) {
        Asiento asientoALiberar = buscarAsientoEnFuncion(idAsiento);
        if (asientoALiberar != null) {
            if (!asientoALiberar.esLibre()) {
                asientoALiberar.liberar();
                return true;
            } else {
                return false; // Ya estaba libre
            }
        }
        return false; // No encontrado
    }

    // Getters y Setters existentes (asegúrate de que estén completos)
    public String getCodigoFuncion() { return codigoFuncion; }
    public void setCodigoFuncion(String codigoFuncion) { this.codigoFuncion = codigoFuncion; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public Sala getSala() { return sala; } // Devuelve la sala (plantilla)
    public void setSala(Sala sala) { this.sala = sala; }
    public Pelicula getPelicula() { return pelicula; }
    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }

    // Getter para la lista de asientos específica de esta función (importante para Main)
    public List<Asiento> getAsientosDeLaFuncion() {
        return asientosDeLaFuncion;
    }

    /**
     * Genera una cadena con el estado completo (disponibles y ocupados)
     * de los asientos para ESTA función.
     * @return Una cadena formateada con la información de los asientos.
     */
    public String getEstadoCompletoAsientosFuncion() {
        StringBuilder sb = new StringBuilder();
        List<String> disponibles = new ArrayList<>();
        List<String> ocupados = new ArrayList<>();

        // Itera sobre los asientos PROPIOS de ESTA función
        for (Asiento asiento : this.asientosDeLaFuncion) {
            if (asiento.esLibre()) {
                disponibles.add(asiento.getIdAsiento());
            } else {
                String detalleOcupado = asiento.getIdAsiento();
                if (asiento.getEspectador() != null) {
                    detalleOcupado += " (Ocupado por: " + asiento.getEspectador().getNombre() + ")";
                } else {
                    detalleOcupado += " (Ocupado)"; // Fallback si no hay espectador pero está marcado como no libre
                }
                ocupados.add(detalleOcupado);
            }
        }

        sb.append("Función: ").append(this.codigoFuncion)
          .append(" | Película: ").append(this.pelicula.getNombre())
          .append(" | Sala: ").append(this.sala.getId()) // ID de la sala plantilla
          .append(" | Horario: ").append(this.horario).append("\n");

        sb.append("--------------------------------------------------\n");
        sb.append("Asientos Disponibles:\n");
        if (disponibles.isEmpty()) {
            sb.append("  (Ninguno)\n");
        } else {
            sb.append("  ").append(String.join(", ", disponibles)).append("\n");
        }

        sb.append("\nAsientos Ocupados:\n");
        if (ocupados.isEmpty()) {
            sb.append("  (Ninguno)\n");
        } else {
            sb.append("  ").append(String.join(", ", ocupados)).append("\n");
        }
        sb.append("--------------------------------------------------");

        return sb.toString();
    }
}