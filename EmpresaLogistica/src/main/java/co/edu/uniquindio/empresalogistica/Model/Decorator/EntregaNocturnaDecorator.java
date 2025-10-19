package co.edu.uniquindio.empresalogistica.Model.Decorator;

import co.edu.uniquindio.empresalogistica.Model.Service.ServicioEnvio;

public class EntregaNocturnaDecorator extends ServicioAdicionalDecorator{
    private static final double COSTO_NOCTURNO = 8000.0;
    private String horario;

    public EntregaNocturnaDecorator(ServicioEnvio servicioDecorado, String horario) {
        super(servicioDecorado);
        this.horario = horario;
    }

    @Override
    public String getDescripcionServicios() {
        return servicioDecorado.getDescripcionServicios() + " + Entrega Nocturna";
    }

    @Override
    public double calcularCostoAdicional() {
        return servicioDecorado.calcularCostoAdicional() + COSTO_NOCTURNO;
    }

    @Override
    public String obtenerDetallesCompletos() {
        return servicioDecorado.obtenerDetallesCompletos() +
                String.format("\n  ✓ Entrega Nocturna: $%.2f (horario: %s)",
                        COSTO_NOCTURNO, horario);
    }
}
