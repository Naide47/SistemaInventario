package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Parcel;
import android.os.Parcelable;

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

    private Externo(Parcel in) {
        super(in);
        externo_no = in.readInt();
        tipo = in.readInt();
        rfc = in.readString();
        ciudad = in.readString();
        saldo = in.readDouble();
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
        dest.writeInt(externo_no);
        dest.writeInt(tipo);
        dest.writeString(rfc);
        dest.writeString(ciudad);
        dest.writeDouble(saldo);
    }
}