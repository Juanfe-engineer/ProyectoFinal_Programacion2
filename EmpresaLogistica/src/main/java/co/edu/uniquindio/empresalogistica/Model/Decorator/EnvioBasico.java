package co.edu.uniquindio.empresalogistica.Model.Decorator;

import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Service.ServicioEnvio;

public class EnvioBasico implements ServicioEnvio {
    protected Envio envio;

    public EnvioBasico(Envio envio) {
        this.envio = envio;
    }

    @Override
    public Envio getEnvioBase() {
        return envio;
    }

    @Override
    public String getDescripcionServicios() {
        return "Envío estándar";
    }

    @Override
    public double calcularCostoAdicional() {
        return 0.0; // Sin servicios adicionales
    }

    @Override
    public String obtenerDetallesCompletos() {
        return String.format("Envío #%s\n" +
                        "De: %s → A: %s\n" +
                        "Peso: %.2f kg | Costo base: $%.2f",
                envio.getIdEnvio(),
                envio.getOrigen(),
                envio.getDestino(),
                envio.getPesoGramos() / 1000.0,
                envio.getCostoTotal());
    }
}
