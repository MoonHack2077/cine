import model.Asiento;
import model.Cine;
import model.Espectador;
import model.Funcion;
import model.Pelicula;
import model.Sala;
import model.Sala2D;
import model.Sala3D;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static Cine cine = new Cine("Cineplex", "Calle Principal #123", 100);

    public static void main(String[] args) {
        // Se pueden activar datos de prueba si se desea.
        // precargarDatos();

        boolean continuar = true;
        while (continuar) {
            String opcion = JOptionPane.showInputDialog(null,
                    "Seleccione una opción:\n" +
                            "1. Crear Película\n" +
                            "2. Crear Sala\n" +
                            "3. Crear Función\n" +
                            "4. Comprar Ticket\n" +
                            "5. Mostrar Puestos Disponibles y Ocupados por Función\n" +
                            "6. Mostrar Información General del Cine\n" +
                            "7. Salir", "Gestión de Cine POO", JOptionPane.QUESTION_MESSAGE);

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
                        mostrarPuestosDisponiblesYOcupadosPorFuncion();
                        break;
                    case 6:
                        mostrarInformacionCine();
                        break;
                    case 7:
                        continuar = false;
                        JOptionPane.showMessageDialog(null, "¡Gracias por usar el sistema de Gestión de Cine!", "Salida", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, intente de nuevo.", "Error de Opción", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para la opción.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void crearPelicula() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre de la película:", "Crear Película", JOptionPane.INFORMATION_MESSAGE);
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre de la película no puede estar vacío.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String duracionStr = JOptionPane.showInputDialog(null, "Ingrese la duración en minutos:", "Crear Película", JOptionPane.INFORMATION_MESSAGE);
        int duracion;
        try {
            duracion = Integer.parseInt(duracionStr);
            if (duracion <= 0) {
                JOptionPane.showMessageDialog(null, "La duración debe ser un número positivo.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Duración no válida. Debe ser un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String director = JOptionPane.showInputDialog(null, "Ingrese el nombre del director:", "Crear Película", JOptionPane.INFORMATION_MESSAGE);
        if (director == null || director.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del director no puede estar vacío.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pelicula pelicula = new Pelicula(nombre, duracion, director);
        cine.getPeliculas().add(pelicula);
        JOptionPane.showMessageDialog(null, "Película '" + nombre + "' creada con éxito.", "Película Creada", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void crearSala() {
        String filasStr = JOptionPane.showInputDialog(null, "Ingrese el número de filas para la sala:", "Crear Sala", JOptionPane.INFORMATION_MESSAGE);
        int filas;
        try {
            filas = Integer.parseInt(filasStr);
            if (filas <= 0) {
                JOptionPane.showMessageDialog(null, "El número de filas debe ser positivo.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de filas no válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String columnasStr = JOptionPane.showInputDialog(null, "Ingrese el número de columnas para la sala:", "Crear Sala", JOptionPane.INFORMATION_MESSAGE);
        int columnas;
        try {
            columnas = Integer.parseInt(columnasStr);
            if (columnas <= 0) {
                JOptionPane.showMessageDialog(null, "El número de columnas debe ser positivo.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de columnas no válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tecnologia = JOptionPane.showInputDialog(null, "Ingrese la tecnología de proyección (Ej: 2D, 3D, IMAX):", "Crear Sala", JOptionPane.INFORMATION_MESSAGE);
        if (tecnologia == null || tecnologia.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La tecnología de proyección no puede estar vacía.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idSala = cine.getSalas().size() + 1; // Generar ID simple basado en el tamaño actual
        Sala nuevaSala;

        if (tecnologia.equalsIgnoreCase("2D")) {
            String sonido = JOptionPane.showInputDialog(null, "Ingrese el tipo de sonido para la sala 2D (Ej: Dolby Digital, Estéreo):", "Crear Sala 2D", JOptionPane.INFORMATION_MESSAGE);
            nuevaSala = new Sala2D(idSala, filas, columnas, tecnologia, (sonido != null && !sonido.trim().isEmpty()) ? sonido : "Estándar");
        } else if (tecnologia.equalsIgnoreCase("3D")) {
            String gafas = JOptionPane.showInputDialog(null, "Ingrese el tipo de gafas para la sala 3D (Ej: Activas, Pasivas):", "Crear Sala 3D", JOptionPane.INFORMATION_MESSAGE);
            String filasVipStr = JOptionPane.showInputDialog(null, "Ingrese el número de filas VIP (0 si no aplica):", "Crear Sala 3D", JOptionPane.INFORMATION_MESSAGE);
            int filasVip = 0;
            try {
                filasVip = Integer.parseInt(filasVipStr);
                if (filasVip < 0 || filasVip > filas) {
                     JOptionPane.showMessageDialog(null, "Número de filas VIP inválido.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
                     return;
                }
            } catch (NumberFormatException e) {
                // No es crítico, se asume 0 si no es válido.
            }
            nuevaSala = new Sala3D(idSala, filas, columnas, tecnologia, (gafas != null && !gafas.trim().isEmpty()) ? gafas : "Estándar", filasVip);
        } else {
            // Sala genérica si no es 2D o 3D específicamente manejado
            nuevaSala = new Sala(idSala, filas, columnas, tecnologia);
        }

        cine.getSalas().add(nuevaSala);
        JOptionPane.showMessageDialog(null, "Sala ID " + idSala + " (" + tecnologia + ") creada con " + filas + " filas y " + columnas + " columnas.", "Sala Creada", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void crearFuncion() {
        if (cine.getSalas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay salas disponibles. Por favor, cree una sala primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cine.getPeliculas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay películas disponibles. Por favor, cree una película primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Sala salaSeleccionada = seleccionarSala("Seleccione la sala para la nueva función:");
        if (salaSeleccionada == null) return;

        Pelicula peliculaSeleccionada = seleccionarPelicula("Seleccione la película para la nueva función:");
        if (peliculaSeleccionada == null) return;

        String horario = JOptionPane.showInputDialog(null, "Ingrese el horario de la función (ej: 18:00, 20:30):", "Crear Función", JOptionPane.INFORMATION_MESSAGE);
        if (horario == null || horario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El horario es obligatorio.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Aquí se podría añadir validación para el formato del horario.

        String codigoFuncion = generarCodigoFuncionUnico();
        cine.crearFuncion(codigoFuncion, salaSeleccionada, peliculaSeleccionada, horario); // El constructor de Funcion ahora crea su propia lista de asientos.

        JOptionPane.showMessageDialog(null, "Función creada con éxito!\nCódigo: " + codigoFuncion + "\nPelícula: " + peliculaSeleccionada.getNombre() + "\nSala: " + salaSeleccionada.getId() + "\nHorario: " + horario, "Función Creada", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String generarCodigoFuncionUnico() {
        String codigo;
        Random random = new Random();
        do {
            codigo = "FUNC" + String.format("%04d", random.nextInt(10000));
        } while (cine.buscarFuncion(codigo) != null);
        return codigo;
    }

    private static Sala seleccionarSala(String mensajeDialogo) {
        if (cine.getSalas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay salas registradas en el sistema.", "Salas Vacías", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String[] opcionesSala = new String[cine.getSalas().size()];
        for (int i = 0; i < cine.getSalas().size(); i++) {
            Sala s = cine.getSalas().get(i);
            opcionesSala[i] = "Sala ID " + s.getId() + " (" + s.getTecnologiaDeProyeccion() + " - " + s.getFilas() + "x" + s.getColumnas() + ")";
        }

        String salaSeleccionadaStr = (String) JOptionPane.showInputDialog(null, mensajeDialogo,
                "Seleccionar Sala", JOptionPane.QUESTION_MESSAGE, null, opcionesSala, opcionesSala[0]);

        if (salaSeleccionadaStr == null) return null;

        // Extraer ID de la cadena, ej: "Sala ID 1" -> 1
        int idSala = Integer.parseInt(salaSeleccionadaStr.split(" ")[2]);
        return cine.getSalas().stream().filter(s -> s.getId() == idSala).findFirst().orElse(null);
    }

    private static Pelicula seleccionarPelicula(String mensajeDialogo) {
        if (cine.getPeliculas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay películas registradas.", "Películas Vacías", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String[] opcionesPelicula = cine.getPeliculas().stream().map(Pelicula::getNombre).toArray(String[]::new);

        String peliculaSeleccionadaNombre = (String) JOptionPane.showInputDialog(null, mensajeDialogo,
                "Seleccionar Película", JOptionPane.QUESTION_MESSAGE, null, opcionesPelicula, opcionesPelicula[0]);

        if (peliculaSeleccionadaNombre == null) return null;

        return cine.getPeliculas().stream().filter(p -> p.getNombre().equals(peliculaSeleccionadaNombre)).findFirst().orElse(null);
    }

    /**
     * Permite al usuario seleccionar un ID de asiento que esté disponible para una función específica.
     * Muestra el estado (Libre/Ocupado) de cada asiento de la función.
     * @param funcion La función para la cual se seleccionará el asiento.
     * @return El ID del asiento seleccionado si está libre, o null si el usuario cancela o elige un asiento ocupado.
     */
    private static String seleccionarIdAsientoDisponible(Funcion funcion) {
        List<Asiento> asientosParaEstaFuncion = funcion.getAsientosDeLaFuncion(); // Obtiene los asientos específicos de ESTA función

        if (asientosParaEstaFuncion == null || asientosParaEstaFuncion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay asientos configurados para esta función.", "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String[] opcionesAsientoVisual = new String[asientosParaEstaFuncion.size()];
        for (int i = 0; i < asientosParaEstaFuncion.size(); i++) {
            Asiento currentAsiento = asientosParaEstaFuncion.get(i);
            opcionesAsientoVisual[i] = currentAsiento.getIdAsiento() + (currentAsiento.esLibre() ? " (Libre)" : " (Ocupado)");
        }

        String tituloDialogo = "Comprar Ticket - Función " + funcion.getCodigoFuncion() + " (" + funcion.getPelicula().getNombre() + " - " + funcion.getHorario() + ")";
        String asientoSeleccionadoConEstado = (String) JOptionPane.showInputDialog(null,
                "Seleccione el asiento:",
                tituloDialogo,
                JOptionPane.QUESTION_MESSAGE, null,
                opcionesAsientoVisual, (opcionesAsientoVisual.length > 0 ? opcionesAsientoVisual[0] : null));

        if (asientoSeleccionadoConEstado == null) {
            return null; // Usuario canceló
        }

        String idAsientoSeleccionado = asientoSeleccionadoConEstado.split(" ")[0];
        Asiento asientoVerificar = funcion.buscarAsientoEnFuncion(idAsientoSeleccionado); // Busca en los asientos de la función

        if (asientoVerificar == null) {
            JOptionPane.showMessageDialog(null, "Error interno: Asiento seleccionado no es válido.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!asientoVerificar.esLibre()) {
            JOptionPane.showMessageDialog(null,
                    "El asiento " + idAsientoSeleccionado + " ya está OCUPADO para esta función. Por favor, elija otro.",
                    "Asiento Ocupado", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return idAsientoSeleccionado;
    }


    private static void comprarTicket() {
        Funcion funcion = seleccionarFuncionParaCompra("Seleccione la función para la cual desea comprar un ticket:");
        if (funcion == null) return;

        String idEspectador = generarIdEspectadorUnico();
        String nombreEspectador = JOptionPane.showInputDialog(null, "Ingrese el nombre del espectador:", "Comprar Ticket", JOptionPane.INFORMATION_MESSAGE);
        if (nombreEspectador == null || nombreEspectador.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del espectador es obligatorio.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String identificacionEspectador = JOptionPane.showInputDialog(null, "Ingrese la identificación del espectador:", "Comprar Ticket", JOptionPane.INFORMATION_MESSAGE);
         if (identificacionEspectador == null || identificacionEspectador.trim().isEmpty()) {
             JOptionPane.showMessageDialog(null, "La identificación del espectador es obligatoria.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Espectador espectador = new Espectador(idEspectador, nombreEspectador, identificacionEspectador);
        cine.getEspectadores().add(espectador); // Añadir espectador a la lista general del cine

        String idAsientoSeleccionado = seleccionarIdAsientoDisponible(funcion); // Usa el método que opera sobre la función
        if (idAsientoSeleccionado == null) {
            return; // Cancelado o asiento no disponible/seleccionado
        }

        boolean exito = funcion.reservarAsiento(idAsientoSeleccionado, espectador); // Llama a reservarAsiento de Funcion

        if (exito) {
            JOptionPane.showMessageDialog(null,
                    "¡Ticket comprado con éxito!\n" +
                            "Función: " + funcion.getCodigoFuncion() + " - " + funcion.getPelicula().getNombre() + "\n" +
                            "Espectador: " + nombreEspectador + "\n" +
                            "Asiento: " + idAsientoSeleccionado,
                    "Compra Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "No se pudo comprar el ticket para el asiento " + idAsientoSeleccionado + ".\n" +
                            "Verifique la disponibilidad o inténtelo de nuevo.",
                    "Error en la Compra", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String generarIdEspectadorUnico() {
        String id;
        Random random = new Random();
        do {
            id = "ESP" + String.format("%05d", random.nextInt(100000));
        } while (cine.buscarEspectador(id) != null);
        return id;
    }

    private static Funcion seleccionarFuncionParaCompra(String mensajeDialogo) {
        if (cine.getFunciones().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay funciones programadas actualmente.", "Funciones Vacías", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        String[] opcionesFuncion = new String[cine.getFunciones().size()];
        for (int i = 0; i < cine.getFunciones().size(); i++) {
            Funcion f = cine.getFunciones().get(i);
            opcionesFuncion[i] = "Función: " + f.getCodigoFuncion() + " - " + f.getPelicula().getNombre() + " (Sala " + f.getSala().getId() + " - " + f.getHorario() + ")";
        }

        String funcionSeleccionadaStr = (String) JOptionPane.showInputDialog(null, mensajeDialogo,
                "Seleccionar Función", JOptionPane.QUESTION_MESSAGE, null, opcionesFuncion, opcionesFuncion[0]);

        if (funcionSeleccionadaStr == null) return null;

        // Extraer código de la función, ej: "Función: FUNC0001" -> "FUNC0001"
        String codigoFuncion = funcionSeleccionadaStr.split(" ")[1];
        return cine.buscarFuncion(codigoFuncion);
    }


    private static void mostrarPuestosDisponiblesYOcupadosPorFuncion() {
        Funcion funcion = seleccionarFuncionParaVisualizacion("Seleccione la función para ver el estado de sus asientos:");
        if (funcion == null) return;

        // Llama al método en Funcion que devuelve el string formateado de sus asientos
        String estadoAsientos = funcion.getEstadoCompletoAsientosFuncion();
        JOptionPane.showMessageDialog(null, estadoAsientos,
                "Estado de Asientos - Función " + funcion.getCodigoFuncion(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static Funcion seleccionarFuncionParaVisualizacion(String mensajeDialogo) {
        // Este método puede ser idéntico a seleccionarFuncionParaCompra o especializado si es necesario.
        // Reutilizamos seleccionarFuncionParaCompra para simplicidad.
        return seleccionarFuncionParaCompra(mensajeDialogo);
    }

    private static void mostrarInformacionCine() {
        StringBuilder info = new StringBuilder("===== Información del Cine =====\n");
        info.append("Nombre: ").append(cine.getNombre()).append("\n");
        info.append("Dirección: ").append(cine.getDireccion()).append("\n");
        info.append("Capacidad Estimada: ").append(cine.getCapacidad()).append(" personas\n\n");

        info.append("--- Películas en Cartelera ---\n");
        if (cine.getPeliculas().isEmpty()) {
            info.append("No hay películas registradas.\n");
        } else {
            for (Pelicula pelicula : cine.getPeliculas()) {
                info.append("- ").append(pelicula.getNombre())
                    .append(" (").append(pelicula.getMinDuracion()).append(" min, Dir: ")
                    .append(pelicula.getDirector()).append(")\n");
            }
        }

        info.append("\n--- Salas Disponibles ---\n");
        if (cine.getSalas().isEmpty()) {
            info.append("No hay salas registradas.\n");
        } else {
            for (Sala sala : cine.getSalas()) {
                info.append("- Sala ID ").append(sala.getId())
                    .append(" (Capacidad: ").append(sala.getCapacidadTotal())
                    .append(", Tec: ").append(sala.getTecnologiaDeProyeccion());
                if (sala instanceof Sala2D) {
                    info.append(", Sonido: ").append(((Sala2D) sala).getTipoSonido());
                } else if (sala instanceof Sala3D) {
                    info.append(", Gafas: ").append(((Sala3D) sala).getTipoGafas());
                    info.append(", Filas VIP: ").append(((Sala3D) sala).getAsientosVip() != null ? ((Sala3D) sala).getAsientosVip().length : 0);
                }
                info.append(")\n");
            }
        }

        info.append("\n--- Funciones Programadas ---\n");
        if (cine.getFunciones().isEmpty()) {
            info.append("No hay funciones programadas.\n");
        } else {
            for (Funcion funcion : cine.getFunciones()) {
                info.append("- Cod: ").append(funcion.getCodigoFuncion())
                    .append(" | ").append(funcion.getPelicula().getNombre())
                    .append(" en Sala ").append(funcion.getSala().getId())
                    .append(" a las ").append(funcion.getHorario()).append("\n");
            }
        }
        
        info.append("\n--- Espectadores Registrados (muestra) ---\n");
        if (cine.getEspectadores().isEmpty()) {
            info.append("No hay espectadores registrados.\n");
        } else {
            int count = 0;
            for (Espectador esp : cine.getEspectadores()) {
                info.append("- ID: ").append(esp.getIdEspectador()).append(", Nombre: ").append(esp.getNombre()).append("\n");
                count++;
                if (count >= 5) { // Mostrar solo los primeros 5 para no alargar mucho
                    info.append("... y más.\n");
                    break;
                }
            }
        }


        JOptionPane.showMessageDialog(null, info.toString(), "Información General del Cine " + cine.getNombre(), JOptionPane.INFORMATION_MESSAGE);
    }

    /*
    // Método de ejemplo para precargar datos, si se desea probar rápidamente.
    private static void precargarDatos() {
        // Crear Películas
        Pelicula p1 = new Pelicula("Interestelar", 169, "Christopher Nolan");
        Pelicula p2 = new Pelicula("El Origen", 148, "Christopher Nolan");
        Pelicula p3 = new Pelicula("Spider-Man: Sin Camino A Casa", 148, "Jon Watts");
        cine.getPeliculas().add(p1);
        cine.getPeliculas().add(p2);
        cine.getPeliculas().add(p3);

        // Crear Salas
        Sala s1 = new Sala2D(cine.getSalas().size() + 1, 5, 6, "2D", "Dolby Atmos"); // 30 asientos
        Sala s2 = new Sala3D(cine.getSalas().size() + 1, 6, 8, "3D RealD", "Activas Premium", 2); // 48 asientos, 2 filas VIP
        cine.getSalas().add(s1);
        cine.getSalas().add(s2);

        // Crear Funciones
        // La creación de la función ahora maneja la copia de asientos internamente.
        cine.crearFuncion(generarCodigoFuncionUnico(), s1, p1, "17:00");
        cine.crearFuncion(generarCodigoFuncionUnico(), s1, p2, "20:00");
        cine.crearFuncion(generarCodigoFuncionUnico(), s2, p3, "18:30");
        cine.crearFuncion(generarCodigoFuncionUnico(), s2, p1, "21:30");

        System.out.println("Datos de prueba precargados.");
    }
    */
}