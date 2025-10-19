package co.edu.uniquindio.empresalogistica.Model;

public class Paquete {
    private String idPaquete;
    private double peso;
    private double alto;
    private double ancho;
    private String contenido;
    private boolean esFragil;


    public Paquete(String idPaquete, double peso, double alto, double ancho,
                   String contenido, boolean esFragil) {
        this.idPaquete = idPaquete;
        this.peso = peso;
        this.alto = alto;
        this.ancho = ancho;
        this.contenido = contenido;
        this.esFragil = esFragil;
    }


    public String getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(String idPaquete) {
        this.idPaquete = idPaquete;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isEsFragil() {
        return esFragil;
    }

    public void setEsFragil(boolean esFragil) {
        this.esFragil = esFragil;
    }


    @Override
    public String toString() {
        return "Paquete{" +
                "idPaquete'" + idPaquete + '\'' +
                ", peso" + peso +
                ", alto" + alto +
                ", ancho" + ancho +
                ", contenido'" + contenido + '\'' +
                ", esFragil" + esFragil +
                '}';
    }
}
