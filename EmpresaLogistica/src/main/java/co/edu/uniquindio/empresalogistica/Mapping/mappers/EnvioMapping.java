package co.edu.uniquindio.empresalogistica.Mapping.mappers;

import co.edu.uniquindio.empresalogistica.Mapping.Dto.EnvioDTO;
import co.edu.uniquindio.empresalogistica.Model.Envio;

public class EnvioMapping {

    public static EnvioDTO envioToDTO(Envio envio) {
        if (envio == null) {
            return null;
        }

        return new EnvioDTO(
                envio.getIdEnvio(),
                envio.getOrigen() != null ? envio.getOrigen().getCalle() : "",
                envio.getDestino() != null ? envio.getDestino().getCalle() : "",
                envio.getPesoGramos(),
                envio.getVolumenCm3(),
                envio.getDescripcion(),
                envio.getEstadoEnvio() != null ? envio.getEstadoEnvio().name() : "",
                envio.getFechaCreacion(),
                envio.getFechaEstimadaEntrega(),
                envio.getFechaRealEntrega(),
                envio.getCostoTotal(),
                ""
        );
    }


}