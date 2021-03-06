package edu.idgs902.macrobios.sistemainventario.view.proveedor;

public class ListElemetProveedor {

    private String color;
    private String nombre;
    private String ciudad;
    private String saldo;
    private int noExterno;
    private int noPersona;

    public ListElemetProveedor(String color, String nombre, String ciudad, String saldo) {
        this.color = color;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.saldo = saldo;
    }

    public ListElemetProveedor(int noExterno, int noPersona, String color, String nombre, String ciudad, String saldo) {
        this.color = color;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.saldo = saldo;
        this.noExterno = noExterno;
        this.noPersona = noPersona;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public int getNoExterno() {
        return noExterno;
    }

    public void setNoExterno(int noExterno) {
        this.noExterno = noExterno;
    }

    public int getNoPersona() {
        return noPersona;
    }

    public void setNoPersona(int noPersona) {
        this.noPersona = noPersona;
    }
}
