package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Compra implements Parcelable {

    private int noCompra; //Nùmero
    private Externo externo_proveedor;
    private String fecha;
    private String f_r;
    private int f_r_no;
    private int total_pares;
    private double suma;
    private double iva;
    private double total_compra;
    private List<DetalleCompra> detallesCompra;

    public Compra(int noCompra, Externo externo_proveedor, String fecha, String f_r, int f_r_no, int total_pares, double suma, double iva, double total_compra, List<DetalleCompra> detallesCompra) {
        this.noCompra = noCompra;
        this.externo_proveedor = externo_proveedor;
        this.fecha = fecha;
        this.f_r = f_r;
        this.f_r_no = f_r_no;
        this.total_pares = total_pares;
        this.suma = suma;
        this.iva = iva;
        this.total_compra = total_compra;
        this.detallesCompra = detallesCompra;
    }

    public Compra(Externo externo_proveedor, String fecha, String f_r, int f_r_no, int total_pares, double suma, double iva, double total_compra, List<DetalleCompra> detallesCompra) {
        this.externo_proveedor = externo_proveedor;
        this.fecha = fecha;
        this.f_r = f_r;
        this.f_r_no = f_r_no;
        this.total_pares = total_pares;
        this.suma = suma;
        this.iva = iva;
        this.total_compra = total_compra;
        this.detallesCompra = detallesCompra;
    }

    protected Compra(Parcel in) {
        noCompra = in.readInt();
        externo_proveedor = in.readParcelable(Externo.class.getClassLoader());
        fecha = in.readString();
        f_r = in.readString();
        f_r_no = in.readInt();
        total_pares = in.readInt();
        suma = in.readDouble();
        iva = in.readDouble();
        total_compra = in.readDouble();
        detallesCompra = in.createTypedArrayList(DetalleCompra.CREATOR);
    }

    public static final Creator<Compra> CREATOR = new Creator<Compra>() {
        @Override
        public Compra createFromParcel(Parcel in) {
            return new Compra(in);
        }

        @Override
        public Compra[] newArray(int size) {
            return new Compra[size];
        }
    };

    public int getNoCompra() {
        return noCompra;
    }

    public void setNoCompra(int noCompra) {
        this.noCompra = noCompra;
    }

    public Externo getExterno_proveedor() {
        return externo_proveedor;
    }

    public void setExterno_proveedor(Externo externo_proveedor) {
        this.externo_proveedor = externo_proveedor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getF_r() {
        return f_r;
    }

    public void setF_r(String f_r) {
        this.f_r = f_r;
    }

    public int getF_r_no() {
        return f_r_no;
    }

    public void setF_r_no(int f_r_no) {
        this.f_r_no = f_r_no;
    }

    public int getTotal_pares() {
        return total_pares;
    }

    public void setTotal_pares(int total_pares) {
        this.total_pares = total_pares;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal_compra() {
        return total_compra;
    }

    public void setTotal_compra(double total_compra) {
        this.total_compra = total_compra;
    }

    public List<DetalleCompra> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<DetalleCompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noCompra);
        dest.writeParcelable(externo_proveedor, flags);
        dest.writeString(fecha);
        dest.writeString(f_r);
        dest.writeInt(f_r_no);
        dest.writeInt(total_pares);
        dest.writeDouble(suma);
        dest.writeDouble(iva);
        dest.writeDouble(total_compra);
        dest.writeTypedList(detallesCompra);
    }

    @Override
    public String toString() {
        return "Compra{" +
                "noCompra=" + noCompra +
                ", externo_proveedor=" + externo_proveedor +
                ", fecha='" + fecha + '\'' +
                ", f_r='" + f_r + '\'' +
                ", f_r_no=" + f_r_no +
                ", total_pares=" + total_pares +
                ", suma=" + suma +
                ", iva=" + iva +
                ", total_compra=" + total_compra +
                ", detallesCompra=" + detallesCompra +
                '}';
    }
}
