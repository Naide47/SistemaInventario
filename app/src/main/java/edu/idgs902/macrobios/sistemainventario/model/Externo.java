package edu.idgs902.macrobios.sistemainventario.model;

public class Externo extends Persona{

    private int externo_no; //Número
    private int tipo;
    private String rfc;
    private String ciudad;
    private double saldo;

    public Externo(int persona_no, String nombre, String calle, String colonia, String telefono, String email, int externo_no, int tipo, String rfc, String ciudad, double saldo) {
        super(persona_no, nombre, calle, colonia, telefono, email);
        this.externo_no = externo_no;
        this.tipo = tipo;
        this.rfc = rfc;
        this.ciudad = ciudad;
        this.saldo = saldo;
    }

    public Externo(String nombre, String calle, String colonia, String telefono, String email, int tipo, String rfc, String ciudad, double saldo) {
        super(nombre, calle, colonia, telefono, email);
        this.tipo = tipo;
        this.rfc = rfc;
        this.ciudad = ciudad;
        this.saldo = saldo;
    }

    public int getExterno_no() {
        return externo_no;
    }

    public int getTipo() {
        return tipo;
    }

    public String getRfc() {
        return rfc;
    }

    public String getCiudad() {
        return ciudad;
    }

    public double getSaldo() {
        return saldo;
    }
}
