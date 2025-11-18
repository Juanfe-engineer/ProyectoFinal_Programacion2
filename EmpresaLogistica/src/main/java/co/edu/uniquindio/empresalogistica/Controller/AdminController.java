package co.edu.uniquindio.empresalogistica.Controller;

import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.RepartidorDTO;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Enums.DisponibilidadRepartidor;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoUsuario;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class AdminController {

    private EmpresaLogisticaFactory factory;

    public AdminController() {
        this.factory = EmpresaLogisticaFactory.getInstance();
    }

    // ==========  GESTIÓN DE USUARIOS ==========

    /*
     * Obtener todos los usuarios del sistema
     */
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return factory.obtenerTodosLosUsuarios();
    }

    /*
     * Crear un nuevo usuario
     */
    public boolean crearUsuario(UsuarioDTO usuarioDTO, String password) {
        try {
            factory.crearUsuario(usuarioDTO);
            factory.crearCredencialUsuario(usuarioDTO.getCorreo(), password);
            System.out.println(" Usuario creado: " + usuarioDTO.getNombre());
            return true;
        } catch (Exception e) {
            System.err.println(" Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    /*
     * Actualizar usuario existente
     */
    public boolean actualizarUsuario(UsuarioDTO usuarioDTO) {
        try {
            factory.actualizarUsuario(usuarioDTO);
            System.out.println(" Usuario actualizado: " + usuarioDTO.getNombre());
            return true;
        } catch (Exception e) {
            System.err.println(" Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    /*
     * Eliminar usuario
     */
    public boolean eliminarUsuario(String idUsuario) {
        try {
            factory.eliminarUsuario(idUsuario);
            System.out.println(" Usuario eliminado: " + idUsuario);
            return true;
        } catch (Exception e) {
            System.err.println(" Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    /*
     * Suspender/Activar usuario
     */
    public boolean cambiarEstadoUsuario(String idUsuario, EstadoUsuario nuevoEstado) {
        try {
            UsuarioDTO usuario = factory.obtenerUsuarioPorId(idUsuario);
            usuario.setEstado(nuevoEstado.toString());
            factory.actualizarUsuario(usuario);
            System.out.println(" Estado de usuario cambiado a: " + nuevoEstado);
            return true;
        } catch (Exception e) {
            System.err.println(" Error al cambiar estado: " + e.getMessage());
            return false;
        }
    }

    // ==========  GESTIÓN DE REPARTIDORES ==========

    /**
     * Obtener todos los repartidores
     */
    public List<RepartidorDTO> obtenerTodosLosRepartidores() {
        return factory.obtenerTodosLosRepartidores();
    }

    /*
     * Crear nuevo repartidor
     */
    public boolean crearRepartidor(RepartidorDTO repartidorDTO, String password) {
        try {
            factory.crearRepartidor(repartidorDTO);
            factory.crearCredencialUsuario(repartidorDTO.getCorreo(), password);
            System.out.println(" Repartidor creado: " + repartidorDTO.getNombre());
            return true;
        } catch (Exception e) {
            System.err.println(" Error al crear repartidor: " + e.getMessage());
            return false;
        }
    }

    /*
     * Actualizar repartidor
     */
    public boolean actualizarRepartidor(RepartidorDTO repartidorDTO) {
        try {
            factory.actualizarRepartidor(repartidorDTO);
            System.out.println(" Repartidor actualizado: " + repartidorDTO.getNombre());
            return true;
        } catch (Exception e) {
            System.err.println(" Error al actualizar repartidor: " + e.getMessage());
            return false;
        }
    }

    /*
     * Cambiar disponibilidad de repartidor
     */
    public boolean cambiarDisponibilidadRepartidor(String idRepartidor, DisponibilidadRepartidor disponibilidad) {
        try {
            factory.cambiarDisponibilidadRepartidor(idRepartidor, disponibilidad);
            System.out.println(" Disponibilidad actualizada: " + disponibilidad);
            return true;
        } catch (Exception e) {
            System.err.println(" Error al cambiar disponibilidad: " + e.getMessage());
            return false;
        }
    }

    /*
     * Obtener repartidores disponibles para asignación
     */
    public List<RepartidorDTO> obtenerRepartidoresDisponibles() {
        return factory.obtenerTodosLosRepartidores().stream()
                .filter(r -> "ACTIVO".equals(r.getDisponibilidad()))
                .collect(Collectors.toList());
    }

    // ==========  GESTIÓN DE ENVÍOS ==========

    /*
     * Obtener todos los envíos del sistema
     */
    public List<Envio> obtenerTodosLosEnvios() {
        return factory.obtenerTodosLosEnvios();
    }

    /*
     * Asignar envío a repartidor
     */
    public boolean asignarEnvioARepartidor(String idEnvio, String idRepartidor) {
        try {
            Envio envio = factory.obtenerEnvioPorId(idEnvio);
            RepartidorDTO repartidor = factory.obtenerRepartidorPorId(idRepartidor);

            if (envio == null || repartidor == null) {
                System.err.println(" Envío o repartidor no encontrado");
                return false;
            }

            // Cambiar estado del envío a CONFIRMADO
            factory.actualizarEstadoEnvio(idEnvio, EstadoEnvio.CONFIRMADO);

            System.out.println(" Envío " + idEnvio.substring(0, 8) + " asignado a " + repartidor.getNombre());
            return true;
        } catch (Exception e) {
            System.err.println(" Error al asignar envío: " + e.getMessage());
            return false;
        }
    }

    /*
     * Obtener envíos sin asignar
     */
    public List<Envio> obtenerEnviosSinAsignar() {
        return factory.obtenerTodosLosEnvios().stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.SOLICITADO)
                .collect(Collectors.toList());
    }

    // ==========  MÉTRICAS Y ESTADÍSTICAS ==========

    /*
     * Calcular tiempo promedio de entrega del sistema
     */
    public double calcularTiempoPromedioEntrega() {
        List<Envio> enviosEntregados = factory.obtenerTodosLosEnvios().stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.ENTREGADO)
                .filter(e -> e.getFechaRealEntrega() != null)
                .collect(Collectors.toList());

        if (enviosEntregados.isEmpty()) {
            return 0.0;
        }

        long totalMinutos = 0;
        for (Envio envio : enviosEntregados) {
            long minutos = java.time.Duration.between(
                    envio.getFechaCreacion(),
                    envio.getFechaRealEntrega()
            ).toMinutes();
            totalMinutos += minutos;
        }

        return (double) totalMinutos / enviosEntregados.size();
    }

    /*
     * Contar envíos por estado
     */
    public Map<EstadoEnvio, Long> contarEnviosPorEstado() {
        return factory.obtenerTodosLosEnvios().stream()
                .collect(Collectors.groupingBy(Envio::getEstadoEnvio, Collectors.counting()));
    }

    /*
     * Calcular ingresos totales
     */
    public double calcularIngresosTotales() {
        return factory.obtenerTodosLosEnvios().stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.ENTREGADO)
                .mapToDouble(Envio::getCostoTotal)
                .sum();
    }

    /*
     * Calcular ingresos por período
     */
    public double calcularIngresosPorPeriodo(LocalDate inicio, LocalDate fin) {
        return factory.obtenerTodosLosEnvios().stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.ENTREGADO)
                .filter(e -> {
                    LocalDate fecha = e.getFechaCreacion().toLocalDate();
                    return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
                })
                .mapToDouble(Envio::getCostoTotal)
                .sum();
    }

    /*
     * Obtener envíos del mes actual
     */
    public List<Envio> obtenerEnviosDelMes() {
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = LocalDate.now();

        return factory.obtenerTodosLosEnvios().stream()
                .filter(e -> {
                    LocalDate fecha = e.getFechaCreacion().toLocalDate();
                    return !fecha.isBefore(inicioMes) && !fecha.isAfter(finMes);
                })
                .collect(Collectors.toList());
    }

    /*
     * Contar incidencias totales del sistema
     */
    public int contarIncidenciasTotales() {
        return factory.obtenerTodosLosEnvios().stream()
                .mapToInt(Envio::contarIncidencias)
                .sum();
    }

    /*
     * Obtener top 5 repartidores por entregas
     */
    public List<Map.Entry<String, Integer>> obtenerTopRepartidores() {
        Map<String, Integer> entregasPorRepartidor = new HashMap<>();

        for (RepartidorDTO repartidor : factory.obtenerTodosLosRepartidores()) {
            try {
                int entregas = Integer.parseInt(repartidor.getEnviosRealizados());
                entregasPorRepartidor.put(repartidor.getNombre(), entregas);
            } catch (NumberFormatException e) {
                entregasPorRepartidor.put(repartidor.getNombre(), 0);
            }
        }

        return entregasPorRepartidor.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    /*
     * Obtener distribución de envíos por día (últimos 7 días)
     */
    public Map<LocalDate, Long> obtenerEnviosPorDia() {
        LocalDate hoy = LocalDate.now();
        LocalDate hace7Dias = hoy.minusDays(7);

        return factory.obtenerTodosLosEnvios().stream()
                .filter(e -> {
                    LocalDate fecha = e.getFechaCreacion().toLocalDate();
                    return !fecha.isBefore(hace7Dias) && !fecha.isAfter(hoy);
                })
                .collect(Collectors.groupingBy(
                        e -> e.getFechaCreacion().toLocalDate(),
                        Collectors.counting()
                ));
    }

    /*
     * Calcular tasa de éxito de entregas
     */
    public double calcularTasaExito() {
        long total = factory.obtenerTodosLosEnvios().size();
        if (total == 0) return 0.0;

        long entregados = factory.obtenerTodosLosEnvios().stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.ENTREGADO)
                .count();

        return (entregados * 100.0) / total;
    }

    // ==========  BÚSQUEDAS Y FILTROS ==========

    /*
     * Buscar usuarios por nombre o correo
     */
    public List<UsuarioDTO> buscarUsuarios(String criterio) {
        String criterioLower = criterio.toLowerCase();
        return factory.obtenerTodosLosUsuarios().stream()
                .filter(u -> u.getNombre().toLowerCase().contains(criterioLower) ||
                        u.getCorreo().toLowerCase().contains(criterioLower))
                .collect(Collectors.toList());
    }

    /*
     * Buscar repartidores por nombre o zona
     */
    public List<RepartidorDTO> buscarRepartidores(String criterio) {
        String criterioLower = criterio.toLowerCase();
        return factory.obtenerTodosLosRepartidores().stream()
                .filter(r -> r.getNombre().toLowerCase().contains(criterioLower) ||
                        r.getZonaCobertura().toLowerCase().contains(criterioLower))
                .collect(Collectors.toList());
    }

    // ========== 🛠️ UTILIDADES ==========

    /*
     * Cerrar sesión
     */
    public void cerrarSesion() {
        factory.logout();
    }

    /*
     * Obtener instancia del factory
     */
    public EmpresaLogisticaFactory getFactory() {
        return factory;
    }
}