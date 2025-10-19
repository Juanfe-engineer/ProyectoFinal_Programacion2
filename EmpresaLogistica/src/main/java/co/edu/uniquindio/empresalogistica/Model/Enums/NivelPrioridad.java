package co.edu.uniquindio.empresalogistica.Model.Enums;

public enum NivelPrioridad {
    NORMAL("Normal", 1.0),
    EXPRESS("Express", 1.5),
    URGENTE("Urgente", 2.0);

    private String nombre;
    private double multiplicador;

    NivelPrioridad(String nombre, double multiplicador) {
        this.nombre = nombre;
        this.multiplicador = multiplicador;
    }

    public String getNombre() {
        return nombre;
    }
    public double getMultiplicador() {
        return multiplicador;
    }
}

