package co.edu.uniquindio.empresalogistica.Model.Strategy;

import co.edu.uniquindio.empresalogistica.Model.Service.TarifaStrategy;

public class TarifaCombinada implements TarifaStrategy {
    @Override
    public double calcularTarifa(InfoEnvio info) {
        double tarifaPeso = info.getPeso() * 1200;
        double tarifaDistancia = info.getDistancia() * 400;
        double tarifa = tarifaPeso + tarifaDistancia;

        switch (info.getPrioridad().toUpperCase()) {
            case "EXPRESS":
                tarifa *= 1.5;
                break;
            case "PRIORITARIO":
                tarifa *= 2.0;
                break;
        }

        if (info.isZonaRural()) {
            tarifa += 5000;
        }

        return tarifa;
    }

    @Override
    public String getNombreEstrategia() {
        return "Tarifa Combinada";
    }

    @Override
    public String getDescripcion() {
        return "Combina peso y distancia con multiplicador de prioridad";
    }
}
