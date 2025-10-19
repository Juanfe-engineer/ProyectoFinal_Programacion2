package co.edu.uniquindio.empresalogistica.Model.Builder;

import co.edu.uniquindio.empresalogistica.Model.Direccion;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Pago;

import java.time.LocalDateTime;

public class EnvioBuilder {
    protected String idEnvio;
    protected Direccion origen;
    protected Direccion destino;
    protected double pesoGramos;
    protected double volumenCm3;
    protected String descripcion;
    protected EstadoEnvio estadoEnvio;
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaEstimadaEntrega;
    protected LocalDateTime fechaRealEntrega;
    protected double costoTotal;
    protected Pago pago;


    public EnvioBuilder id(String idEnvio) {
        this.idEnvio = idEnvio;
        return this;
    }

    public EnvioBuilder origen(Direccion origen) {
        this.origen = origen;
        return this;
    }

    public EnvioBuilder destino(Direccion destino) {
        this.destino = destino;
        return this;
    }

    public EnvioBuilder pesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
        return this;
    }

    public EnvioBuilder volumenCm3(double volumenCm3) {
        this.volumenCm3 = volumenCm3;
        return this;
    }

    public EnvioBuilder descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public EnvioBuilder estadoEnvio(EstadoEnvio estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
        return this;
    }

    public EnvioBuilder fechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public EnvioBuilder fechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
        return this;
    }

    public EnvioBuilder fechaRealEntrega(LocalDateTime fechaRealEntrega) {
        this.fechaRealEntrega = fechaRealEntrega;
        return this;
    }

    public EnvioBuilder costoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
        return this;
    }

    public EnvioBuilder pago(Pago pago) {
        this.pago = pago;
        return this;
    }

    public Envio build() {
        return new Envio(idEnvio,origen,destino,pesoGramos,volumenCm3,descripcion);
    }

}
