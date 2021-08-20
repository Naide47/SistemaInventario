package edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor;

public class ListElementVendedor {

    private String comision;
    private int noVendedor;
    private int noPersona;
    private String nombre;

    public ListElementVendedor(String comision, int noVendedor, int noPersona, String nombre) {
        this.comision = comision;
        this.noVendedor = noVendedor;
        this.noPersona = noPersona;
        this.nombre = nombre;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public int getNoVendedor() {
        return noVendedor;
    }

    public void setNoVendedor(int noVendedor) {
        this.noVendedor = noVendedor;
    }

    public int getNoPersona() {
        return noPersona;
    }

    public void setNoPersona(int noPersona) {
        this.noPersona = noPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
