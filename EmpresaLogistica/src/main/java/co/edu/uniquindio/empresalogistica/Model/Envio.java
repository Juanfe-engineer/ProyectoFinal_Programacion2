package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Builder.EnvioBuilder;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Envio {
    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private double pesoGramos;
    private double volumenCm3;
    private String descripcion;
    private EstadoEnvio estadoEnvio;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private LocalDateTime fechaRealEntrega;
    private double costoTotal;
    private Pago pago;


    private Usuario usuarioAsociado;
    private Repartidor repartidorAsociado;
    private Paquete paqueteAsociado;
    private Tarifa tarifaAsociada;
    private List<ServicioAdicional> listaServiciosAdicionales = new ArrayList<>();
    private List<Incidencia> listaIncidencias = new ArrayList<>();

    public Envio(String idEnvio, Direccion origen, Direccion destino, double pesoGramos,
                 double volumenCm3, String descripcion) {
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.pesoGramos = pesoGramos;
        this.volumenCm3 = volumenCm3;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDateTime.now();
        this.costoTotal = 0;

    }

    public static EnvioBuilder builder(){
        return new EnvioBuilder();
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Direccion getOrigen() {
        return origen;
    }

    public void setOrigen(Direccion origen) {
        this.origen = origen;
    }

    public Direccion getDestino() {
        return destino;
    }

    public void setDestino(Direccion destino) {
        this.destino = destino;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public double getVolumenCm3() {
        return volumenCm3;
    }

    public void setVolumenCm3(double volumenCm3) {
        this.volumenCm3 = volumenCm3;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoEnvio getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(EstadoEnvio estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    public LocalDateTime getFechaRealEntrega() {
        return fechaRealEntrega;
    }

    public void setFechaRealEntrega(LocalDateTime fechaRealEntrega) {
        this.fechaRealEntrega = fechaRealEntrega;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "idEnvio'" + idEnvio + '\'' +
                ", origen" + origen +
                ", destino" + destino +
                ", pesoGramos" + pesoGramos +
                ", volumenCm3" + volumenCm3 +
                ", descripcion'" + descripcion + '\'' +
                ", estadoEnvio" + estadoEnvio +
                ", fechaCreacion" + fechaCreacion +
                ", fechaEstimadaEntrega" + fechaEstimadaEntrega +
                ", fechaRealEntrega" + fechaRealEntrega +
                ", costoTotal" + costoTotal +
                ", pago" + pago +
                '}';
    }
}
