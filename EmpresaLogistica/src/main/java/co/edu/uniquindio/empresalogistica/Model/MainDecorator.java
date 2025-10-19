package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Decorator.*;
import co.edu.uniquindio.empresalogistica.Model.Service.ServicioEnvio;

public class MainDecorator {
    public static void main(String[] args) {

        System.out.println("    PATRÓN DECORATOR - SERVICIOS ADICIONALES     ");

        Direccion origen = new Direccion("D001", "Calle 19", "19","100500",6.7,8.9,"Ninguna");
        Direccion destino = new Direccion("D011", "Calle Bolivar", "14","100500",7.2,9.3,"Ninguna");
        Envio envioReal = new Envio(
                "ENV-2024-001",
                origen,
                destino,
                5000.0,  // 5 kg
                30000.0, // 30,000 cm³
                "Documentos importantes"
        );
        envioReal.setCostoTotal(25000.0);

        System.out.println("=== ENVÍO BÁSICO (Sin servicios) ===");
        ServicioEnvio servicio = new EnvioBasico(envioReal);
        System.out.println(servicio.obtenerDetallesCompletos());
        System.out.println("Costo adicional: $0.00");
        System.out.println("TOTAL: $" + String.format("%.2f",
                envioReal.getCostoTotal() + servicio.calcularCostoAdicional()));
        System.out.println();

        // Agregar seguro
        System.out.println("=== Agregando SEGURO ===");
        servicio = new SeguroEnvioDecorator(servicio, 500000.0);
        System.out.println(servicio.obtenerDetallesCompletos());
        System.out.println("Costo adicional: $" + String.format("%.2f",
                servicio.calcularCostoAdicional()));
        System.out.println("TOTAL: $" + String.format("%.2f",
                envioReal.getCostoTotal() + servicio.calcularCostoAdicional()));
        System.out.println();

        // Agregar rastreo
        System.out.println("=== Agregando RASTREO PREMIUM ===");
        servicio = new RastreoPremiumDecorator(servicio);
        System.out.println(servicio.obtenerDetallesCompletos());
        System.out.println("Costo adicional: $" + String.format("%.2f",
                servicio.calcularCostoAdicional()));
        System.out.println("TOTAL: $" + String.format("%.2f",
                envioReal.getCostoTotal() + servicio.calcularCostoAdicional()));
        System.out.println();

        // Agregar entrega nocturna
        System.out.println("=== Agregando ENTREGA NOCTURNA ===");
        servicio = new EntregaNocturnaDecorator(servicio, "8:00 PM - 11:00 PM");
        System.out.println(servicio.obtenerDetallesCompletos());
        System.out.println("Costo adicional: $" + String.format("%.2f",
                servicio.calcularCostoAdicional()));
        System.out.println("TOTAL: $" + String.format("%.2f",
                envioReal.getCostoTotal() + servicio.calcularCostoAdicional()));
        System.out.println();

        // Agregar firma digital
        System.out.println("=== Agregando FIRMA DIGITAL ===");
        servicio = new FirmaDigitalDecorator(servicio);
        System.out.println(servicio.obtenerDetallesCompletos());
        System.out.println("Costo adicional: $" + String.format("%.2f",
                servicio.calcularCostoAdicional()));
        System.out.println("TOTAL: $" + String.format("%.2f",
                envioReal.getCostoTotal() + servicio.calcularCostoAdicional()));
        System.out.println();

        // Agregar embalaje especial
        System.out.println("=== Agregando EMBALAJE ESPECIAL ===");
        servicio = new EmbalajeEspecialDecorator(servicio, "Antihumedad", 4000.0);
        System.out.println(servicio.obtenerDetallesCompletos());
        System.out.println("Costo adicional: $" + String.format("%.2f",
                servicio.calcularCostoAdicional()));
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COSTO FINAL TOTAL: $" + String.format("%.2f",
                envioReal.getCostoTotal() + servicio.calcularCostoAdicional()));
        System.out.println("=".repeat(60));

        envioReal.setCostoTotal(envioReal.getCostoTotal() + servicio.calcularCostoAdicional());

        System.out.println("\n El envío original se actualizó con el costo total");
        System.out.println("   Costo final en Envio: $" + envioReal.getCostoTotal());
    }
}
