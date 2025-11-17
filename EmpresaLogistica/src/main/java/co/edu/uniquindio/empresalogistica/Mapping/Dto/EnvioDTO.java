package co.edu.uniquindio.empresalogistica.Mapping.Dto;

import java.time.LocalDateTime;

public class EnvioDTO {
    private String idEnvio;
    private String origen;
    private String destino;
    private double peso;
    private double volumen;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private LocalDateTime fechaRealEntrega;
    private double costoTotal;
    private String usuarioCorreo;

    // Constructor vacío
    public EnvioDTO() {}

    // Constructor completo
    public EnvioDTO(String idEnvio, String origen, String destino, double peso,
                    double volumen, String descripcion, String estado,
                    LocalDateTime fechaCreacion, LocalDateTime fechaEstimadaEntrega,
                    LocalDateTime fechaRealEntrega, double costoTotal, String usuarioCorreo) {
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.volumen = volumen;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
        this.fechaRealEntrega = fechaRealEntrega;
        this.costoTotal = costoTotal;
        this.usuarioCorreo = usuarioCorreo;
    }

    // Getters y Setters
    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }

    @Override
    public String toString() {
        return "EnvioDTO{" +
                "idEnvio='" + idEnvio + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", peso=" + peso +
                ", volumen=" + volumen +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaEstimadaEntrega=" + fechaEstimadaEntrega +
                ", fechaRealEntrega=" + fechaRealEntrega +
                ", costoTotal=" + costoTotal +
                ", usuarioCorreo='" + usuarioCorreo + '\'' +
                '}';
    }
}