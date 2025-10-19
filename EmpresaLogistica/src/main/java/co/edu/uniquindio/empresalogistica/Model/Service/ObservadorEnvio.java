package co.edu.uniquindio.empresalogistica.Model.Service;

import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;

public interface ObservadorEnvio {
    void actualizar(String idEnvio, EstadoEnvio nuevoEstado, String mensaje);
    String getNombreObservador();
    String getTipoObservador();
}
