package co.edu.uniquindio.empresalogistica.Model.Strategy;

public class InfoEnvio {
    private double peso;           // en kilogramos
    private double distancia;      // en kilómetros
    private String prioridad;      // ESTANDAR, EXPRESS, PRIORITARIO
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
