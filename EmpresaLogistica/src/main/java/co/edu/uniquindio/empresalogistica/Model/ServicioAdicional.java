package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Enums.TipoServicio;

import java.math.BigDecimal;

public class ServicioAdicional {
    private String idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal costoAdicional;
    private TipoServicio tipoServicio;

    public ServicioAdicional(String idServicio, String nombre, String descripcion,
                             BigDecimal costoAdicional, TipoServicio tipoServicio) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoAdicional = costoAdicional;
        this.tipoServicio = tipoServicio;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCostoAdicional() {
        return costoAdicional;
    }

    public void setCostoAdicional(BigDecimal costoAdicional) {
        this.costoAdicional = costoAdicional;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    @Override
    public String toString() {
        return "ServicioAdicional{" +
                "idServicio'" + idServicio + '\'' +
                ", nombre'" + nombre + '\'' +
                ", descripcion'" + descripcion + '\'' +
                ", costoAdicional" + costoAdicional +
                ", tipoServicio" + tipoServicio +
                '}';
    }
}
