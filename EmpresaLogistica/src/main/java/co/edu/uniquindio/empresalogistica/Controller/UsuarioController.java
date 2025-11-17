package co.edu.uniquindio.empresalogistica.Controller;

import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;

import java.time.LocalDate;
import java.util.List;

public class UsuarioController {
    private EmpresaLogisticaFactory factory;

    public UsuarioController() {
        this.factory = EmpresaLogisticaFactory.getInstance();
    }

    // ========== GESTIÓN DE PERFIL ==========

    public UsuarioDTO obtenerUsuarioActual() {
        return factory.getUsuarioActual();
    }

    public boolean actualizarUsuario(UsuarioDTO usuarioDTO) {
        try {
            factory.actualizarUsuario(usuarioDTO);
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // ========== GESTIÓN DE CARTERA ==========

    public double obtenerSaldo(String correo) {
        return factory.obtenerSaldoCartera(correo);
    }

    public boolean recargarCartera(String correo, double monto) {
        try {
            factory.recargarCartera(correo, monto);
            return true;
        } catch (Exception e) {
            System.err.println("Error al recargar: " + e.getMessage());
            return false;
        }
    }

    public boolean descontarSaldo(String correo, double monto) {
        try {
            factory.descontarDeCartera(correo, monto);
            return true;
        } catch (Exception e) {
            System.err.println("Error al descontar: " + e.getMessage());
            return false;
        }
    }

    // ========== GESTIÓN DE ENVÍOS ==========

    public double cotizarEnvio(double peso, double distancia, String prioridad,
                               boolean zonaRural, String tipoTarifa) {
        return factory.cotizarEnvio(peso, distancia, prioridad, zonaRural, tipoTarifa);
    }

    public Envio crearEnvio(String origen, String destino, double peso, double volumen,
                            String descripcion, String correoUsuario) {
        return factory.crearEnvio(origen, destino, peso, volumen, descripcion, correoUsuario);
    }

    public List<Envio> obtenerEnviosPorUsuario(String correoUsuario) {
        return factory.obtenerEnviosPorUsuario(correoUsuario);
    }

    public Envio obtenerEnvioPorId(String idEnvio) {
        try {
            return factory.obtenerEnvioPorId(idEnvio);
        } catch (Exception e) {
            System.err.println("Error al obtener envío: " + e.getMessage());
            return null;
        }
    }

    public boolean actualizarEstadoEnvio(String idEnvio, EstadoEnvio nuevoEstado) {
        try {
            factory.actualizarEstadoEnvio(idEnvio, nuevoEstado);
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelarEnvio(String idEnvio) {
        try {
            factory.cancelarEnvio(idEnvio);
            return true;
        } catch (Exception e) {
            System.err.println("Error al cancelar envío: " + e.getMessage());
            return false;
        }
    }

    public List<Envio> filtrarEnviosPorEstado(EstadoEnvio estado) {
        return factory.filtrarEnviosPorEstado(estado);
    }

    public List<Envio> filtrarEnviosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return factory.filtrarEnviosPorFecha(fechaInicio, fechaFin);
    }

    // ========== GESTIÓN DE SESIÓN ==========

    public void cerrarSesion() {
        factory.logout();
    }
}