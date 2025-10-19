package co.edu.uniquindio.empresalogistica.Model;

public class Direccion {
    private String idDireccion;
    private String alias;
    private String calle;
    private String codigoPostal;
    private Double coordenadaX;
    private Double coordenadaY;
    private String referencias;


    public Direccion(String idDireccion, String alias, String calle,
                     String codigoPostal, Double coordenadaX, Double coordenadaY,
                     String referencias) {
        this.idDireccion = idDireccion;
        this.alias = alias;
        this.calle = calle;
        this.codigoPostal = codigoPostal;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.referencias = referencias;
    }

    public String getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Double getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(Double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public Double getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(Double coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }


    @Override
    public String toString() {
        return "Direccion{" +
                "idDireccion'" + idDireccion + '\'' +
                ", alias'" + alias + '\'' +
                ", calle'" + calle + '\'' +
                ", codigoPostal'" + codigoPostal + '\'' +
                ", coordenadaX" + coordenadaX +
                ", coordenadaY" + coordenadaY +
                ", referencias'" + referencias + '\'' +
                '}';
    }
}
