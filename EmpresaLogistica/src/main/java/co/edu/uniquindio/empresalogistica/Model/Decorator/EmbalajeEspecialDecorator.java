package co.edu.uniquindio.empresalogistica.Model.Decorator;

import co.edu.uniquindio.empresalogistica.Model.Service.ServicioEnvio;

public class EmbalajeEspecialDecorator extends ServicioAdicionalDecorator{
    private String tipoEmbalaje;
    private double costo;

    public EmbalajeEspecialDecorator(ServicioEnvio servicioDecorado,
                                     String tipoEmbalaje, double costo) {
        super(servicioDecorado);
        this.tipoEmbalaje = tipoEmbalaje;
        this.costo = costo;
    }

    @Override
    public String getDescripcionServicios() {
        return servicioDecorado.getDescripcionServicios() + " + Embalaje Especial";
    }

    @Override
    public double calcularCostoAdicional() {
        return servicioDecorado.calcularCostoAdicional() + costo;
    }

    @Override
    public String obtenerDetallesCompletos() {
        return servicioDecorado.obtenerDetallesCompletos() +
                String.format("\n  ✓ Embalaje %s: $%.2f", tipoEmbalaje, costo);
    }
}
