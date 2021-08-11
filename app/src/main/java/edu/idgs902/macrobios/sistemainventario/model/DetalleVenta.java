package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetalleVenta implements Parcelable {

    private int noDetalleVenta;
    private Producto producto;
    private int cantidad_producto;
    private double precio_venta;

    public DetalleVenta(int noDetalleVenta, Producto producto, int cantidad_producto, double precio_venta) {
        this.noDetalleVenta = noDetalleVenta;
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_venta = precio_venta;
    }

    public DetalleVenta(Producto producto, int cantidad_producto, double precio_venta) {
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_venta = precio_venta;
    }

    protected DetalleVenta(Parcel in) {
        noDetalleVenta = in.readInt();
        producto = in.readParcelable(Producto.class.getClassLoader());
        cantidad_producto = in.readInt();
        precio_venta = in.readDouble();
    }

    public static final Creator<DetalleVenta> CREATOR = new Creator<DetalleVenta>() {
        @Override
        public DetalleVenta createFromParcel(Parcel in) {
            return new DetalleVenta(in);
        }

        @Override
        public DetalleVenta[] newArray(int size) {
            return new DetalleVenta[size];
        }
    };

    public int getNoDetalleVenta() {
        return noDetalleVenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noDetalleVenta);
        dest.writeParcelable(producto, flags);
        dest.writeInt(cantidad_producto);
        dest.writeDouble(precio_venta);
    }
}
