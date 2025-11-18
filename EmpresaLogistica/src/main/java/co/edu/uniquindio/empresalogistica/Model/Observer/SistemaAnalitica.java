package co.edu.uniquindio.empresalogistica.Model.Observer;

import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Service.ObservadorEnvio;

public class SistemaAnalitica implements ObservadorEnvio {
    private int enviosMonitoreados = 0;

    @Override
    public void actualizar(String idEnvio, EstadoEnvio nuevoEstado, String mensaje) {
        enviosMonitoreados++;
        System.out.println(" [ANALYTICS]");
        System.out.println("   Métricas actualizadas para envío #" + idEnvio);
        System.out.println("   Total eventos registrados: " + enviosMonitoreados);

        if (nuevoEstado == EstadoEnvio.ENTREGADO) {
            System.out.println("Entrega exitosa registrada en KPIs");
        }
    }

    @Override
    public String getNombreObservador() {
        return "Sistema de Analítica";
    }

    @Override
    public String getTipoObservador() {
        return "Analytics";
    }
}
