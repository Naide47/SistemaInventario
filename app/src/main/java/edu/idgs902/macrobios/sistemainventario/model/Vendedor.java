package edu.idgs902.macrobios.sistemainventario.model;

public class Vendedor extends Persona{

    private int vendedor_no; //Numero
    private double comisiones;

    public Vendedor(int persona_no, String nombre, String calle, String colonia, String telefono, String email, int vendedor_no, double comisiones) {
        super(persona_no, nombre, calle, colonia, telefono, email);
        this.vendedor_no = vendedor_no;
        this.comisiones = comisiones;
    }

    public Vendedor(String nombre, String calle, String colonia, String telefono, String email, double comisiones) {
        super(nombre, calle, colonia, telefono, email);
        this.comisiones = comisiones;
    }

    public int getVendedor_no() {
        return vendedor_no;
    }

    public double getComisiones() {
        return comisiones;
    }
}
