package co.edu.uniquindio.empresalogistica.Model.Decorator;

import co.edu.uniquindio.empresalogistica.Model.Service.ServicioEnvio;

public class FirmaDigitalDecorator extends ServicioAdicionalDecorator{
    private static final double COSTO_FIRMA = 3000.0;

    public FirmaDigitalDecorator(ServicioEnvio servicioDecorado) {
        super(servicioDecorado);
    }

    @Override
    public String getDescripcionServicios() {
        return servicioDecorado.getDescripcionServicios() + " + Firma Digital";
    }

    @Override
    public double calcularCostoAdicional() {
        return servicioDecorado.calcularCostoAdicional() + COSTO_FIRMA;
    }

    @Override
    public String obtenerDetallesCompletos() {
        return servicioDecorado.obtenerDetallesCompletos() +
                String.format("\n  ✓ Firma Digital: $%.2f (verificación biométrica)", COSTO_FIRMA);
    }
}
