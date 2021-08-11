package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Venta implements Parcelable {

    private int noVenta; //Nùmero
    private Externo externo_cliente;
    private Vendedor vendedor;
    private String fecha;
    private int comision; //Porcentaje
    private String f_r;
    private int f_r_no;
    private int total_pares;
    private double suma;
    private double iva;
    private double total_venta;
    private List<DetalleVenta> detallesVenta;

    public Venta(int noVenta, Externo externo_cliente, Vendedor vendedor, String fecha, int comision, String f_r, int f_r_no, int total_pares, double suma, double iva, double total_venta) {
        this.noVenta = noVenta;
        this.externo_cliente = externo_cliente;
        this.vendedor = vendedor;
        this.fecha = fecha;
        this.comision = comision;
        this.f_r = f_r;
        this.f_r_no = f_r_no;
        this.total_pares = total_pares;
        this.suma = suma;
        this.iva = iva;
        this.total_venta = total_venta;
    }

    public Venta(Externo externo_cliente, Vendedor vendedor, String fecha, int comision, String f_r, int f_r_no, int total_pares, double suma, double iva, double total_venta) {
        this.externo_cliente = externo_cliente;
        this.vendedor = vendedor;
        this.fecha = fecha;
        this.comision = comision;
        this.f_r = f_r;
        this.f_r_no = f_r_no;
        this.total_pares = total_pares;
        this.suma = suma;
        this.iva = iva;
        this.total_venta = total_venta;
    }

    protected Venta(Parcel in) {
        noVenta = in.readInt();
        externo_cliente = in.readParcelable(Externo.class.getClassLoader());
        vendedor = in.readParcelable(Vendedor.class.getClassLoader());
        fecha = in.readString();
        comision = in.readInt();
        f_r = in.readString();
        f_r_no = in.readInt();
        total_pares = in.readInt();
        suma = in.readDouble();
        iva = in.readDouble();
        total_venta = in.readDouble();
        detallesVenta = in.createTypedArrayList(DetalleVenta.CREATOR);
    }

    public static final Creator<Venta> CREATOR = new Creator<Venta>() {
        @Override
        public Venta createFromParcel(Parcel in) {
            return new Venta(in);
        }

        @Override
        public Venta[] newArray(int size) {
            return new Venta[size];
        }
    };

    public int getNoVenta() {
        return noVenta;
    }

    public Externo getExterno_cliente() {
        return externo_cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public String getFecha() {
        return fecha;
    }

    public int getComision() {
        return comision;
    }

    public String getF_r() {
        return f_r;
    }

    public int getF_r_no() {
        return f_r_no;
    }

    public int getTotal_pares() {
        return total_pares;
    }

    public double getSuma() {
        return suma;
    }

    public double getIva() {
        return iva;
    }

    public double getTotal_venta() {
        return total_venta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noVenta);
        dest.writeParcelable(externo_cliente, flags);
        dest.writeParcelable(vendedor, flags);
        dest.writeString(fecha);
        dest.writeInt(comision);
        dest.writeString(f_r);
        dest.writeInt(f_r_no);
        dest.writeInt(total_pares);
        dest.writeDouble(suma);
        dest.writeDouble(iva);
        dest.writeDouble(total_venta);
        dest.writeTypedList(detallesVenta);
    }
}
