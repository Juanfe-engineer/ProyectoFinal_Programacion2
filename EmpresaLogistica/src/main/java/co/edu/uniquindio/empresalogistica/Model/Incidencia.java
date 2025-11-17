package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoIncidencia;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Incidencia {
    private String idIncidencia;
    private String descripcion;
    private EstadoIncidencia estado;
    private LocalDateTime fechaReporte;
    private LocalDate fechaResolucion;
    private String solucion;

    public Incidencia(String idIncidencia, String descripcion, EstadoIncidencia estado,
                      LocalDateTime fechaReporte, LocalDate fechaResolucion, String solucion) {
        this.idIncidencia = idIncidencia;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaReporte = fechaReporte;
        this.fechaResolucion = fechaResolucion;
        this.solucion = solucion;
    }

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public LocalDate getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }


    @Override
    public String toString() {
        return "Incidencia{" +
                "idIncidencia'" + idIncidencia + '\'' +
                ", descripcion'" + descripcion + '\'' +
                ", estado" + estado +
                ", fechaReporte" + fechaReporte +
                ", fechaResolucion" + fechaResolucion +
                ", solucion'" + solucion + '\'' +
                '}';
    }
}
