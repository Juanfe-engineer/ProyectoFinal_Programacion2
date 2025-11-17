package co.edu.uniquindio.empresalogistica.Mapping.mappers;

import co.edu.uniquindio.empresalogistica.Mapping.Dto.RepartidorDTO;
import co.edu.uniquindio.empresalogistica.Model.Repartidor;
import co.edu.uniquindio.empresalogistica.Model.Enums.DisponibilidadRepartidor;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoRepartidor;

public class RepartidorMapping {

    public static RepartidorDTO repartidorToDTO(Repartidor repartidor) {
        if (repartidor == null) {
            return null;
        }

        return new RepartidorDTO(
                repartidor.getId(),
                repartidor.getNombre(),
                repartidor.getCorreo(),
                repartidor.getTelefono(),
                repartidor.getId(),
                repartidor.getDisponibilidadRepartidor() != null ?
                        repartidor.getDisponibilidadRepartidor().name() : "INACTIVO",
                repartidor.getZonaCobertura(),
                repartidor.getFechaRegistro(),
                repartidor.getEstado() != null ? repartidor.getEstado().name() : "ACTIVO",
                repartidor.getEnviosRealizados() != null ? repartidor.getEnviosRealizados() : "0",
                4.5, repartidor.getIncidenciasReportadas()
        );
    }

    public static Repartidor dtoToRepartidor(RepartidorDTO dto) {
        if (dto == null) {
            return null;
        }

        Repartidor repartidor = new Repartidor(
                dto.getId(),
                dto.getNombre(),
                dto.getCorreo(),
                dto.getTelefono(),
                DisponibilidadRepartidor.valueOf(dto.getDisponibilidad()),
                dto.getZonaCobertura(),
                dto.getFechaRegistro(),
                EstadoRepartidor.valueOf(dto.getEstado()),
                dto.getEnviosRealizados()
        );

        repartidor.setIncidenciasReportadas(dto.getIncidenciasReportadas());

        return repartidor;
    }
}