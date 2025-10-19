package co.edu.uniquindio.empresalogistica.Model.Observer;

import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Service.ObservadorEnvio;

public class ClienteRemitente implements ObservadorEnvio {
    private String nombre;
    private String email;

    public ClienteRemitente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    @Override
    public void actualizar(String idEnvio, EstadoEnvio nuevoEstado, String mensaje) {
        System.out.println("📧 [EMAIL → " + email + "]");
        System.out.println("   Estimado/a " + nombre + ",");
        System.out.println("   Su envío #" + idEnvio + ": " + mensaje);
        System.out.println("   Estado actual: " + nuevoEstado.getDescripcion());
    }

    @Override
    public String getNombreObservador() {
        return nombre;
    }

    @Override
    public String getTipoObservador() {
        return "Cliente Remitente";
    }
}
