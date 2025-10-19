package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Enums.TipoPerfil;

import java.util.HashMap;
import java.util.Map;


public class ServicioAutenticacion {

    private Map<String, CredencialUsuario> credencialesRegistrados = new HashMap<>();
    private CredencialUsuario usuarioActualLogueado;

    public ServicioAutenticacion() {
        crearCredencialPrueba("Juan123", "123456", TipoPerfil.USUARIO, "Juan Garcia");
        crearCredencialPrueba("Johan456", "123456", TipoPerfil.REPARTIDOR, "Johan Lopez");
        crearCredencialPrueba("admin789", "123456", TipoPerfil.ADMINISTRADOR, "Admin Sistema");
    }


    /*
    Registrar Nuevo Usuario
     */

    public boolean registrarse (String usuario, String password, TipoPerfil tipoPerfil, String nombreCompleto) {
        if(usuario == null || usuario.trim().isEmpty()){
            System.out.println("Usuario Vacio");
            return false;
        }

        if(password == null || password.trim().isEmpty()){
            System.out.println("Password Vacio");
            return false;
        }

        if(credencialesRegistrados.containsKey(usuario)){
            System.out.println("Usuario ya existe");
            return false;
        }

        return crearCredencialPrueba(usuario,password,tipoPerfil,nombreCompleto);
    }


    /*
    Iniciar Sesion
     */

    public boolean iniciarSesion(String usuario, String password){
        if(usuario == null || usuario.trim().isEmpty()){
            System.out.println("Usuario Requerido");
            return false;
        }

        if(password == null || password.trim().isEmpty()){
            System.out.println("Password Requerido");
            return false;
        }

        CredencialUsuario credencial = credencialesRegistrados.get(usuario);

        if(credencial == null){
            System.out.println("Usuario no encontrado");
            return false;
        }

        if(!credencial.getPassword().equals(password)){
            System.out.println("Password Incorrecta");
            return false;
        }

        this.usuarioActualLogueado = credencial;
        System.out.println("Sesion iniciada: " + usuario + " (" + credencial.getTipoPerfil().getNombre() + ")");
        return true;
    }


    /*
    Obtener usuario actualmente logueado
     */

    public CredencialUsuario obtenerUsuarioActual(){
        return usuarioActualLogueado;
    }


    /*
    Cerrar Sesion
     */

    public void cerrarSesion(){
        if (usuarioActualLogueado != null){
            System.out.println("Sesion cerrada: " + usuarioActualLogueado.getUsuario());
            usuarioActualLogueado = null;
        }
    }

    /*
    Obtener tipo de perfil del usuario actual
     */

    public TipoPerfil obtenerTipoPerfilActual(){
        if (usuarioActualLogueado != null){
            return usuarioActualLogueado.getTipoPerfil();
        }
        return null;
    }

    /*
    Verificar usuario logueado
     */

    public boolean estaLogueado(){
        return usuarioActualLogueado != null;
    }

    // metodo privado

    private boolean crearCredencialPrueba(String usuario, String password, TipoPerfil tipoPerfil, String nombreCompleto) {
         CredencialUsuario credencial = new CredencialUsuario(usuario, password, tipoPerfil, nombreCompleto);
         credencialesRegistrados.put(usuario, credencial);
         System.out.println("Credencial creada: " + usuario + " (" + tipoPerfil.getNombre() + ")");
         return true;
    }
}
