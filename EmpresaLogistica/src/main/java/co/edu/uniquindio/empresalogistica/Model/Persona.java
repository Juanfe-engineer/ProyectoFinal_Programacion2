package co.edu.uniquindio.empresalogistica.Model;

public abstract class Persona {
    protected String id;
    protected String nombre;
    protected String correo;
    protected String telefono;

    public Persona(String id, String nombre,
                   String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id'" + id + '\'' +
                ", nombre'" + nombre + '\'' +
                ", correo'" + correo + '\'' +
                ", telefono'" + telefono + '\'' +
                '}';
    }
}
