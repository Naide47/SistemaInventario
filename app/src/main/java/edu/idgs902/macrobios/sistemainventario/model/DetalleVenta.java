package edu.idgs902.macrobios.sistemainventario.model;

public class DetalleVenta {

    private int detalle_venta_no;
    private Producto producto;
    private int cantidad_producto;
    private double precio_venta;

    public DetalleVenta(int detalle_venta_no, Producto producto, int cantidad_producto, double precio_venta) {
        this.detalle_venta_no = detalle_venta_no;
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_venta = precio_venta;
    }

    public DetalleVenta(Producto producto, int cantidad_producto, double precio_venta) {
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_venta = precio_venta;
    }

    public int getDetalle_venta_no() {
        return detalle_venta_no;
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
}
