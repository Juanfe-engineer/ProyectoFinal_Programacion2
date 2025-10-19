package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoPago;
import co.edu.uniquindio.empresalogistica.Model.Enums.MetodoPago;

import java.time.LocalDate;

public class Pago {
    private String idPago;
    private Double monto;
    private LocalDate fechaPago;
    private MetodoPago metodoPago;
    private EstadoPago estadoPago;
    private String numeroTransaccion;
    private String comprobante;

    public Pago(String idPago, Double monto, LocalDate fechaPago,
                MetodoPago metodoPago, EstadoPago estadoPago,
                String numeroTransaccion, String comprobante) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.estadoPago = estadoPago;
        this.numeroTransaccion = numeroTransaccion;
        this.comprobante = comprobante;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago'" + idPago + '\'' +
                ", monto" + monto +
                ", fechaPago" + fechaPago +
                ", metodoPago" + metodoPago +
                ", estadoPago" + estadoPago +
                ", numeroTransaccion'" + numeroTransaccion + '\'' +
                ", comprobante'" + comprobante + '\'' +
                '}';
    }
}
