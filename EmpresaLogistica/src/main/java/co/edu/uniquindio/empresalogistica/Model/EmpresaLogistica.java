package co.edu.uniquindio.empresalogistica.Model;

import java.util.ArrayList;
import java.util.List;

public class EmpresaLogistica {
    private String nombre;
    private String nit;

    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<Repartidor> listaRepartidores = new ArrayList<>();
    private List<Administrador> listaAdministradores = new ArrayList<>();
    private List<Envio> listaEnvios = new ArrayList<>();
    private List<Paquete> listaPaquetes = new ArrayList<>();
    private List<Tarifa> listaTarifas = new ArrayList<>();


    public EmpresaLogistica(String nombre, String nit) {
        this.nombre = "EmpresaLogistica SAS";
        this.nit = "123456789-0";
    }


    // CRUD USUARIOS

    public boolean crearUsuario(Usuario usuario) {

        if (usuario == null) {
            return false;
        }
        if (obtenerUsuarioPorId(usuario.getId()) != null){
            return false;
        }
        listaUsuarios.add(usuario);
        return true;
    }


    public Usuario obtenerUsuarioPorId(String id){
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId().equalsIgnoreCase(id)) {
                return usuario;
            }
        }
        return null;
    }


    public List<Usuario> obtenerTodosLosUsuarios(){
        return new ArrayList<>(listaUsuarios);
    }


    public boolean actualizarUsuario(Usuario usuarioActualizado){
        Usuario usuarioExistente = obtenerUsuarioPorId(usuarioActualizado.getId());
        if (usuarioExistente == null) {
            return false;
        }

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        usuarioExistente.setFechaRegistro(usuarioActualizado.getFechaRegistro());
        usuarioExistente.setEstado(usuarioActualizado.getEstado());
        usuarioExistente.setDireccionFrecuente(usuarioActualizado.getDireccionFrecuente());
        usuarioExistente.setMetodosPago(usuarioActualizado.getMetodosPago());
        usuarioExistente.setEnviosRealizados(usuarioActualizado.getEnviosRealizados());
        return true;
    }

    public boolean eliminarUsuarioPorId(String id){
        Usuario usuario = obtenerUsuarioPorId(id);
        if (usuario == null) {
            return false;
        }
        listaUsuarios.remove(usuario);
        return true;
    }


    // CRUD REPARTIDORES

    public boolean crearRepartidor(Repartidor repartidor) {
        if (repartidor == null) {
            return false;
        }
        if(obtenerRepartidorPorId(repartidor.getId()) != null){
            return false;
        }
        listaRepartidores.add(repartidor);
        return true;
    }


    public Repartidor obtenerRepartidorPorId(String id){
        for(Repartidor repartidor : listaRepartidores){
            if(repartidor.getId().equalsIgnoreCase(id)){
                return repartidor;
            }
        }
        return null;
    }


    public List<Repartidor> obtenerTodosLosRepartidores(){
        return new ArrayList<>(listaRepartidores);
    }


    public boolean actualizarRepartidor(Repartidor repartidorActualizado){
        Repartidor repartidorExistente = obtenerRepartidorPorId(repartidorActualizado.getId());
        if(repartidorExistente == null) {
            return false;
        }
        repartidorExistente.setNombre(repartidorActualizado.getNombre());
        repartidorExistente.setCorreo(repartidorActualizado.getCorreo());
        repartidorExistente.setTelefono(repartidorActualizado.getTelefono());
        repartidorExistente.setFechaRegistro(repartidorActualizado.getFechaRegistro());
        repartidorExistente.setDisponibilidadRepartidor(repartidorActualizado.getDisponibilidadRepartidor());
        repartidorExistente.setZonaCobertura(repartidorActualizado.getZonaCobertura());
        return true;
    }


    public boolean eliminarRepartidorPorId(String id){
        Repartidor repartidor = obtenerRepartidorPorId(id);
        if (repartidor == null) {
            return false;
        }
        listaRepartidores.remove(repartidor);
        return true;
    }






}
