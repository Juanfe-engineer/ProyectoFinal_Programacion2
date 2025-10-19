package co.edu.uniquindio.empresalogistica.Model;

public class Administrador extends Persona {

    public Administrador(String id, String nombre, String correo, String telefono) {
        super(id, nombre, correo, telefono);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id'" + id + '\'' +
                ", nombre'" + nombre + '\'' +
                ", correo'" + correo + '\'' +
                ", telefono'" + telefono + '\'' +
                '}';
    }
}
