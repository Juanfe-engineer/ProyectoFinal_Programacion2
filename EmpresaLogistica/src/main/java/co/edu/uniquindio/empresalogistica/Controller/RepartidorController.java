package co.edu.uniquindio.empresalogistica.Controller;

import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.RepartidorDTO;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoIncidencia;
import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Enums.DisponibilidadRepartidor;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Incidencia;
import co.edu.uniquindio.empresalogistica.Model.Repartidor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RepartidorController {
    private EmpresaLogisticaFactory factory;

    public RepartidorController() {
        this.factory = EmpresaLogisticaFactory.getInstance();
    }

    // ========== GESTIÓN DE PERFIL ==========

    public RepartidorDTO obtenerRepartidorActual() {
        return factory.getRepartidorActual();
    }

    public boolean actualizarRepartidor(RepartidorDTO repartidorDTO) {
        try {
            factory.actualizarRepartidor(repartidorDTO);
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar repartidor: " + e.getMessage());
            return false;
        }
    }

    // ========== GESTIÓN DE DISPONIBILIDAD ==========

    public boolean cambiarDisponibilidad(String idRepartidor, DisponibilidadRepartidor nuevaDisponibilidad) {
        try {
            factory.cambiarDisponibilidadRepartidor(idRepartidor, nuevaDisponibilidad);
            return true;
        } catch (Exception e) {
            System.err.println("Error al cambiar disponibilidad: " + e.getMessage());
            return false;
        }
    }

    public DisponibilidadRepartidor obtenerDisponibilidadActual(String idRepartidor) {
        try {
            return factory.obtenerDisponibilidadRepartidor(idRepartidor);
        } catch (Exception e) {
            System.err.println("Error al obtener disponibilidad: " + e.getMessage());
            return DisponibilidadRepartidor.INACTIVO;
        }
    }

    // ========== GESTIÓN DE ENVÍOS ASIGNADOS ==========

    public List<Envio> obtenerEnviosAsignados(String idRepartidor) {
        return factory.obtenerEnviosAsignadosARepartidor(idRepartidor);
    }

    public List<Envio> obtenerEnviosPendientes(String idRepartidor) {
        return factory.obtenerEnviosPendientesRepartidor(idRepartidor);
    }

    public List<Envio> obtenerEnviosCompletados(String idRepartidor) {
        return factory.obtenerEnviosCompletadosRepartidor(idRepartidor);
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

    public Envio obtenerDetalleEnvio(String idEnvio) {
        try {
            return factory.obtenerEnvioPorId(idEnvio);
        } catch (Exception e) {
            System.err.println("Error al obtener envío: " + e.getMessage());
            return null;
        }
    }

    // ========== ESTADÍSTICAS Y MÉTRICAS ==========

    public int contarEnviosCompletadosHoy(String idRepartidor) {
        return factory.contarEnviosRepartidorPorFecha(idRepartidor, LocalDate.now());
    }

    public int contarEnviosCompletadosMes(String idRepartidor) {
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        return factory.contarEnviosRepartidorPorPeriodo(idRepartidor, inicioMes, LocalDate.now());
    }

    public double calcularPromedioTiempoEntrega(String idRepartidor) {
        return factory.calcularPromedioTiempoEntregaRepartidor(idRepartidor);
    }

    public double calcularCalificacionPromedio(String idRepartidor) {
        return factory.obtenerCalificacionPromedioRepartidor(idRepartidor);
    }

    public int contarIncidenciasRepartidor(String idRepartidor) {
        return factory.contarIncidenciasRepartidor(idRepartidor);
    }

    // ========== GESTIÓN DE ZONA ==========

    public String obtenerZonaCobertura(String idRepartidor) {
        try {
            RepartidorDTO repartidor = factory.obtenerRepartidorPorId(idRepartidor);
            return repartidor.getZonaCobertura();
        } catch (Exception e) {
            System.err.println("Error al obtener zona: " + e.getMessage());
            return "No definida";
        }
    }

    public boolean actualizarZonaCobertura(String idRepartidor, String nuevaZona) {
        try {
            factory.actualizarZonaCoberturaRepartidor(idRepartidor, nuevaZona);
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar zona: " + e.getMessage());
            return false;
        }
    }

    // ========== REPORTAR INCIDENCIAS ==========

    public boolean reportarIncidencia(String idEnvio, String descripcion) {
        try {
            // Buscar el envío
            Envio envio = factory.obtenerEnvioPorId(idEnvio);
            if (envio == null) {
                System.err.println("Envío no encontrado: " + idEnvio);
                return false;
            }

            // Obtener repartidor actual (asumiendo que está logueado)
            RepartidorDTO repartidorActual = factory.getRepartidorActual();
            if (repartidorActual == null) {
                System.err.println("No hay repartidor logueado");
                return false;
            }

            // Crear la incidencia
            String idIncidencia = "INC-" + UUID.randomUUID().toString().substring(0, 8);
            Incidencia incidencia = new Incidencia(
                    idIncidencia,
                    descripcion,
                    EstadoIncidencia.REPORTADA,
                    LocalDateTime.now(),
                    null, // Sin fecha de resolución aún
                    null  // Sin solución aún
            );

            factory.agregarIncidenciaAEnvio(idEnvio, incidencia);

            factory.incrementarIncidenciasRepartidor(repartidorActual.getId());

            System.out.println("Incidencia reportada: " + idIncidencia);
            System.out.println("Total incidencias del repartidor: " +
                    (repartidorActual.getIncidenciasReportadas() + 1));

            return true;

        } catch (Exception e) {
            System.err.println("Error al reportar incidencia: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Actualizar estado de disponibilidad del repartidor
     */
    public boolean actualizarDisponibilidad(String idRepartidor, String nuevaDisponibilidad) {
        try {
            return factory.actualizarDisponibilidadRepartidor(idRepartidor, nuevaDisponibilidad);
        } catch (Exception e) {
            System.err.println("Error al actualizar disponibilidad: " + e.getMessage());
            return false;
        }
    }


    /**
     * Cambiar estado de un envío
     */
    public boolean cambiarEstadoEnvio(String idEnvio, String nuevoEstado) {
        try {
            return factory.cambiarEstadoEnvio(idEnvio, nuevoEstado);
        } catch (Exception e) {
            System.err.println("Error al cambiar estado: " + e.getMessage());
            return false;
        }
    }


    // ========== GESTIÓN DE SESIÓN ==========

    public void cerrarSesion() {
        factory.logoutRepartidor();
    }
}