package co.edu.uniquindio.empresalogistica.Model.Strategy;

import co.edu.uniquindio.empresalogistica.Model.Service.TarifaStrategy;

public class TarifaPorPeso implements TarifaStrategy {
    private static final double TARIFA_BASE_KG = 1500;
    private static final double RECARGO_RURAL = 0.20; // 20%

    @Override
    public double calcularTarifa(InfoEnvio info) {
        double tarifa = info.getPeso() * TARIFA_BASE_KG;

        if (info.isZonaRural()) {
            tarifa += tarifa * RECARGO_RURAL;
        }

        return tarifa;
    }

    @Override
    public String getNombreEstrategia() {
        return "Tarifa por Peso";
    }

    @Override
    public String getDescripcion() {
        return "Calcula según el peso del paquete ($1500/kg)";
    }

}
