package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Enums.TipoPerfil;

import java.util.HashMap;
import java.util.Map;

public class ServicioAutenticacion {

    private Map<String, CredencialUsuario> credencialesRegistrados = new HashMap<>();
    private CredencialUsuario usuarioActualLogueado;

    public ServicioAutenticacion() {
        // Crear credenciales de prueba
        crearCredencialPrueba("juan@email.com", "123456", TipoPerfil.USUARIO, "Juan Garcia");
        crearCredencialPrueba("carlos@repartidor.com", "123456", TipoPerfil.REPARTIDOR, "Carlos Repartidor");
        crearCredencialPrueba("admin@admin.com", "123456", TipoPerfil.ADMINISTRADOR, "Admin Sistema");
    }

    /**
     * Registrar Nuevo Usuario
     */
    public boolean registrarse(String usuario, String password, TipoPerfil tipoPerfil, String nombreCompleto) {
        if(usuario == null || usuario.trim().isEmpty()){
            System.out.println(" Usuario Vacío");
            return false;
        }

        if(password == null || password.trim().isEmpty()){
            System.out.println(" Password Vacío");
            return false;
        }

        if(credencialesRegistrados.containsKey(usuario)){
            System.out.println(" Usuario ya existe");
            return false;
        }

        return crearCredencialPrueba(usuario, password, tipoPerfil, nombreCompleto);
    }

    /**
     * Iniciar Sesión
     */
    public boolean iniciarSesion(String usuario, String password){
        if(usuario == null || usuario.trim().isEmpty()){
            System.out.println(" Usuario Requerido");
            return false;
        }

        if(password == null || password.trim().isEmpty()){
            System.out.println(" Password Requerido");
            return false;
        }

        CredencialUsuario credencial = credencialesRegistrados.get(usuario);

        if(credencial == null){
            System.out.println(" Usuario no encontrado: " + usuario);
            return false;
        }

        if(!credencial.getPassword().equals(password)){
            System.out.println(" Password Incorrecta");
            return false;
        }

        this.usuarioActualLogueado = credencial;
        System.out.println(" Sesión iniciada: " + usuario + " (" + credencial.getTipoPerfil().getNombre() + ")");
        return true;
    }

    /**
     *  CAMBIAR CONTRASEÑA - CORREGIDO
     */
    public boolean cambiarContrasena(String contrasenaActual, String contrasenaNueva) {
        if (usuarioActualLogueado == null) {
            System.out.println(" No hay usuario logueado");
            return false;
        }

        // Buscar la credencial en el mapa
        String correoUsuario = usuarioActualLogueado.getUsuario();
        CredencialUsuario credencial = credencialesRegistrados.get(correoUsuario);

        if (credencial == null) {
            System.out.println(" Credencial no encontrada");
            return false;
        }

        if (!credencial.getPassword().equals(contrasenaActual)) {
            System.out.println(" Contraseña actual incorrecta");
            return false;
        }

        if (contrasenaNueva == null || contrasenaNueva.trim().isEmpty()) {
            System.out.println(" La nueva contraseña no puede estar vacía");
            return false;
        }

        if (contrasenaNueva.length() < 6) {
            System.out.println(" La nueva contraseña debe tener al menos 6 caracteres");
            return false;
        }

        //  ACTUALIZAR LA CONTRASEÑA EN EL MAPA Y EN LA INSTANCIA ACTUAL
        credencial.setPassword(contrasenaNueva);
        usuarioActualLogueado.setPassword(contrasenaNueva);

        System.out.println(" Contraseña cambiada exitosamente para: " + correoUsuario);
        return true;
    }

    /**
     * Obtener usuario actualmente logueado
     */
    public CredencialUsuario obtenerUsuarioActual(){
        return usuarioActualLogueado;
    }

    /**
     * Cerrar Sesión
     */
    public void cerrarSesion(){
        if (usuarioActualLogueado != null){
            System.out.println(" Sesión cerrada: " + usuarioActualLogueado.getUsuario());
            usuarioActualLogueado = null;
        }
    }

    /**
     * Obtener tipo de perfil del usuario actual
     */
    public TipoPerfil obtenerTipoPerfilActual(){
        if (usuarioActualLogueado != null){
            return usuarioActualLogueado.getTipoPerfil();
        }
        return null;
    }

    /**
     * Verificar usuario logueado
     */
    public boolean estaLogueado(){
        return usuarioActualLogueado != null;
    }

    /**
     * Método privado para crear credenciales
     */
    private boolean crearCredencialPrueba(String usuario, String password, TipoPerfil tipoPerfil, String nombreCompleto) {
        CredencialUsuario credencial = new CredencialUsuario(usuario, password, tipoPerfil, nombreCompleto);
        credencialesRegistrados.put(usuario, credencial);
        System.out.println(" Credencial creada: " + usuario + " (" + tipoPerfil.getNombre() + ")");
        return true;
    }

    /**
     *  OBTENER CREDENCIALES (para debug)
     */
    public Map<String, CredencialUsuario> getCredencialesRegistrados() {
        return credencialesRegistrados;
    }
}