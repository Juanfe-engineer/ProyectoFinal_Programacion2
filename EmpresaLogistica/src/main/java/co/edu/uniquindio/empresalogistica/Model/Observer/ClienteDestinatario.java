package co.edu.uniquindio.empresalogistica.Model.Observer;

import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Service.ObservadorEnvio;

import java.util.Observer;

public class ClienteDestinatario implements ObservadorEnvio {
    private String nombre;
    private String telefono;

    public ClienteDestinatario(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    @Override
    public void actualizar(String idEnvio, EstadoEnvio nuevoEstado, String mensaje) {
        System.out.println(" [SMS → " + telefono + "]");
        System.out.println("   Hola " + nombre + "!");
        System.out.println("   Paquete #" + idEnvio + ": " + mensaje);

        if (nuevoEstado == EstadoEnvio.EN_REPARTO) {
            System.out.println("   ⚠️  Tu paquete llegará en 1-2 horas. Prepárate para recibirlo.");
        }
    }

    @Override
    public String getNombreObservador() {
        return nombre;
    }

    @Override
    public String getTipoObservador() {
        return "Cliente Destinatario";
    }
}
