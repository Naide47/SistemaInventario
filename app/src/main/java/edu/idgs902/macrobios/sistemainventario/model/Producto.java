package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {

    private int noProducto; //Nùmero
    private String nuProducto;
    private String nombre;
    private String linea;
    private int existencia;
    private double p_costo;
    private double p_promedio;
    private double p_venta_mayor;
    private double p_venta_menor;

    public Producto(int noProducto, String nuProducto, String nombre, String linea, int existencia, double p_costo, double p_promedio, double p_venta_mayor, double p_venta_menor) {
        this.noProducto = noProducto;
        this.nuProducto = nuProducto;
        this.nombre = nombre;
        this.linea = linea;
        this.existencia = existencia;
        this.p_costo = p_costo;
        this.p_promedio = p_promedio;
        this.p_venta_mayor = p_venta_mayor;
        this.p_venta_menor = p_venta_menor;
    }

    public Producto(String nuProducto, String nombre, String linea, int existencia, double p_costo, double p_promedio, double p_venta_mayor, double p_venta_menor) {
        this.nuProducto = nuProducto;
        this.nombre = nombre;
        this.linea = linea;
        this.existencia = existencia;
        this.p_costo = p_costo;
        this.p_promedio = p_promedio;
        this.p_venta_mayor = p_venta_mayor;
        this.p_venta_menor = p_venta_menor;
    }

    public Producto(String nuProducto, String nombre, String linea) {
        this.nuProducto = nuProducto;
        this.nombre = nombre;
        this.linea = linea;
    }

    protected Producto(Parcel in) {
        noProducto = in.readInt();
        nuProducto = in.readString();
        nombre = in.readString();
        linea = in.readString();
        existencia = in.readInt();
        p_costo = in.readDouble();
        p_promedio = in.readDouble();
        p_venta_mayor = in.readDouble();
        p_venta_menor = in.readDouble();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public String getNuProducto() {
        return nuProducto;
    }
    public int getNoProducto() {
        return noProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLinea() {
        return linea;
    }

    public int getExistencia() {
        return existencia;
    }

    public double getP_costo() {
        return p_costo;
    }

    public double getP_promedio() {
        return p_promedio;
    }

    public double getP_venta_mayor() {
        return p_venta_mayor;
    }

    public double getP_venta_menor() {
        return p_venta_menor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noProducto);
        dest.writeString(nuProducto);
        dest.writeString(nombre);
        dest.writeString(linea);
        dest.writeInt(existencia);
        dest.writeDouble(p_costo);
        dest.writeDouble(p_promedio);
        dest.writeDouble(p_venta_mayor);
        dest.writeDouble(p_venta_menor);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "noProducto=" + noProducto +
                ", nuProducto='" + nuProducto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", linea='" + linea + '\'' +
                ", existencia=" + existencia +
                ", p_costo=" + p_costo +
                ", p_promedio=" + p_promedio +
                ", p_venta_mayor=" + p_venta_mayor +
                ", p_venta_menor=" + p_venta_menor +
                '}';
    }
}
