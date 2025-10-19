package co.edu.uniquindio.empresalogistica.Model.Service;

import co.edu.uniquindio.empresalogistica.Model.Strategy.InfoEnvio;

public interface TarifaStrategy {
    double calcularTarifa(InfoEnvio infoEnvio);
    String getNombreEstrategia();
    String getDescripcion();
}
