package co.edu.uniquindio.empresalogistica.Model.Enums;

public enum EstadoEnvio {
    SOLICITADO("Envío solicitado"),
    CONFIRMADO("Envío confirmado"),
    EN_PREPARACION("En preparación"),
    RECOGIDO("Paquete recogido"),
    EN_RUTA("En camino al destino"),
    EN_CENTRO_DISTRIBUCION("En centro de distribución"),
    EN_REPARTO("En reparto final"),
    ENTREGADO("Entregado exitosamente"),
    CANCELADO("Envío cancelado"),
    DEVUELTO("Devuelto al remitente");

    private final String descripcion;

    EstadoEnvio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
