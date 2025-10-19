package co.edu.uniquindio.empresalogistica.Model.Strategy;

import co.edu.uniquindio.empresalogistica.Model.Service.TarifaStrategy;

public class TarifaPorDistancia implements TarifaStrategy {
    private static final double TARIFA_BASE = 5000;
    private static final double TARIFA_POR_KM = 500;

    @Override
    public double calcularTarifa(InfoEnvio info) {
        double tarifa = TARIFA_BASE + (info.getDistancia() * TARIFA_POR_KM);

        if (info.isZonaRural()) {
            tarifa += 3000; // Recargo fijo para zonas rurales
        }

        return tarifa;
    }

    @Override
    public String getNombreEstrategia() {
        return "Tarifa por Distancia";
    }

    @Override
    public String getDescripcion() {
        return "Calcula según la distancia ($5000 base + $500/km)";
    }

}
