package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Enums.DisponibilidadRepartidor;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoRepartidor;
import co.edu.uniquindio.empresalogistica.Model.Observer.*;
import co.edu.uniquindio.empresalogistica.Model.Service.ObservadorEnvio;

public class MainObserver {
    public static void main(String[] args) {

        System.out.println("   PATRÓN OBSERVER - SISTEMA DE NOTIFICACIONES     ");

        // Crear envío
        EnvioObservable envio = new EnvioObservable("ENV-001", "Medellín", "Bogotá");
        System.out.println(" Envío creado: " + envio.getId());
        System.out.println("   Origen: Medellín → Destino: Bogotá\n");

        // Crear observadores
        ObservadorEnvio remitente = new ClienteRemitente("Juan Pérez", "juan@email.com");
        ObservadorEnvio destinatario = new ClienteDestinatario("María García", "310-555-1234");
        ObservadorEnvio repartidor = new Repartidor("REP-789","Carlos Lopez", "Carlos@email.com","32837467271", DisponibilidadRepartidor.ACTIVO,"Norte",null, EstadoRepartidor.DISPONIBLE,"0");
        ObservadorEnvio admin = new SistemaAdministracion();
        ObservadorEnvio analytics = new SistemaAnalitica();

        // Suscribir observadores
        System.out.println("=== SUSCRIPCIÓN DE OBSERVADORES ===");
        envio.agregarObservador(remitente);
        envio.agregarObservador(destinatario);
        envio.agregarObservador(repartidor);
        envio.agregarObservador(admin);
        envio.agregarObservador(analytics);

        // Simular cambios de estado
        System.out.println("\n" + "=".repeat(60));
        System.out.println("CONFIRMANDO ENVÍO...");
        System.out.println("=".repeat(60));
        envio.cambiarEstado(EstadoEnvio.CONFIRMADO, "Repartidor asignado");

        pausa();

        System.out.println("=".repeat(60));
        System.out.println("PAQUETE RECOGIDO...");
        System.out.println("=".repeat(60));
        envio.cambiarEstado(EstadoEnvio.RECOGIDO, "Recogido en Calle 10 #50-25");

        pausa();

        System.out.println("=".repeat(60));
        System.out.println("EN RUTA...");
        System.out.println("=".repeat(60));
        envio.cambiarEstado(EstadoEnvio.EN_RUTA, "Viajando por autopista Norte");

        pausa();

        System.out.println("=".repeat(60));
        System.out.println(" EN REPARTO FINAL...");
        System.out.println("=".repeat(60));
        envio.cambiarEstado(EstadoEnvio.EN_REPARTO, "Repartidor a 2 km del destino");

        pausa();

        // Remover un observador antes de la entrega final
        System.out.println("\n  El repartidor completó su tarea, desuscribiéndose...");
        envio.removerObservador(repartidor);

        pausa();

        System.out.println("=".repeat(60));
        System.out.println("  ENTREGADO ");
        System.out.println("=".repeat(60));
        envio.cambiarEstado(EstadoEnvio.ENTREGADO, "Entregado y firmado por destinatario");

        // Mostrar historial
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" HISTORIAL DEL ENVÍO " + envio.getId());
        System.out.println("=".repeat(60));
        for (String evento : envio.getHistorial()) {
            System.out.println("   " + evento);
        }

        System.out.println("\n DEMOSTRACIÓN COMPLETA");
        System.out.println("   Los observadores fueron notificados automáticamente en cada cambio");
    }

    private static void pausa() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
