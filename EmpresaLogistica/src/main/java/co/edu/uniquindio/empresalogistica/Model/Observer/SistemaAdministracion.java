package co.edu.uniquindio.empresalogistica.Model.Observer;

import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Service.ObservadorEnvio;

public class SistemaAdministracion implements ObservadorEnvio {
    @Override
    public void actualizar(String idEnvio, EstadoEnvio nuevoEstado, String mensaje) {
        System.out.println("  [SISTEMA ADMIN]");
        System.out.println("   LOG: Envío #" + idEnvio + " → " + nuevoEstado);
        System.out.println("   Registrado en base de datos.");

        if (nuevoEstado == EstadoEnvio.CANCELADO || nuevoEstado == EstadoEnvio.DEVUELTO) {
            System.out.println("   Alerta: Requiere revisión del administrador");
        }
    }

    @Override
    public String getNombreObservador() {
        return "Sistema Central";
    }

    @Override
    public String getTipoObservador() {
        return "Administración";
    }
}
