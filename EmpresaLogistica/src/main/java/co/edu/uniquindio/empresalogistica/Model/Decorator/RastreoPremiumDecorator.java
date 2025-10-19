package co.edu.uniquindio.empresalogistica.Model.Decorator;

import co.edu.uniquindio.empresalogistica.Model.Service.ServicioEnvio;

public class RastreoPremiumDecorator extends ServicioAdicionalDecorator{
    private static final double COSTO_RASTREO = 5000.0;

    public RastreoPremiumDecorator(ServicioEnvio servicioDecorado) {
        super(servicioDecorado);
    }

    @Override
    public String getDescripcionServicios() {
        return servicioDecorado.getDescripcionServicios() + " + Rastreo Premium";
    }

    @Override
    public double calcularCostoAdicional() {
        return servicioDecorado.calcularCostoAdicional() + COSTO_RASTREO;
    }

    @Override
    public String obtenerDetallesCompletos() {
        return servicioDecorado.obtenerDetallesCompletos() +
                String.format("\n  ✓ Rastreo Premium: $%.2f (GPS en tiempo real)", COSTO_RASTREO);
    }
}
