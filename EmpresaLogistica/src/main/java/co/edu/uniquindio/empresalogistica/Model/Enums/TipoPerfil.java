package co.edu.uniquindio.empresalogistica.Model.Enums;

public enum TipoPerfil {
    USUARIO("Usuario"),
    REPARTIDOR("Repartidor"),
    ADMINISTRADOR("Administrador");

    private String nombre;

    TipoPerfil(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
