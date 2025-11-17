package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Model.Enums.TipoPerfil;

public class CredencialRepartidor {
    private String usuario;
    private String password;
    private TipoPerfil tipoPerfil;
    private String nombreCompleto;

    public CredencialRepartidor(String usuario, String password, TipoPerfil tipoPerfil, String nombreCompleto) {
        this.usuario = usuario;
        this.password = password;
        this.tipoPerfil = tipoPerfil;
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public TipoPerfil getTipoPerfil() { return tipoPerfil; }
    public void setTipoPerfil(TipoPerfil tipoPerfil) { this.tipoPerfil = tipoPerfil; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
}
