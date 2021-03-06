package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vendedor extends Persona {

    private int noVendedor; //Numero
    private double comisiones;

    // Con IDS
    public Vendedor(int persona_no, String nombre, String calle, String colonia, String telefono, String email, int noVendedor, double comisiones) {
        super(persona_no, nombre, calle, colonia, telefono, email);
        this.noVendedor = noVendedor;
        this.comisiones = comisiones;
    }

    // Sin IDS
    public Vendedor(String nombre, String calle, String colonia, String telefono, String email, double comisiones) {
        super(nombre, calle, colonia, telefono, email);
        this.comisiones = comisiones;
    }

    private Vendedor(Parcel in) {
        super(in);
        noVendedor = in.readInt();
        comisiones = in.readDouble();
    }

    public int getNoVendedor() {
        return noVendedor;
    }

    public double getComisiones() {
        return comisiones;
    }

    public static final Parcelable.Creator<Vendedor> CREATOR = new Parcelable.Creator<Vendedor>() {

        @Override
        public Vendedor createFromParcel(Parcel source) {
            return new Vendedor(source);
        }

        @Override
        public Vendedor[] newArray(int size) {
            return new Vendedor[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(noVendedor);
        dest.writeDouble(comisiones);
    }
}