package co.edu.uniquindio.empresalogistica.Model.Strategy;

public class InfoEnvio {
    private double peso;
    private double distancia;
    private String prioridad;
    private boolean zonaRural;

    public InfoEnvio(double peso, double distancia, String prioridad, boolean zonaRural) {
        this.peso = peso;
        this.distancia = distancia;
        this.prioridad = prioridad;
        this.zonaRural = zonaRural;
    }

    public double getPeso() { return peso; }
    public double getDistancia() { return distancia; }
    public String getPrioridad() { return prioridad; }
    public boolean isZonaRural() { return zonaRural; }
}
