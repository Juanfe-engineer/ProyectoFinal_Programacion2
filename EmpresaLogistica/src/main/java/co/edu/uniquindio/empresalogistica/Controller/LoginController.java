package co.edu.uniquindio.empresalogistica.Controller;

import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Model.Enums.TipoPerfil;
import co.edu.uniquindio.empresalogistica.Model.ServicioAutenticacion;

public class LoginController {
    private ServicioAutenticacion servicioAutenticacion;

    public LoginController() {
        servicioAutenticacion = EmpresaLogisticaFactory.getInstance().getServicioAutenticacion();
    }

    /*
     * Intentar Iniciar Sesion
     */
    public boolean iniciarSesion(String usuario, String password) {
        return servicioAutenticacion.iniciarSesion(usuario, password);
    }

    /*
     * Registrar nuevo usuario
     */
    public boolean registrarse(String usuario, String password, TipoPerfil tipoPerfil, String nombreCompleto) {
        return servicioAutenticacion.registrarse(usuario, password, tipoPerfil, nombreCompleto);
    }

    /*
     * Cambiar Contraseña
     */
    public boolean cambiarContrasena(String contrasenaActual, String contrasenaNueva) {
        return servicioAutenticacion.cambiarContrasena(contrasenaActual, contrasenaNueva);
    }

    /*
     * Obtener tipo de perfil actual
     */
    public TipoPerfil obtenerTipoPerfilActual() {
        return servicioAutenticacion.obtenerTipoPerfilActual();
    }

    /*
     * Verificar si esta logueado
     */
    public boolean estaLogueado() {
        return servicioAutenticacion.estaLogueado();
    }

    /*
     * Cerrar Sesion
     */
    public void cerrarSesion() {
        servicioAutenticacion.cerrarSesion();
    }

    /*
     * Obtener nombre del usuario actual
     */
    public String obtenerNombreUsuarioActual() {
        if (servicioAutenticacion.obtenerUsuarioActual() != null) {
            return servicioAutenticacion.obtenerUsuarioActual().getNombreCompleto();
        }
        return null;
    }
}