package co.edu.uniquindio.empresalogistica.Model.Observer;

import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Service.ObservadorEnvio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EnvioObservable {
    private String id;
    private String origen;
    private String destino;
    private EstadoEnvio estadoActual;
    private List<ObservadorEnvio> observadores;
    private List<String> historialEstados;


    public EnvioObservable(String id, String origen, String destino) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.estadoActual = EstadoEnvio.SOLICITADO;
        this.observadores = new ArrayList<>();
        this.historialEstados = new ArrayList<>();
        registrarHistorial("Envío creado");
    }

    /**
     * Agregar observador
     */
    public void agregarObservador(ObservadorEnvio observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
            System.out.println("✓ " + observador.getNombreObservador() +
                    " (" + observador.getTipoObservador() + ") se suscribió al envío " + id);
        }
    }

    /**
     * Remover observador
     */
    public void removerObservador(ObservadorEnvio observador) {
        if (observadores.remove(observador)) {
            System.out.println("✗ " + observador.getNombreObservador() +
                    " se desuscribió del envío " + id);
        }
    }

    /**
     * Notificar a todos los observadores
     */
    private void notificarObservadores(String mensaje) {
        System.out.println("\n📢 Notificando a " + observadores.size() + " observadores...");
        for (ObservadorEnvio observador : observadores) {
            observador.actualizar(id, estadoActual, mensaje);
        }
        System.out.println();
    }

    /**
     * Cambiar estado del envío (trigger de notificaciones)
     */

    public void cambiarEstado(EstadoEnvio nuevoEstado, String detalleAdicional) {
        EstadoEnvio estadoAnterior = this.estadoActual;
        this.estadoActual = nuevoEstado;

        String mensaje = String.format("Estado cambió de '%s' a '%s'",
                estadoAnterior.getDescripcion(),
                nuevoEstado.getDescripcion());

        if (detalleAdicional != null && !detalleAdicional.isEmpty()) {
            mensaje += ". " + detalleAdicional;
        }

        registrarHistorial(mensaje);
        notificarObservadores(mensaje);
    }

    private void registrarHistorial(String evento) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        historialEstados.add(timestamp + " - " + evento);
    }

    public String getId() { return id; }
    public EstadoEnvio getEstadoActual() { return estadoActual; }
    public List<String> getHistorial() { return new ArrayList<>(historialEstados); }
}
