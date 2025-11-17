package co.edu.uniquindio.empresalogistica.Mapping.Dto;

import java.time.LocalDate;

public class UsuarioDTO {
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private LocalDate fechaRegistro;
    private String estado; // ACTIVO, INACTIVO, SUSPENDIDO
    private String direccionFrecuente;
    private String metodosPago;
    private String enviosRealizados;

    // Constructor vacío
    public UsuarioDTO() {}

    // Constructor completo
    public UsuarioDTO(String id, String nombre, String correo, String telefono,
                      LocalDate fechaRegistro, String estado, String direccionFrecuente,
                      String metodosPago, String enviosRealizados) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.direccionFrecuente = direccionFrecuente;
        this.metodosPago = metodosPago;
        this.enviosRealizados = enviosRealizados;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccionFrecuente() {
        return direccionFrecuente;
    }

    public void setDireccionFrecuente(String direccionFrecuente) {
        this.direccionFrecuente = direccionFrecuente;
    }

    public String getMetodosPago() {
        return metodosPago;
    }

    public void setMetodosPago(String metodosPago) {
        this.metodosPago = metodosPago;
    }

    public String getEnviosRealizados() {
        return enviosRealizados;
    }

    public void setEnviosRealizados(String enviosRealizados) {
        this.enviosRealizados = enviosRealizados;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estado='" + estado + '\'' +
                ", direccionFrecuente='" + direccionFrecuente + '\'' +
                ", metodosPago='" + metodosPago + '\'' +
                ", enviosRealizados='" + enviosRealizados + '\'' +
                '}';
    }

    public void setPassword(String password) {
    }
}
