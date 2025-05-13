package model;

public class Sala3D extends Sala {
    private String tipoGafas;
    private Asiento[][] asientosVip;

    public Sala3D(int id, int filas, int columnas, String tecnologiaDeProyeccion, String tipoGafas, int filasVip) {
        super(id, filas, columnas, tecnologiaDeProyeccion);
        this.tipoGafas = tipoGafas;
        this.asientosVip = new Asiento[filasVip][columnas];
    }

    public String getTipoGafas() {
        return tipoGafas;
    }

    public void setTipoGafas(String tipoGafas) {
        this.tipoGafas = tipoGafas;
    }

    public Asiento[][] getAsientosVip() {
        return asientosVip;
    }

    public void setAsientosVip(Asiento[][] asientosVip) {
        this.asientosVip = asientosVip;
    }
    
}
