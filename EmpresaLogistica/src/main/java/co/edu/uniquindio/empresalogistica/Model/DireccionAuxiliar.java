package co.edu.uniquindio.empresalogistica.Model;

public class DireccionAuxiliar {
    private String calle;
    private String ciudad;
    private String departamento;

    public DireccionAuxiliar(String calle, String ciudad, String departamento) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.departamento = departamento;
    }

    public String getCiudad() { return ciudad; }
}
