package co.edu.uniquindio.empresalogistica.Mapping.mappers;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Model.Usuario;

import co.edu.uniquindio.empresalogistica.Model.Usuario;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoUsuario;

public class UsuarioMapping {

    public static UsuarioDTO usuarioToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getFechaRegistro(),
                usuario.getEstado().toString(),
                usuario.getDireccionFrecuente(),
                usuario.getMetodosPago(),
                usuario.getEnviosRealizados()
        );
    }

    public static Usuario dtoToUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }

        return new Usuario(
                usuarioDTO.getId(),
                usuarioDTO.getNombre(),
                usuarioDTO.getCorreo(),
                usuarioDTO.getTelefono(),
                usuarioDTO.getFechaRegistro(),
                EstadoUsuario.valueOf(usuarioDTO.getEstado()),
                usuarioDTO.getDireccionFrecuente(),
                usuarioDTO.getMetodosPago(),
                usuarioDTO.getEnviosRealizados()
        );
    }
}
