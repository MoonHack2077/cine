import model.*;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static Cine cine = new Cine("Cineplex", "Calle Principal #123", 100);

    public static void main(String[] args) {
        // Precargar datos para demostración
        //precargarDatos();

        boolean continuar = true;
        while (continuar) {
            String opcion = JOptionPane.showInputDialog(null,
                    "Seleccione una opción:\n" +
                            "1. Crear Película\n" +
                            "2. Crear Sala\n" +
                            "3. Crear Función\n" +
                            "4. Comprar Ticket\n" +
                            "5. Mostrar Puestos Disponibles y Ocupados\n" +
                            "6. Mostrar Información del Cine\n" +
                            "7. Salir", "Gestión de Cine", JOptionPane.QUESTION_MESSAGE);

            if (opcion == null) { // El usuario presionó Cancelar o Cerrar
                continuar = false;
                break;
            }

            try {
                int seleccion = Integer.parseInt(opcion);

                switch (seleccion) {
                    case 1:
                        crearPelicula();
                        break;
                    case 2:
                        crearSala();
                        break;
                    case 3:
                        crearFuncion();
                        break;
                    case 4:
                        comprarTicket();
                        break;
                    case 5:
                        mostrarPuestosDisponiblesYOcupados();
                        break;
                    case 6:
                        mostrarInformacionCine();
                        break;
                    case 7:
                        continuar = false;
                        JOptionPane.showMessageDialog(null, "¡Hasta luego!", "Gestión de Cine", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void crearPelicula() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre de la película:", "Crear Película", JOptionPane.INFORMATION_MESSAGE);
        String duracionStr = JOptionPane.showInputDialog(null, "Ingrese la duración en minutos:", "Crear Película", JOptionPane.INFORMATION_MESSAGE);
        String director = JOptionPane.showInputDialog(null, "Ingrese el nombre del director:", "Crear Película", JOptionPane.INFORMATION_MESSAGE);

        try {
            int duracion = Integer.parseInt(duracionStr);
            Pelicula pelicula = new Pelicula(nombre, duracion, director);
            cine.getPeliculas().add(pelicula);
            JOptionPane.showMessageDialog(null, "Película creada con éxito.", "Crear Película", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Duración no válida. Debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void crearSala() {
        String filasStr = JOptionPane.showInputDialog(null, "Ingrese el número de filas:", "Crear Sala", JOptionPane.INFORMATION_MESSAGE);
        String columnasStr = JOptionPane.showInputDialog(null, "Ingrese el número de columnas:", "Crear Sala", JOptionPane.INFORMATION_MESSAGE);
        String tecnologia = JOptionPane.showInputDialog(null, "Ingrese la tecnología de proyección (2D/3D):", "Crear Sala", JOptionPane.INFORMATION_MESSAGE);

        try {
            int filas = Integer.parseInt(filasStr);
            int columnas = Integer.parseInt(columnasStr);
            int id = cine.getSalas().size() + 1; // Generar ID automático

            Sala sala;
            if (tecnologia.equalsIgnoreCase("2D")) {
                String sonido = JOptionPane.showInputDialog(null, "Ingrese el tipo de sonido:", "Crear Sala 2D", JOptionPane.INFORMATION_MESSAGE);
                sala = new Sala2D(id, filas, columnas, tecnologia, sonido);
            } else if (tecnologia.equalsIgnoreCase("3D")) {
                String gafas = JOptionPane.showInputDialog(null, "Ingrese el tipo de gafas:", "Crear Sala 3D", JOptionPane.INFORMATION_MESSAGE);
                String filasVipStr = JOptionPane.showInputDialog(null, "Ingrese el número de filas VIP:", "Crear Sala 3D", JOptionPane.INFORMATION_MESSAGE);
                int filasVip = Integer.parseInt(filasVipStr);
                sala = new Sala3D(id, filas, columnas, tecnologia, gafas, filasVip);
            } else {
                sala = new Sala(id, filas, columnas, tecnologia);
            }

            cine.getSalas().add(sala);
            JOptionPane.showMessageDialog(null, "Sala creada con éxito.", "Crear Sala", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada no válida. Asegúrese de ingresar números enteros para filas y columnas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void crearFuncion() {
        String codigo = generarCodigoFuncionUnico(); // Generar código único
        String horario = JOptionPane.showInputDialog(null, "Ingrese el horario de la función (hh:mm):", "Crear Función", JOptionPane.INFORMATION_MESSAGE);

        Sala sala = seleccionarSala();
        if (sala == null) return;

        Pelicula pelicula = seleccionarPelicula();
        if (pelicula == null) return;

        cine.crearFuncion(codigo, sala, pelicula, horario);
        JOptionPane.showMessageDialog(null, "Función creada con éxito. Código: " + codigo, "Crear Función", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String generarCodigoFuncionUnico() {
        String codigo;
        Random random = new Random();
        do {
            codigo = "F" + String.format("%03d", random.nextInt(1000)); // F001, F002,...
        } while (cine.buscarFuncion(codigo) != null); // Asegurar que sea único
        return codigo;
    }

    private static Sala seleccionarSala() {
        if (cine.getSalas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay salas disponibles. Cree una sala primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String[] opcionesSala = new String[cine.getSalas().size()];
        for (int i = 0; i < cine.getSalas().size(); i++) {
            opcionesSala[i] = "Sala " + cine.getSalas().get(i).getId();
        }

        String salaSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione la sala para la función:", "Crear Función", JOptionPane.QUESTION_MESSAGE, null, opcionesSala, opcionesSala[0]);

        if (salaSeleccionada == null) return null; // El usuario canceló

        int idSalaSeleccionada = Integer.parseInt(salaSeleccionada.substring(5)); // Extraer el ID de "Sala X"
        for (Sala sala : cine.getSalas()) {
            if (sala.getId() == idSalaSeleccionada) {
                return sala;
            }
        }

        return null; // Sala no encontrada (esto no debería ocurrir)
    }

    private static Pelicula seleccionarPelicula() {
        if (cine.getPeliculas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay películas disponibles. Cree una película primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String[] opcionesPelicula = new String[cine.getPeliculas().size()];
        for (int i = 0; i < cine.getPeliculas().size(); i++) {
            opcionesPelicula[i] = cine.getPeliculas().get(i).getNombre();
        }

        String peliculaSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione la película para la función:", "Crear Función", JOptionPane.QUESTION_MESSAGE, null, opcionesPelicula, opcionesPelicula[0]);

        if (peliculaSeleccionada == null) return null; // El usuario canceló

        for (Pelicula pelicula : cine.getPeliculas()) {
            if (pelicula.getNombre().equals(peliculaSeleccionada)) {
                return pelicula;
            }
        }

        return null; // Película no encontrada (esto no debería ocurrir)
    }

    private static void comprarTicket() {
        Funcion funcion = seleccionarFuncionParaCompra();
        if (funcion == null) return;

        String idEspectador = generarIdEspectadorUnico(); // Generar ID único
        String nombreEspectador = JOptionPane.showInputDialog(null, "Ingrese el nombre del espectador:", "Comprar Ticket", JOptionPane.INFORMATION_MESSAGE);
        String identificacionEspectador = JOptionPane.showInputDialog(null, "Ingrese la identificación del espectador:", "Comprar Ticket", JOptionPane.INFORMATION_MESSAGE);

        Espectador espectador = new Espectador(idEspectador, nombreEspectador, identificacionEspectador);
        cine.getEspectadores().add(espectador); // Agregar el espectador a la lista

        Asiento asiento = seleccionarAsiento(funcion.getSala());
        if (asiento == null) return;

        boolean exito = funcion.reservarAsiento(asiento, espectador);
        if (exito) {
            JOptionPane.showMessageDialog(null, "Ticket comprado con éxito. ID Espectador: " + idEspectador + ", Asiento: " + asiento.getIdAsiento(), "Comprar Ticket", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo comprar el ticket. El asiento no está disponible.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String generarIdEspectadorUnico() {
        String id;
        Random random = new Random();
        do {
            id = "E" + String.format("%04d", random.nextInt(10000)); // E0001, E0002,...
        } while (cine.buscarEspectador(id) != null); // Asegurar que sea único
        return id;
    }

    private static Funcion seleccionarFuncionParaCompra() {
        if (cine.getFunciones().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay funciones disponibles. Cree una función primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String[] opcionesFuncion = new String[cine.getFunciones().size()];
        for (int i = 0; i < cine.getFunciones().size(); i++) {
            Funcion funcion = cine.getFunciones().get(i);
            opcionesFuncion[i] = "Función: " + funcion.getCodigoFuncion() + " - " + funcion.getPelicula().getNombre() + " (" + funcion.getHorario() + ")";
        }

        String funcionSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione la función para comprar el ticket:", "Comprar Ticket", JOptionPane.QUESTION_MESSAGE, null, opcionesFuncion, opcionesFuncion[0]);

        if (funcionSeleccionada == null) return null; // El usuario canceló

        String codigoFuncion = funcionSeleccionada.split(":")[1].split("-")[0].trim();
        return cine.buscarFuncion(codigoFuncion);
    }

    private static Asiento seleccionarAsiento(Sala sala) {
        String[] opcionesAsiento = new String[sala.getAsientos().size()];
        for (int i = 0; i < sala.getAsientos().size(); i++) {
            opcionesAsiento[i] = sala.getAsientos().get(i).getIdAsiento();
        }

        String asientoSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione el asiento:", "Comprar Ticket", JOptionPane.QUESTION_MESSAGE, null, opcionesAsiento, opcionesAsiento[0]);

        if (asientoSeleccionado == null) return null;

        Asiento asiento = sala.buscarAsiento(asientoSeleccionado);
        if (asiento.esLibre()) {
            JOptionPane.showMessageDialog(null, "El asiento " + asiento.getIdAsiento() + " no está disponible.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return asiento;
    }

    private static void mostrarPuestosDisponiblesYOcupados() {
        Funcion funcion = seleccionarFuncionParaMostrarAsientos();
        if (funcion == null) return;

        Sala sala = funcion.getSala();
        JOptionPane.showMessageDialog(null, "Asientos disponibles: \n" + sala.mostrarDisponibles());
    }

    private static Funcion seleccionarFuncionParaMostrarAsientos() {
        if (cine.getFunciones().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay funciones disponibles. Cree una función primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String[] opcionesFuncion = new String[cine.getFunciones().size()];
        for (int i = 0; i < cine.getFunciones().size(); i++) {
            Funcion funcion = cine.getFunciones().get(i);
            opcionesFuncion[i] = "Función: " + funcion.getCodigoFuncion() + " - " + funcion.getPelicula().getNombre() + " (" + funcion.getHorario() + ")";
        }

        String funcionSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione la función para mostrar los asientos:", "Mostrar Asientos", JOptionPane.QUESTION_MESSAGE, null, opcionesFuncion, opcionesFuncion[0]);

        if (funcionSeleccionada == null) return null;

        String codigoFuncion = funcionSeleccionada.split(":")[1].split("-")[0].trim();
        return cine.buscarFuncion(codigoFuncion);
    }

    private static void mostrarInformacionCine() {
        StringBuilder info = new StringBuilder("Información del Cine:\n");
        info.append("Nombre: ").append(cine.getNombre()).append("\n");
        info.append("Dirección: ").append(cine.getDireccion()).append("\n");
        info.append("Capacidad: ").append(cine.getCapacidad()).append("\n\n");

        info.append("Películas:\n");
        if (cine.getPeliculas().isEmpty()) {
            info.append("No hay películas registradas.\n");
        } else {
            for (Pelicula pelicula : cine.getPeliculas()) {
                info.append("- ").append(pelicula.getNombre()).append(" (Duración: ").append(pelicula.getMinDuracion()).append(" min, Director: ").append(pelicula.getDirector()).append(")\n");
            }
        }

        info.append("\nSalas:\n");
        if (cine.getSalas().isEmpty()) {
            info.append("No hay salas registradas.\n");
        } else {
            for (Sala sala : cine.getSalas()) {
                info.append("- Sala ").append(sala.getId()).append(" (Capacidad: ").append(sala.getCapacidadTotal()).append(", Tecnología: ").append(sala.getTecnologiaDeProyeccion()).append(")\n");
            }
        }

        info.append("\nFunciones:\n");
        if (cine.getFunciones().isEmpty()) {
            info.append("No hay funciones programadas.\n");
        } else {
            for (Funcion funcion : cine.getFunciones()) {
                info.append("- ").append(funcion.getPelicula().getNombre()).append(" en Sala ").append(funcion.getSala().getId()).append(" a las ").append(funcion.getHorario()).append("\n");
            }
        }

        JOptionPane.showMessageDialog(null, info.toString(), "Información del Cine", JOptionPane.INFORMATION_MESSAGE);
    }

    // private static void precargarDatos() {
    //     // Crear algunas películas
    //     cine.getPeliculas().add(new Pelicula("Oppenheimer", 180, "Christopher Nolan"));
    //     cine.getPeliculas().add(new Pelicula("Barbie", 114, "Greta Gerwig"));

    //     // Crear algunas salas
    //     cine.getSalas().add(new Sala2D(1, 8, 8, "2D", "Dolby Digital"));
    //     cine.getSalas().add(new Sala3D(2, 8, 8, "3D", "Activo", 2));

    //     // Crear algunas funciones
    //     cine.crearFuncion("F001", cine.getSalas().get(0), cine.getPeliculas().get(0), "18:00");
    // }
}