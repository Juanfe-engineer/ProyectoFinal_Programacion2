package co.edu.uniquindio.empresalogistica.Model.Service;

import co.edu.uniquindio.empresalogistica.Model.Envio;

public interface ServicioEnvio {
    Envio getEnvioBase();
    String getDescripcionServicios();
    double calcularCostoAdicional();
    String obtenerDetallesCompletos();
}
