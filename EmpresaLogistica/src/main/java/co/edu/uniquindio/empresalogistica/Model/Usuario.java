package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoUsuario;

import java.time.LocalDate;

public class Usuario extends Persona{
    private LocalDate fechaRegistro;
    private EstadoUsuario estado;
    private String direccionFrecuente;
    private String metodosPago;
    private String enviosRealizados;


    public Usuario(String id, String nombre, String correo,
                   String telefono, LocalDate fechaRegistro,EstadoUsuario estado,
                   String direccionFrecuente, String metodosPago, String enviosRealizados) {
        super(id, nombre, correo, telefono);
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.direccionFrecuente = direccionFrecuente;
        this.metodosPago = metodosPago;
        this.enviosRealizados = enviosRealizados;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
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
        return "Usuario{" +
                "fechaRegistro" + fechaRegistro +
                ", estado" + estado +
                ", direccionFrecuente'" + direccionFrecuente + '\'' +
                ", metodosPago'" + metodosPago + '\'' +
                ", enviosRealizados'" + enviosRealizados + '\'' +
                ", id'" + id + '\'' +
                ", nombre'" + nombre + '\'' +
                ", correo'" + correo + '\'' +
                ", telefono'" + telefono + '\'' +
                '}';
    }
}
