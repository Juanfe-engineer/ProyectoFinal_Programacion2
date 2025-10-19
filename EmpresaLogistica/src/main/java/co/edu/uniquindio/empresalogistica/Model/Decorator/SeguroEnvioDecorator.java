package co.edu.uniquindio.empresalogistica.Model.Decorator;

import co.edu.uniquindio.empresalogistica.Model.Service.ServicioEnvio;

public class SeguroEnvioDecorator extends ServicioAdicionalDecorator{
    private double valorAsegurado;

    public SeguroEnvioDecorator(ServicioEnvio servicioDecorado, double valorAsegurado) {
        super(servicioDecorado);
        this.valorAsegurado = valorAsegurado;
    }

    @Override
    public String getDescripcionServicios() {
        return servicioDecorado.getDescripcionServicios() + " + Seguro";
    }

    @Override
    public double calcularCostoAdicional() {
        double costoSeguro = valorAsegurado * 0.02; // 2% del valor
        return servicioDecorado.calcularCostoAdicional() + costoSeguro;
    }

    @Override
    public String obtenerDetallesCompletos() {
        return servicioDecorado.obtenerDetallesCompletos() +
                String.format("\n  ✓ Seguro: $%.2f (valor asegurado: $%.2f)",
                        valorAsegurado * 0.02, valorAsegurado);
    }
}
