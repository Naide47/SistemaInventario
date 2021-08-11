package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetalleCompra implements Parcelable {

    private int noDetalleCompra;
    private Producto producto;
    private int cantidad_producto;
    private double precio_venta;

    public DetalleCompra(int noDetalleCompra, Producto producto, int cantidad_producto, double precio_venta) {
        this.noDetalleCompra = noDetalleCompra;
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_venta = precio_venta;
    }

    public DetalleCompra(Producto producto, int cantidad_producto, double precio_venta) {
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_venta = precio_venta;
    }

    protected DetalleCompra(Parcel in) {
        noDetalleCompra = in.readInt();
        producto = in.readParcelable(Producto.class.getClassLoader());
        cantidad_producto = in.readInt();
        precio_venta = in.readDouble();
    }

    public static final Creator<DetalleCompra> CREATOR = new Creator<DetalleCompra>() {
        @Override
        public DetalleCompra createFromParcel(Parcel in) {
            return new DetalleCompra(in);
        }

        @Override
        public DetalleCompra[] newArray(int size) {
            return new DetalleCompra[size];
        }
    };

    public int getNoDetalleCompra() {
        return noDetalleCompra;
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
        dest.writeInt(noDetalleCompra);
        dest.writeParcelable(producto, flags);
        dest.writeInt(cantidad_producto);
        dest.writeDouble(precio_venta);
    }
}
