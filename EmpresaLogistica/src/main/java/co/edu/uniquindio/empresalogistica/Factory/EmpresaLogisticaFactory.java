package co.edu.uniquindio.empresalogistica.Factory;

import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Mapping.mappers.UsuarioMapping;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoUsuario;
import co.edu.uniquindio.empresalogistica.Model.ServicioAutenticacion;
import co.edu.uniquindio.empresalogistica.Model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmpresaLogisticaFactory {

    private static EmpresaLogisticaFactory instance;
    private List<Usuario> usuariosRegistrados;
    private Usuario usuarioActual;
    private ServicioAutenticacion servicioAutenticacion;

    // Singleton
    public static EmpresaLogisticaFactory getInstance() {
        if (instance == null) {
            instance = new EmpresaLogisticaFactory();
        }
        return instance;
    }

    // Constructor privado
    private EmpresaLogisticaFactory() {
        this.usuariosRegistrados = new ArrayList<>();
        this.servicioAutenticacion = new ServicioAutenticacion();
        inicializarDatosDemo();
    }

    public ServicioAutenticacion getServicioAutenticacion() {
        return servicioAutenticacion;
    }

    private void inicializarDatosDemo(){
        usuariosRegistrados.add(new Usuario(
                "1",
                "Juan Carlos",
                "juan@email.com",
                "3105556543",
                LocalDate.of(2023,1,15),
                EstadoUsuario.ACTIVO,
                "Calle 10 #50-25, Apartado",
                "Tarjeta de credito",
                "5"
        ));

        usuariosRegistrados.add(new Usuario(
                "2",
                "Johan García",
                "maria@email.com",
                "3104444444",
                LocalDate.of(2023, 5, 20),
                EstadoUsuario.ACTIVO,
                "Carrera 5 #100-10, Medellín",
                "Billetera virtual",
                "3"
        ));

        usuariosRegistrados.add(new Usuario(
                "3",
                "Pedro Rodríguez",
                "pedro@email.com",
                "3103333333",
                LocalDate.of(2023, 8, 10),
                EstadoUsuario.SUSPENDIDO,
                "Diagonal 20 #5-50, Cali",
                "Transferencia bancaria",
                "2"
        ));
    }

    // ============= CRUD USUARIOS =============

    // CREAR USUARIO
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) throws Exception{
        for(Usuario usuario : usuariosRegistrados) {
            if(usuario.getCorreo().equalsIgnoreCase(usuarioDTO.getCorreo())) {
                throw new Exception("El correo ya esta registrado");
            }
        }

        Usuario nuevoUsuario = new Usuario(
                UUID.randomUUID().toString(),
                usuarioDTO.getNombre(),
                usuarioDTO.getCorreo(),
                usuarioDTO.getTelefono(),
                LocalDate.now(),
                EstadoUsuario.ACTIVO,
                usuarioDTO.getDireccionFrecuente(),
                usuarioDTO.getMetodosPago(),
                "0"
        );

        usuariosRegistrados.add(nuevoUsuario);
        return UsuarioMapping.usuarioToDTO(nuevoUsuario);
    }

    // OBTENER TODOS LOS USUARIOS
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuariosRegistrados.stream()
                .map(UsuarioMapping::usuarioToDTO)
                .collect(Collectors.toList());
    }

    // OBTENER USUARIO POR ID
    public UsuarioDTO obtenerUsuarioPorId(String id) throws Exception {
        Usuario usuario = usuariosRegistrados.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        return UsuarioMapping.usuarioToDTO(usuario);
    }

    // OBTENER USUARIO POR CORREO
    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) throws Exception {
        Usuario usuario = usuariosRegistrados.stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        this.usuarioActual = usuario;
        return UsuarioMapping.usuarioToDTO(usuario);
    }

    // ACTUALIZAR USUARIO
    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = usuariosRegistrados.stream()
                .filter(u -> u.getId().equals(usuarioDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuario no encontrado para actualizar"));

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccionFrecuente(usuarioDTO.getDireccionFrecuente());
        usuario.setMetodosPago(usuarioDTO.getMetodosPago());
        usuario.setEstado(EstadoUsuario.valueOf(usuarioDTO.getEstado()));

        return UsuarioMapping.usuarioToDTO(usuario);
    }

    // ELIMINAR USUARIO
    public void eliminarUsuario(String id) throws Exception {
        Usuario usuario = usuariosRegistrados.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuario no encontrado para eliminar"));

        usuariosRegistrados.remove(usuario);
    }

    // BUSCAR USUARIOS POR NOMBRE
    public List<UsuarioDTO> buscarUsuariosPorNombre(String nombre) {
        return usuariosRegistrados.stream()
                .filter(u -> u.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .map(UsuarioMapping::usuarioToDTO)
                .collect(Collectors.toList());
    }

    // OBTENER USUARIOS POR ESTADO
    public List<UsuarioDTO> obtenerPorEstado(EstadoUsuario estado) {
        return usuariosRegistrados.stream()
                .filter(u -> u.getEstado() == estado)
                .map(UsuarioMapping::usuarioToDTO)
                .collect(Collectors.toList());
    }

    // ============= GESTIÓN DE SESIÓN =============

    public UsuarioDTO getUsuarioActual() {
        if (usuarioActual == null) {
            return null;
        }
        return UsuarioMapping.usuarioToDTO(usuarioActual);
    }

    public void logout() {
        usuarioActual = null;
    }
}