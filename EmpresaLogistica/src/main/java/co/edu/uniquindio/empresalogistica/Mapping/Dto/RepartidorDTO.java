package co.edu.uniquindio.empresalogistica.Mapping.Dto;

import java.time.LocalDate;

public class RepartidorDTO {
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String documento;
    private String disponibilidad;
    private String zonaCobertura;
    private LocalDate fechaRegistro;
    private String estado;
    private String enviosRealizados;
    private double calificacionPromedio;
    private int incidenciasReportadas;

    // Constructor vacío
    public RepartidorDTO() {}

    // Constructor completo
    public RepartidorDTO(String id, String nombre, String correo, String telefono,
                         String documento, String disponibilidad, String zonaCobertura,
                         LocalDate fechaRegistro, String estado, String enviosRealizados,
                         double calificacionPromedio, int incidenciasReportadas) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.documento = documento;
        this.disponibilidad = disponibilidad;
        this.zonaCobertura = zonaCobertura;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.enviosRealizados = enviosRealizados;
        this.calificacionPromedio = calificacionPromedio;
        this.incidenciasReportadas = incidenciasReportadas;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(String disponibilidad) { this.disponibilidad = disponibilidad; }

    public String getZonaCobertura() { return zonaCobertura; }
    public void setZonaCobertura(String zonaCobertura) { this.zonaCobertura = zonaCobertura; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getEnviosRealizados() { return enviosRealizados; }
    public void setEnviosRealizados(String enviosRealizados) { this.enviosRealizados = enviosRealizados; }

    public double getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(double calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }

    public int getIncidenciasReportadas() {
        return incidenciasReportadas;
    }

    public void setIncidenciasReportadas(int incidenciasReportadas) {
        this.incidenciasReportadas = incidenciasReportadas;
    }

    @Override
    public String toString() {
        return "RepartidorDTO{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", disponibilidad='" + disponibilidad + '\'' +
                ", zonaCobertura='" + zonaCobertura + '\'' +
                ", enviosRealizados='" + enviosRealizados + '\'' +
                '}';
    }
}