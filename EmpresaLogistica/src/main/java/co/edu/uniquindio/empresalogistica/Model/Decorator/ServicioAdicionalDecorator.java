package co.edu.uniquindio.empresalogistica.Model.Decorator;

import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Service.ServicioEnvio;

public class ServicioAdicionalDecorator implements ServicioEnvio {
    protected ServicioEnvio servicioDecorado;

    public ServicioAdicionalDecorator(ServicioEnvio servicioDecorado) {
        this.servicioDecorado = servicioDecorado;
    }

    @Override
    public Envio getEnvioBase() {
        return servicioDecorado.getEnvioBase();
    }

    @Override
    public String getDescripcionServicios() {
        return servicioDecorado.getDescripcionServicios();
    }

    @Override
    public double calcularCostoAdicional() {
        return servicioDecorado.calcularCostoAdicional();
    }

    @Override
    public String obtenerDetallesCompletos() {
        return servicioDecorado.obtenerDetallesCompletos();
    }
}
