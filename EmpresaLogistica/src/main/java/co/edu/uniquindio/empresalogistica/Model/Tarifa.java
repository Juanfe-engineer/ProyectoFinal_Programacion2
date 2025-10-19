package co.edu.uniquindio.empresalogistica.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tarifa {
    private String idTarifa;
    private Double tarifaBase;
    private Double costoPorKilometro;
    private Double costoPorKilogramo;
    private Double costoPorMetroCubico;
    private Double factorPrioridad;
    private LocalDateTime fechaVigencia;
    private boolean activa;

    public Tarifa(){
    }

    public Tarifa(String idTarifa, Double tarifaBase, Double costoPorKilometro,
                  Double costoPorKilogramo, Double costoPorMetroCubico,
                  Double factorPrioridad, LocalDateTime fechaVigencia, boolean activa) {
        this.idTarifa = idTarifa;
        this.tarifaBase = tarifaBase;
        this.costoPorKilometro = costoPorKilometro;
        this.costoPorKilogramo = costoPorKilogramo;
        this.costoPorMetroCubico = costoPorMetroCubico;
        this.factorPrioridad = factorPrioridad;
        this.fechaVigencia = fechaVigencia;
        this.activa = activa;
    }

    // Metodo para calcular costo

    public double calcularCosto(double peso, int distancia, double Volumen){
        if(!activa){
            return 0;
        }

        double costo = tarifaBase;
        costo += (distancia * costoPorKilometro);
        costo += (peso * costoPorKilogramo);
        costo += (Volumen * costoPorMetroCubico);
        costo *= factorPrioridad;

        return costo;
    }

    // Getters and Setters

    public String getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(String idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(Double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public Double getCostoPorKilometro() {
        return costoPorKilometro;
    }

    public void setCostoPorKilometro(Double costoPorKilometro) {
        this.costoPorKilometro = costoPorKilometro;
    }

    public Double getCostoPorKilogramo() {
        return costoPorKilogramo;
    }

    public void setCostoPorKilogramo(Double costoPorKilogramo) {
        this.costoPorKilogramo = costoPorKilogramo;
    }

    public Double getCostoPorMetroCubico() {
        return costoPorMetroCubico;
    }

    public void setCostoPorMetroCubico(Double costoPorMetroCubico) {
        this.costoPorMetroCubico = costoPorMetroCubico;
    }

    public Double getFactorPrioridad() {
        return factorPrioridad;
    }

    public void setFactorPrioridad(Double factorPrioridad) {
        this.factorPrioridad = factorPrioridad;
    }

    public LocalDateTime getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(LocalDateTime fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Tarifa{" +
                "idTarifa'" + idTarifa + '\'' +
                ", tarifaBase" + tarifaBase +
                ", costoPorKilometro" + costoPorKilometro +
                ", costoPorKilogramo" + costoPorKilogramo +
                ", costoPorMetroCubico" + costoPorMetroCubico +
                ", factorPrioridad" + factorPrioridad +
                ", fechaVigencia" + fechaVigencia +
                ", activa" + activa +
                '}';
    }
}
