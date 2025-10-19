package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Strategy.*;

public class MainStrategy {
    public static void main(String[] args) {
        // Información del envío
        InfoEnvio envio = new InfoEnvio(5.0, 50.0, "EXPRESS", false);

        System.out.println("=== INFORMACIÓN DEL ENVÍO ===");
        System.out.println("Peso: " + envio.getPeso() + " kg");
        System.out.println("Distancia: " + envio.getDistancia() + " km");
        System.out.println("Prioridad: " + envio.getPrioridad());
        System.out.println("Zona rural: " + (envio.isZonaRural() ? "Sí" : "No"));
        System.out.println();

        // Crear tarificador
        Tarificador tarificador = new Tarificador(new TarifaPorPeso());

        // Probar con Tarifa por Peso
        System.out.println("1. " + tarificador.getInfoEstrategia());
        System.out.println("   Costo: $" + String.format("%.2f", tarificador.calcularCosto(envio)));
        System.out.println();

        // Cambiar a Tarifa por Distancia
        tarificador.setEstrategia(new TarifaPorDistancia());
        System.out.println("2. " + tarificador.getInfoEstrategia());
        System.out.println("   Costo: $" + String.format("%.2f", tarificador.calcularCosto(envio)));
        System.out.println();

        // Cambiar a Tarifa por Prioridad
        tarificador.setEstrategia(new TarifaPorPrioridad());
        System.out.println("3. " + tarificador.getInfoEstrategia());
        System.out.println("   Costo: $" + String.format("%.2f", tarificador.calcularCosto(envio)));
        System.out.println();

        // Cambiar a Tarifa Combinada
        tarificador.setEstrategia(new TarifaCombinada());
        System.out.println("4. " + tarificador.getInfoEstrategia());
        System.out.println("   Costo: $" + String.format("%.2f", tarificador.calcularCosto(envio)));
        System.out.println();

        System.out.println("=== VENTAJA: La estrategia se cambio sin modificar el Tarificador ===");
    }
}
