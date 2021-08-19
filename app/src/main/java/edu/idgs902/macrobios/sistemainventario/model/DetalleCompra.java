package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class DetalleCompra implements Parcelable {

    private int noDetalleCompra;
    private Producto producto;
    private int cantidad_producto;
    private double precio_compra;
    private boolean estado = true;

    public DetalleCompra(int noDetalleCompra, Producto producto, int cantidad_producto, double precio_compra) {
        this.noDetalleCompra = noDetalleCompra;
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_compra = precio_compra;
    }

    public DetalleCompra(Producto producto, int cantidad_producto, double precio_compra) {
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_compra = precio_compra;
    }

    protected DetalleCompra(Parcel in) {
        noDetalleCompra = in.readInt();
        producto = in.readParcelable(Producto.class.getClassLoader());
        cantidad_producto = in.readInt();
        precio_compra = in.readDouble();
        estado = in.readInt() != 0;
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

    public void setNoDetalleCompra(int noDetalleCompra) {
        this.noDetalleCompra = noDetalleCompra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(int cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }


    public double getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(double precio_compra) {
        this.precio_compra = precio_compra;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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
        dest.writeDouble(precio_compra);
        dest.writeInt(estado ? 1 : 0);
    }

    @Override
    public String toString() {
        return "DetalleCompra{" +
                "noDetalleCompra=" + noDetalleCompra +
                ", producto=" + producto +
                ", cantidad_producto=" + cantidad_producto +
                ", precio_compra=" + precio_compra +
                ", estado=" + estado +
                '}';
    }
}
