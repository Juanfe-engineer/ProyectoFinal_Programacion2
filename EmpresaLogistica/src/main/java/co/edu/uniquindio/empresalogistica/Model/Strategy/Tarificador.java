package co.edu.uniquindio.empresalogistica.Model.Strategy;

import co.edu.uniquindio.empresalogistica.Model.Service.TarifaStrategy;

public class Tarificador {
    private TarifaStrategy estrategia;

    public Tarificador(TarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    /*
     * Cambia la estrategia en tiempo de ejecución
     */
    public void setEstrategia(TarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    /*
     * Calcula la tarifa usando la estrategia actual
     */
    public double calcularCosto(InfoEnvio info) {
        return estrategia.calcularTarifa(info);
    }

    public String getInfoEstrategia() {
        return estrategia.getNombreEstrategia() + ": " + estrategia.getDescripcion();
    }
}
