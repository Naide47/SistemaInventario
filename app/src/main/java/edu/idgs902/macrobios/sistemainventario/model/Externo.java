package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Externo extends Persona{

    private int noExterno; //Número
    private int tipo;
    private String rfc;
    private String ciudad;
    private double saldo;
    private int noPersona;

    public Externo(int persona_no, String nombre, String calle, String colonia, String telefono, String email, int noExterno, int tipo, String rfc, String ciudad, double saldo) {
        super(persona_no, nombre, calle, colonia, telefono, email);
        this.noExterno = noExterno;
        this.tipo = tipo;
        this.rfc = rfc;
        this.ciudad = ciudad;
        this.saldo = saldo;
    }

    public Externo(int persona_no, String nombre, String calle, String colonia, String telefono, String email, int tipo, String rfc, String ciudad, double saldo) {
        super(persona_no, nombre, calle, colonia, telefono, email);
        this.tipo = tipo;
        this.rfc = rfc;
        this.ciudad = ciudad;
        this.saldo = saldo;
    }

    public Externo(int noExterno, String rfc, String ciudad, double saldo){
        super();
        this.noExterno = noExterno;
        this.rfc = rfc;
        this.ciudad = ciudad;
        this.saldo = saldo;
    }

    public Externo(int noExterno, int tipo, String rfc, String ciudad, double saldo){
        super();
        this.noExterno = noExterno;
        this.tipo = tipo;
        this.rfc = rfc;
        this.ciudad = ciudad;
        this.saldo = saldo;
    }

    public Externo(int noExterno, int tipo, String rfc, String ciudad, double saldo, int noPersona){
        super();
        this.noExterno = noExterno;
        this.tipo = tipo;
        this.rfc = rfc;
        this.ciudad = ciudad;
        this.saldo = saldo;
        this.noPersona = noPersona;
    }

    public Externo(int tipo, String rfc, String ciudad, double saldo, int noPersona){
        super();
        this.tipo = tipo;
        this.rfc = rfc;
        this.ciudad = ciudad;
        this.saldo = saldo;
        this.noPersona = noPersona;
    }

    private Externo(Parcel in) {
        super(in);
        noExterno = in.readInt();
        tipo = in.readInt();
        rfc = in.readString();
        ciudad = in.readString();
        saldo = in.readDouble();
    }

    public int getNoExterno() {
        return noExterno;
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

    @Override
    public int getNoPersona() {
        return noPersona;
    }

    public static final Parcelable.Creator<Externo> CREATOR = new Creator<Externo>() {
        @Override
        public Externo createFromParcel(Parcel in) {
            return new Externo(in);
        }

        @Override
        public Externo[] newArray(int size) {
            return new Externo[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeInt(noExterno);
        dest.writeInt(tipo);
        dest.writeString(rfc);
        dest.writeString(ciudad);
        dest.writeDouble(saldo);
    }
}
