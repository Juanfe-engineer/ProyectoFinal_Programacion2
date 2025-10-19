package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Enums.DisponibilidadRepartidor;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoRepartidor;
import co.edu.uniquindio.empresalogistica.Model.Service.ObservadorEnvio;

import java.time.LocalDate;

public class Repartidor extends Persona implements ObservadorEnvio {
    private DisponibilidadRepartidor disponibilidadRepartidor;
    private String ZonaCobertura;
    private LocalDate fechaRegistro;
    private EstadoRepartidor estado;
    private String enviosRealizados;


    public Repartidor(String id, String nombre, String correo,
                      String telefono, DisponibilidadRepartidor disponibilidadRepartidor,
                      String zonaCobertura, LocalDate fechaRegistro, EstadoRepartidor estado,String enviosRealizados) {
        super(id, nombre, correo, telefono);
        this.disponibilidadRepartidor = disponibilidadRepartidor;
        this.ZonaCobertura = zonaCobertura;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.enviosRealizados = enviosRealizados;
    }

    public DisponibilidadRepartidor getDisponibilidadRepartidor() {
        return disponibilidadRepartidor;
    }

    public void setDisponibilidadRepartidor(DisponibilidadRepartidor disponibilidadRepartidor) {
        this.disponibilidadRepartidor = disponibilidadRepartidor;
    }

    public String getZonaCobertura() {
        return ZonaCobertura;
    }

    public void setZonaCobertura(String zonaCobertura) {
        ZonaCobertura = zonaCobertura;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "disponibilidadRepartidor" + disponibilidadRepartidor +
                ", ZonaCobertura'" + ZonaCobertura + '\'' +
                ", fechaRegistro" + fechaRegistro +
                ", id'" + id + '\'' +
                ", nombre'" + nombre + '\'' +
                ", correo'" + correo + '\'' +
                ", telefono'" + telefono + '\'' +
                '}';
    }

    @Override
    public void actualizar(String idEnvio, EstadoEnvio nuevoEstado, String mensaje) {
        System.out.println("📲 [NOTIFICACIÓN APP REPARTIDOR - " + this.nombre + "]");
        System.out.println("   ID Repartidor: " + this.id);
        System.out.println("   Envío #" + idEnvio + ": " + mensaje);
        System.out.println("   Estado: " + nuevoEstado.getDescripcion());

        // Lógica específica según el estado
        switch (nuevoEstado) {
            case CONFIRMADO:
                System.out.println("   ➡️  Nuevo envío asignado. Dirígete al punto de recogida.");
                this.estado = EstadoRepartidor.EN_CAMINO;
                break;

            case RECOGIDO:
                System.out.println("   ✅ Confirma que recogiste el paquete en la app.");
                break;

            case EN_RUTA:
                System.out.println("   🚚 En camino al destino. Mantén actualizada tu ubicación.");
                this.estado = EstadoRepartidor.OCUPADO;
                break;

            case EN_REPARTO:
                System.out.println("   📍 Cerca del destino. Prepárate para la entrega.");
                break;

            case ENTREGADO:
                System.out.println("   🎉 Entrega completada. ¡Buen trabajo!");
                this.estado = EstadoRepartidor.DISPONIBLE;
                // Incrementar contador de envíos
                int envios = Integer.parseInt(this.enviosRealizados);
                this.enviosRealizados = String.valueOf(envios + 1);
                break;

            case CANCELADO:
                System.out.println("   ⚠️  Envío cancelado. Regresa el paquete al centro.");
                this.estado = EstadoRepartidor.DISPONIBLE;
                break;

            default:
                System.out.println("   ℹ️  Estado actualizado.");
        }
    }

    @Override
    public String getNombreObservador() {
        return this.nombre + " (" + this.id + ")";
    }

    @Override
    public String getTipoObservador() {
        return "Repartidor";
    }
}
