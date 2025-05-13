package model;

public class Sala2D extends Sala {
    private String tipoSonido;

    public Sala2D(int id, int filas, int columnas, String tecnologiaDeProyeccion, String tipoSonido) {
        super(id, filas, columnas, tecnologiaDeProyeccion);
        this.tipoSonido = tipoSonido;
    }

    public String getTipoSonido() {
        return tipoSonido;
    }

    public void setTipoSonido(String tipoSonido) {
        this.tipoSonido = tipoSonido;
    }

}