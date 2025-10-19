package co.edu.uniquindio.empresalogistica.Model.Strategy;

import co.edu.uniquindio.empresalogistica.Model.Service.TarifaStrategy;

public class TarifaPorPrioridad implements TarifaStrategy {
    private static final double TARIFA_ESTANDAR = 8000;
    private static final double TARIFA_EXPRESS = 15000;
    private static final double TARIFA_PRIORITARIO = 25000;

    @Override
    public double calcularTarifa(InfoEnvio info) {
        double tarifa;

        switch (info.getPrioridad().toUpperCase()) {
            case "EXPRESS":
                tarifa = TARIFA_EXPRESS;
                break;
            case "PRIORITARIO":
                tarifa = TARIFA_PRIORITARIO;
                break;
            default:
                tarifa = TARIFA_ESTANDAR;
        }

        if (info.getPeso() > 10) {
            tarifa += (info.getPeso() - 10) * 2000;
        }

        if (info.isZonaRural()) {
            tarifa *= 1.15; // 15% de recargo
        }

        return tarifa;
    }

    @Override
    public String getNombreEstrategia() {
        return "Tarifa por Prioridad";
    }

    @Override
    public String getDescripcion() {
        return "Calcula según el nivel de prioridad del envío";
    }
}
